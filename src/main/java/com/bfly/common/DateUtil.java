package com.bfly.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {
	
	/**
	 * 按照指定格式格式化时间
	 * @author 胡礼波-Andy
	 * @2015年8月4日上午11:53:40
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatterDateTime(Date date,String format)
	{
		SimpleDateFormat sf=new SimpleDateFormat(format);
		return sf.format(date);
	}
	 
	/**
	 * 格式化时间为字符串 格式：yyyy-MM-dd hh:mm:ss
	 * @author 胡礼波
	 * 2012-5-22 下午04:00:42
	 * @return
	 */
	public static String formatterDateTime(Date date)
	{
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(date);
	}
	
	/**
	 * 格式化时间为字符串 格式：yyyy-MM-dd
	 * @author 胡礼波
	 * 2012-5-22 下午04:00:42
	 * @return
	 */
	public static String formatterDate(Date date)
	{
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		return date==null?null:sf.format(date);
	}

	/**
	 * 格式化时间 格式：yyyy-MM-dd
	 * @author 胡礼波
	 * 2012-5-22 下午04:00:42
	 * @return
	 */
	public static Date formatterStrDate(Date date)
	{
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sf.parse(sf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 格式化时间 格式：yyyy-MM-dd hh:mm:ss
	 * @author 胡礼波
	 * 2012-5-22 下午04:00:42
	 * @return
	 */
	public static Date formatterStrDateTime(Date date)
	{
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sf.parse(sf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 字符串日期转化为日期类型 格式：yyyy-MM-dd
	 * @author 胡礼波
	 * 2012-10-30 下午05:56:24
	 * @param dateStr
	 * @return
	 */
	public static Date parseStrDate(String dateStr)
	{
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		Date date=null;
		try
		{
			date=sf.parse(dateStr);
		}catch (Exception e) {
		}
		return date;
		
	}
	
	/**
	 * 字符串日期转化为日期时间类型 格式：yyyy-MM-dd hh:mm:ss
	 * @author 胡礼波
	 * 2012-10-30 下午05:56:24
	 * @param dateStr
	 * @return
	 */
	public static Date parseStrDateTime(String dateStr)
	{
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=null;
		try
		{
			date=sf.parse(dateStr);
		}catch (Exception e) {
		}
		return date;
		
	}
	
	/**
	 * 获得当前时间的字符串比如:20080216
	 * @author 胡礼波
	 * 2012-5-9 下午02:06:26
	 * @return
	 */
	public static String getCurrentDate()
	{
		int year=Calendar.getInstance().get(Calendar.YEAR);
		int month=Calendar.getInstance().get(Calendar.MONTH)+1;
		int day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		String str=year+""+(month>=10?month:"0"+month)+(day>=10?day:"0"+day);
		return str;
	}
	
	/**
	 * 获得当前的时间字符串格式：年月日时分秒毫秒比如:20120102123602001
	 * @author 胡礼波
	 * 2012-11-9 下午01:58:05
	 * @return
	 */
	public static String getCurrentDateTime()
	{
		int hour=Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		int minute=Calendar.getInstance().get(Calendar.MINUTE);
		int second=Calendar.getInstance().get(Calendar.SECOND);
		int milsecond=Calendar.getInstance().get(Calendar.MILLISECOND);
		String str=getCurrentDate()+""+(hour>=10?hour:"0"+hour)+(minute>=10?minute:"0"+minute)+(second>=10?minute:"0"+second)+(milsecond>=100?milsecond:"00"+milsecond);
		return str;
	}
	
	/**
	 * 获得文字类型的时间 比如：12小时前 33分钟前 10秒前 刚刚
	 * @author 胡礼波
	 * 2013-3-21 下午4:10:16
	 * @param date
	 * @return
	 */
	public static String getTimeText(Date date)
	{
		long subTime=(System.currentTimeMillis()-date.getTime())/1000;
		long sub=0;
		sub=subTime/(24*60*60);
		//大于1天
		if(sub>0)
		{
			return formatterDateTime(date);
		}
		sub=subTime%(24*60*60)/(60*60);
		if(sub>0)
		{
			return sub+"小时前"; 
		}
		sub=subTime%(24*60*60)%(60*60)/60;
		if(sub>0)
		{
			return sub+"分钟前";
		}
		sub=subTime%(24*60*60)%(60*60)%60;
		return sub+"秒前";
	}
	
	/**
	 * 获得当天0点时间
	 * @author 胡礼波-Andy
	 * @2015年5月10日下午5:11:46
	 * @return
	 */
	public static Date getCurrentDayMorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获得当天24点时间
	 * @author 胡礼波-Andy
	 * @2015年5月10日下午5:11:41
	 * @return
	 */
	public static Date getCurrentDayNight() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return  cal.getTime();
	}
	
}
