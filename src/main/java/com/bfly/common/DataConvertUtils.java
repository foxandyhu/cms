package com.bfly.common;

import org.apache.commons.beanutils.ConvertUtils;


/**
 * 数据转化工具类
 *
 * @author 胡礼波
 * 2012-10-31 下午03:44:19
 */
public class DataConvertUtils {

    /**
     * 把字符串转化为数字类型
     *
     * @param data
     * @return
     * @author 胡礼波
     * 2013-3-31 下午1:21:06
     */
    public static Integer convertToInteger(String data) {
        if (data == null || data.length() == 0) {
            return null;
        }
        return (Integer) ConvertUtils.convert(data, Integer.class);
    }

    /**
     * 把字符串转化为数字类型
     *
     * @param data
     * @return
     * @author 胡礼波
     * 2013-3-31 下午1:21:06
     */
    public static Integer[] convertToIntegerArray(String[] data) {
        try {
            return (Integer[]) ConvertUtils.convert(data, Integer.class);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param data
     * @return
     * @author 胡礼波
     * 2013-4-18 下午3:52:03
     */
    public static Boolean convertToBoolean(String data) {
        if (data == null || data.length() == 0) {
            return null;
        }
        return (Boolean) ConvertUtils.convert(data, Boolean.class);
    }

    /**
     * 字符串转换为long型
     *
     * @param data
     * @return
     * @author 胡礼波
     * 2013-5-20 下午3:57:17
     */
    public static Long convertToLong(String data) {
        if (data == null || data.length() == 0) {
            return null;
        }
        return (Long) ConvertUtils.convert(data, Long.class);
    }
}
