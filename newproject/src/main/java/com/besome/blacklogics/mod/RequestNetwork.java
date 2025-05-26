package mod;

import android.app.Activity;

import java.util.HashMap;

public class RequestNetwork {
    private HashMap<String, Object> params = new HashMap<>();
    private HashMap<String, Object> headers = new HashMap<>();

    private final Activity activity;

    private int requestType = 0; // Default to REQUEST_PARAM (0)

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

    /**
     * Starts an asynchronous network request.
     *
     * @param method          The HTTP method (GET, POST, etc.).
     * @param url             The URL to send the request to.
     * @param tag             A tag to identify the request.
     * @param requestListener The listener for request callbacks.
     */
    public void startRequestNetwork(String method, String url, String tag, RequestListener requestListener) {
        validateRequestParams(method, url, requestListener);
        RequestNetworkController.getInstance().execute(this, method, url, tag, requestListener);
    }

    /**
     * Starts a synchronous network request.
     *
     * @param method          The HTTP method (GET, POST, etc.).
     * @param url             The URL to send the request to.
     * @param tag             A tag to identify the request.
     * @param requestListener The listener for request callbacks.
     */
    public void startRequestNetworkSynchronized(String method, String url, String tag, RequestListener requestListener) {
        validateRequestParams(method, url, requestListener);
        RequestNetworkController.getInstance().executeSynchronized(this, method, url, tag, requestListener);
    }

    /**
     * Validates the request parameters before execution.
     *
     * @param method          The HTTP method.
     * @param url             The request URL.
     * @param requestListener The request listener.
     */
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
        /**
         * Called when the request is successful.
         *
         * @param tag             The request tag.
         * @param response        The response body as a string.
         * @param responseHeaders A map of response headers.
         */
        void onResponse(String tag, String response, HashMap<String, Object> responseHeaders);

        /**
         * Called when there is an error with the request.
         *
         * @param tag     The request tag.
         * @param message The error message.
         */
        void onErrorResponse(String tag, String message);
    }
}
