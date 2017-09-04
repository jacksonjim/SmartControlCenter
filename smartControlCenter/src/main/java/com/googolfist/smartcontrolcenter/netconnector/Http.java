package com.googolfist.smartcontrolcenter.netconnector;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/6/2.
 */

public class Http extends Thread {

    private static final String TAG = "HttpConnector";
    private String ser = "http://192.168.1.241:8088";
    private HttpURLConnection connection;
    URL url = null;

    public Http() {
        super();
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public void run() {
        super.run();
        try {
            url = new URL(ser);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/x-www-from-urlencoded");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.connect();

            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
            dos.writeBytes("");
            dos.flush();
            dos.close();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String rev = "";
            String readLine = null;
            while ((readLine = bufferedReader.readLine()) != null) {
                rev += readLine;
            }
            bufferedReader.close();
            connection.disconnect();
            Log.d(TAG, "run: " + rev + "/n");

        } catch (Exception e) {
            Log.e(TAG, "connect error ", e);
            e.printStackTrace();
        }
    }
}
