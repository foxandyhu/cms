package com.bfly.common;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

public class FileUtil {
	
	/**
	 * 检查文件是否存在
	 * @author 胡礼波-Andy
	 * @2015年5月16日下午7:17:31
	 * @param filePath
	 * @return
	 */
	public static boolean checkExist(String filePath)
	{
		File file=new File(filePath);
		return file.exists();
	}
	
	/**
	 * 项目路径
	 * @author 胡礼波-Andy
	 * @2015年5月16日下午7:17:23
	 * @param request
	 * @return
	 */
	public static String getAppBase(HttpServletRequest request)
	{
		return request.getServletContext().getRealPath("/");
	}
	
	/**
	 * 获得系统临时目录
	 * @author 胡礼波-Andy
	 * @2015年5月10日下午8:30:13
	 * @return
	 */
	public static String getSysTempPath()
	{
		return System.getProperty("java.io.tmpdir");
	}
}
