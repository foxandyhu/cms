package com.bfly.cms.siteconfig.dao;

import com.bfly.cms.siteconfig.entity.Ftp;
import com.bfly.common.hibernate4.Updater;

import java.util.List;

/**
 * FTP数据接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/23 14:33
 */
public interface FtpDao {

    /**
     * 查询所有的FTP集合
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 14:33
     */
    List<Ftp> getList();

    /**
     * 根据 ID查询FTP对象
     *
     * @param id FTP配置ID
     * @return FTP对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 14:33
     */
    Ftp findById(Integer id);

    /**
     * 保存FTP配置
     *
     * @param bean FTP对象
     * @return FTP对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 14:34
     */
    Ftp save(Ftp bean);

    /**
     * 更新FTP对象
     *
     * @param updater FTP对象
     * @return FTP对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 14:35
     */
    Ftp updateByUpdater(Updater<Ftp> updater);

    /**
     * 删除FTP
     *
     * @param id FTP ID
     * @return FTP对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 14:35
     */
    Ftp deleteById(Integer id);
}