package com.wugang.src.blog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wugang.src.blog.utils.ParserUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 深圳州富科技有限公司
 * Created by lwg on 2016/4/6.
 */
public class WebViewDetailActivtiy extends AppCompatActivity implements ParserUtils.OnParserCompletedListener<String> {
    public static final String EXTRA_DATA = "data";
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(webView = new WebView(getApplicationContext()));
        String url = getIntent().getStringExtra(EXTRA_DATA);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
//        webView.getSettings().setDefaultTextEncodingName("GBK");
        webView.loadUrl(url);
        // ParserUtils.parseDetail(new CSDNParser(), url, this);
    }

    @Override
    public void onCompleted(String s) {
        try {
            webView.loadData(URLEncoder.encode(s, "GBK"), "text/html", "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onError(Throwable t) {

    }
}
