package com.googolfist.smartcontrolcenter.cctv;

import android.app.Application;
import android.util.Log;

import com.google.gson.JsonObject;
import com.googolfist.smartcontrolcenter.utils.JSONUtil;
import com.videogo.openapi.EZOpenSDK;

import java.io.IOException;
import java.util.Date;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/6/30.
 */

public class VideoBase {

    public static final String SRV_URL = "https://open.ys7.com/api/lapp/token/get";
    private static final MediaType CONTENT_TYPE = MediaType.parse("application/x-www-form-urlencoded");
    private static final String TAG = "VideoBase";

    /**
     * @param application
     * @param enableP2P
     * @param appkey
     */
    public static void init(Application application, boolean enableP2P, String appkey) {
        //debug show EZ open sdk log
        EZOpenSDK.showSDKLog(true);

        EZOpenSDK.enableP2P(enableP2P);

        EZOpenSDK.initLib(application, appkey, "");
    }

    /**
     * @param appkey
     * @param appSecret
     */
    public static void getToken(String appkey, String appSecret) {
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("appKey", appkey)
                .add("appSecret", appSecret)
                .build();

        Request request = new Request.Builder()
                .url(SRV_URL)
                .post(body)
                .build();

        final Call mycall = httpClient.newCall(request);

        new Thread() {
            @Override
            public void run() {
                try {
                    Response response = mycall.execute();
                    JsonObject object = JSONUtil.StringToJson(response.body().string());
                    Log.d(TAG, "getToken: " + object.get("data"));
                    Log.d(TAG, "getToken: " + object.get("data").getAsJsonObject().get("accessToken").getAsString());
                    Date date = new Date();
                    Log.d(TAG, "getToken: " + date.toString());
                    Log.d(TAG, "getToken: " + date.toString());
                    Long t = object.get("data").getAsJsonObject().get("expireTime").getAsLong();
                    date.setTime(t);
                    Log.d(TAG, "getToken: " + t + "\n data:" + date.toString());
                    Log.d(TAG, "getToken: " + object.get("code").getAsString());
                    Log.d(TAG, "getToken: " + object.get("msg").getAsString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }


}
