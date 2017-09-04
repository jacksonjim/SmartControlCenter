package com.googolfist.smartcontrolcenter.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Administrator on 2017/7/1.
 */

public class DangerousPermissionsHelper {

    public static final int PERMISSION_REQUEST_CODE = 1;

    //CALENDAR
    private static final String READ_CALENDAR = Manifest.permission.READ_CALENDAR;
    private static final String WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR;
    //CAMERA
    private static final String CAMERA = Manifest.permission.CAMERA;
    //CONTACTS
    private static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    private static final String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;
    private static final String GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    //LOCATION
    private static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    //MICROPHONE
    private static final String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    //PHONE
    private static final String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    private static final String CALL_PHONE = Manifest.permission.CALL_PHONE;
    private static final String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
    private static final String WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG;
    private static final String ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;
    private static final String USE_SIP = Manifest.permission.USE_SIP;
    private static final String PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;
    //SENSORS
    private static final String BODY_SENSORS = Manifest.permission.BODY_SENSORS;
    //SMS
    private static final String SMS = Manifest.permission.SEND_SMS;
    private static final String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
    private static final String READ_SMS = Manifest.permission.READ_SMS;
    private static final String RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH;
    private static final String RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;
    //STORAGE
    private static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    public static String[] getExternalStorage() {
        return new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};
    }

    public static String[] getPhone() {
        return new String[]{READ_PHONE_STATE, CALL_PHONE};
    }

    public static boolean checkSelfPremission(Context context, String... permisions) {
        if (permisions == null || permisions.length == 0) {
            return true;
        }

        for (String permission : permisions) {
            boolean result = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
            if (!result) {
                return false;
            }
        }
        return true;

    }

    public static void requitPermission(Activity activity, String... premisions) {
        ActivityCompat.requestPermissions(activity, premisions, PERMISSION_REQUEST_CODE);
    }
}
