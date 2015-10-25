package com.example.skyle.promise_1;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    threadLogin t = new threadLogin("", "");
    EditText editTextAccount, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editTextAccount = (EditText) this.findViewById(R.id.editTextAccount);
        editTextPassword = (EditText) this.findViewById(R.id.editTextPassword);


        //


    }

    /*
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
    }*/

    public class threadLogin extends Thread {

        private String account, password;

        public threadLogin(String account, String password) {
            this.account = account;
            this.password = password;
        }

        @Override
        public void run() {
            super.run();
            final Promise p = new Promise();
            //p.Request("{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\":{\"user\":{\"domain\":{\"name\":\"local\"},\"name\":\"skyle0115@gmail.com\",\"password\":\"H07-jhsa\"}}},\"scope\":{\"project\":{\"domain\":{\"name\":\"local\"},\"name\":\"skyle0115@gmail.com\"}}}}");
            String s = ("{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\":{\"user\":{\"domain\":{\"name\":\"local\"},\"name\":\"" + account + "\",\"password\":\"" + password + "\"}}},\"scope\":{\"project\":{\"domain\":{\"name\":\"local\"},\"name\":\"" + account + "\"}}}}");
            try {
                p.Request(s.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (p.getResponseCode() == 201) {
                final String token = p.getToken();
                p.Disconnect();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Intent it = new Intent(MainActivity.this, ExplorerActivity.class);
                        it.putExtra("Token", token);
                        MainActivity.this.startActivity(it);
                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
                p.Disconnect();
            }

            //Log.i("tag", p.Response());
            //p.DownloadFile("/sdcard/json.txt");
            //String token = p.getToken();

            //Log.i("tag", token);
            //Log.i("code", "" + p.getResponseCode());
        }
    }

    public void mainLogin(View v) {
        switch (v.getId()) {
            case R.id.buttonLogin:
                if (t.isAlive()) {
                    t.interrupt();
                } else {
                    t = new threadLogin(editTextAccount.getText().toString(), editTextPassword.getText().toString());
                    t.start();
                }
        }
    }
}
