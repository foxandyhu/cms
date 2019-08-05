package com.bfly.common;

import com.bfly.core.Constants;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件工具类
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/4 11:10
 */
public class FileUtil {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 检查文件是否存在
     *
     * @param filePath
     * @return
     * @author 胡礼波-Andy
     * @2015年5月16日下午7:17:31
     */
    public static boolean checkExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 批量删除文件或文件夹
     *
     * @param filePath Map对象中的List对象
     * @author 胡礼波
     * 2012-5-9 下午04:38:44
     */
    public static boolean deleteFiles(String... filePath) {
        boolean flag = false;
        try {
            for (String url : filePath) {
                if (url == null || url.length() == 0) {
                    continue;
                }
                File file = new File(url);
                FileUtils.deleteQuietly(file);
            }
            flag = true;
        } catch (Exception e) {
            logger.error("删除文件出错", e);
        }
        return flag;
    }

    /**
     * 写入文件
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/3 14:57
     */
    public static boolean writeFile(InputStream inputStream, String filePath) {
        boolean flag = false;
        try {
            FileUtils.copyInputStreamToFile(inputStream, new File(filePath));
            flag = true;
        } catch (IOException e) {
            logger.error("写入文件出错", e);
        }
        return flag;
    }

    /**
     * 复制文件
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/4 10:57
     */
    public static boolean copyFileToDirectory(String source, String destination) {
        try {
            File srcFile = new File(source);
            //如果是临时文件则需要修改文件名
            if (srcFile.getName().endsWith(Constants.TEMP_RESOURCE_SUFFIX)) {
                int index = srcFile.getName().lastIndexOf(Constants.TEMP_RESOURCE_SUFFIX);
                destination = destination + File.separator + srcFile.getName().substring(0, index);
            }
            File destDir = new File(destination);
            if (destDir.isDirectory()) {
                FileUtils.copyFileToDirectory(srcFile, destDir);
            } else {
                FileUtils.copyFile(srcFile, destDir);
            }
        } catch (IOException e) {
            logger.error("复制文件到目标地址出错!", e);
            return false;
        }
        return true;
    }

    /**
     * 获得文件后缀名 例如 .jpg .gif .html
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/3 15:05
     */
    public static String getFileSuffix(String filePath) {
        int index = filePath.lastIndexOf(".");
        String suffix = "";
        if (index > 0) {
            suffix = filePath.substring(index);
        }
        return suffix;
    }

    /**
     * 返回随机文件名
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/3 15:11
     */
    public static String getRandomName() {
        return System.currentTimeMillis() + StringUtil.getRandomString(10);
    }

    /**
     * 创建文件夹
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/5 11:38
     */
    public static void mkdir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
