package com.googolfist.smartcontrolcenter.home;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.googolfist.smartcontrolcenter.R;
import com.googolfist.smartcontrolcenter.common.ViewHolder;

/**
 * Created by Administrator on 2017/7/5.
 */

public class DeviceHolder extends ViewHolder {
    private static final String TAG = "DeviceHolder";
    private TextView mDeviceNameView;
    private ImageView mIconView;
    private Switch mSwitchBtn;
    private ImageView mMoreBtn;
    private HolderCallbak callbak;
    private String mName;
    //
    private CompoundButton.OnCheckedChangeListener mCheckedListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Log.d(TAG, "onCheckedChanged: " + isChecked);
            if (callbak != null) {
                callbak.call(mName, isChecked);
            }
        }
    };

    public DeviceHolder(Context context, View itemView, ViewGroup parent) {
        super(context, itemView);
        initView();
    }

    private void initView() {
        mDeviceNameView = (TextView) itemView.findViewById(R.id.device_type_name);
        mIconView = (ImageView) itemView.findViewById(R.id.device_type_icon);
        mSwitchBtn = (Switch) itemView.findViewById(R.id.device_switch_button);
        mMoreBtn = (ImageView) itemView.findViewById(R.id.device_type_more);
        initListener();
    }

    private void initListener() {
        mSwitchBtn.setOnCheckedChangeListener(mCheckedListener);
    }

    public TextView getDeviceName() {
        return mDeviceNameView;
    }

    public void setName(CharSequence text) {
        mName = text.toString();
        mDeviceNameView.setText(text);
    }

    public void enabledSwitchBtn(boolean enabled) {
        mSwitchBtn.setEnabled(enabled);
    }

    public void showSwicth() {
        mSwitchBtn.setVisibility(View.VISIBLE);
    }

    public void hideSwictch() {
        mSwitchBtn.setVisibility(View.GONE);
    }

    public void setSwitchChecked(boolean checked) {
        mSwitchBtn.setChecked(checked);
    }

    public void setCallbak(HolderCallbak callbak) {
        this.callbak = callbak;
    }

    interface HolderCallbak {
        void call(String name, boolean isOpened);
    }

}
