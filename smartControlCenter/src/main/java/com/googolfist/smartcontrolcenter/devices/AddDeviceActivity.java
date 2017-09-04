package com.googolfist.smartcontrolcenter.devices;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.googolfist.smartcontrolcenter.R;

public class AddDeviceActivity extends AppCompatActivity {

    private static final String TAG = "AddDeviceActivity";
    private TextInputLayout mNameWrapper;
    private TextInputEditText mNameEditor;

    /*private TextInputLayout mSerialNumberWrapper;
    private TextInputEditText mSerialNumberEditor;*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        Toolbar toolbar = (Toolbar) findViewById(R.id.add_device_toolbar);
        toolbar.setTitle(R.string.add_device);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        mNameWrapper = (TextInputLayout) findViewById(R.id.device_name_wrapper);
        mNameEditor = (TextInputEditText) findViewById(R.id.device_name_editor);
        /*mSerialNumberWrapper = (TextInputLayout) findViewById(R.id.serial_number_wrapper);
        mSerialNumberEditor = (TextInputEditText) findViewById(R.id.serial_number_editor);
        mSerialNumberEditor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
               *//* // left:0 top:1 right:2 bottom:3
                Drawable[] drawables = mSerialNumberEditor.getCompoundDrawables();
                Drawable right = drawables[2];
                if(right == null){
                    return false;
                }
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    float evtX = event.getX();
                    int snW = mSerialNumberEditor.getWidth();
                    int snPR = mSerialNumberEditor.getPaddingRight();
                    boolean touched = evtX > (snW - snPR - right.getIntrinsicWidth())
                            && (evtX < (snW - snPR));
                    if(touched) {
                        Intent intent = new Intent(AddDeviceActivity.this, QRCodeScanActivity.class);
                        startActivityForResult(intent, 0x00ff);
                    }
                }
*//*
                return false;
            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + requestCode + ", " + resultCode);
    }
}
