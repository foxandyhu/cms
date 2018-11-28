package com.bfly.cms.system.dao;

import com.bfly.cms.system.entity.CmsConfigContentCharge;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

/**
 * 内容收费配置数据接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/24 18:20
 */
public interface CmsConfigContentChargeDao {

    /**
     * 内容佣金收费配置分页对象
     *
     * @param pageSize 每页数据
     * @param pageNo   页码
     * @return 配置对象
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:21
     */
    Pagination getPage(int pageNo, int pageSize);


    /**
     * 根据ID查找配置信息
     *
     * @param id 配置ID
     * @return 配置信息
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:22
     */
    CmsConfigContentCharge findById(Integer id);

    /**
     * 添加配置信息
     *
     * @param bean 配置对象
     * @return 配置对象
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:22
     */
    CmsConfigContentCharge save(CmsConfigContentCharge bean);

    /**
     * 更新配置对象
     *
     * @param updater 配置对象
     * @return 配置对象
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:23
     */
    CmsConfigContentCharge updateByUpdater(Updater<CmsConfigContentCharge> updater);

    /**
     * 删除配置对象
     *
     * @param id 配置ID
     * @return 配置对象
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:23
     */
    CmsConfigContentCharge deleteById(Integer id);
}