package com.googolfist.smartcontrolcenter.services;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.IntDef;
import android.util.Log;

public class PollingService extends Service {
    private static final String TAG = "PollingService";
    public static final String ACTION = "com.googolfist.smartcontrolcenter.services.PollingService";
    private ServiceConnection serviceConnection;
    private ISmartHomeService mISmartService;

    public PollingService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mISmartService = ISmartHomeService.Stub.asInterface(service);
                Log.d(TAG, "onServiceConnected: " + name);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mISmartService = null;
            }
        };

        Intent bindIntent = new Intent(getApplicationContext(), SmartHomeService.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("flag", true);
        bindIntent.putExtras(bundle);
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        pollingDevice();
        return START_STICKY;
    }

    private void pollingDevice() {
        new Thread() {
            @Override
            public void run() {
                if (mISmartService != null) {
                    try {
                        mISmartService.pollingDevice();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
