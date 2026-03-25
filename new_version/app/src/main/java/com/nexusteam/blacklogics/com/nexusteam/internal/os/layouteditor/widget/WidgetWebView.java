package com.nexusteam.internal.os.layouteditor.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;

public class WidgetWebView extends Widget {
    private WebView mWebView;
    private TextView mPlaceholderText;
    private FrameLayout container;
    private String url = "";

    public WidgetWebView(Context context) {
        super(context);

        container = new FrameLayout(context);
        container.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));


        mWebView = new WebView(context);
        mWebView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));


        mPlaceholderText = new TextView(context);
        mPlaceholderText.setText("WebView");
        mPlaceholderText.setTextSize(14);
        mPlaceholderText.setTextColor(Color.GRAY);
        mPlaceholderText.setGravity(Gravity.CENTER);


        FrameLayout.LayoutParams textParams = new FrameLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT,
            Gravity.CENTER
        );
        mPlaceholderText.setLayoutParams(textParams);

        container.addView(mWebView);
        container.addView(mPlaceholderText);
        addView(this.container, new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT));
    }


    public void setLayoutParams(LayoutParams layoutParams) {
        super.setLayoutParams(layoutParams);
        container.setLayoutParams(layoutParams);
    }

    public static String newWidgetId() {
        int i = 1;
        while (WidgetUtil.isWidgetIdExist("webview" + i)) {
            i++;
        }
        return "webview" + i;
    }


    public void loadUrl(String url) {
        mWebView.loadUrl(url);
        mPlaceholderText.setVisibility(GONE); // Hide Placeholder when URL loads
    }


    public void enableJavaScript(boolean enable) {
        mWebView.getSettings().setJavaScriptEnabled(enable);
    }


    public void setWebViewClient(WebViewClient client) {
        mWebView.setWebViewClient(client);
    }


    public void setWebChromeClient(WebChromeClient client) {
        mWebView.setWebChromeClient(client);
    }


    public void reloadWebView() {
        mWebView.reload();
    }


    public void clearCache(boolean includeDiskFiles) {
        mWebView.clearCache(includeDiskFiles);
        mWebView.clearHistory();
    }


    public void goBack() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        }
    }


    public void goForward() {
        if (mWebView.canGoForward()) {
            mWebView.goForward();
        }
    }


    public void stopLoading() {
        mWebView.stopLoading();
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        if (url != null && !url.isEmpty()) {
            loadUrl(url);
        }
    }
    
}
