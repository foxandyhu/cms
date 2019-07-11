package com.bfly.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

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
     * @author andy_hulibo@163.com
     * @date 2019/7/4 16:30
     */
    @Value("${spring.res.server}")
    private String server;

    /**
     * 用户头像图片存放路径
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/4 10:53
     */
    public String getFace() {
        return getRoot() + File.separator + "face";
    }

    /**
     * 获得相对root路劲的绝对路劲,必须是root的子目录或文件
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/4 11:11
     */
    public String getRelativePathForRoot(String path) {
        Path p = Paths.get(getRoot()).relativize(Paths.get(path));
        return p.toString();
    }

    public String getServer() {
        return server;
    }

    public String getTemp() {
        return temp;
    }

    public String getRoot() {
        return root;
    }

}
