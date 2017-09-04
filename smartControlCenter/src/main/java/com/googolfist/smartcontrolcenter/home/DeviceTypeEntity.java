package com.googolfist.smartcontrolcenter.home;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/6.
 */

public class DeviceTypeEntity {
    private List<DeviceEntity> mDeivecEntitis;

    private int mTypeID = -1;
    private String mTypeName;

    public DeviceTypeEntity() {
        mDeivecEntitis = new ArrayList<>();
    }

    public List<DeviceEntity> getDeivecEntitis() {
        return mDeivecEntitis;
    }

    public void setDeivecEntitis(List<DeviceEntity> deivecEntitis) {
        mDeivecEntitis = deivecEntitis;
    }

    public String getTypeName() {
        return mTypeName;
    }

    public void setTypeName(String typeName) {
        mTypeName = typeName;
    }

    public int getTypeID() {
        return mTypeID;
    }

    public void setTypeID(int typeID){
        mTypeID = typeID;
    }
}
