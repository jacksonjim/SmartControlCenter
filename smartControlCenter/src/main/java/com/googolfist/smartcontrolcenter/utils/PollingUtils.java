package com.googolfist.smartcontrolcenter.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * Created by Administrator on 2017/6/13.
 */

public class PollingUtils {
    public static void startPollingService(Context context, int ms, Class<?> cls, String action) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long tiggetAtTime = SystemClock.elapsedRealtime();

        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, tiggetAtTime, ms, pendingIntent);
    }

    public static void stopPollingService(Context context, Class<?> cls, String action) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);
    }
}
