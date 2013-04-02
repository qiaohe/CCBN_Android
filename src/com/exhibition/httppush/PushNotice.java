package com.exhibition.httppush;

public class PushNotice {
    
	public static final String NEW_MESSAGES = "new_messages";
    public static final String NEW_COMMENTS = "new_comments";
    public static final String NEW_NOTIFICATIONS = "new_notifications";

    public int ok;
    public int msg_new;
    public int msg_unread;
    public int comment_new;
    public int notification_new;
    public int new_version;
    public int sound;
    public String lastest_msg;
    public String lastest_comment;
    public String lastest_notification;
    
}
