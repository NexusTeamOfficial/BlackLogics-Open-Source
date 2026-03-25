package com.nexusteam.internal.lib.utils;

import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GoogleApiUtil {
    
    /* renamed from: a  reason: collision with root package name */
    public static final MediaType f1607a = MediaType.parse("application/json; charset=utf-8");
    
    public String a(String str) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();
            Response execute = okHttpClient.newCall(new Request.Builder().url("https://www.googleapis.com/urlshortener/v1/url?key=AIzaSyBv696FDTPStkyYTGrW26Hb209wM2d6h_Q").post(RequestBody.create(f1607a, gson.toJson(new RequestModel(str)))).build()).execute();
            if (execute.isSuccessful()) {
                return ((ResponseModel) gson.fromJson(execute.body().string(), ResponseModel.class)).getId();
            }
        } catch(Exception ex) {
            return ex.toString();
        }
        return "an exception here.";
    }
    
    public class RequestModel {
        private String longUrl;
        
        public RequestModel() {
        }
        
        public RequestModel(String str) {
            this.longUrl = str;
        }
        
        public String getLongUrl() {
            return this.longUrl;
        }
    }
    
    public class ResponseModel {
        private String id;
        private String kind;
        private String longUrl;
        
        public ResponseModel() {
        }
        
        public String getKind() {
            return this.kind;
        }
        
        public String getId() {
            return this.id;
        }
        
        public String getLongUrl() {
            return this.longUrl;
        }
    }
}
