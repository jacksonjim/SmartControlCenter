// IStartHomeSeviceAidlInterface.aidl
package com.googolfist.smartcontrolcenter.services;
import com.googolfist.smartcontrolcenter.model.DevicesModel;
import com.googolfist.smartcontrolcenter.model.EquipModel;
import com.googolfist.smartcontrolcenter.model.ItemData;

interface ISmartHomeService {

    //AIDL中支持以下的数据类型
    //1. 基本数据类型
    //2. String 和CharSequence
    //3. List 和 Map ,List和Map 对象的元素必须是AIDL支持的数据类型;
    //4. AIDL自动生成的接口（需要导入-import）
    //5. 实现android.os.Parcelable 接口的类（需要导入-import)

    //get EquipNoList
    List<String> getEquipNoList();
    ///
    Map getEquipData(in String equipNo);
    // get equip list
    Map getEquipList();
    // get sets list
    List getSetsList(in String equipNo);
    // get set item
    Map getSetItem(in int equipNo, int setNo);
    // get ycp list
    Map getYcpList(in String equipNo, in String m_iYCNo);
    // get yxp list
    List getYxpList(in String equipNo);
    // execu command
    void execuEquipSet(in String equipNo, String setNo);

    void pollingDevice();
}
