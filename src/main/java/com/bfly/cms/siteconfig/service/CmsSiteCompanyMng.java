package com.bfly.cms.siteconfig.service;

import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.siteconfig.entity.CmsSiteCompany;

/**
 * 机构基本信息业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/23 10:44
 */
public interface CmsSiteCompanyMng {

    /**
     * 保存机构信息
     *
     * @param bean 机构信息对象
     * @param site 站点对象
     * @return 返回站点对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 10:45
     */
    CmsSiteCompany save(CmsSite site, CmsSiteCompany bean);

    /**
     * 更新机构信息
     *
     * @param bean 站点对象
     * @return 返回站点对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 10:45
     */
    CmsSiteCompany update(CmsSiteCompany bean);
}