package com.example.skyle.promise_1;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Skyle on 2015/10/25.
 */
public class Promise {
    private HttpURLConnection httpcon;
    private int totalSize, downloadedSize;

    public static final String URL_METADATA = "metadata", URL_FILES = "files", URL_UPLOAD = "files";

    public int Percentage() {
        return downloadedSize / totalSize;
    }

    public byte[] File2byteArray(String path){
        File file = new File(path);
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bytes;
    }

    public String getToken() {
        return httpcon.getHeaderField("X-Subject-Token");
    }

    public String getHeaderField(String s) {
        return httpcon.getHeaderField(s);
    }

    public int getResponseCode() {
        try {
            return httpcon.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void DownloadFile(String dest) {
        try {
            totalSize = 100;
            downloadedSize = 0;
            FileOutputStream fileOutput = new FileOutputStream(new File(dest));
            InputStream stream = httpcon.getInputStream();

            totalSize = httpcon.getContentLength();
            downloadedSize = 0;
            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            while ((bufferLength = stream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                //Log.i("status", "" + downloadedSize + " / " + totalSize);
            }
            stream.close();
            fileOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String Response() {
        try {
            InputStream stream = httpcon.getInputStream();
            StringBuffer buffer = new StringBuffer();
            InputStreamReader isr = new InputStreamReader(stream, "UTF8");
            Reader in = new BufferedReader(isr);
            int ch;
            while ((ch = in.read()) > -1) {
                buffer.append((char) ch);
            }
            in.close();
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void Request(byte[] outputBytes) {
        try {
            //byte[] outputBytes = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\":{\"user\":{\"domain\":{\"name\":\"local\"},\"name\":\"skyle0115@gmail.com\",\"password\":\"H07-jhsa\"}}},\"scope\":{\"project\":{\"domain\":{\"name\":\"local\"},\"name\":\"skyle0115@gmail.com\"}}}}".getBytes("UTF-8");
            OutputStream os = httpcon.getOutputStream();
            os.write(outputBytes);
            os.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Promise(String url, String token){
        try {
            httpcon = (HttpURLConnection) ((new URL("http://125.227.28.235/fileop/v1/files" + url).openConnection()));
            httpcon.setRequestProperty("User-Agent", "Android.ED:93:28:0F:83:A4");
            httpcon.setRequestProperty("X-Auth-Token", token);
            httpcon.setRequestProperty("X-Meta-FC-Compress", "false");
            httpcon.setRequestProperty("X-Meta-FC-Encrypt", "false");
            httpcon.setRequestMethod("POST");
            httpcon.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Promise(String url, String token, String type) {
        try {
            httpcon = (HttpURLConnection) ((new URL("http://125.227.28.235/fileop/v1/" + type + url).openConnection()));
            httpcon.setRequestProperty("User-Agent", "Android.ED:93:28:0F:83:A4");
            httpcon.setRequestProperty("X-Auth-Token", token);
            httpcon.setRequestMethod("GET");
            httpcon.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Promise() {
        try {
            httpcon = (HttpURLConnection) ((new URL("http://125.227.28.235/keystone/v3/auth/tokens").openConnection()));
            httpcon.setRequestProperty("User-Agent", "Android.ED:93:28:0F:83:A4");
            httpcon.setDoOutput(true);
            httpcon.setRequestProperty("Content-Type", "application/json");
            httpcon.setRequestMethod("POST");
            httpcon.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Disconnect() {
        httpcon.disconnect();
    }
}
