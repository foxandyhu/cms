package com.bfly.common;

import org.apache.commons.beanutils.ConvertUtils;


/**
 * 数据转化工具类
 * @author 胡礼波
 * 2012-10-31 下午03:44:19
 */
public class DataConvertUtils {

	/**
	 * 把字符串转化为数字类型
	 * @author 胡礼波
	 * 2013-3-31 下午1:21:06
	 * @param data
	 * @return
	 */
	public static int convertToInteger(String data)
	{
		return (Integer)ConvertUtils.convert(data,Integer.class);
	}
	
	/**
	 * 把字符串转化为数字类型
	 * @author 胡礼波
	 * 2013-3-31 下午1:21:06
	 * @param data
	 * @return
	 */
	public static Integer[] convertToInteger(String[] data)
	{
		try {
			return (Integer[])ConvertUtils.convert(data,Integer.class);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @author 胡礼波
	 * 2013-4-18 下午3:52:03
	 * @param data
	 * @return
	 */
	public static boolean convertToBoolean(String data)
	{
		return (Boolean)ConvertUtils.convert(data,Boolean.class);
	}
	
	/**
	 * 字符串转换为long型
	 * @author 胡礼波
	 * 2013-5-20 下午3:57:17
	 * @param data
	 * @return
	 */
	public static Long convertToLong(String data)
	{
		return (Long)ConvertUtils.convert(data,Long.class);
	}
}
