package com.exhibition.netty.client;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.exhibition.conts.StringPools;
import com.exhibition.db.XmlDB;
import com.exhibition.utils.NotificationUtil;

public class ClientHandler extends SimpleChannelUpstreamHandler {
	private Context context;
	private Intent it = new Intent();

	/**
	 * linkService(socket连接)回调
	 */
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
Log.i("data", "socket link success");
		if (!e.getMessage().equals(null) && !e.getMessage().equals("")) {
			try {
				XmlDB.getInstance(context).saveKey(StringPools.mServiceToken,
						e.getMessage().toString());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
//			NotificationUtil.testNotification(context, it, e.getMessage()
//					.toString());
		}
		e.getChannel().write("byte");
	}

	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		e.getChannel().close();
	}
}
