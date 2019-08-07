package com.bfly.common;

import com.bfly.core.Constants;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * 字符串工具类
 *
 * @author 胡礼波
 * 2012-5-18 下午03:02:13
 */
public class StringUtil {

    /**
     * 随机生成指定长度的字符串
     *
     * @param length
     * @return
     * @author 胡礼波-andy
     * @2013-6-23下午6:24:04
     */
    public static String getRandomString(int length) {
        String base = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 随机生成指定长度的字符串数字
     *
     * @param length
     * @return
     * @author 胡礼波-andy
     * @2013-6-23下午6:24:04
     */
    public static String getRandomNumber(int length) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 把字符串转为UTF-8类型的字符串
     *
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     * @author 胡礼波
     * 2012-5-18 下午03:04:29
     */
    public static String chartDecodeToUTF8(String data) {
        if (data == null || data.length() == 0) {
            return null;
        }
        try {
            return new String(data.getBytes("ISO-8859-1"), Constants.ENCODEING_UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 产生随机的最大数为maxNum的数字
     *
     * @param maxNum
     * @return
     * @author 胡礼波
     * 2012-6-6 下午02:49:42
     */
    public static String getRandom(int maxNum) {
        int num = 1 + new Random().nextInt(maxNum - 1);
        return String.valueOf(num);
    }

    /**
     * 去掉所有的html标签
     *
     * @param html
     * @return
     * @author 胡礼波
     * 2013-3-10 下午4:56:18
     */
    public static String trimHtmlTag(String html) {
        if (html == null || html.length() == 0) {
            return null;
        }
        return trimAllTags(html, false).replaceAll("\r", "").replaceAll("\n", "");
    }

    /**
     * @param input
     * @param inside
     * @return
     * @author 胡礼波
     * 2013-4-25 上午10:49:35
     */
    private static String trimAllTags(String input, boolean inside) {
        StringBuffer output = new StringBuffer();

        if (inside) {
            if ((input.indexOf('<') == -1) || (input.lastIndexOf('>') == -1) || (input.lastIndexOf('>') < input.indexOf('<'))) {
                output.append(input);
            } else {
                output.append(input.substring(0, input.indexOf('<')));
                output.append(input.substring(input.lastIndexOf('>') + 1, input.length()));
            }
        } else {
            boolean write = true;
            for (int index = 0; index < input.length(); index++) {
                if ((input.charAt(index) == '<') && (write)) {
                    write = false;
                }
                if (write) {
                    output.append(input.charAt(index));
                }
                if ((input.charAt(index) == '>') && (!write)) {
                    write = true;
                }
            }
        }
        return output.toString();
    }

    /**
     * 得到易懂的大小带单位
     *
     * @param size 字节数
     * @author andy_hulibo@163.com
     * @date 2019/7/22 15:20
     */
    public static String getHumanSize(long size) {
        DecimalFormat df = new DecimalFormat("0.##");
        if (size < 1024) {
            return df.format(size) + "B";
        }
        if (size < 1048576) {
            return df.format((float)size / 1024) + "K";
        }
        if (size < 1073741824) {
            return df.format((float)size / 1048576) + "M";
        }
        return df.format((float)size / 1073741824) + "G";
    }
}
