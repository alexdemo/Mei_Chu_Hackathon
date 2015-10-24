package com.example.skyle.promise_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    HttpURLConnection httpcon = (HttpURLConnection) ((new URL("http://125.227.28.235/keystone/v3/auth/tokens").openConnection()));
                    httpcon.setDoOutput(true);
                    httpcon.setRequestProperty("Content-Type", "application/json");
                    httpcon.setRequestProperty("User-Agent", "Android.ED:93:28:0F:83:A4");
                    httpcon.setRequestMethod("POST");
                    httpcon.connect();

                    byte[] outputBytes = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\":{\"user\":{\"domain\":{\"name\":\"local\"},\"name\":\"skyle0115@gmail.com\",\"password\":\"H07-jhsa\"}}},\"scope\":{\"project\":{\"domain\":{\"name\":\"local\"},\"name\":\"skyle0115@gmail.com\"}}}}".getBytes("UTF-8");
                    OutputStream os = httpcon.getOutputStream();
                    os.write(outputBytes);
                    os.close();

                    InputStream stream = httpcon.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    InputStreamReader isr = new InputStreamReader(stream, "UTF8");
                    Reader in = new BufferedReader(isr);
                    int ch;
                    while ((ch = in.read()) > -1) {
                        buffer.append((char) ch);
                    }
                    in.close();

                    httpcon.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            this.startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void mainLogin(View v) {
        switch (v.getId()) {
            case R.id.buttonLogin:
                if (true) //TODO login certification
                    this.startActivity(new Intent(this, ExplorerActivity.class));

        }
    }
}
