package com.bfly.common;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.internet.InternetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 数据校验工具类
 *
 * @author 胡礼波
 * @2013-2-11 @下午11:35:47
 */
public class ValidateUtil {

    private static Logger logger = LoggerFactory.getLogger(ValidateUtil.class);
    private static Pattern phonePattern = Pattern.compile("^1[3|4|5|8|7|9][0-9]\\d{8}$");
    private static Pattern mediaPattern = Pattern.compile("(.*).(mp3|mp4|rmvb|flv|mpeg|avi)$");
    private static Pattern convertDocPattern = Pattern.compile("(.*)(.pdf|.ppt|.pptx|.doc|.docx|.xls|.xlsx|.txt)$");

    /**
     * 是否为手机号码
     *
     * @author 胡礼波-Andy
     * @2014年12月4日下午3:38:49
     */
    public static boolean isCellPhone(String phoneNo) {
        if (phoneNo == null || phoneNo.length() == 0) {
            return false;
        }

        Matcher m = phonePattern.matcher(phoneNo);
        return m.matches();
    }


    /**
     * 邮箱校验
     *
     * @param email
     * @return
     * @author 胡礼波
     * @2013-2-11 @下午11:37:44
     */
    public static boolean isEmail(String email) {
        boolean flag;
        try {
            InternetAddress address = new InternetAddress(email);
            address.validate();
            flag = true;
        } catch (Exception e) {
            logger.warn("邮箱格式不正确!");
            flag = false;
        }
        return flag;
    }

    /**
     * 检查URL的合法性
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/6 10:04
     */
    public static boolean isUrl(String url) {
        if(!StringUtils.hasLength(url))
        {
            return false;
        }
        URI uri;
        String http = "http", https = "https";
        try {
            uri = new URI(url);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if (uri.getHost() == null) {
            return false;
        }
        if (uri.getScheme().equalsIgnoreCase(http) || uri.getScheme().equalsIgnoreCase(https)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是多媒体类型
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/8 12:06
     */
    public static boolean isMedia(File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        String suffix = FileUtil.getFileSuffix(file.getName());
        return isMedia(suffix);
    }

    /**
     * 判断是否是多媒体类型
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/14 20:37
     */
    public static boolean isMedia(String path) {
        String suffix = FileUtil.getFileSuffix(path);
        return mediaPattern.matcher(suffix.toLowerCase()).find();
    }

    /**
     * 判断是否可以转换为PDF
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/8 13:22
     */
    public static boolean isConvertPDF(File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        String suffix = FileUtil.getFileSuffix(file.getName());
        return convertDocPattern.matcher(suffix.toLowerCase()).find();
    }
}
