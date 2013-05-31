package com.exhibition.domain.mobile;

public class StringMessage extends MessageObject {
    private String value;

    public StringMessage(String value) {
        super(MessageType.STRING);
        this.value = value;
    }

    public StringMessage() {
        super(MessageType.STRING);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
