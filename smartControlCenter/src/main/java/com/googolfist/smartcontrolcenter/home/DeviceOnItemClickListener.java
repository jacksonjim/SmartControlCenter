package com.googolfist.smartcontrolcenter.home;

import android.view.View;

import com.googolfist.smartcontrolcenter.common.ViewHolder;

/**
 * Created by Administrator on 2017/8/9.
 */

public interface DeviceOnItemClickListener {
    void onItemClick(View v, ViewHolder holder, int position, Object itemData);
}
