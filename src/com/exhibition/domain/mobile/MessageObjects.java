package com.exhibition.domain.mobile;

public class MessageObjects {
    private static final MessageObject PING = new MessageObject(MessageType.REQ_PING);
    private static final MessageObject PONG = new MessageObject(MessageType.RESP_PONG);
    
    public static MessageObject reqPing() {
        return PING;
    }
    
    public static MessageObject respPong() {
        return PONG;
    }

    public static ReqToken reqToken(String appCode, String macAddress) {
        return new ReqToken(appCode, macAddress);
    }

    public static RespToken respToken(String token) {
        return new RespToken(token);
    }

    public static StringMessage stringMessage(String value) {
        return new StringMessage(value);
    }
}
