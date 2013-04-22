package com.exhibition.reciver;

import com.exhibition.conts.StringPools;
import com.exhibition.db.XmlDB;
import com.exhibition.service.ClientContext;
import com.exhibition.service.ClientService;
import com.exhibition.service.ClientServiceImplForNet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.util.Log;

/**
 * 消息广播接收者
 * @author pjq
 *
 */
public class MessageReciver extends BroadcastReceiver {
	private Context context;
	private ClientService clientService;
	private double latitude;
	private double longitude;
	private String address = "";
	@Override
	public void onReceive(Context context, Intent intent) {

		this.context = context;
		ClientContext clientContext = ClientContext.createClientContext();
		clientService = new ClientServiceImplForNet(clientContext);
		latitude = intent.getDoubleExtra("latitude", 0.0);
		longitude = intent.getDoubleExtra("longitude", 0.0);
		address = intent.getStringExtra("address");
Log.i("data", "reciver: " + latitude + "  " + longitude + " " + address);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
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

	/**
	 * 注册
	 */
	private void registSevice() {
		try {
			if (!XmlDB.getInstance(context)
					.getKeyStringValue(StringPools.mServiceToken, "")
					.equals(""))
				clientService.registerService(
                        XmlDB.getInstance(context).getKeyStringValue(
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
					+ XmlDB.getInstance(context).getKeyStringValue(
							StringPools.mServiceToken, ""));
			clientService.checkIn(
							XmlDB.getInstance(context).getKeyStringValue(
									StringPools.mServiceToken, ""),
									"CCBN",
									latitude,
									longitude, 
									address);
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
