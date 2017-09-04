package com.googolfist.smartcontrolcenter.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/14.
 */
public class EquipModel implements IBaseModel {
    private static final String TAG = "EquipModel";
    public String name;
    public String equipNo;
    public boolean isExpanded;
    //返回值意义0（不通讯），1（通讯正常），2(有报警)，3（正在进行设置），4（正在初始化），5（撤防）
    private EquipStatus status;

    public EquipModel() {
        super();
    }

    public EquipModel(String name, String equipNo, boolean isExpanded) {
        this.name = name;
        this.equipNo = equipNo;
        this.isExpanded = isExpanded;
        status = EquipStatus.DISCONNECTED;
    }

    @Override
    public String toString() {
        return "{ name = " + name + ", equipNo = " + equipNo + ", isExpanded = " + isExpanded + " }";
    }

    public EquipStatus getStatus() {
        return status;
    }

    public void setStatus(EquipStatus status) {
        this.status = status;
    }

    @Override
    public void fromObject(ArrayList<ItemData> list) {

    }

    @Override
    public ItemData toObject() {
        return null;
    }

    public static final Parcelable.Creator<EquipModel> CREATOR = new Creator<EquipModel>() {
        @Override
        public EquipModel createFromParcel(Parcel source) {
            EquipModel em = new EquipModel();
            em.name = source.readString();
            em.equipNo = source.readString();
            em.isExpanded = Boolean.valueOf(source.readString());
            em.setStatus(EquipStatus.valueOf(source.readInt()));
            return em;
        }

        @Override
        public EquipModel[] newArray(int size) {
            return new EquipModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //dest.writeParcelable(, flags);
        dest.writeString(name);
        dest.writeString(equipNo);
        dest.writeString(String.valueOf(isExpanded));
        dest.writeInt(status.toInt());
    }
}
