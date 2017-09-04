package com.googolfist.smartcontrolcenter.model;

import android.os.Bundle;
import android.os.Parcel;

/**
 * Created by Administrator on 2017/6/3.
 */

public class DeviceYxpItem extends ItemData {
    /*{"m_iYXNo":"1","m_YXNm":"插座开关状态","m_YXState":"离线","m_AdviceMsg":"","m_IsAlarm":"False"}*/
    public String m_iYXNo;
    public String m_YXNm;
    public String m_YXState;
    public String m_AdviceMsg;
    public String m_IsAlarm;

    @Override
    public String toString() {
        return "{ m_iYXNo = " + m_iYXNo + ", m_YXNm = " + m_YXNm + ", m_YXState = " + m_YXState +
                ", m_AdviceMsg =  " + m_AdviceMsg + ", m_IsAlarm = " + m_IsAlarm + "}";
    }

    public DeviceYxpItem() {
        super();
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("m_iYXNo", m_iYXNo);
        bundle.putString("m_YXNm", m_YXNm);
        bundle.putString("m_YXState", m_YXState);
        bundle.putString("m_AdviceMsg", m_AdviceMsg);
        bundle.putString("m_IsAlarm", m_IsAlarm);
        return bundle;
    }

    public void fromBundle(Bundle bundle) {
        this.m_iYXNo = bundle.getString("m_iYXNo");
        this.m_YXNm = bundle.getString("m_YXNm");
        this.m_YXState = bundle.getString("m_YXState");
        this.m_AdviceMsg = bundle.getString("m_AdviceMsg");
        this.m_IsAlarm = bundle.getString("m_IsAlarm");
    }

    public static final Creator<DeviceYxpItem> CREATOR = new Creator<DeviceYxpItem>() {
        @Override
        public DeviceYxpItem createFromParcel(Parcel source) {
            DeviceYxpItem yxpItem = new DeviceYxpItem();
            yxpItem.fromBundle(source.readBundle());
            return yxpItem;
        }

        @Override
        public DeviceYxpItem[] newArray(int size) {
            return new DeviceYxpItem[0];
        }
    };

    @Override
    public int describeContents() {
        return super.describeContents();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.toBundle());
    }
}
