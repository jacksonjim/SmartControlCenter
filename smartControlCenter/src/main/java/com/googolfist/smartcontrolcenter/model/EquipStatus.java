package com.googolfist.smartcontrolcenter.model;

/**
 * Created by Administrator on 2017/6/14.
 */

/**
 * 0（不通讯），1（通讯正常），2(有报警)，3（正在进行设置），4（正在初始化），5（撤防）
 */
public enum EquipStatus {
    DISCONNECTED,   //不通讯
    CONNECTED,      //通讯正常
    ALARM,          //有报警
    SETTING,        //正在进行设置
    INITIALIZING,   //正在初始化
    DISAM;          //撤防

    private int value = 0;

    private EquipStatus(int status) {
        this.value = status;
    }


    EquipStatus() {
    }

    public static EquipStatus valueOf(int status) {
        switch (status) {
            case 0:
                return DISCONNECTED;
            case 1:
                return CONNECTED;
            case 2:
                return ALARM;
            case 3:
                return SETTING;
            case 4:
                return INITIALIZING;
            case 5:
                return DISAM;
            default:
                return null;
        }
    }

    public int value() {
        return this.value;
    }

    public int toInt() {
        switch (this) {
            case DISCONNECTED:
                return 0;
            case CONNECTED:
                return 1;
            case ALARM:
                return 2;
            case SETTING:
                return 3;
            case INITIALIZING:
                return 4;
            case DISAM:
                return 5;
            default:
                return -1;
        }
    }

}
