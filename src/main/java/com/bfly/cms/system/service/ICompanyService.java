package com.bfly.cms.system.service;

import com.bfly.cms.system.entity.Company;
import com.bfly.core.base.service.IBaseService;

/**
 * 企业信息业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/18 11:03
 */
public interface ICompanyService extends IBaseService<Company, Integer> {

    /**
     * 获得企业信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/20 11:44
     */
    Company getCompany();
}
