package com.exhibition.domain.mobile;

import android.util.Log;

import java.io.Serializable;

public class MessageObject implements Serializable {
    private MessageType type;

    public MessageObject() {
        Log.i("md_msg", "MessageObject");
    }

    public MessageObject(MessageType type) {
        this.type = type;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
