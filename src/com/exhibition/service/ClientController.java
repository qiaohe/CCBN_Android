package com.exhibition.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.telephony.TelephonyManager;

/**
 * 客户端控制器类，用来控制页面的跳转，响应界面按钮事件。该类在整个应用程序中单例
 * @author clhe
 */
public class ClientController implements Serializable {
	/**
	 * 客户端上下文，该对象用来缓存客户端业务对象及配置参数
	 */
	private ClientContext context;
	/**
	 * 控制器单例对象
	 */
	private static ClientController controller = null;
	/**
	 * 当前Android活动对象(页面Activity)
	 */
	private Activity currentActivity;
	/**
	 * 服务对象
	 */
	private ClientService service;
	/**
	 * 手机信息对象
	 */
	private TelephonyManager phoneManager;


	private ClientController(Activity act) {
		this.currentActivity = act;
		context = ClientContext.createClientContext();
		loadClientConfig();
		service = new ClientServiceImplForNet(context);
	}
	
	public ClientController() {
		context = ClientContext.createClientContext();
		loadClientConfig();
		service = new ClientServiceImplForNet(context);
	}

	/**
	 * 加载配置文件
	 */
	private void loadClientConfig() {
		Properties pro = new Properties();
		AssetManager asset = currentActivity.getAssets();
		InputStream is;
		try {
			is = asset.open("config" + File.separator
					+ "client_config.properties");
			pro.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		context.setConfigProperties(pro);
	}

	public ClientContext getContext() {
		return context;
	}

	public Activity getCurrentActivity() {
		return currentActivity;
	}

	public void setCurrentActivity(Activity act) {
		this.currentActivity = act;
	}

	public ClientService getService() {
		return service;
	}

	public void setService(ClientService service) {
		this.service = service;
	}

	/**
	 * 得到单例的ClientController对象
	 */
	public static synchronized ClientController getController() {
		if (controller == null) {
			controller = new ClientController(null);
		}
		return controller;
	}

	/**
	 * 得到（controller相互关联Activity）的对象
	 */
	public static synchronized ClientController getController(Activity act) {
		if (controller == null) {
			controller = new ClientController(act);
		} else {
			controller.setCurrentActivity(act);
		}
		return controller;
	}
	/**
	 * 获取手机IMEI码
	 */
	public String getImei(){
		return phoneManager.getDeviceId();
	}
}
