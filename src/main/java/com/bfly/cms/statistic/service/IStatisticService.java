package com.bfly.cms.statistic.service;

import com.bfly.core.context.ServletRequestDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * 流量统计接口
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/23 17:29
 */
public interface IStatisticService {

    /**
     * 流量统计
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/23 17:31
     */
    void flow(String page, ServletRequestDTO request);
}
