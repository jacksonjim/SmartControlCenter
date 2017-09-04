package com.google.zxing.client.android;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.camera.CameraManager;
import com.googolfist.smartcontrolcenter.R;
import com.googolfist.smartcontrolcenter.devices.AddDeviceActivity;

import java.io.IOException;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;

public final class QRCodeScanActivity extends AbstractCaptureActivity implements SurfaceHolder.Callback {
    private final static String TAG = QRCodeScanActivity.class.getSimpleName();
    private boolean mHasSurface;
    private TextInputLayout mSerialNumberWrapper;
    private TextInputEditText mSerialNumberEditor;
    private final static String SCAN_RESULT = "ScanResult";
    private final static String SUPPORT_TYPE = "SupportType";
    private final static int REQUEST_PERMISSIONS = 1;
    private static final Collection<ResultMetadataType> DISPLAYABLE_METADATA_TYPES =
            EnumSet.of(
                    ResultMetadataType.ISSUE_NUMBER,
                    ResultMetadataType.SUGGESTED_PRICE,
                    ResultMetadataType.ERROR_CORRECTION_LEVEL,
                    ResultMetadataType.POSSIBLE_COUNTRY
            );
    private CameraManager mCameraManager;
    private Result mResult;
    private ViewfinderView mViewfinderView;
    private SurfaceView mScanPreview;
    private InactivityTimer mInactivityTimer;
    private BeepManager mBeepManager;
    private AmbientLightManager mAmbientLightManager;
    private TextView mResultView;
    private TextView mStatusView;
    private CaptureActivityHandler mHandler;
    private Collection<BarcodeFormat> mDecodeformats;
    private Map<DecodeHintType, ?> mDecodeHints;
    private String mCharacterSet;
    private Result mSavedResultToShow;

    public CaptureActivityHandler getHandler() {
        return mHandler;
    }

    @Override
    public void drawViewfinder() {
        mViewfinderView.drawViewfinder();
    }

    public ViewfinderView getViewfinderView() {
        return mViewfinderView;
    }

    @Override
    CameraManager getCameraManager() {
        return mCameraManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_qr_code_scan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.capture_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // init ptys

        setupViews();
        initData();

        //test result
        setResult(0x00ff);
    }

    private void initData() {
        mHasSurface = false;
        mBeepManager = new BeepManager(this);
        mAmbientLightManager = new AmbientLightManager(this);
        mInactivityTimer = new InactivityTimer(this);

    }

    private void setupViews() {
        mStatusView = (TextView) findViewById(R.id.status_view);
        mResultView = (TextView) findViewById(R.id.result_view);
        mViewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        mScanPreview = (SurfaceView) findViewById(R.id.preview_view);
        mSerialNumberWrapper = (TextInputLayout) findViewById(R.id.serial_number_wrapper);
        mSerialNumberWrapper.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(TAG, "onFocusChange: " + hasFocus);
            }
        });
        mSerialNumberEditor = (TextInputEditText) findViewById(R.id.serial_number_editor);
        mSerialNumberEditor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Drawable[] drawables = mSerialNumberEditor.getCompoundDrawables();
                Drawable right = drawables[2];
                if (right == null) {
                    return false;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float evtX = event.getX();
                    int snW = mSerialNumberEditor.getWidth();
                    int snPR = mSerialNumberEditor.getPaddingRight();
                    boolean touched = evtX > (snW - snPR - right.getIntrinsicWidth())
                            && (evtX < (snW - snPR));
                    if (touched) {
                        String serialNumber = mSerialNumberEditor.getText().toString();
                        if (serialNumber.isEmpty()) {
                            mSerialNumberWrapper.setError(getString(R.string.serial_number_is_empty));
                        } else {
                            Intent intent = new Intent(QRCodeScanActivity.this, AddDeviceActivity.class);
                            startActivityForResult(intent, 0x00ff);
                        }
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCameraManager = new CameraManager(this);
        mViewfinderView.setCameraManager(mCameraManager);

        mHandler = null;

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        resetStatusView();
        //
        mBeepManager.updatePrefs();
        mAmbientLightManager.start(mCameraManager);
        mInactivityTimer.onResume();

        Intent intent = getIntent();

        SurfaceView surface = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surface.getHolder();

        if (mHasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
        }
        mInactivityTimer.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                setResult(RESULT_CANCELED);
                finish();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void resetStatusView() {
        mResultView.setVisibility(View.GONE);
        mStatusView.setText(R.string.scan_qrcode_desc);
        mStatusView.setVisibility(View.VISIBLE);
        mViewfinderView.setVisibility(View.VISIBLE);

    }

    public void restartPreviewAfterDelay(long delayMS) {
        if (mHandler != null) {
            mHandler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
        }
        resetStatusView();
    }

    @Override
    protected void onPause() {
        if(mHandler != null) {
            mHandler.quitSynchronously();
            mHandler = null;
        }
        mInactivityTimer.onPause();
        mBeepManager.close();
        mAmbientLightManager.stop();
        mCameraManager.closeDriver();
        if (!mHasSurface) {
            mScanPreview.getHolder().removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mInactivityTimer.shutdown();
        super.onDestroy();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No surfaceHolder provider");
        }

        if (mCameraManager.isOpen()) {
            Log.w(TAG, "initCamera: camera is opened");
            return;
        }

        try {
            mCameraManager.openDriver(surfaceHolder);
            if(mHandler == null) {
                mHandler = new CaptureActivityHandler(this, mDecodeformats, mDecodeHints, mCharacterSet, mCameraManager);
            }
            decodeOrStoreSavedBitmap(null, null);
        }catch (IOException ioe) {
            Log.e(TAG, "initCamera() IO error",ioe );
            displayFrameworkBugMessageAndExit();
        }catch (RuntimeException re) {
            // Barcode Scanner has seen crashes in the wild of this variety:
            // java.?lang.?RuntimeException: Fail to connect to camera service
            Log.e(TAG, "Unexpected error initializing camera",re );
            displayFrameworkBugMessageAndExit();
        }

        //surfaceHolder
    }

    private void displayFrameworkBugMessageAndExit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(getString(R.string.msg_camera_framework_bug));
        builder.setPositiveButton(android.R.string.ok, new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }

    private void decodeOrStoreSavedBitmap(Bitmap bitmap, Result result){
        // Bitmap isn't used yet -- will be used soon
        if(mHandler == null) {
            mSavedResultToShow = result;
        }else {
            if (result != null) {
                mSavedResultToShow = result;
            }
            if (mSavedResultToShow != null) {
                Message message = Message.obtain(mHandler, R.id.decode_succeeded, mSavedResultToShow);
                mHandler.sendMessage(message);
            }
            mSavedResultToShow = null;

        }

    }

    /**
     * draw line or the barcode
     *
     * @param canvas
     * @param paint
     * @param a           start point of the line
     * @param b           end point of the line
     * @param scaleFactor amount by which thumbnail was scaled
     */
    public static void drawLine(Canvas canvas, Paint paint, ResultPoint a,
                                ResultPoint b, float scaleFactor) {
        if (null != a && null != b) {
            canvas.drawLine(scaleFactor * a.getX(),
                    scaleFactor * a.getY(),
                    scaleFactor * b.getX(),
                    scaleFactor * b.getY(), paint);
        }
    }

    /**
     * Superimpose a line for 1D or dots for 2D to highlight the key features of the barcode.
     *
     * @param barcode     a bitmap of the captured image
     * @param scaleFactor amount by which thumbnail was scaled
     * @param rawResult     The decoded results which contains the points to draw.
     */
    public void drawResultPoints(Bitmap barcode, float scaleFactor, Result rawResult) {
        ResultPoint[] points = rawResult.getResultPoints();
        if (points != null && points.length > 0) {
            int len = points.length;
            Canvas canvas = new Canvas(barcode);
            Paint paint = new Paint();
            int color = -1;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                color = getResources().getColor(R.color.result_points, null);
            } else {
                color = getResources().getColor(R.color.result_points);
            }
            paint.setColor(color);

            if (len == 2) {
                paint.setStrokeWidth(4.0f);
                drawLine(canvas, paint, points[0], points[1], scaleFactor);
            }
            if (len == 4 &&
                    (rawResult.getBarcodeFormat() == BarcodeFormat.UPC_A
                            || rawResult.getBarcodeFormat() == BarcodeFormat.EAN_13)) {
                drawLine(canvas, paint, points[0], points[1], scaleFactor);
                drawLine(canvas, paint, points[2], points[3], scaleFactor);
            } else {
                paint.setStrokeWidth(10.0f);
                for (ResultPoint p :
                        points) {
                    if (p != null) {
                        canvas.drawPoint(scaleFactor * p.getX(), scaleFactor * p.getY(), paint);
                    }
                }
            }

        }
    }

    @Override
    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        mInactivityTimer.onActivity();

        boolean fromLiveScan = barcode != null;
        if (fromLiveScan) {
            mBeepManager.playBeepSoundAndVibrate();
            drawResultPoints(barcode,scaleFactor, rawResult);
        }

        Log.d(TAG, "scan result is : " + rawResult.getText());
        Toast.makeText(this, "scan Result is " + rawResult.getText(), Toast.LENGTH_LONG).show();
        //test
        restartPreviewAfterDelay(20l);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            Log.d(TAG, "onOptionsItemSelected: ");
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if(holder == null) {
            Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if(!mHasSurface) {
            mHasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mHasSurface = false;
    }
}
