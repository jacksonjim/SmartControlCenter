package com.googolfist.smartcontrolcenter.receviver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.googolfist.smartcontrolcenter.services.SmartHomeService;

/**
 * Created by Administrator on 2017/6/13.
 */

public class BootBrocastRecevicer extends BroadcastReceiver {
    private static final String TAG = "BootBrocastRecevicer";

    public BootBrocastRecevicer() {
        super();

    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return super.peekService(myContext, service);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: action = " + intent.getAction());
        if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())) {
            Intent srvIntent = new Intent(SmartHomeService.ACTION_SMART_HOME);
            context.sendBroadcast(srvIntent);
        }

        if (SmartHomeService.ACTION_SMART_HOME.equals(intent.getAction())) {
            Log.d(TAG, "onReceive: " + intent);
            context.startService(intent);
        }
    }
}
