
package com.nexusteam.blacklogics.model.network;

import java.util.Map;

public class HttpResponseModel {
    private String tag;
    private String body;
    private int statusCode;
    private String statusMessage;
    private Map<String, String> headers;
    private boolean isSuccess;
    private String errorMessage;
    private long responseTime;

    public HttpResponseModel() {
    }

    public HttpResponseModel(String tag, String body, Map<String, String> headers) {
        this.tag = tag;
        this.body = body;
        this.headers = headers;
        this.isSuccess = true;
    }

    public HttpResponseModel(String tag, String errorMessage) {
        this.tag = tag;
        this.errorMessage = errorMessage;
        this.isSuccess = false;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public String getHeader(String key) {
        return headers != null ? headers.get(key) : null;
    }
}