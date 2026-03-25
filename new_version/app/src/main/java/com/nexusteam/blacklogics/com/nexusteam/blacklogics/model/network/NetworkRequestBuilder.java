
package com.nexusteam.blacklogics.utils.network;

import com.nexusteam.blacklogics.model.network.HttpRequestModel;

import java.util.Map;

public class NetworkRequestBuilder {
    private final HttpRequestModel request;

    public NetworkRequestBuilder() {
        request = new HttpRequestModel();
    }

    public NetworkRequestBuilder url(String url) {
        request.setUrl(url);
        return this;
    }

    public NetworkRequestBuilder method(HttpRequestModel.HttpMethod method) {
        request.setMethod(method);
        return this;
    }

    public NetworkRequestBuilder get() {
        return method(HttpRequestModel.HttpMethod.GET);
    }

    public NetworkRequestBuilder post() {
        return method(HttpRequestModel.HttpMethod.POST);
    }

    public NetworkRequestBuilder put() {
        return method(HttpRequestModel.HttpMethod.PUT);
    }

    public NetworkRequestBuilder delete() {
        return method(HttpRequestModel.HttpMethod.DELETE);
    }

    public NetworkRequestBuilder requestType(HttpRequestModel.RequestType type) {
        request.setRequestType(type);
        return this;
    }

    public NetworkRequestBuilder jsonRequest() {
        return requestType(HttpRequestModel.RequestType.JSON_BODY);
    }

    public NetworkRequestBuilder formRequest() {
        return requestType(HttpRequestModel.RequestType.FORM_DATA);
    }

    public NetworkRequestBuilder addHeader(String key, String value) {
        request.addHeader(key, value);
        return this;
    }

    public NetworkRequestBuilder addHeaders(Map<String, String> headers) {
        if (headers != null) {
            request.getHeaders().putAll(headers);
        }
        return this;
    }

    public NetworkRequestBuilder addParameter(String key, Object value) {
        request.addParameter(key, value);
        return this;
    }

    public NetworkRequestBuilder addParameters(Map<String, Object> parameters) {
        if (parameters != null) {
            request.getParameters().putAll(parameters);
        }
        return this;
    }

    public NetworkRequestBuilder addQueryParam(String key, String value) {
        request.addQueryParam(key, value);
        return this;
    }

    public NetworkRequestBuilder addQueryParams(Map<String, String> queryParams) {
        if (queryParams != null) {
            request.getQueryParams().putAll(queryParams);
        }
        return this;
    }

    public NetworkRequestBuilder tag(String tag) {
        request.setTag(tag);
        return this;
    }

    public NetworkRequestBuilder jsonBody(String json) {
        request.setJsonBody(json);
        return this;
    }

    public NetworkRequestBuilder timeout(int timeout) {
        request.setTimeout(timeout);
        return this;
    }

    public NetworkRequestBuilder readTimeout(int readTimeout) {
        request.setReadTimeout(readTimeout);
        return this;
    }

    public HttpRequestModel build() {
        validateRequest();
        return request;
    }

    private void validateRequest() {
        if (request.getUrl() == null || request.getUrl().trim().isEmpty()) {
            throw new IllegalArgumentException("URL is required");
        }

        if (request.getMethod() == null) {
            throw new IllegalArgumentException("HTTP method is required");
        }
    }
}