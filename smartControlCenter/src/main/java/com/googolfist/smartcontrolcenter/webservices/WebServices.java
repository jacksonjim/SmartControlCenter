package com.googolfist.smartcontrolcenter.webservices;

import java.util.HashMap;

import static com.googolfist.smartcontrolcenter.webservices.WebServiceAction.*;

/**
 * Created by Administrator on 2017/6/2.
 */

public class WebServices {
    private static final String TAG = "WebServices";

    private String serAdd = "http://192.168.1.241:8088";

    public WebServices() {
        WebServiceUtils.setSerUrl(serAdd);
    }

    /**
     * @param equipno 设备号
     * @return $E(equipNo)
     */
    public static String getExpression(String equipno) {
        return "$E(" + equipno + ")";
    }
    /*public class CallBack implements WebServiceCallback {
        private static final String TAG = "CallBack";
        private final DateType mType;

        public CallBack() {
            mType = String;
        }

        public CallBack(DateType type) {
            mType = type;
        }

        @Override
        public void callback(SoapObject result) {
            if (result != null) {
                Object rstobj = result.getProperty(0);
                String revData = rstobj instanceof SoapObject ? ((SoapObject) rstobj).toString() : rstobj.toString();

                if (revData.equals("false")) {

                Log.d(TAG, "server is have down");
                    return;
                }
                switch (mType) {
                    case String:
                        Log.d(TAG, "data = " + revData);
                        break;
                    case JSON:
                        ArrayList<ItemData>  data = JSONUtil.paserNoHanderJArray(revData, DeviceSetItem.class);
                        for (int i = 0; i < data.size(); i++) {
                            Log.d(TAG, "Json data:" + data.get(i).toString());
                        }
                        break;
                    case XML:
                        Document doc = XMLUtil.StringToXML(revData);
                        //Log.d(TAG, doc.getXMLEncoding());
                        List<Element> list = doc.getRootElement().elements();
                        for (int i = 0; i < list.size(); i++) {
                            Element ele = list.get(i);
                            Log.d(TAG, "Name = "+ ele.attributeValue("Name") + ", EquipNo = "
                                    + ele.attributeValue("EquipNo") + ", IsExpanded = "
                                    + ele.attributeValue("IsExpanded"));
                        }
                        break;
                    case Boolean:
                        break;
                }

            }
        }
    }*/

    /**
     * 注册连接网络服务
     *
     * @param pageUserNm 用户名
     */
    public void initContectSevivce(String pageUserNm, WebServiceCallback callback) {
        HashMap<String, String> properties = new HashMap<>();
        // Log.d(TAG, serAdd.replace("http://", ""));
        properties.put("wcfIP", serAdd.replace("http://", ""));
        properties.put("pageUserNm", pageUserNm);
        WebServiceUtils.callWebService(INIT_ENSURE_RUN_PROXY, properties, callback);
    }

    /**
     * 登陆接口
     *
     * @param nameKey     用户名称
     * @param passwordKey 密码
     */
    public void loginService(String nameKey, String passwordKey, WebServiceCallback callback) {
        HashMap<String, String> properties = new HashMap<>();
        properties.put("nameKey", nameKey);
        properties.put("passwordKey", passwordKey);
        WebServiceUtils.callWebService(LOGIN, properties, callback);
    }

    public void getDevices(WebServiceCallback callback) {
        WebServiceUtils.callWebService(GET_EQUIP_TREE_LISTS, null, callback);
    }

    /**
     * 以XML格式返回设备列表
     */
    public void getDevices2(WebServiceCallback callback) {
        WebServiceUtils.callWebService(GET_EQUIP_TREE_LISTS2, null, callback);
    }

    /**
     * 获取设备的实时数据或设置信息
     *
     * @param selectedEquipNo 设备号
     * @param tableName       数据表类型TableName已定义了对应类型
     */
    public void getDeviceRealtimeData(String selectedEquipNo, String tableName, WebServiceCallback callback) {
        HashMap<String, String> properties = new HashMap<>();
        properties.put("selectedEquipNo", selectedEquipNo);
        properties.put("tableName", tableName);
        WebServiceUtils.callWebService(REAL_TIME_DATA, properties, callback);
    }

    /**
     * 获取单个设备报警状态信息，返回设备实时报警状态信息：
     * 实时报警状态：
     * 0（不通讯），1（通讯正常），2(有报警)，3（正在进行设置），4（正在初始化），5（撤防）
     *
     * @param equipNo  设备号
     * @param userName 操作的用户名
     */
    public void getDeviceRealTimeStatus(String equipNo, String userName, WebServiceCallback callback) {
        HashMap<String, String> properties = new HashMap<>();
        properties.put("expression", getExpression(equipNo));
        properties.put("userName", userName);
        WebServiceUtils.callWebService(EXPRESSION_EVAL, properties, callback);
    }

    /**
     * 通过getRealData获取对应设备号的的set项数据
     *
     * @param set_nm            设备号
     * @param main_instruction  与设备数据
     * @param minor_instruction 与数据库配置字段
     * @param values            数据库字段中的value
     * @param usern             操作员帐号
     */
    public void setUpdates(String set_nm, String main_instruction, String minor_instruction,
                           String values, String usern, WebServiceCallback callback) {
        HashMap<String, String> properties = new HashMap<>();
        properties.put("set_nm", set_nm);
        properties.put("main_instruction", main_instruction);
        properties.put("minor_instruction", minor_instruction);
        properties.put("values", values);
        properties.put("usern", usern);
        WebServiceUtils.callWebService(SET_UPDATES, properties, callback);
    }

    /**
     * 通过sql命令获取数据
     *
     * @param sqlcmd 数据库命令
     */
    public void getDataFromTabel(String sqlcmd, WebServiceCallback callback) {
        HashMap<String, String> properties = new HashMap<>();
        properties.put("sql", sqlcmd);
        WebServiceUtils.callWebService(GET_DATA_TABLE_FROM_SQL_SER, properties, callback);
    }

    /**
     * 获取yxp, ycp, set的设置项数, 返回数据
     * [
     * {"count":"6"},
     * {"count":"6"},
     * {"count":"12"}
     * ]
     *
     * @param equipNo
     * @param user
     * @param callback
     */
    public void realEquipCount(String equipNo, String user, WebServiceCallback callback) {
        HashMap<String, String> properties = new HashMap<>();
        properties.put("equip_no", equipNo);
        properties.put("userName", user);
        WebServiceUtils.callWebService(REAL_EQUIP_COUNT, properties, callback);
    }

    public final class TableName {

        /**
         * 获取所有设备的设备状态
         */
        public static final String ALL = "mypxs";
        /**
         * 获取设备的模拟量
         */
        public static final String DEVICE_ANALOG = "ycp";
        /**
         * 获取设备的状态量
         */
        public static final String DEVICE_STATE_AMOUNT = "yxp";
        /**
         * 获取设备的所有设置项
         */
        public static final String DEVICE_SETTING = "Set";

    }
}
