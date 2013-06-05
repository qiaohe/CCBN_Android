package com.exhibition.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;
import android.util.Log;

/**
 * 
 * @author pjq
 *
 */
public class Tools {
	public static String transformToHtml(String data) {
		StringBuilder html = new StringBuilder();  
		html.append("<html>");  
		html.append("<body>");   
		html.append(data);   
		html.append("</body>");
		html.append("</html>");
		return html.toString();
	}  
	
	public static void saveMessage(Map<String,Object> map,Context context){
		SharedPreferences sharedPreferences = 
				context.getSharedPreferences("message", Activity.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		List<Map<String,Object>> messageList = getMessage(context);
		if(null == messageList){
			messageList = new ArrayList<Map<String,Object>>();
		}
		messageList.add(map);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		try {  
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(messageList);
			String strMessageList = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
			editor.putString("messageList", strMessageList);
			editor.commit();
			oos.close();  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				baos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static List<Map<String,Object>> getMessage(Context context){
		List<Map<String,Object>> messageList ;
		SharedPreferences sharedPreferences = 
				context.getSharedPreferences("message", Activity.MODE_PRIVATE);
		String message  = sharedPreferences.getString("messageList", "");
Log.i("info", message);
		byte[] buffer = Base64.decode(message.getBytes(), Base64.DEFAULT);
		ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);
			messageList = (List<Map<String,Object>>)ois.readObject();
			ois.close();
			return messageList;
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				bais.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}
