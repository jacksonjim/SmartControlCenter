package com.googolfist.smartcontrolcenter.model;

/**
 * Created by Administrator on 2017/6/3.
 */

import android.os.Bundle;
import android.os.Parcel;

/**
 *
 * */
public class DeviceSetItem extends ItemData {
    /* {"sta_n":"1","equip_no":"2003","set_no":"1","set_nm":"插座开关控制","set_type":"X",
    "main_instruction":"84:86:f3:00:3a:e0","minor_instruction":"onoff","record":"True",
    "action":"设置","value":"0#1","canexecution":"True","VoiceKeys":"","EnableVoice":"False",
    "qr_equip_no":"0","Reserve1":"1","Reserve2":"","Reserve3":""}*/

    public String sta_n;
    public String equip_no;
    public String set_no;
    public String set_nm;
    public String set_type;
    public String main_instruction;
    public String minor_instruction;
    public String record;
    public String action;
    public String value;
    public String canexecution;
    public String VoiceKeys;
    public String EnableVoice;
    public String qr_equip_no;
    public String Reserve1;
    public String Reserve2;
    public String Reserve3;

    @Override
    public String toString() {
        String str = "sta_n = " + sta_n + ", equip_no = " + equip_no + ", set_no = " + set_no +
                ", set_nm = " + set_nm + ", set_type = " + set_type + ", main_instruction = " +
                main_instruction + ", minor_instruction = " + minor_instruction + ", record = " +
                record + ", action = " + action + ", value = " + value + ", canexecution = " +
                canexecution + ", VoiceKeys = " + VoiceKeys + ", EnableVoice = " + EnableVoice +
                ", qr_equip_no =" + qr_equip_no + ", Reserve1 = " + Reserve1 + ", Reserve2 = " +
                Reserve2 + ", Reserve3 = " + Reserve3;
        return str;
    }

    public DeviceSetItem() {
        super();
    }

    public static final Creator<DeviceSetItem> CREATOR = new Creator<DeviceSetItem>() {
        @Override
        public DeviceSetItem createFromParcel(Parcel source) {
            DeviceSetItem setItem = new DeviceSetItem();
            setItem.sta_n = source.readString();
            setItem.equip_no = source.readString();
            setItem.set_no = source.readString();
            setItem.set_nm = source.readString();
            setItem.set_type = source.readString();
            setItem.main_instruction = source.readString();
            setItem.minor_instruction = source.readString();
            setItem.action = source.readString();
            setItem.value = source.readString();
            setItem.canexecution = source.readString();
            setItem.VoiceKeys = source.readString();
            setItem.EnableVoice = source.readString();
            setItem.qr_equip_no = source.readString();
            setItem.Reserve1 = source.readString();
            setItem.Reserve2 = source.readString();
            setItem.Reserve3 = source.readString();
            return setItem;
        }

        @Override
        public DeviceSetItem[] newArray(int size) {
            return new DeviceSetItem[0];
        }
    };

    @Override
    public int describeContents() {
        return super.describeContents();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sta_n);
        dest.writeString(equip_no);
        dest.writeString(set_no);
        dest.writeString(set_nm);
        dest.writeString(set_type);
        dest.writeString(main_instruction);
        dest.writeString(minor_instruction);
        dest.writeString(action);
        dest.writeString(value);
        dest.writeString(canexecution);
        dest.writeString(VoiceKeys);
        dest.writeString(EnableVoice);
        dest.writeString(qr_equip_no);
        dest.writeString(Reserve1);
        dest.writeString(Reserve2);
        dest.writeString(Reserve3);
    }
}
