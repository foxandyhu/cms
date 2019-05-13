package com.bfly.cms.system.service;

import com.bfly.cms.system.entity.SysWaterMark;
import com.bfly.core.base.service.IBaseService;

/**
 * 系统水印配置业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/20 9:30
 */
public interface ISysWaterMarkService extends IBaseService<SysWaterMark, Integer> {

    /**
     * 得到系统水印配置
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/20 9:38
     */
    SysWaterMark getWaterMark();
}
