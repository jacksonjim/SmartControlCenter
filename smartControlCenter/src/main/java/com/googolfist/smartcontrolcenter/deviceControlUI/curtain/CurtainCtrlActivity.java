package com.googolfist.smartcontrolcenter.deviceControlUI.curtain;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.googolfist.smartcontrolcenter.R;

public class CurtainCtrlActivity extends AppCompatActivity {

    private ImageView mAnimationView;
    private ImageButton mOpenBtn;
    private ImageButton mCloseBtn;
    private boolean mOpened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curtain_control);
        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.base_toolbar);
        toolbar.setTitle("Curtain Control");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mOpened = false;
    }

    private void setupView() {
        mOpenBtn = (ImageButton) findViewById(R.id.curtain_open_btn);
        mCloseBtn = (ImageButton) findViewById(R.id.curtain_close_btn);
        //
        mAnimationView = (ImageView) findViewById(R.id.curtain_animator);
        initButtonListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupView();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void finish() {
        super.finish();
    }

    private void open(){
        if(!mOpened) {
            mAnimationView.setImageResource(R.drawable.curtain_open);
            AnimationDrawable animationDrawable = (AnimationDrawable) mAnimationView.getDrawable();
            animationDrawable.start();
            mOpened = true;
        }
    }

    private void close(){
        if(mOpened) {
            mAnimationView.setImageResource(R.drawable.curtain_close);
            AnimationDrawable animationDrawable = (AnimationDrawable) mAnimationView.getDrawable();
            animationDrawable.start();
            mOpened = false;
        }
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

}
