package com.googolfist.smartcontrolcenter.services;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.googolfist.smartcontrolcenter.database.DaoSession;
import com.googolfist.smartcontrolcenter.SmartControlAppliction;
import com.googolfist.smartcontrolcenter.model.DeviceSetItem;
import com.googolfist.smartcontrolcenter.model.DeviceYcpItem;
import com.googolfist.smartcontrolcenter.model.DeviceYxpItem;
import com.googolfist.smartcontrolcenter.model.DevicesModel;
import com.googolfist.smartcontrolcenter.model.EquipModel;
import com.googolfist.smartcontrolcenter.model.ItemData;
import com.googolfist.smartcontrolcenter.model.ServerUserModel;
import com.googolfist.smartcontrolcenter.utils.JSONUtil;
import com.googolfist.smartcontrolcenter.utils.PollingUtils;
import com.googolfist.smartcontrolcenter.utils.XMLUtil;
import com.googolfist.smartcontrolcenter.webservices.WebServiceAction;
import com.googolfist.smartcontrolcenter.webservices.WebServiceCallback;
import com.googolfist.smartcontrolcenter.webservices.WebServices;

import org.dom4j.Document;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.googolfist.smartcontrolcenter.webservices.WebServices.TableName.ALL;
import static com.googolfist.smartcontrolcenter.webservices.WebServices.TableName.DEVICE_ANALOG;
import static com.googolfist.smartcontrolcenter.webservices.WebServices.TableName.DEVICE_SETTING;
import static com.googolfist.smartcontrolcenter.webservices.WebServices.TableName.DEVICE_STATE_AMOUNT;

public class SmartHomeService extends Service {
    private static final String TAG = "SmartHomeService";
    public static final String ACTION_SMART_HOME = "com.googolfist.smartcontrolcenter.services.SmartHomeService";
    private WebServices mWebServices;
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    private static final int POLL_TIME = 500;

    private DevicesModel mDevicesModel;

    private ServerUserModel userModel;

    public SmartHomeBinder mBinder = new SmartHomeBinder();

    private boolean mIsLogin = false;

    private CallBack mCallback;

    private AlarmManager mAlarmManager;


    public SmartHomeService() {
        initModel();
    }

    private void initModel() {
        mDevicesModel = new DevicesModel();
        userModel = new ServerUserModel("admin");
    }

    private void connectNet() {
        mWebServices.initContectSevivce(userModel.getUser(), mCallback);
    }

    public boolean checkLogin() {
        if (mIsLogin) {
            return true;
        } else {
            Log.e(TAG, "checkLogin:" + mIsLogin);
            login();
            return false;
        }
    }

    private void login() {
        if (mIsLogin == false) {
            mWebServices.loginService(userModel.getUser(), userModel.getPassword(), mCallback);
        }
    }

    private void getDeviceList() {
        if (checkLogin()) {
            mWebServices.getDevices2(mCallback);
        }
    }

    private void getDeviceRealtimeData(String equipNo, String type) {
        if (checkLogin()) {
            mWebServices.getDeviceRealtimeData(equipNo, type, mCallback);
        }
    }

    private void getDevRealTimeStatus(String equipNo) {
        if (checkLogin()) {
            mWebServices.getDeviceRealTimeStatus(equipNo, userModel.getUser(), mCallback);
        }
    }

    public void executeDevice(String equipNo, String setNo) {
        if (checkLogin()) {
            final DeviceSetItem setItem = mDevicesModel.getEquipSetting(equipNo, setNo);
            if (setItem != null) {
                new Runnable() {
                    @Override
                    public void run() {
                        mWebServices.setUpdates(setItem.equip_no, setItem.main_instruction, setItem.minor_instruction, setItem.value, userModel.getUser(), mCallback);
                    }
                }.run();
            } else {
                Log.w(TAG, "executeDevice: equipNo or setNo is null, equipoNo = " + equipNo + ", setNo = " + setNo);
            }
        }
    }

    private void setEquipList(String dataXml) {
        Document doc = XMLUtil.StringToXML(dataXml);
        //Log.d(TAG, doc.getXMLEncoding());
        List<Element> list = doc.getRootElement().elements();
        addEquips(list);
        doc.clearContent();
        PollingUtils.startPollingService(getApplicationContext(), 10000, PollingService.class, PollingService.ACTION);
    }

    private void addEquips(List<Element> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Element ele = list.get(i);
            String name = ele.attributeValue("Name");
            String equipNo = ele.attributeValue("EquipNo");
            if (equipNo.equals("")) {
                //Log.d(TAG, "setEquipList: "+ ele.elements());
                addEquips(ele.elements());
                continue;
            }
            Boolean isExpanded = Boolean.parseBoolean(ele.attributeValue("IsExpanded"));
            EquipModel model = new EquipModel(name, equipNo, isExpanded);
            mDevicesModel.addEquip(model);
            //
            checkEquipDevAnalog(equipNo);
            checkEquipDevStatus(equipNo);
            checkEquipDevSetting(equipNo);
            getDevRealTimeStatus(equipNo);
        }
        list.clear();
        list = null;
    }

    private void updateEquipSttsut(String equipNo, String status) {
        mDevicesModel.updateEquipStatus(equipNo, status);
    }


    private void updateEquipData(String devStatus, String tableName, String equipNo) {
        Class cls = null;
        switch (tableName) {
            case ALL:
                break;
            case DEVICE_ANALOG:
                cls = DeviceYcpItem.class;
                break;
            case DEVICE_STATE_AMOUNT:
                cls = DeviceYxpItem.class;
                break;
            case DEVICE_SETTING:
                cls = DeviceSetItem.class;
                break;
        }

        if (devStatus == null || devStatus.equals("") || devStatus.equals("[]") || devStatus.equals("anyType{}") || cls == null) {
            //Log.w(TAG, "updateEquipData: " + devStatus + ", tableName = " + tableName);
            return;
        }

        ArrayList<ItemData> data = JSONUtil.paserNoHanderJArray(devStatus, cls);
        if (data != null) {
            int size = data.size();
            for (int i = 0; i < size; i++) {
                ItemData item = data.get(i);
                boolean isSet = item instanceof DeviceSetItem;
                boolean isYXP = item instanceof DeviceYxpItem;
                boolean isYCP = item instanceof DeviceYcpItem;
                if (isSet) {
                    mDevicesModel.addEquipSetting((DeviceSetItem) item);
                } else if (isYCP) {
                    mDevicesModel.addEquipYcp(equipNo, (DeviceYcpItem) item);
                } else if (isYXP) {
                    mDevicesModel.addEquipYxp(equipNo, (DeviceYxpItem) item);
                }
            }
            data.clear();
            data = null;

            /*Set<String> sets = mDevicesModel.getEquipSetNoByEquipNo("2003");
            if (sets != null) {
                for (String s :
                        sets) {
                    executeDevice("2003", s);
                }
            }*/
        }
    }

    private void checkEquipDevStatus(String equipNo) {
        getDeviceRealtimeData(equipNo, DEVICE_STATE_AMOUNT);
    }

    private void checkEquipDevAnalog(String equipNo) {
        getDeviceRealtimeData(equipNo, DEVICE_ANALOG);
    }

    private void checkEquipDevSetting(String equipNo) {
        getDeviceRealtimeData(equipNo, DEVICE_SETTING);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mWebServices = new WebServices();
        //mBinder = new SmartHomeBinder(mWebServices);
        mCallback = new CallBack();
        mHandlerThread = new HandlerThread("ISmarthomeService");
        userModel.setPassword("admin");
        Log.d(TAG, "onCreate: " + getApplication());
        DaoSession daoSession = ((SmartControlAppliction) getApplication()).getDaoSession();
        daoSession.getUserDao();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startHandler();
        if (mIsLogin == false) {
            Message msg = new Message();
            msg.obj = new String[]{"", ""};
            mHandler.sendMessage(msg);
        }
        return START_STICKY;
    }

    private void startHandler() {
        if (mHandler != null)
            return;
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case WebServiceAction.INIT_ENSURE_RUN_PROXY:
                        connectNet();
                        break;
                    case WebServiceAction.LOGIN:

                        break;

                }
            }
        };
    }

    @Override
    public void onDestroy() {
        Intent intent = new Intent(ACTION_SMART_HOME);
        sendBroadcast(intent);
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public IBinder onBind(Intent intent) {
/*        mHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //mWebServices.getDeviceRealTimeStatus(WebServices.getExpression("2003"), "admin");
                Log.d(TAG, "handleMessage: -------------->");
                //mHandler.sendMessageDelayed(msg, 300);

            }
        };*/
        return mBinder;
    }

    class CallBack implements WebServiceCallback {
        @Override
        public void callback(Message msg) {
            /*SoapObject soapObj = null;
            Object rstobj = null;
            boolean isSoapObject = false;
            boolean isFailed = false;
            String returnData = "";
            if (msg.obj != null) {
                soapObj = (SoapObject) msg.obj;
                int pc = soapObj.getPropertyCount();
                if (pc > 0) {
                    rstobj = soapObj.getProperty(0);
                    isSoapObject = rstobj instanceof SoapObject;
                }
                if (rstobj != null) {
                    returnData = rstobj.toString();
                }
                if (!isSoapObject) {
                    isFailed = returnData.equals("false");
                }
            }

            switch (msg.what) {
                case WebServiceAction.INIT_ENSURE_RUN_PROXY:
                    if (isFailed) {
                        Log.d(TAG, "INIT_ENSURE_RUN_PROXY: " + isFailed);
                        mIsLogin = false;
                    } else {
                        String rightAt = returnData;
                        Log.d(TAG, "callback: " + rightAt);
                        checkLogin();
                    }
                    break;
                case WebServiceAction.LOGIN:
                    if (isFailed) {
                        Log.d(TAG, "LOGIN: " + isFailed + ", -> " + returnData);
                        mIsLogin = false;
                    } else {
                        mIsLogin = true;
                        getDeviceList();

                    }
                    break;
                case WebServiceAction.GET_EQUIP_TREE_LISTS:
                    Log.d(TAG, "GET_EQUIP_TREE_LISTS: ");
                    break;
                case WebServiceAction.GET_EQUIP_TREE_LISTS2:
                    if (isFailed) {
                        Log.w(TAG, "GET_EQUIP_TREE_LISTS2: is Failed =" + isFailed);
                    } else {
                        setEquipList(returnData);
                    }
                    break;
                case WebServiceAction.REAL_TIME_DATA:
                    if (isFailed) {
                        Log.d(TAG, "REAL_TIME_DATA: isFailed = " + isFailed);
                    } else {
                        String tableName = (String) soapObj.getProperty("tableName");
                        String equipNo = (String) soapObj.getProperty("equipNo");
                        updateEquipData(returnData, tableName, equipNo);
                    }
                    break;
                case WebServiceAction.EXPRESSION_EVAL:
                    if (isFailed) {
                        Log.d(TAG, "EXPRESSION_EVAL: isFailed = " + isFailed);

                    } else {
                        String equipNo = (String) soapObj.getProperty("equipNo");
                        updateEquipSttsut(equipNo, returnData);
                    }
                    break;
                case WebServiceAction.SET_UPDATES:
                    if (isFailed) {
                        Log.d(TAG, "SET_UPDATES: " + returnData);
                    } else {
                        Log.d(TAG, "SET_UPDATES: " + returnData);
                    }
                    break;
            }*/
        }

    }


    class SmartHomeBinder extends ISmartHomeService.Stub {

        @Override
        public List<String> getEquipNoList() throws RemoteException {
            return mDevicesModel.getmEquipNoList();
        }

        @Override
        public Map getEquipData(String equipNo) throws RemoteException {
            return null;
        }

        @Override
        public Map getEquipList() throws RemoteException {
            Map<String, Object> list = new HashMap<>();
            List<String> noList = mDevicesModel.getmEquipNoList();
            int size = noList.size();
            for (int i = 0; i < size; i++) {
                String eqNo = noList.get(i);
                EquipModel eq = mDevicesModel.getEquip(eqNo);
                list.put(eqNo, eq);
            }
            return list;
        }

        @Override
        public List getSetsList(String equipNo) throws RemoteException {
            return mDevicesModel.getSetsByEquipNo(equipNo);
        }

        @Override
        public Map getSetItem(int equipNo, int setNo) throws RemoteException {
            return null;
        }

        @Override
        public Map getYcpList(String equipNo, String m_iYCNo) throws RemoteException {
            return null;
        }

        @Override
        public List<ItemData> getYxpList(String equipNo) throws RemoteException {
            return mDevicesModel.getYxpItemByEquipNo(equipNo);
        }

        @Override
        public void execuEquipSet(String equipNo, String setNo) throws RemoteException {
            executeDevice(equipNo, setNo);
        }

        @Override
        public void pollingDevice() throws RemoteException {

            String equipNo = "";
            ArrayList<String> equipNoList = mDevicesModel.getmEquipNoList();
            int size = equipNoList.size();
            for (int i = 0; i < size; i++) {
                equipNo = equipNoList.get(i);
                checkEquipDevAnalog(equipNo);
                checkEquipDevStatus(equipNo);
                getDevRealTimeStatus(equipNo);

            }
        }
    }

}
