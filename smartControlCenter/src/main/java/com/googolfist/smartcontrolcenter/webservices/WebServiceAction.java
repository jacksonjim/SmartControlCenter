package com.googolfist.smartcontrolcenter.webservices;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/14.
 */

public class WebServiceAction {
    public static final int INIT_ENSURE_RUN_PROXY = 0;      //InitEnsureRunProxy
    public static final int LOGIN = 1;                      //Login
    public static final int GET_EQUIP_TREE_LISTS = 2;       //GET_EQUIP_TREE_LISTS
    public static final int GET_EQUIP_TREE_LISTS2 = 3;      //GET_EQUIP_TREE_LISTS2
    public static final int REAL_TIME_DATA = 4;             //REAL_TIME_DATA
    public static final int EXPRESSION_EVAL = 5;            //EXPRESSION_EVAL
    public static final int SET_UPDATES = 6;                //SET_UPDATES
    public static final int GET_DATA_TABLE_FROM_SQL_SER = 7;//GET_DATA_TABLE_FROM_SQL_SER
    public static final int REAL_EQUIP_COUNT = 8;           //REAL_EQUIP_COUNT


    private static final ArrayList<String> ACTION_LIST = new ArrayList<String>(8) {{
        add("InitEnsureRunProxy"); //1
        add("Login"); //2
        add("GetEquipTreeLists"); //3
        add("GetEquipTreeLists2"); //4
        add("RealTimeData"); //5
        add("ExpressionEval"); //6
        add("SetUpdateS"); //7
        add("GetDataTableFromSQLSer"); //8
        add("RealEquipCount"); //8
    }};

    public static String getActionName(final int action) {
        return ACTION_LIST.get(action);
    }
}
