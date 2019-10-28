package com.bfly.core.config;

import com.bfly.common.FileUtil;
import com.bfly.core.Constants;
import com.bfly.core.context.ContextUtil;
import com.bfly.core.context.ServletRequestThreadLocal;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Value("${spring.res.tempServer}")
    private String tempServer;

    /**
     * 获得文章索引库文件绝对路径
     *
     * @author andy_hulibo@163.com
     * @date 2019/10/25 14:46
     */
    public static String getArticleIndexDir() {
        return getRootDir() + File.separator + "index/article";
    }

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
     * 获得文章内容图片存放路径
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/6 15:24
     */
    public static String getContentDir() {
        return getRootDir() + File.separator + "content";
    }

    /**
     * 获得多媒体存放路径
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/8 13:46
     */
    public static String getMediaDir() {
        return getRootDir() + File.separator + "media";
    }

    /**
     * 获得文档存放路径
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/8 13:46
     */
    public static String getDocDir() {
        return getRootDir() + File.separator + "doc";
    }

    /**
     * 获得附件存放路径
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/8 13:48
     */
    public static String getAttachmentDir() {
        return getRootDir() + File.separator + "attachment";
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
     * 获得模板文件夹路径
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/6 12:49
     */
    public static String getTemplatePath() {
        HttpServletRequest request = ServletRequestThreadLocal.get();
        String path = request.getServletContext().getRealPath(ResourceConfig.getTemplateRelativePath());
        return path;
    }

    /**
     * 获得模板文件夹路径
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/20 11:38
     */
    public static String getTemplatePath(String childDirName) {
        return getTemplatePath() + File.separator + childDirName;
    }

    /**
     * 获得模板绝对路径
     *
     * @param relativeTplPath 模板相对template目录路径
     * @author andy_hulibo@163.com
     * @date 2019/8/20 13:45
     */
    public static String getTemplateAbsolutePath(String relativeTplPath) {
        String path = ContextUtil.getWebApp() + File.separator + getTemplateRelativePath() + relativeTplPath;
        return path;
    }

    /**
     * 获得模板路径下的模板名称集合
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/20 11:52
     */
    public static List<String> getTemplateNames(String childDirName, boolean isPcTpl) {
        String path = getTemplatePath(childDirName);
        String[] names = FileUtil.getFileNames(path);
        List<String> list = new ArrayList<>();
        if (names != null) {
            String html = ".html", pc = "pc_", mobile = "mobile_";
            Stream<String> stream = Arrays.stream(names);
            stream = stream.filter(s -> {
                if (!s.endsWith(html)) {
                    return false;
                }
                return isPcTpl ? s.startsWith(pc) : s.startsWith(mobile);
            });
            list = stream.collect(Collectors.toList());
        }
        return list;
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

    /**
     * 获得相对root路径的绝对路径
     *
     * @param path 相对路径
     * @author andy_hulibo@163.com
     * @date 2019/8/25 14:35
     */
    public static String getAbsolutePathForRoot(String path) {
        return getRootDir() + File.separator + path;
    }

    /**
     * 获得相对template路径的绝对路径,必须是template的子目录或文件
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/6 12:57
     */
    public static String getRelativePathForTemplate(String path) {
        Path p = Paths.get(getTemplatePath()).relativize(Paths.get(path));
        return "/" + p.toString().replaceAll("\\\\", "/");
    }

    public static String getServer() {
        return config.server;
    }

    public static String getTempServer() {
        return config.tempServer;
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
