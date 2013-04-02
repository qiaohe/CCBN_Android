package com.exhibition.utils;

import com.exhibition.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: zhanggb
 * Date: 13-3-30
 * Time: 下午5:23
 * To change this template use File | Settings | File Templates.
 */
public class NotificationUtil {

    // 通知管理器
    private static NotificationManager mNotificationManager;
    // 通知显示内容
    private static PendingIntent mPendingIntent;

    public static void testNotification(Context context, Intent intent, String chatRecordStr) {
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mPendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Notification mBaseNotification = new Notification();
        // 设置通知的图标（在状态栏和下拉栏都会显示此图标）
        mBaseNotification.icon = R.drawable.icon;
        // 设置通知显示的内容（状态栏）
//        mBaseNotification.tickerText = "You Clicked Base Notification！";
        mBaseNotification.tickerText = chatRecordStr;
        // 设置通知的默认提示音，振动，灯光
        // 【|=】的意思是3个都有效，要是【=】的话，只有最后一个有效
        // 如果要全部采用默认值, 用 DEFAULT_ALL
        mBaseNotification.defaults |= Notification.DEFAULT_SOUND;// 默认提示音
        mBaseNotification.defaults |= Notification.DEFAULT_VIBRATE;// 默认振动
        mBaseNotification.defaults |= Notification.DEFAULT_LIGHTS;// 默认灯光
        // 让声音、振动无限循环，直到用户响应
//        mBaseNotification.flags |= Notification.FLAG_INSISTENT;
        // 通知被点击后，自动消失
        mBaseNotification.flags |= Notification.FLAG_AUTO_CANCEL;
        // 点击'Clear'时，不清除该通知(QQ的通知无法清除，就是用的这个)
        mBaseNotification.flags |= Notification.FLAG_NO_CLEAR;
        // 第二个参数：下拉状态栏时显示的消息标题
        // 第三个参数：下拉状态栏时显示的消息内容
        // 第四个参数：点击该通知时执行页面跳转
        mBaseNotification.setLatestEventInfo(context, "Title01", chatRecordStr, mPendingIntent);
        // 发出状态栏通知
        // The first parameter is the unique ID for the Notification
        // The second parameter is the Notification object.
        mNotificationManager.notify(001, mBaseNotification);

    }
}
