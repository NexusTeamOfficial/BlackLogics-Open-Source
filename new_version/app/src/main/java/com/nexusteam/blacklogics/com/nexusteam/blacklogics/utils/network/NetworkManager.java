
package com.nexusteam.blacklogics.utils.network;

import android.content.Context;

import com.nexusteam.blacklogics.model.network.HttpRequestModel;
import com.nexusteam.blacklogics.model.network.HttpResponseModel;
import com.nexusteam.blacklogics.model.network.NetworkConfig;
import com.nexusteam.blacklogics.utils.BlackLogicsUtil;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
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

public class NetworkManager {

    private static NetworkManager instance;
    private OkHttpClient httpClient;
    private NetworkConfig config;
    private Map<String, Call> activeCalls;

    private NetworkManager() {
        config = new NetworkConfig();
        activeCalls = new HashMap<>();
    }

    public static synchronized NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    public void setConfig(NetworkConfig config) {
        this.config = config;
        initializeHttpClient();
    }

    public NetworkConfig getConfig() {
        return config;
    }

    private synchronized void initializeHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(config.getConnectTimeout(), TimeUnit.MILLISECONDS)
                .readTimeout(config.getReadTimeout(), TimeUnit.MILLISECONDS)
                .writeTimeout(config.getWriteTimeout(), TimeUnit.MILLISECONDS)
                .followRedirects(config.isFollowRedirects())
                .followSslRedirects(config.isFollowSslRedirects())
                .retryOnConnectionFailure(config.isRetryOnConnectionFailure());

        if (config.isDisableSslVerification()) {
            configureUnsafeSsl(builder);
        } else if (config.getSslSocketFactory() != null && config.getTrustManager() != null) {
            builder.sslSocketFactory(config.getSslSocketFactory(), config.getTrustManager());
        }

        httpClient = builder.build();
    }

    private void configureUnsafeSsl(OkHttpClient.Builder builder) {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }

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
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, javax.net.ssl.SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private OkHttpClient getClient() {
        if (httpClient == null) {
            initializeHttpClient();
        }
        return httpClient;
    }

    public void sendRequest(final HttpRequestModel request, final NetworkCallback callback) {
        if (request == null || callback == null) {
            throw new IllegalArgumentException("Request and callback cannot be null");
        }

        try {
            Request okHttpRequest = buildOkHttpRequest(request);
            final Call call = getClient().newCall(okHttpRequest);
            
            if (request.getTag() != null) {
                activeCalls.put(request.getTag(), call);
            }

            final long startTime = System.currentTimeMillis();
            
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    removeActiveCall(request.getTag());
                    HttpResponseModel response = new HttpResponseModel(
                        request.getTag(), 
                        "Network error: " + e.getMessage()
                    );
                    response.setResponseTime(System.currentTimeMillis() - startTime);
                    callback.onError(response);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    removeActiveCall(request.getTag());
                    
                    HttpResponseModel httpResponse = new HttpResponseModel();
                    httpResponse.setTag(request.getTag());
                    httpResponse.setStatusCode(response.code());
                    httpResponse.setStatusMessage(response.message());
                    httpResponse.setSuccess(response.isSuccessful());
                    httpResponse.setResponseTime(System.currentTimeMillis() - startTime);

                    if (response.isSuccessful()) {
                        try {
                            String responseBody = response.body().string();
                            httpResponse.setBody(responseBody);
                            
                            Map<String, String> headers = new HashMap<>();
                            Headers responseHeaders = response.headers();
                            for (String name : responseHeaders.names()) {
                                headers.put(name, responseHeaders.get(name));
                            }
                            httpResponse.setHeaders(headers);
                            
                            callback.onSuccess(httpResponse);
                        } catch (Exception e) {
                            httpResponse.setErrorMessage("Failed to read response: " + e.getMessage());
                            httpResponse.setSuccess(false);
                            callback.onError(httpResponse);
                        }
                    } else {
                        httpResponse.setErrorMessage("HTTP error: " + response.code() + " - " + response.message());
                        callback.onError(httpResponse);
                    }
                }
            });
        } catch (Exception e) {
            HttpResponseModel errorResponse = new HttpResponseModel(
                request.getTag(),
                "Request preparation failed: " + e.getMessage()
            );
            callback.onError(errorResponse);
        }
    }

    public HttpResponseModel sendRequestSync(HttpRequestModel request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        HttpResponseModel httpResponse = new HttpResponseModel();
        httpResponse.setTag(request.getTag());

        long startTime = System.currentTimeMillis();

        try {
            Request okHttpRequest = buildOkHttpRequest(request);
            Response response = getClient().newCall(okHttpRequest).execute();

            httpResponse.setStatusCode(response.code());
            httpResponse.setStatusMessage(response.message());
            httpResponse.setSuccess(response.isSuccessful());
            httpResponse.setResponseTime(System.currentTimeMillis() - startTime);

            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                httpResponse.setBody(responseBody);

                Map<String, String> headers = new HashMap<>();
                Headers responseHeaders = response.headers();
                for (String name : responseHeaders.names()) {
                    headers.put(name, responseHeaders.get(name));
                }
                httpResponse.setHeaders(headers);
            } else {
                httpResponse.setErrorMessage("HTTP error: " + response.code() + " - " + response.message());
            }

        } catch (Exception e) {
            httpResponse.setErrorMessage("Request failed: " + e.getMessage());
            httpResponse.setSuccess(false);
        }

        return httpResponse;
    }

    private Request buildOkHttpRequest(HttpRequestModel request) {
        Request.Builder builder = new Request.Builder();


        String url = buildUrlWithQueryParams(request);
        builder.url(url);


        for (Map.Entry<String, String> header : request.getHeaders().entrySet()) {
            builder.addHeader(header.getKey(), header.getValue());
        }


        RequestBody body = buildRequestBody(request);
        if (body != null) {
            builder.method(request.getMethod().getValue(), body);
        } else {
            builder.method(request.getMethod().getValue(), null);
        }

        return builder.build();
    }

    private String buildUrlWithQueryParams(HttpRequestModel request) {
        HttpUrl httpUrl = HttpUrl.parse(request.getUrl());
        if (httpUrl == null) {
            throw new IllegalArgumentException("Invalid URL: " + request.getUrl());
        }

        HttpUrl.Builder urlBuilder = httpUrl.newBuilder();
        for (Map.Entry<String, String> param : request.getQueryParams().entrySet()) {
            urlBuilder.addQueryParameter(param.getKey(), param.getValue());
        }

        return urlBuilder.build().toString();
    }

    private RequestBody buildRequestBody(HttpRequestModel request) {
        switch (request.getRequestType()) {
            case PARAMETER_BASED:
                if (request.getMethod() == HttpRequestModel.HttpMethod.GET) {
                    return null; // GET requests don't have body
                } else {
                    return buildFormBody(request);
                }

            case JSON_BODY:
                return buildJsonBody(request);

            case FORM_DATA:
                return buildFormBody(request);

            case MULTIPART:
                return buildMultipartBody(request);

            default:
                return null;
        }
    }

    private RequestBody buildFormBody(HttpRequestModel request) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        for (Map.Entry<String, Object> param : request.getParameters().entrySet()) {
            formBuilder.add(param.getKey(), param.getValue().toString());
        }
        return formBuilder.build();
    }

    private RequestBody buildJsonBody(HttpRequestModel request) {
        if (request.getJsonBody() != null && !request.getJsonBody().isEmpty()) {
            return RequestBody.create(
                MediaType.parse("application/json"),
                request.getJsonBody()
            );
        }


        com.google.gson.Gson gson = new com.google.gson.Gson();
        String json = gson.toJson(request.getParameters());
        return RequestBody.create(
            MediaType.parse("application/json"),
            json
        );
    }

    private RequestBody buildMultipartBody(HttpRequestModel request) {


        return buildFormBody(request);
    }

    public void cancelRequest(String tag) {
        Call call = activeCalls.get(tag);
        if (call != null) {
            call.cancel();
            activeCalls.remove(tag);
        }
    }

    public void cancelAllRequests() {
        for (Call call : activeCalls.values()) {
            call.cancel();
        }
        activeCalls.clear();
    }

    private void removeActiveCall(String tag) {
        if (tag != null) {
            activeCalls.remove(tag);
        }
    }

    public interface NetworkCallback {
        void onSuccess(HttpResponseModel response);
        void onError(HttpResponseModel error);
    }
}