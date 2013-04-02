package com.exhibition.httppush;

import com.exhibition.utils.NetworkHelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class HttpPushReceiver extends BroadcastReceiver {

	static final String POWERN_ON_ACTION = "android.intent.action.BOOT_COMPLETED";
	static final String LAUNCH_ACTION = "com.niupai.intent.action.LAUNCH";
	static final String NETWORK_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

	public static final String NOTIFICATION_TYPE = "notification_type";
	public static final String NOTIFICATION_NUMBER = "notification_number";
	/** 网络连接类型. */
	public static int gNetType = -1;

	public void onReceive(Context context, Intent intent) {
		if ((intent.getAction().equals(POWERN_ON_ACTION)
				|| intent.getAction().equals(LAUNCH_ACTION) || intent
				.getAction().equals(NETWORK_CHANGE_ACTION))) {
			HttpPushService.actionStart(context);
			gNetType = NetworkHelper.getInstance(context).getNetType();
		}
	}
}
