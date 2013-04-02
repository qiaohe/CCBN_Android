package com.exhibition.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {
	public static String getDateTimeByMillisecond(String str) {
		Date date = new Date(Long.valueOf(str));
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日  EEEE");
		String time = format.format(date);
		return time;
	}
	public static String getWeekTimeByMillisecond(String str) {
		Date date = new Date(Long.valueOf(str));
		SimpleDateFormat format = new SimpleDateFormat("EEEE  HH:mm");
		String time = format.format(date);
		return time;
	}
}
