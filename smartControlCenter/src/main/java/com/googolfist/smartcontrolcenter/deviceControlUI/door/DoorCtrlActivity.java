package com.googolfist.smartcontrolcenter.deviceControlUI.door;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.googolfist.smartcontrolcenter.R;


public class DoorCtrlActivity extends AppCompatActivity {
    private final static String TAG = DoorCtrlActivity.class.getSimpleName();
    private ImageView mAnimationView;
    private boolean mOpened;
    private ImageButton mOpenBtn;
    private ImageButton mCloseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_door_control);
        Toolbar toolbar = (Toolbar) findViewById(R.id.base_toolbar);
        toolbar.setTitle("Door Control");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupView(){
        mAnimationView = (ImageView) findViewById(R.id.animator);
        mOpenBtn = (ImageButton) findViewById(R.id.door_open_btn);
        mCloseBtn = (ImageButton) findViewById(R.id.door_close_btn);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupView();
        initButtonListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    private void initButtonListener() {
        mOpenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open();
            }
        });

        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
    }

    private void open(){
        if(!mOpened) {
            mAnimationView.setImageResource(R.drawable.door_open);
            AnimationDrawable animationDrawable = (AnimationDrawable) mAnimationView.getDrawable();
            animationDrawable.start();
            mOpened = true;
        }
    }

    private void close(){
        if(mOpened) {
            mAnimationView.setImageResource(R.drawable.door_close);
            AnimationDrawable animationDrawable = (AnimationDrawable) mAnimationView.getDrawable();
            animationDrawable.start();
            mOpened = false;
        }
    }
}
