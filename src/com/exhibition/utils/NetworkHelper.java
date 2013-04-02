package com.exhibition.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.telephony.TelephonyManager;

/**
 * @Description:用于判断手机网络连接状态的工具类.
 * 
 * @author brain
 * 
 * @Date 2011-12-14 下午12:09:12
 * 
 */
public class NetworkHelper {

	public static final int TYPE_WIFI = 0;
	public static final int TYPE_FAST_MOBILE = 1;
	public static final int TYPE_NORMAL_MOBILE = 2;
	private Context mCon;
	private static NetworkHelper mNetHelper;

	public NetworkHelper(Context mCon) {
		this.mCon = mCon;
	}

	public static NetworkHelper getInstance(Context mCon) {
		if (mNetHelper == null) {
			mNetHelper = new NetworkHelper(mCon);
		}
		return mNetHelper;
	}

	/** 判断手机是否处于连接网络或断网状态. */
	public boolean isConnected() {
		ConnectivityManager mConManager = (ConnectivityManager) mCon.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (mConManager != null) {
			NetworkInfo info = mConManager.getActiveNetworkInfo();
			if (info != null && info.isConnectedOrConnecting()) {
				if (info.getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}

	/** 检查手机网络是否正常(可连接或不可连接2种状态) */
	public boolean isNetSucces() {
		ConnectivityManager cwjManager = (ConnectivityManager) mCon.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cwjManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			return true;
		} else {
			return false;
		}
	}

	public int getNetType() {
		ConnectivityManager manager = (ConnectivityManager) mCon.getSystemService(Context.CONNECTIVITY_SERVICE);
		State mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		TelephonyManager tm = (TelephonyManager) mCon.getSystemService(Context.TELEPHONY_SERVICE); // 获得手机SIMType 　　
		int subType = tm.getNetworkType();
		// 如果3G、wifi、2G等网络状态是连接的，则退出，否则显示提示信息进入网络设置界面
		if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
			return TYPE_WIFI;
		}
		if (mobile == State.CONNECTED || mobile == State.CONNECTING) {
			return isConnectionFast(ConnectivityManager.TYPE_MOBILE, subType) ? TYPE_FAST_MOBILE : TYPE_NORMAL_MOBILE;
		}
		return -1;
	}

	public static boolean isConnectionFast(int type, int subType) {
		if (type == ConnectivityManager.TYPE_WIFI) {
			return true;
		} else if (type == ConnectivityManager.TYPE_MOBILE) {
			switch (subType) {
			case TelephonyManager.NETWORK_TYPE_1xRTT:
				return false; // ~ 50-100 kbps
			case TelephonyManager.NETWORK_TYPE_CDMA:
				return false; // ~ 14-64 kbps
			case TelephonyManager.NETWORK_TYPE_EDGE:
				return false; // ~ 50-100 kbps
			case TelephonyManager.NETWORK_TYPE_EVDO_0:
				return true; // ~ 400-1000 kbps
			case TelephonyManager.NETWORK_TYPE_EVDO_A:
				return true; // ~ 600-1400 kbps
			case TelephonyManager.NETWORK_TYPE_GPRS:
				return false; // ~ 100 kbps
				// case TelephonyManager.NETWORK_TYPE_HSDPA:
				// return true; // ~ 2-14 Mbps
				// case TelephonyManager.NETWORK_TYPE_HSPA:
				// return true; // ~ 700-1700 kbps
				// case TelephonyManager.NETWORK_TYPE_HSUPA:
				// return true; // ~ 1-23 Mbps
			case TelephonyManager.NETWORK_TYPE_UMTS:
				return true; // ~ 400-7000 kbps
				// NOT AVAILABLE YET IN API LEVEL 7
				// case Connectivity.NETWORK_TYPE_EHRPD:
				// return true; // ~ 1-2 Mbps
				// case Connectivity.NETWORK_TYPE_EVDO_B:
				// return true; // ~ 5 Mbps
				// case Connectivity.NETWORK_TYPE_HSPAP:
				// return true; // ~ 10-20 Mbps
				// case Connectivity.NETWORK_TYPE_IDEN:
				// return false; // ~25 kbps
				// case Connectivity.NETWORK_TYPE_LTE:
				// return true; // ~ 10+ Mbps
				// Unknown
			case TelephonyManager.NETWORK_TYPE_UNKNOWN:
				return false;
			default:
				return false;
			}
		} else {
			return true;
		}
	}

	public String getAPN() {
		try {
			ConnectivityManager manager = (ConnectivityManager) mCon.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netWrokInfo = manager.getActiveNetworkInfo();
			String apnString=netWrokInfo.getExtraInfo();
			if (apnString!=null) {
				return apnString;
			}else {
				return "";
			}
		} catch (Exception e) {
			return "";
		}
	}
}
