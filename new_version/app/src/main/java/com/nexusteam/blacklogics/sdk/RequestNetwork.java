package mod;

import android.app.Activity;
import java.util.HashMap;

public class RequestNetwork {
    private HashMap<String, Object> params = new HashMap<>();
    private HashMap<String, Object> headers = new HashMap<>();

    private final Activity activity;

    private int requestType = 0;

    public RequestNetwork(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("Activity cannot be null");
        }
        this.activity = activity;
    }

    public void setHeaders(HashMap<String, Object> headers) {
        if (headers == null) {
            throw new IllegalArgumentException("Headers cannot be null");
        }
        this.headers = headers;
    }

    public void setParams(HashMap<String, Object> params, int requestType) {
        if (params == null) {
            throw new IllegalArgumentException("Params cannot be null");
        }
        this.params = params;
        this.requestType = requestType;
    }

    public HashMap<String, Object> getParams() {
        return params;
    }

    public HashMap<String, Object> getHeaders() {
        return headers;
    }

    public Activity getActivity() {
        return activity;
    }

    public int getRequestType() {
        return requestType;
    }

    public void startRequestNetwork(
        String method, String url, String tag, RequestListener requestListener) {
        validateRequestParams(method, url, requestListener);
        RequestNetworkController.getInstance().execute(this, method, url, tag, requestListener);
    }

    public void startRequestNetworkSynchronized(
        String method, String url, String tag, RequestListener requestListener) {
        validateRequestParams(method, url, requestListener);
        RequestNetworkController.getInstance().executeSynchronized(
            this, method, url, tag, requestListener);
    }

    private void validateRequestParams(String method, String url, RequestListener requestListener) {
        if (method == null || method.isEmpty()) {
            throw new IllegalArgumentException("HTTP method cannot be null or empty");
        }
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }
        if (requestListener == null) {
            throw new IllegalArgumentException("RequestListener cannot be null");
        }
    }

    public interface RequestListener {
        void onResponse(String tag, String response, HashMap<String, Object> responseHeaders);

        void onErrorResponse(String tag, String message);
    }
}
