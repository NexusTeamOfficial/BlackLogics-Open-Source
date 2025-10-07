package com.besome.blacklogics.util;

public class MainActivityGenerator {

    private String packageName = "com.besome.blacklogics.webconverter";
    private String layoutName = "main";
    private String url = "https://example.com";

    public MainActivityGenerator() {
        // Default constructor
    }

    public MainActivityGenerator setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public MainActivityGenerator setLayoutName(String layoutName) {
        this.layoutName = layoutName;
        return this;
    }

    public MainActivityGenerator setURL(String url) {
        this.url = url;
        return this;
    }

    public String generateMainActivity() {
        StringBuilder sb = new StringBuilder();

        sb.append("package ").append(packageName).append(";\n\n");
        sb.append("import android.app.Activity;\n");
        sb.append("import android.os.Bundle;\n");
        sb.append("import android.webkit.WebSettings;\n");
        sb.append("import android.webkit.WebView;\n");
        sb.append("import android.webkit.WebViewClient;\n\n");

        sb.append("public class MainActivity extends Activity {\n\n");
        sb.append("    private WebView webView;\n\n");

        sb.append("    @Override\n");
        sb.append("    protected void onCreate(Bundle savedInstanceState) {\n");
        sb.append("        super.onCreate(savedInstanceState);\n");
        sb.append("        setContentView(R.layout.").append(layoutName).append(");\n");
        sb.append("        initialize();\n");
        sb.append("    }\n\n");

        sb.append("    private void initialize() {\n");
        sb.append("        webView = findViewById(R.id.webView);\n");
        sb.append("        WebSettings ws = webView.getSettings();\n");
        sb.append("        ws.setJavaScriptEnabled(true);\n");
        sb.append("        ws.setDomStorageEnabled(true);\n");
        sb.append("        ws.setLoadWithOverviewMode(true);\n");
        sb.append("        ws.setUseWideViewPort(true);\n");
        sb.append("        ws.setBuiltInZoomControls(true);\n");
        sb.append("        ws.setDisplayZoomControls(false);\n");
        sb.append("        ws.setSupportZoom(true);\n");
        sb.append("        ws.setCacheMode(WebSettings.LOAD_DEFAULT);\n\n");

        sb.append("        webView.setWebViewClient(new WebViewClient() {\n");
        sb.append("            @Override\n");
        sb.append("            public boolean shouldOverrideUrlLoading(WebView view, String url) {\n");
        sb.append("                view.loadUrl(url);\n");
        sb.append("                return true;\n");
        sb.append("            }\n");
        sb.append("        });\n\n");

        sb.append("        webView.loadUrl(\"").append(url).append("\");\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    public void onBackPressed() {\n");
        sb.append("        if (webView.canGoBack()) {\n");
        sb.append("            webView.goBack();\n");
        sb.append("        } else {\n");
        sb.append("            super.onBackPressed();\n");
        sb.append("        }\n");
        sb.append("    }\n\n");

        sb.append("}\n");

        return sb.toString();
    }
}
