package com.exhibition.netty.client;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.exhibition.MessageActivity;
import com.exhibition.conts.StringPools;
import com.exhibition.db.XmlDB;
import com.exhibition.reciver.MessageReciver;
import com.exhibition.utils.Resources;
import org.jboss.netty.channel.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 监听消息回调
 *
 * @author pjq
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
	/*public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		
		recivedMessage = e.getMessage().toString();
		if (! (null == e.getMessage()) && !"".equals(e.getMessage())) {
			try {
				XmlDB.getInstance(context).saveKey(StringPools.mServiceToken,recivedMessage);
			} catch (Exception ex) {
				ex.printStackTrace();
			}  
		}
		e.getChannel().write("byte");
		if(isCheckin ){
			addMessageToList();
			Notification notification = new Notification(android.R.id.icon, 
														 recivedMessage,
														 System.currentTimeMillis());
			if(!recivedMessage.equals(":[PING:PONG]")){
				intent = new Intent(context,MessageActivity.class);
				PendingIntent pendingIntent = PendingIntent.getActivity(
								context,0, intent, 0);
				
				notification.setLatestEventInfo(context, "推送的消息", recivedMessage, pendingIntent);	//通知列表里的显示情况
				NotificationManager noManager = (NotificationManager) context.getSystemService(
									Context.NOTIFICATION_SERVICE);
				notification.defaults = Notification.DEFAULT_SOUND;
				noManager.notify(110, notification);
			}
		}else{
			intent = new Intent(context,MessageReciver.class);
			intent.putExtra("latitude", Resources.latitude);
			intent.putExtra("longitude", Resources.longitude);
			intent.putExtra("address", Resources.address);
			context.sendBroadcast(intent);
			isCheckin = true;
		}
	}*/

	
	private void addMessageToList() {
		if(!(":[PING:PONG]".equals(recivedMessage))){
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
	}
	
	/*public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		e.getChannel().close();
	}  */ 
	
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		super.channelDisconnected(ctx, e);
Log.i("data","reconnect");
		/*Intent intent = new Intent(context,SocketService.class);
=======
    public static final String HEART_BEAT = ":[PING:PONG]";
    private Context context;
    private boolean checkedIn;

    public ClientHandler(Context context) {
        this.context = context;
    }

    public ClientHandler() {
    }

    /**
     * linkService(socket连接)回调
     */
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        String message = e.getMessage().toString();
        if (message == null) {
            return;
        }
        while (message.startsWith(HEART_BEAT)) {
            message = message.substring(HEART_BEAT.length());
        }
        if (message.isEmpty()) {
            return;
        }
        // It is not be able to divide service token from text message for now.
        // Just assuming the first message received will be service token, but eventually,
        // the doom day will be there.
        if (!checkedIn) {
            try {
                checkedIn = true;
                XmlDB.getInstance(context).saveKey(StringPools.mServiceToken, message);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            addMessageToList(message);
            Notification notification = new Notification(android.R.id.icon, message, System.currentTimeMillis());
            Intent intent = new Intent(context, MessageActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            notification.setLatestEventInfo(context, "推送的消息", message, pendingIntent);
            NotificationManager noManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notification.defaults = Notification.DEFAULT_SOUND;
            noManager.notify(110, notification);
        }
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.channelConnected(ctx, e);
        Intent intent = new Intent(context, MessageReciver.class);
        intent.putExtra("latitude", Resources.latitude);
        intent.putExtra("longitude", Resources.longitude);
        intent.putExtra("address", Resources.address);
        context.sendBroadcast(intent);
    }

    private void addMessageToList(String message) {
        if (!message.equals(":[PING:PONG]")) {
            Map<String, Object> map = new HashMap<String, Object>();
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            StringBuilder time = new StringBuilder();
            time.append(hour + ":");
            time.append(minute + "  ");
            map.put("timeAndContent", time.toString() + "         " + message);
            Resources.messageMap.add(map);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        try {
            e.getChannel().close();
        } catch (Exception ignore) {
        }
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.channelDisconnected(ctx, e);
        Log.i("data", "reconnect");
        /*Intent intent = new Intent(context,SocketService.class);
>>>>>>> 7640277c0830372432f0b0f38b0cc7a20903088a
		context.startService(intent);*/

    }
}
