package com.example.administrator.meituan.Service;

import android.app.IntentService;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Administrator on 2016/9/12.
 */
public class ServiceForOrder extends IntentService {

    public ServiceForOrder() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

}
