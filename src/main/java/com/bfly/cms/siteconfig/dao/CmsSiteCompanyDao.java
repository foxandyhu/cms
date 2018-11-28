package com.bfly.cms.siteconfig.dao;

import com.bfly.cms.siteconfig.entity.CmsSiteCompany;
import com.bfly.common.hibernate4.Updater;

/**
 * 机构信息业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/23 13:40
 */
public interface CmsSiteCompanyDao {

    /**
     * 保存机构信息
     *
     * @param bean 机构对象
     * @return 机构对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 13:40
     */
    CmsSiteCompany save(CmsSiteCompany bean);

    /**
     * 更新机构信息
     *
     * @param updater 机构信息
     * @return 机构信息
     * @author andy_hulibo@163.com
     * @date 2018/11/23 13:41
     */
    CmsSiteCompany updateByUpdater(Updater<CmsSiteCompany> updater);
}