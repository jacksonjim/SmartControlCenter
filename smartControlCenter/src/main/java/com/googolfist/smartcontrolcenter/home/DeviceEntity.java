package com.googolfist.smartcontrolcenter.home;

/**
 * Created by Administrator on 2017/7/6.
 */

public class DeviceEntity {
    private int mTypeId;
    private int mDeviceID;
    private String mName;
    private boolean mOpened;


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public boolean isOpened() {
        return mOpened;
    }

    public void setOpened(boolean opened) {
        mOpened = opened;
    }

    public int getTypeId() {
        return mTypeId;
    }

    public void setTypeId(int typeId) {
        mTypeId = typeId;
    }

    public int getDeviceID() {
        return mDeviceID;
    }

    public void setDeviceID(int deviceID) {
        mDeviceID = deviceID;
    }
}
