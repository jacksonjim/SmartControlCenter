package com.google.zxing.client.android;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.zxing.Result;
import com.google.zxing.client.android.camera.CameraManager;

/**
 * Created by Administrator on 2017/8/4.
 */

public abstract class AbstractCaptureActivity extends AppCompatActivity {

    abstract ViewfinderView getViewfinderView();

    abstract CameraManager getCameraManager();

    public abstract void handleDecode(Result result, Bitmap barcode, float scaleFactor);

    public abstract Handler getHandler();

    public abstract void drawViewfinder();
}
