package com.exhibition.conts;

public class StringPools {
	/**
	 * App所有数据
	 */
	public final static String CCBN_ALL_DATA = "ccbn_all_data";
	/**
	 * 保存的键值信息只有当清除数据或卸载应用后才会进行数据清空操作. 
	 * 也用于保存例如上一次登陆用户的id信息，保存应用的版本信息.
	 */
	public static final String PermanentSetting = "mPermanentSetting";
	/** 客户端保存设备唯一标识位的键值. */
	public static final String mDeviceIdKey = "mDeviceIdKey";
	/** 客户端图片下载URL. */
	public static final String baiDuMapStrKey = "63541DA073C3E684B5815C6A1FB24599E42FF479";
	public static final String mPushIntervalKey = "mPushInterval";
	public static final String mServiceToken = "serviceToken";
	public static final String SHARED_PREFERENCE_NAME = "client_preferences";
	public static final String MAC_ID = "mac_id";
	
	
	
	// INTENT ACTIONS

    public static final String ACTION_SHOW_NOTIFICATION = "org.androidpn.client.SHOW_NOTIFICATION";

    public static final String ACTION_NOTIFICATION_CLICKED = "org.androidpn.client.NOTIFICATION_CLICKED";

    public static final String ACTION_NOTIFICATION_CLEARED = "org.androidpn.client.NOTIFICATION_CLEARED";

 // NOTIFICATION FIELDS

    public static final String NOTIFICATION_ID = "NOTIFICATION_ID";

    public static final String NOTIFICATION_API_KEY = "NOTIFICATION_API_KEY";

    public static final String NOTIFICATION_TITLE = "NOTIFICATION_TITLE";

    public static final String NOTIFICATION_MESSAGE = "NOTIFICATION_MESSAGE";

    public static final String NOTIFICATION_URI = "NOTIFICATION_URI";

}
