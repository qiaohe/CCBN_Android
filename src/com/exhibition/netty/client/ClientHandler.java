package com.exhibition.netty.client;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.util.Log;

import com.exhibition.HomeActivity;
import com.exhibition.MessageActivity;
import com.exhibition.conts.StringPools;
import com.exhibition.db.XmlDB;
import com.exhibition.reciver.MessageReciver;
import com.exhibition.utils.NotificationUtil;
import com.exhibition.utils.Resources;

/**
 * 监听消息回调
 * @author pjq
 *
 */
public class ClientHandler extends SimpleChannelUpstreamHandler {
	private Context context;
	private Intent intent = new Intent();
	private boolean isCheckin = false;
	private String recivedMessage; 
	public ClientHandler(Context context) {
		this.context = context;
	}
	public ClientHandler() {
		
	}
	
	/**
	 * linkService(socket连接)回调
	 */
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		recivedMessage = e.getMessage().toString();  
		if (!e.getMessage().equals(null) && !e.getMessage().equals("")) {
			try {
				XmlDB.getInstance(context).saveKey(StringPools.mServiceToken,recivedMessage);
			} catch (Exception ex) {
				ex.printStackTrace();
			}  
		}
		e.getChannel().write("byte");
		if(isCheckin){
			addMessageToList();
			Notification notification = new Notification(android.R.id.icon, 
														 recivedMessage,
														 System.currentTimeMillis());
			intent = new Intent(context,MessageActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(
							context,0, intent, 0);
			
			notification.setLatestEventInfo(context, "推送的消息", recivedMessage, pendingIntent);	//通知列表里的显示情况
			NotificationManager noManager = (NotificationManager) context.getSystemService(
								Context.NOTIFICATION_SERVICE);
			noManager.notify(110, notification);
		}else{
			intent = new Intent(context,MessageReciver.class);
			context.sendBroadcast(intent);
			isCheckin = true;
		}
	}

	
	private void addMessageToList() {
		Map<String,Object> map = new HashMap<String, Object>();
		Calendar calendar  = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		StringBuilder time = new StringBuilder();
		time.append(hour + ":");
		time.append(minute + "  ");
		map.put("timeAndContent", time.toString() + "         " + recivedMessage);
		Resources.messageMap.add(map);
		
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		e.getChannel().close();
	}

	
}
