package com.exhibition.service;

import java.util.ArrayList;

import com.exhibition.HomeActivity;
import com.exhibition.conts.StringPools;
import com.exhibition.db.XmlDB;
import com.exhibition.netty.client.ClientData;
import com.exhibition.netty.client.MyClient;
import com.exhibition.utils.MobileConfig;
import com.google.gson.Gson;

import android.app.IntentService;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.IBinder;

public class SocketService extends IntentService {
	
	
	private ArrayList<ClientController>  listController;
	private MobileConfig mMobileConfig;
	private Gson gson;
	private ClientController controller;
	private double latitude;	
	private double longitude;	
	private String address = "";	
	private ClientService clientService;
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
		if (XmlDB.getInstance(this)
				.getKeyStringValue(StringPools.mServiceToken, "").equals("")) {
			MyClient client = new MyClient();
			ClientData data = new ClientData();
			data.setMacAddress(mMobileConfig.getLocalMacAddress());
			data.setAppCode("CCBN");
			String startupMessage = gson.toJson(data);
			client.send(startupMessage, "10.94.5.71", 8888);//本地服务器
			//flag = false;
			System.out.println("----------------clientStart-----------------");
		}
	}
	
	private void registSevice() {
		try {
			if (!XmlDB.getInstance(this)
					.getKeyStringValue(StringPools.mServiceToken, "")
					.equals(""))
				clientService.registService(
						XmlDB.getInstance(this).getKeyStringValue(
								StringPools.mServiceToken, ""), "CCBN",
						"ANDROID");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 签到
	 */
	private void checkIn() {
		try {
			System.out.println("21312321312312321"
					+ XmlDB.getInstance(this).getKeyStringValue(
							StringPools.mServiceToken, ""));
			clientService.checkIn(
							XmlDB.getInstance(this).getKeyStringValue(
									StringPools.mServiceToken, ""),
							"CCBN", latitude,longitude, address);
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		//listController =(ArrayList<ClientController>) intent.getExtras().get("listController");
		//controller = new ClientController();
		clientService =  new ClientServiceImplForNet(
										ClientContext.createClientContext());
		latitude = intent.getDoubleExtra("latitude", 0.0);
		longitude = intent.getDoubleExtra("longitude", 0.0);
		address = intent.getStringExtra("address");
		mMobileConfig = MobileConfig.getMobileConfig(this);
		gson = new Gson();	
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				linkSevice();
				try {
					Thread.sleep(3000);
					registSevice();  
				} catch (InterruptedException e) { 
					e.printStackTrace();
				}  
				checkIn();
			}
		}).start();
		
	}

}
