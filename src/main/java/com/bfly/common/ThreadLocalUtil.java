package com.bfly.common;

/**
 * ThreadLocal工具类
 * @author 胡礼波
 * 2014-9-22 下午1:39:48
 */
public class ThreadLocalUtil {

	private static ThreadLocal<Object> threadLocal=new ThreadLocal<>();
	
	/**
	 * 把数据放到ThreadLocal中
	 * @author 胡礼波
	 * 2014-9-22 下午1:44:20
	 * @param object
	 */
	public static void set(Object object)
	{
		threadLocal.set(object);
	}
	
	/**
	 * 获得ThreadLocal中数据 并移除ThreadLocal中的数据
	 * @author 胡礼波
	 * 2014-9-22 下午1:49:23
	 * @param cls
	 * @return
	 */
	public static <T> T get(Class<T> cls)
	{
		try
		{
			T t=cls.cast(threadLocal.get());
			return t;
		}catch (Exception ex) {
			return null;
		}finally
		{
			remove();
		}
	}
	
	/**
	 * 移除ThreadLocal中数据
	 * @author 胡礼波
	 * 2014-9-22 下午1:49:47
	 */
	public static void remove()
	{
		threadLocal.remove();
	}
	
}
