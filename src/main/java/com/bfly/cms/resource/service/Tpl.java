package com.bfly.cms.resource.service;

import java.util.Date;

/**
 * 模板接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 12:05
 */
public interface Tpl {

    /**
     * 获得模板完整名称，是文件的唯一标识。
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:06
     */
    String getName();

    /**
     * 获得路径，不包含文件名的路径。
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:06
     */
    String getPath();

    /**
     * 获得模板名称，不包含路径的文件名。
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:06
     */
    String getFilename();

    /**
     * 获得模板内容
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:06
     */
    String getSource();

    /**
     * 获得最后修改时间（毫秒）
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:06
     */
    long getLastModified();

    /**
     * 获得最后修改时间（日期）
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:06
     */
    Date getLastModifiedDate();

    /**
     * 获得文件大小，单位bytes
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:06
     */
    long getLength();

    /**
     * 获得文件大小，单位K bytes
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:06
     */
    int getSize();

    /**
     * 是否目录
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:07
     */
    boolean isDirectory();
}
