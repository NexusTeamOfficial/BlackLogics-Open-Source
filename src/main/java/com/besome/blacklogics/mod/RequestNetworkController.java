package mod;

import com.google.gson.Gson;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestNetworkController {
	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final String PUT = "PUT";
	public static final String DELETE = "DELETE";
	
	public static final int REQUEST_PARAM = 0;
	public static final int REQUEST_BODY = 1;
	
	private static final int SOCKET_TIMEOUT = 15000;
	private static final int READ_TIMEOUT = 25000;
	
	protected OkHttpClient client;
	
	private static RequestNetworkController mInstance;
	
	public static synchronized RequestNetworkController getInstance() {
		if (mInstance == null) {
			mInstance = new RequestNetworkController();
		}
		return mInstance;
	}
	
	private OkHttpClient getClient() {
		if (client == null) {
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			try {
				TrustManager[] trustAllCerts = new TrustManager[]{
					new X509TrustManager() {
						@Override
						public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
						@Override
						public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
						@Override
						public X509Certificate[] getAcceptedIssuers() {
							return new X509Certificate[]{};
						}
					}
				};
				
				SSLContext sslContext = SSLContext.getInstance("TLS");
				sslContext.init(null, trustAllCerts, new SecureRandom());
				SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
				builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
				builder.connectTimeout(SOCKET_TIMEOUT, TimeUnit.MILLISECONDS);
				builder.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);
				builder.writeTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);
				builder.hostnameVerifier((hostname, session) -> true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			client = builder.build();
		}
		return client;
	}
	
	public void execute(RequestNetwork requestNetwork, String method, String url, String tag, RequestNetwork.RequestListener requestListener) {
		buildRequest(requestNetwork, method, url, tag, requestListener, true);
	}
	
	public void executeSynchronized(RequestNetwork requestNetwork, String method, String url, String tag, RequestNetwork.RequestListener requestListener) {
		buildRequest(requestNetwork, method, url, tag, requestListener, false);
	}
	
	private void buildRequest(RequestNetwork requestNetwork, String method, String url, String tag, RequestNetwork.RequestListener requestListener, boolean async) {
		url = url.trim(); // Ensure no hidden characters
		if (url.isEmpty()) {
			throw new IllegalArgumentException("URL cannot be empty.");
		}
		
		Request.Builder reqBuilder = new Request.Builder();
		Headers headers = prepareHeaders(requestNetwork);
		RequestBody requestBody = prepareRequestBody(requestNetwork, method, url);
		
		reqBuilder.headers(headers).url(url).method(method, requestBody);
		
		Request request = reqBuilder.build();
		handleRequestExecution(requestNetwork, request, async, tag, requestListener);
	}
	
	private Headers prepareHeaders(RequestNetwork requestNetwork) {
		Headers.Builder headerBuilder = new Headers.Builder();
		HashMap<String, Object> headers = requestNetwork.getHeaders();
		if (headers.size() > 0) {
			for (HashMap.Entry<String, Object> entry : headers.entrySet()) {
				headerBuilder.add(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
		return headerBuilder.build();
	}
	
	private RequestBody prepareRequestBody(RequestNetwork requestNetwork, String method, String url) {
		if (requestNetwork.getRequestType() == REQUEST_PARAM) {
			if (method.equals(GET)) {
				return buildUrlWithParams(requestNetwork, url);
			} else {
				return buildFormBody(requestNetwork);
			}
		} else {
			return buildJsonRequestBody(requestNetwork);
		}
	}
	
	private RequestBody buildUrlWithParams(RequestNetwork requestNetwork, String url) {
		HttpUrl httpUrl = HttpUrl.parse(url);
		if (httpUrl == null) {
			throw new IllegalArgumentException("Invalid URL: " + url);
		}
		
		HttpUrl.Builder httpBuilder = httpUrl.newBuilder();
		HashMap<String, Object> params = requestNetwork.getParams();
		if (params.size() > 0) {
			for (HashMap.Entry<String, Object> param : params.entrySet()) {
				String key = param.getKey();
				String value = String.valueOf(param.getValue());
				
				if (key == null || value == null) {
					throw new IllegalArgumentException("Null key or value in parameters");
				}
				
				httpBuilder.addQueryParameter(key, value);
			}
		}
		
		return null; // GET requests don't use RequestBody
	}
	
	
	private RequestBody buildFormBody(RequestNetwork requestNetwork) {
		FormBody.Builder formBuilder = new FormBody.Builder();
		HashMap<String, Object> params = requestNetwork.getParams();
		for (HashMap.Entry<String, Object> param : params.entrySet()) {
			formBuilder.add(param.getKey(), String.valueOf(param.getValue()));
		}
		return formBuilder.build();
	}
	
	private RequestBody buildJsonRequestBody(RequestNetwork requestNetwork) {
		String json = new Gson().toJson(requestNetwork.getParams());
		return RequestBody.create(MediaType.parse("application/json"), json);
	}
	
	private void handleRequestExecution(final RequestNetwork requestNetwork, Request request, boolean async, final String tag, final RequestNetwork.RequestListener requestListener) {
		Consumer<IOException> onFailureHandler = e -> requestNetwork.getActivity().runOnUiThread(() -> requestListener.onErrorResponse(tag, e.getMessage()));
		Consumer<Response> onResponseHandler = response -> {
			try {
				String responseBody = response.body().string().trim();
				requestNetwork.getActivity().runOnUiThread(() -> {
					HashMap<String, Object> responseHeaders = new HashMap<>();
					Headers headers = response.headers();
					for (String name : headers.names()) {
						responseHeaders.put(name, headers.get(name));
					}
					requestListener.onResponse(tag, responseBody, responseHeaders);
				});
			} catch (Exception e) {
				requestListener.onErrorResponse(tag, e.getMessage());
			}
		};
		
		Call call = getClient().newCall(request);
		if (async) {
			call.enqueue(new Callback() {
				@Override
				public void onFailure(Call call, IOException e) {
					onFailureHandler.accept(e);
				}
				
				@Override
				public void onResponse(Call call, Response response) {
					onResponseHandler.accept(response);
				}
			});
		} else {
			try {
				onResponseHandler.accept(call.execute());
			} catch (IOException e) {
				e.printStackTrace(); // or handle the error
                onFailureHandler.accept(e);
			}
			
		}
	}
}
