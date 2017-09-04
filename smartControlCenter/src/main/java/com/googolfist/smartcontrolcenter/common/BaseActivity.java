package com.googolfist.smartcontrolcenter.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.googolfist.smartcontrolcenter.R;

/**
 * Created by Administrator on 2017/8/29.
 */

public class BaseActivity extends AppCompatActivity {
    protected Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = (Toolbar) findViewById(R.id.base_toolbar);
        if (toolbar != null) {
            toolbar.setTitle("BaseActivity");
            setSupportActionBar(toolbar);
        }
    }

}
