package com.googolfist.smartcontrolcenter.deviceControlUI.window;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.googolfist.smartcontrolcenter.R;

public class WindowCtrlActivity extends AppCompatActivity {

    private final static String TAG = "WindowCtrlActivity";
    private ImageView mAnimatorView;
    private ImageButton mOpenBtn;
    private ImageButton mCloseBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_control);
        Toolbar toolbar = (Toolbar) findViewById(R.id.base_toolbar);
        toolbar.setTitle("Window Control");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupView(){
        mAnimatorView = (ImageView) findViewById(R.id.window_anim);
        mOpenBtn = (ImageButton) findViewById(R.id.window_open_btn);
        mCloseBtn = (ImageButton) findViewById(R.id.window_close_btn);

        mOpenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimatorView.setImageResource(R.drawable.open_window);
            }
        });

        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimatorView.setImageResource(R.drawable.closed_window);

            }
        });
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
    protected void onDestroy() {
        super.onDestroy();
    }
}
