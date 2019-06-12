package com.arraiax.kairi.arraiax;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebFilhoActivity extends AppCompatActivity {

    private WebView wvPrincipal;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_filho);
        context = this;
        wvPrincipal = (WebView) findViewById(R.id.wvFilho);
        //wvPrincipal = new WebView(this);
        //setContentView(wvPrincipal);
        wvPrincipal.loadUrl("file:android_asset/index3.html");
        wvPrincipal.clearCache(true);
        WebSettings webSettings = wvPrincipal.getSettings();
        //Habilitando o JavaScript
        webSettings.setJavaScriptEnabled(true);
        //Chama Interface JavaScript
        wvPrincipal.addJavascriptInterface(new WebAppInterface(this), "Android");
        wvPrincipal.setWebChromeClient(new WebChromeClient());
        wvPrincipal.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Toast.makeText(WebFilhoActivity.this, url, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //Toast.makeText(MainActivity.this, url, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                StringBuffer sb = new StringBuffer();
                sb.append("errorCode = " + errorCode);
                sb.append("\ndescription = " + description);
                sb.append("\nurl = " + failingUrl);
                Toast.makeText(WebFilhoActivity.this, sb.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wvPrincipal.canGoBack()) {
            wvPrincipal.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static String getImei(Context context) {
        String imei = "";
        TelephonyManager tm = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            imei = tm.getImei();
            if (imei == null || imei == "") {
                imei = tm.getLine1Number();
                if (imei == null || imei == "") {
                    imei = tm.getSimSerialNumber();
                    if (imei == null || imei == "") {
                        imei = tm.getSimSerialNumber();
                        if (imei == null || imei == "") {
                            imei = tm.getMeid();
                        }
                    }
                }
            }
        }
        return imei;
    }

}
