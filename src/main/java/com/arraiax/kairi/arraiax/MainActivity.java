package com.arraiax.kairi.arraiax;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btWebUsuario, btWebFilho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btWebUsuario = (Button) findViewById(R.id.btWebUsuario);
        btWebUsuario.setOnClickListener(this);
        btWebFilho = (Button) findViewById(R.id.btWebFilho);
        btWebFilho.setOnClickListener(this);
        //
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btWebUsuario) {
            startActivity(new Intent(this, WebUsuarioActivity.class));
        } else if (id == R.id.btWebFilho) {
            startActivity(new Intent(this, WebFilhoActivity.class));
        }
    }
}

