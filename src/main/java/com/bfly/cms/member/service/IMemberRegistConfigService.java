package com.bfly.cms.member.service;

import com.bfly.cms.member.entity.RegistConfig;
import com.bfly.core.base.service.IBaseService;

/**
 * 会员注册接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/20 10:39
 */
public interface IMemberRegistConfigService extends IBaseService<RegistConfig, Integer> {

    /**
     * 获得会员注册配置信息
     *
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/14 22:42
     */
    RegistConfig getRegistConfig();

    /**
     * 修改注册配置
     *
     * @param config
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/7/21 12:42
     */
    boolean editMemberRegisterConfig(RegistConfig config);
}
