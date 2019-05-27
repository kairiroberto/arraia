package com.arraiax.kairi.arraiax;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView wvPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wvPrincipal = (WebView) findViewById(R.id.wvPrincipal);
        //wvPrincipal.loadUrl("http://www.google.com");
        wvPrincipal.loadUrl("file:android_asset/index.html");
        WebSettings webSettings = wvPrincipal.getSettings();
        //Habilitando o JavaScript
        webSettings.setJavaScriptEnabled(true);
        wvPrincipal.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Toast.makeText(MainActivity.this, url, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Toast.makeText(MainActivity.this, url, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }
        });
    }
}
