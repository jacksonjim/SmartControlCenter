package com.googolfist.smartcontrolcenter;


import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

import com.googolfist.smartcontrolcenter.cctv.VideoBase;
import com.googolfist.smartcontrolcenter.database.DaoMaster;
import com.googolfist.smartcontrolcenter.database.DaoMaster.DevOpenHelper;
import com.googolfist.smartcontrolcenter.database.DaoSession;
import com.googolfist.smartcontrolcenter.utils.DangerousPermissionsHelper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by Administrator on 2017/6/21.
 */

public class SmartControlAppliction extends Application {
    private static final String TAG = "SmartControlAppliction";
    private static final String DB_NAME = "smartHome.db";
    private static DaoSession mDaoSession;
    // 安全加密数据库
    private static final boolean ENCRYPTED = false;

    @Override
    public void onCreate() {
        super.onCreate();
        setupDatabase();
        initVideo();
        testWebsocket();
    }

    private void initVideo() {
        String appkey = "295654fb3d2944a48782d71bb4a66b1f";
        //VideoBase.init(this, false, appkey);
        getVideoTokey();
    }

    private void getVideoTokey() {
        VideoBase.getToken("295654fb3d2944a48782d71bb4a66b1f", "c2179bb10cca35b3781ca74cf959127e");
    }

    /**
     * DevOpenHelper：创建SQLite数据库的SQLiteOpenHelper的具体实现
     * DaoMaster：GreenDao的顶级对象，作为数据库对象、用于创建表和删除表
     * DaoSession：管理所有的Dao对象，Dao对象中存在着增删改查等API
     */

    private void setupDatabase() {
        DangerousPermissionsHelper.checkSelfPremission(this, DangerousPermissionsHelper.getExternalStorage());
        DangerousPermissionsHelper.checkSelfPremission(this, DangerousPermissionsHelper.getPhone());
        // create Database file
        DevOpenHelper openHelper = new DevOpenHelper(this, DB_NAME, null);
        // get writable Database
        Database db = ENCRYPTED ? openHelper.getEncryptedWritableDb("super-secret") : openHelper.getWritableDb();
        //SQLiteDatabase  sqliteDb = openHelper.getWritableDatabase();

        //get database object
        DaoMaster daoMaster = new DaoMaster(db);

        //get DaoMaster Session Object
        mDaoSession = daoMaster.newSession();

    }

    private void testWebsocket() {
        WebSocketListener listener = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                webSocket.send("123456");
                webSocket.send("123456->>>");
                webSocket.send(ByteString.decodeHex("deadbeef"));
                Log.d(TAG, "------------------------------" + response.body().toString());
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                Log.d(TAG, "onMessage =>" + text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
                Log.d(TAG, "onMessage =>" + bytes);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                Log.d(TAG, "onClosing: " + code + "->" + reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                Log.d(TAG, "onClosed:" + code + "->" + reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                t.printStackTrace();

                Log.d(TAG, "onFailure" + t.toString() + "\n response:" + response);

            }
        };
        //assertEquals(4, 2 + 2);
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(0, TimeUnit.SECONDS)
                .writeTimeout(0, TimeUnit.SECONDS)
                .connectTimeout(0, TimeUnit.SECONDS)
                .build();

        String url = "ws://echo.websocket.org";
        //String url = "ws://192.168.1.143:8086/signalr/";
        Request request = new Request.Builder().url(url).addHeader("connectionToken", "sssss").build();
        Log.d(TAG, "666" + request);
        client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }


    public static DaoSession getDaoSession() {
        return mDaoSession;
    }
}