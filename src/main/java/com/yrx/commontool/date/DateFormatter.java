package com.yrx.commontool.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * 日期格式转化类
 * @author zjj
 *
 */
public class DateFormatter {
	static final SimpleDateFormat sDateFormatYYYYMMDDHHmm = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
	static final SimpleDateFormat sDateFormatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
	
	public static Date getDateDMYHMS(String times){
		
		
		Date date = null;
		try {
			if(times != null){
				sDateFormatYYYYMMDDHHmm.setTimeZone(getTimeZone(8));
				date = sDateFormatYYYYMMDDHHmm.parse(times);
			}			
		} catch (ParseException e) {
			e.printStackTrace();			
		} 
		return date;
	}

	private static TimeZone getTimeZone(int timeZoneOffset){
		if (timeZoneOffset > 13 || timeZoneOffset < -12) {  
	        timeZoneOffset = 0;  
	    }  
		TimeZone timeZone;
	    String[] ids = TimeZone.getAvailableIDs(timeZoneOffset * 60 * 60 * 1000);
	    if (ids.length == 0) {  
	        // if no ids were returned, something is wrong. use default TimeZone  
	        timeZone = TimeZone.getDefault();
	    } else {  
	        timeZone = new SimpleTimeZone(timeZoneOffset * 60 * 60 * 1000, ids[0]);
	    }  	   
	    return timeZone;
	}
	
	public static Date getDateFromMilliSeconds(Long times){
		try{
			return new Date(times);
		}catch(Exception e){
			
		}
		return null;
	}

	/**
	 * 返回日期格式字符串(yyyy-MM-dd HH:mm)
	 * @param times
	 * @return
	 */
	public static String gettDateFromStr(Long times) {
		String strdate = "";
		strdate = sDateFormatYYYYMMDDHHmm.format(new Date(times));
		return strdate;
	}

	/**
	 * 返回日期格式字符串(yyyy-MM-dd)
	 * @param times
	 * @return
	 */
	public static String gettDateFromStrYMD(Long times) {
		String strdate = "";
		strdate = sDateFormatYYYYMMDD.format(new Date(times));
		return strdate;
	}

	/**
	 * 格式化日期字符串
	 * @param date	日期字符串 如"2016-7-27 09:40:20"
	 * @param oldPattern  旧的格式 如"yyyy-MM-dd HH:mm:ss"
	 * @param newPattern  新的格式 如"yyyy年MM月dd日 HH时mm分ss秒"
     * @return
     */
	public static String dateStringFormat(String date, String oldPattern, String newPattern) {
		if (date == null || oldPattern == null || newPattern == null)
			return "";
		SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern) ;        // 实例化模板对象
		SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern) ;        // 实例化模板对象
		Date d = null ;
		try{
			d = sdf1.parse(date) ;   // 将给定的字符串中的日期提取出来
		}catch(Exception e){            // 如果提供的字符串格式有错误，则进行异常处理
			e.printStackTrace() ;       // 打印异常信息
			return "";
		}
		return sdf2.format(d);
	}

}
