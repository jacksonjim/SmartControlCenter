package com.googolfist.smartcontrolcenter.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.googolfist.smartcontrolcenter.R;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.R.id.list;

/**
 * Created by Administrator on 2017/6/3.
 */

@SuppressLint("ParcelCreator")
public class DevicesModel implements IBaseModel {
    private static final String TAG = "DevicesModel";
    public static final int LIGHT = R.drawable.light;
    //private ArrayList<Map<String, DeviceSetItem>> mDevices;
    // equipNO
    private HashMap<String, EquipModel> mEquipList;
    private ArrayList<String> mEquipNoList;

    //settings
    HashMap<String, HashMap<String, DeviceSetItem>> mDeviceSets;

    //status (yxp)
    HashMap<String, HashMap<String, DeviceYxpItem>> mDevStatus;

    //ycp
    HashMap<String, HashMap<String, DeviceYcpItem>> mDevAnalog;

    public DevicesModel() {
        super();
        initData();
    }

    private void initData() {
        // mDevices = new ArrayList<>();
        mEquipList = new HashMap<>();
        mEquipNoList = new ArrayList<>();
        mDeviceSets = new HashMap<>();
        mDevStatus = new HashMap<>();
        mDevAnalog = new HashMap<>();
    }

    @Override
    public void fromObject(ArrayList<ItemData> list) {
        DeviceSetItem itemData = (DeviceSetItem) list.get(0);
        HashMap<String, DeviceSetItem> item = new HashMap<>();
        item.put(itemData.equip_no, itemData);
    }

    @Override
    public ItemData toObject() {
        return null;
    }


    public void addEquip(EquipModel model) {
        if (model.equipNo == null || model.equipNo.equals(""))
            return;
        if (mEquipNoList.indexOf(model.equipNo) == -1) {
            mEquipList.put(model.equipNo, model);
            mEquipNoList.add(model.equipNo);
        } else {
            EquipModel em = mEquipList.get(model.equipNo);
            Log.d(TAG, "NO ->" + model.equipNo + "addEquip: " + em.getStatus() + ">" + model.getStatus());
        }
    }

    public void removeEquip(String equipNo) {
        int index = mEquipNoList.indexOf(equipNo);
        if (index == -1) {
            mEquipNoList.remove(index);
            mEquipList.remove(equipNo);
        }
    }

    public EquipModel getEquip(String equipNo) {
        return mEquipList.get(equipNo);
    }

    public void updateEquipStatus(String equipNo, String status) {
        if (equipNo != null && !equipNo.equals("")) {
            EquipModel equipModel = mEquipList.get(equipNo);
            equipModel.setStatus(EquipStatus.valueOf(Integer.parseInt(status)));
            mEquipList.put(equipNo, equipModel);
        }
    }

    public ArrayList<String> getmEquipNoList() {
        return mEquipNoList;
    }

    public void addEquipSetting(DeviceSetItem setItem) {
        if (setItem == null)
            return;
        HashMap<String, DeviceSetItem> devSet = mDeviceSets.get(setItem.equip_no);
        if (devSet != null) {
            devSet.put(setItem.set_no, setItem);
        } else {
            devSet = new HashMap<>();
            devSet.put(setItem.set_no, setItem);
            mDeviceSets.put(setItem.equip_no, devSet);
        }
    }

    public DeviceSetItem getEquipSetting(String equipNo, String setNo) {
        HashMap<String, DeviceSetItem> sets = mDeviceSets.get(equipNo);
        if (sets != null) {
            return sets.get(setNo);
        }
        return null;
    }

    public Set<String> getEquipSetNoByEquipNo(String equipNo) {
        HashMap<String, DeviceSetItem> sets = mDeviceSets.get(equipNo);
        if (sets != null) {
            return sets.keySet();
        }
        return null;
    }

    public List<ItemData> getSetsByEquipNo(String equipNo) {
        HashMap<String, DeviceSetItem> sets = mDeviceSets.get(equipNo);
        List<ItemData> list = new ArrayList<>();
        if (sets != null) {
            for (String key : sets.keySet()) {
                DeviceSetItem setItem = sets.get(key);
                list.add(setItem);
            }
        }
        return list;
    }


    public void addEquipYxp(String equipNo, DeviceYxpItem yxpItem) {
        if (yxpItem == null)
            return;
        HashMap<String, DeviceYxpItem> devSta = mDevStatus.get(equipNo);
        if (devSta != null) {
            devSta.put(yxpItem.m_iYXNo, yxpItem);
        } else {
            devSta = new HashMap<>();
            devSta.put(yxpItem.m_iYXNo, yxpItem);
            mDevStatus.put(equipNo, devSta);
        }
    }

    public List<ItemData> getYxpItemByEquipNo(String equipNo) {
        HashMap<String, DeviceYxpItem> sets = mDevStatus.get(equipNo);
        List<ItemData> list = new ArrayList<>();
        if (sets != null) {
            for (String key : sets.keySet()) {
                DeviceYxpItem setItem = sets.get(key);
                list.add(setItem);
            }
        }
        return list;
    }

    public void addEquipYcp(String equipNo, DeviceYcpItem ycpItem) {
        if (ycpItem == null)
            return;
        HashMap<String, DeviceYcpItem> devSta = mDevAnalog.get(equipNo);
        if (devSta != null) {
            devSta.put(ycpItem.m_iYCNo, ycpItem);
        } else {
            devSta = new HashMap<>();
            devSta.put(ycpItem.m_iYCNo, ycpItem);
            mDevAnalog.put(equipNo, devSta);
        }
    }

    /**
     * json date format
     */

    /*public void callback(SoapObject result) {
        if(result != null) {
            Object rstobj = result.getProperty(0);
            String revData = rstobj instanceof SoapObject ? ((SoapObject) rstobj).toString() : rstobj.toString();

            if (revData.equals("false")) {
                Log.d(TAG, "server is have down");
                return;
            }
            ArrayList<ItemData> sets = JSONUtil.paserNoHanderJArray(revData, DeviceSetItem.class);

            for (int i = 0; i < sets.size(); i++) {
                Log.d(TAG, "Json data:" + sets.get(i).toString());
            }
        }
    }*/
    @Override
    public String toString() {
        return super.toString();
    }

    private void StringToXML(String string) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document xmldoc = reader.read(new ByteArrayInputStream(string.getBytes()));
        DocumentHelper.parseText(string);
    }

    public static final Parcelable.Creator<DevicesModel> CREATOR = new Creator<DevicesModel>() {
        @Override
        public DevicesModel createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public DevicesModel[] newArray(int size) {
            return new DevicesModel[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
