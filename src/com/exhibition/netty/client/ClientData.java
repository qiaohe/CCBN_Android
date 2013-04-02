package com.exhibition.netty.client;

import java.io.Serializable;

public class ClientData implements Serializable{
	private String macAddress;
	private String appCode;
	
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
}
