package com.bfly.cms.siteconfig.service;

import com.bfly.cms.siteconfig.entity.Ftp;

import java.util.List;

/**
 * FTP配置业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/23 14:24
 */
public interface FtpMng {

    /**
     * 获取所有的FTP配置
     *
     * @return FTP集合
     * @author andy_hulibo@163.com
     * @date 2018/11/23 14:24
     */
    List<Ftp> getList();

    /**
     * 根据ID查找对应的FTP
     *
     * @param id FTP配置ID
     * @return FTP对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 14:25
     */
    Ftp findById(Integer id);

    /**
     * 新增FTP配置
     *
     * @param bean FTP对象
     * @return FTP对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 14:25
     */
    Ftp save(Ftp bean);

    /**
     * 更新FTP配置信息
     *
     * @param bean FTP对象
     * @return FTP对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 14:29
     */
    Ftp update(Ftp bean);

    /**
     * 删除FTP配置
     *
     * @param id FTP配置ID
     * @return FTP对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 14:29
     */
    Ftp deleteById(Integer id);

    /**
     * 批量删除FTP配置
     *
     * @param ids FTP ID集合
     * @return FTP集合对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 14:29
     */
    Ftp[] deleteByIds(Integer[] ids);
}