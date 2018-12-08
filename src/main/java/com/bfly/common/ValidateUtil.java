package com.bfly.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.internet.InternetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据校验工具类
 * @author 胡礼波
 * @2013-2-11 @下午11:35:47
 */
public class ValidateUtil {

	private static Logger logger= LoggerFactory.getLogger(ValidateUtil.class);
	private static Pattern phonePattern = Pattern.compile("^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|18[0-9]{9}$");

	/**
	 * 是否为手机号码
	 * @author 胡礼波-Andy
	 * @2014年12月4日下午3:38:49
	 */
	public static boolean isCellPhone(String phoneNo)
	{
		if(phoneNo==null || phoneNo.length()==0)
		{
			return false;
		}

		Matcher m = phonePattern.matcher(phoneNo);
		return m.matches();  
	}


	/**
	 * 邮箱校验
	 * @author 胡礼波
	 * @2013-2-11 @下午11:37:44
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email)
	{
		boolean flag=false;
		try
		{
			InternetAddress address=new InternetAddress(email);
			address.validate();
			flag=true;
		}catch (Exception e) {
			logger.warn("邮箱格式不正确!");
			flag=false;
		}
		return flag;
	}
}
