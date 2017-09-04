package com.googolfist.smartcontrolcenter.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/6/3.
 */

public class DeviceYcpItem extends ItemData {
    /*{"m_iYCNo":"1","m_YCNm":"插座开关光照度","m_YCValue":"-1","m_AdviceMsg":" ","m_IsAlarm":"False","m_Unit":""}*/
    public String m_iYCNo;
    public String m_YCNm;
    public String m_YCValue;
    public String m_AdviceMsg;
    public String m_IsAlarm;
    public String m_Unit;

    public DeviceYcpItem() {
        super();
    }

    @Override
    public String toString() {
        return "{ m_iYCNo = " + m_iYCNo + ", m_YCNm = " + m_YCNm + ", m_YCValue = " + m_YCValue +
                ", m_AdviceMsg = " + m_AdviceMsg + ", m_IsAlarm = " + m_IsAlarm + ", m_Unit= " + m_Unit + "}";
    }

    public static final Parcelable.Creator<DeviceYcpItem> CREATOR = new Creator<DeviceYcpItem>() {
        @Override
        public DeviceYcpItem createFromParcel(Parcel source) {
            DeviceYcpItem ycpItem = new DeviceYcpItem();
            ycpItem.m_iYCNo = source.readString();
            ycpItem.m_YCNm = source.readString();
            ycpItem.m_YCValue = source.readString();
            ycpItem.m_AdviceMsg = source.readString();
            ycpItem.m_IsAlarm = source.readString();
            ycpItem.m_Unit = source.readString();
            return ycpItem;
        }

        @Override
        public DeviceYcpItem[] newArray(int size) {
            return new DeviceYcpItem[size];
        }
    };

    @Override
    public int describeContents() {
        return super.describeContents();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(m_iYCNo);
        dest.writeString(m_YCNm);
        dest.writeString(m_YCValue);
        dest.writeString(m_AdviceMsg);
        dest.writeString(m_IsAlarm);
        dest.writeString(m_Unit);
    }
}
