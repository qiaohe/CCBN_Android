package com.exhibition.domain.mobile;

public class RespToken extends MessageObject {
    private String token;

    public RespToken(String token) {
        super(MessageType.RESP_TOKEN);
        this.token = token;
    }

    public RespToken() {
        super(MessageType.RESP_TOKEN);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
