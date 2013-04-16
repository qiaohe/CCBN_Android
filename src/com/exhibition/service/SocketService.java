package com.exhibition.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import com.exhibition.AppConfig;
import com.exhibition.netty.client.ClientData;
import com.exhibition.netty.client.MyClient;
import com.exhibition.utils.MobileConfig;
import com.google.gson.Gson;

public class SocketService extends IntentService {

    private MobileConfig mMobileConfig;
    private Gson gson;

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

    private void linkSevice() {
        /*if (XmlDB.getInstance(this)
				.getKeyStringValue(StringPools.mServiceToken, "").equals("")) {*/
        MyClient client = new MyClient(getApplicationContext());
        ClientData data = new ClientData();
        mMobileConfig = MobileConfig.getMobileConfig(this);
        gson = new Gson();
        data.setMacAddress(mMobileConfig.getLocalMacAddress());
        data.setAppCode("CCBN");
        String startupMessage = gson.toJson(data);
        client.send(startupMessage, AppConfig.HOST, AppConfig.MESSAGE_PORT);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                linkSevice();
            }
        }).start();

    }

}
