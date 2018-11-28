package com.bfly.cms.system.service;

import com.bfly.cms.system.entity.CmsConfigContentCharge;

import java.util.Map;

/**
 * 内容佣金收费配置业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/24 18:12
 */
public interface CmsConfigContentChargeMng {

    /**
     * 根据ID获得内容佣金配置
     *
     * @param id 佣金配置ID
     * @return 佣金配置
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:12
     */
    CmsConfigContentCharge findById(Integer id);

    /**
     * 获得默认的佣金配置
     *
     * @return 佣金配置
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:13
     */
    CmsConfigContentCharge getDefault();

    /**
     * 更新内容佣金配置
     *
     * @param payTransferPassword 转账登陆密码
     * @param bean                配置
     * @param fixVal              打赏配置属性
     * @param keys                不更新的属性
     * @return 佣金配置
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:13
     */
    CmsConfigContentCharge update(CmsConfigContentCharge bean
            , String payTransferPassword, Map<String, String> keys, Map<String, String> fixVal);

    /**
     * 设置抽成
     *
     * @param payAmout 金额
     * @return 佣金配置
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:16
     */
    CmsConfigContentCharge afterUserPay(Double payAmout);

}