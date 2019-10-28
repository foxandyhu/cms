package com.bfly.common;

import org.hashids.Hashids;

/**
 * Id 加解密
 *
 * @author andy_hulibo@163.com
 * @date 2019/8/19 17:31
 */
public class IDEncrypt {

    private static Hashids hashids;

    static {
        hashids = new Hashids("cms@)!(123", 16);
    }

    /**
     * id加密返回字符串
     *
     * @param number 加密数字
     * @return 加密后字符串
     * @author andy_hulibo@163.com
     * @date 2019/8/19 17:32
     */
    public static String encode(long number) {
        String result = hashids.encode(number);
        return result;
    }

    /**
     * 解密
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/19 17:33
     */
    public static Long decode(String content) {
        long[] numbers = hashids.decode(content);
        return numbers.length > 0 ? numbers[0] : null;
    }
}
