package com.exhibition.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.exhibition.conts.StringPools;
import com.exhibition.db.XmlDB;
import com.exhibition.entities.EventData;
import com.exhibition.entities.EventData.EventSchedule;
import com.exhibition.entities.EventData.Exhibitor;
import com.exhibition.entities.EventData.Speaker;
import com.google.gson.Gson;

import android.content.Context;

public class DataUtil {
	private static EventData mEventData;
	private static String JSONDATA;
	/**得到会展数据*/
	public static EventData getEventData(Context context){
		JSONDATA = XmlDB.getInstance(context).getKeyStringValue(StringPools.CCBN_ALL_DATA, "");
		if(!JSONDATA.equals("")){
			return mEventData = new Gson().fromJson(JSONDATA, EventData.class);
		}else{
			return mEventData = new Gson().fromJson(getEventDataDefault(context), EventData.class);
		}  
	}
	/**会展日程数组*/
	public static ArrayList<EventSchedule> getEventSchedules(Context context){
		return getEventData(context).getEventSchedules();
	}
	/**参展商数组*/
	public static ArrayList<Exhibitor> getExhibitors(Context context){
		return getEventData(context).getExhibitors();
	}
	/**发言人数组*/
	public static ArrayList<Speaker> getSpeakers(Context context){
		return getEventData(context).getSpeakers();
	}
	/**
	 * 获取客户端默认配置的数据
	 */
	public static String getEventDataDefault(Context context) {
		InputStream is;
		String str = "";
		try {
			is = context.getAssets().open("ccbnData");
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);

			str = new String(buffer, "utf-8");
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
}
