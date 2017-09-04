package com.googolfist.smartcontrolcenter.model;

import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/3.
 */

public interface IBaseModel extends Parcelable {

    void fromObject(ArrayList<ItemData> list);

    ItemData toObject();

}
