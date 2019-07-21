package com.bfly.cms.member.service;

import com.bfly.cms.member.entity.MemberConfig;
import com.bfly.core.base.service.IBaseService;

/**
 * 会员设置业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/20 10:39
 */
public interface IMemberConfigService extends IBaseService<MemberConfig, Integer> {

    /**
     * 获得会员配置信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/20 10:41
     */
    MemberConfig getMemberConfig();

    /**
     * 修改登录配置
     * @author andy_hulibo@163.com
     * @date 2019/7/21 12:41
     */
    boolean editMemberLoginConfig(MemberConfig config);

    /**
     * 修改注册配置
     * @author andy_hulibo@163.com
     * @date 2019/7/21 12:42
     */
    boolean editMemberRegistConfig(MemberConfig config);
}
