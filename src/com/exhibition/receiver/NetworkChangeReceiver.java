package com.exhibition.receiver;

import com.exhibition.service.SocketService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * 网络状态广播
 * @author pjq
 *
 */
public class NetworkChangeReceiver extends BroadcastReceiver {
	private static final String CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if(CONNECTIVITY_CHANGE.equals(action)){
Log.i("data", "网络状态发生了改变");
			ConnectivityManager connectivityManager = 
					(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			if(null != networkInfo){
				if(networkInfo.isAvailable()){
Log.i("data", "当前网络是可用的");
					Intent socketServiceIntent = new Intent(context,SocketService.class);
					context.startService(socketServiceIntent);
				}
			}else{
Log.i("data", "当前无可用网络");  
			}
		}  
	}

}
