package com.bfly.core.config;

import com.bfly.common.FileUtil;
import com.bfly.core.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 资源配置
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/3 16:09
 */
@Configuration
public class ResourceConfig {

    private static ResourceConfig config;

    @PostConstruct
    public void init() {
        config = this;
    }

    /**
     * 上传的资源临时存放路径
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/4 10:52
     */
    @Value("${spring.res.temp}")
    private String temp;

    /**
     * 系统静态资源存放路径
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/4 10:53
     */
    @Value("${spring.res.root}")
    private String root;

    /**
     * 静态资源服务器地址
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/4 16:30
     */
    @Value("${spring.res.server}")
    private String server;

    /**
     * 用户头像图片存放路径文件夹
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/4 10:53
     */
    public static String getFaceDir() {
        return getRootDir() + File.separator + "face";
    }

    /**
     * 评分项图片存放路径文件夹
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/16 10:41
     */
    public static String getScoreDir() {
        return getRootDir() + File.separator + "score";
    }

    /**
     * 友情链接图片存放路径
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/17 22:01
     */
    public static String getFriendLinkDir() {
        return getRootDir() + File.separator + "friendlink";
    }

    /**
     * 广告图片存放路径
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/18 14:54
     */
    public static String getAdvertisingDir() {
        return getRootDir() + File.separator + "ad";
    }

    /**
     * 系统图片存放路径
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/21 21:21
     */
    public static String getSysImgDir() {
        return getRootDir() + File.separator + "system" + File.separator + "images";
    }

    /**
     * 调查问卷图片存放路径
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/30 15:47
     */
    public static String getVoteTopicDir() {
        return getRootDir() + File.separator + "vote";
    }

    /**
     * 获得模板相对路径
     * "/template/"
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/5 11:29
     */
    public static String getTemplateRelativePath() {
        String path = "/template/";
        return path;
    }

    /**
     * 获得相对root路径的绝对路径,必须是root的子目录或文件
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/4 11:11
     */
    public static String getRelativePathForRoot(String path) {
        Path p = Paths.get(getRootDir()).relativize(Paths.get(path));
        return "/" + p.toString().replaceAll("\\\\", "/");
    }

    public static String getServer() {
        return config.server;
    }

    public static String getTempDir() {
        return config.temp;
    }

    public static String getRootDir() {
        return config.root;
    }


    /**
     * 把上传的临时文件复制到目标文件并返回基于目标文件的相对路径
     *
     * @param destDir  上传文件的目标目录
     * @param filePath 文件的路径 临时文件--相对路径
     * @author andy_hulibo@163.com
     * @date 2019/7/16 10:30
     */
    public static String getUploadTempFileToDestDirForRelativePath(String filePath, String destDir) {
        //把临时文件夹中的图片复制到face目录中
        if (StringUtils.hasLength(filePath) && filePath.endsWith(Constants.TEMP_RESOURCE_SUFFIX)) {
            String source = getTempDir() + File.separator + filePath;
            boolean result = FileUtil.copyFileToDirectory(source, destDir);
            if (result) {
                filePath = filePath.substring(0, filePath.lastIndexOf(Constants.TEMP_RESOURCE_SUFFIX));
                return getRelativePathForRoot(destDir + File.separator + filePath);
            }
        }
        return null;
    }
}
