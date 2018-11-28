package com.bfly.cms.resource.service;

import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.resource.entity.FileWrap;
import com.bfly.common.util.Zipper.FileEntry;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 模板资源管理接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 12:19
 */
public interface CmsResourceMng {

    /**
     * 获得子文件列表
     *
     * @param path           父文件夹路径
     * @param dirAndEditable 是否只获取文件夹和可编辑文件
     * @return 文件集合
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:19
     */
    List<FileWrap> listFile(String path, boolean dirAndEditable);


    /**
     * 文件查询
     *
     * @param path  路径
     * @param valid 是否验证
     * @return 文件集合
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:42
     */
    List<FileWrap> queryFiles(String path, Boolean valid);

    /**
     * 保存文件
     *
     * @param path    保存路径
     * @param file    保存文件
     * @param request HttpServletRequest
     * @throws IllegalStateException 异常
     * @throws IOException           异常
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:43
     */
    void saveFile(HttpServletRequest request, String path, MultipartFile file)
            throws IllegalStateException, IOException;

    /**
     * 创建文件夹
     *
     * @param path    当前目录
     * @param dirName 文件夹名
     * @return true成功false失败
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:43
     */
    boolean createDir(String path, String dirName);

    /**
     * 创建文件
     *
     * @param path     当前目录
     * @param filename 文件名
     * @param data     文件内容
     * @param request  HttpServletRequest
     * @throws IOException 异常
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:44
     */
    void createFile(HttpServletRequest request, String path, String filename, String data)
            throws IOException;

    /**
     * 读取文件
     *
     * @param name 文件名称
     * @return 文件内容
     * @throws IOException 异常
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:44
     */
    String readFile(String name) throws IOException;

    /**
     * 更新文件
     *
     * @param name 文件名称
     * @param data 文件内容
     * @throws IOException 异常
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:44
     */
    void updateFile(String name, String data) throws IOException;

    /**
     * 文件重命名
     *
     * @param origName 原文件名
     * @param destName 目标文件名
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:44
     */
    void rename(String origName, String destName);

    /**
     * 删除文件
     *
     * @param names 文件名
     * @return 删除数量
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:44
     */
    int delete(String[] names);

    /**
     * 拷贝模板及资源
     *
     * @param from 源站点
     * @param to   目标站点
     * @throws IOException 异常
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:44
     */
    void copyTplAndRes(CmsSite from, CmsSite to) throws IOException;

    /**
     * 列出所有模板方案
     *
     * @param path 模板方案路径
     * @return 模板方案
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:45
     */
    String[] getSolutions(String path);

    /**
     * 导出模板
     *
     * @param site     站点
     * @param solution 方案
     * @return 模板集合
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:45
     */
    List<FileEntry> export(CmsSite site, String solution);

    /**
     * 导入模板
     *
     * @param file 文件
     * @param site 站点
     * @throws IOException 异常
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:45
     */
    void imoport(File file, CmsSite site) throws IOException;
}
