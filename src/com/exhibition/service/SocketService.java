package com.exhibition.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import com.exhibition.AppConfig;
import com.exhibition.domain.mobile.MessageObjects;
import com.exhibition.netty.client.MyClient;
import com.exhibition.utils.MobileConfig;

public class SocketService extends IntentService {
    public SocketService(String name) {
        super(name);
    }

    public SocketService() {
        super("1");
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    private void linkService() {
        MyClient client = new MyClient(getApplicationContext());
        MobileConfig mMobileConfig = MobileConfig.getMobileConfig(this);
        client.send(AppConfig.HOST, AppConfig.MESSAGE_PORT,
                MessageObjects.reqToken(AppConfig.APP_CODE, mMobileConfig.getLocalMacAddress()));
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                linkService();
            }
        }).start();
    }
}
