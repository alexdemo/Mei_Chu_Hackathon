package com.example.skyle.promise_1;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Skyle on 2015/10/24.
 */
public class BrowserActivity extends AppCompatActivity {

    ProgressBar progressBarBrowser;
    ImageView imageViewBrowser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imageViewBrowser = (ImageView) this.findViewById(R.id.imageViewBrowser);
        imageViewBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getSupportActionBar().isShowing())
                    getSupportActionBar().hide();
                else
                    getSupportActionBar().show();
            }
        });

        progressBarBrowser = (ProgressBar) BrowserActivity.this.findViewById(R.id.progressBarBrowser);

        Intent it = getIntent();
        final String token = it.getStringExtra("Token");
        final String path = it.getStringExtra("Path");

        new File("/sdcard/promise" + path.substring(0, path.lastIndexOf("/") + 1)).mkdirs();

        new Thread() {
            @Override
            public void run() {
                super.run();
                final Promise p = new Promise(path, token, Promise.URL_FILES);

                p.DownloadFile("/sdcard/promise" + path);

                runOnUiThread(new Runnable() {
                    public void run() {
                        imageViewBrowser.setImageBitmap(BitmapFactory.decodeFile("/sdcard/promise" + path));
                        progressBarBrowser.setVisibility(View.GONE);
                    }
                });
                //Log.i("tag", "" + p.getResponseCode());
                //Log.i("tag", path);
                //Log.i("tag", p.getHeaderField("Content-Length"));
                p.Disconnect();
            }
        }.start();
    }
}
