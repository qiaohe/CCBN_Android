package com.exhibition.domain.mobile;

public class ReqToken extends MessageObject {
    private String appCode;
    private String macAddress;

    public ReqToken(String appCode, String macAddress) {
        super(MessageType.REQ_TOKEN);
        this.appCode = appCode;
        this.macAddress = macAddress;
    }

    public ReqToken() {
        super(MessageType.REQ_TOKEN);
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
