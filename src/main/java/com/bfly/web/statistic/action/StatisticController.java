package com.bfly.web.statistic.action;

import com.bfly.cms.statistic.service.IStatisticService;
import com.bfly.common.ResponseUtil;
import com.bfly.core.context.ServletRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 流量统计入口
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/23 17:23
 */
@Controller
public class StatisticController {

    /**
     * 前端调用流量入口
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/23 17:24
     */
    @RequestMapping("/flow_statistic.html")
    public void flowStatistic(String page, HttpServletRequest request, HttpServletResponse response) {
        try {
            statisticService.flow(page, ServletRequestDTO.to(request));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseUtil.writeJson(response, "");
    }

    @Autowired
    private IStatisticService statisticService;
}
