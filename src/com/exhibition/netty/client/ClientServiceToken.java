package com.exhibition.netty.client;

import android.annotation.SuppressLint;
import java.io.Serializable;

@SuppressLint("ParserError")
public class ClientServiceToken implements Serializable {
	private String serviceToken;
	private String exhibitionCode;
	private String mobilePlatform;

	public String getServiceToken() {
		return serviceToken;
	}

	public void setServiceToken(String serviceToken) {
		this.serviceToken = serviceToken;
	}

	public String getExhibitionCode() {
		return exhibitionCode;
	}

	public void setExhibitionCode(String exhibitionCode) {
		this.exhibitionCode = exhibitionCode;
	}

	public String getMobilePlatform() {
		return mobilePlatform;
	}

	public void setMobilePlatform(String mobilePlatform) {
		this.mobilePlatform = mobilePlatform;
	}
}
