package com.ehabahmed.studentcertificate;


import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class Show_Course extends AppCompatActivity {
WebView webView;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__course);
        progressBar=findViewById(R.id.progress);

        String link=getIntent().getExtras().getString("link");
        webView=findViewById(R.id.wv_course);
        webView.loadUrl(link);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        webView.getSettings().supportZoom();
        webView.requestFocus();




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        webView.destroy();
        webView = null;

    }
}
