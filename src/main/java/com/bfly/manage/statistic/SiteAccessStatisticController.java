package com.bfly.manage.statistic;

import com.bfly.cms.statistic.entity.StatisticDataDTO;
import com.bfly.cms.statistic.service.ISiteAccessStatisticService;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.DateUtil;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.enums.SiteAccessSource;
import com.bfly.core.enums.StatisticType;
import com.bfly.core.enums.SysError;
import com.bfly.core.exception.WsResponseException;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 流量统计报表Controller
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/24 7:57
 */
@RestController
@RequestMapping(value = "/manage/statistic")
public class SiteAccessStatisticController extends BaseManageController {

    @Autowired
    private ISiteAccessStatisticService statisticService;

    /**
     * 流量趋势统计
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/24 11:00
     */
    @GetMapping("/flow")
    @ActionModel(value = "流量趋势统计", need = false)
    public void staticsticFlow(HttpServletResponse response) {
        DateParam param = getDateParam();
        List<StatisticDataDTO> list = statisticService.statistic(param.getBegin(), param.getEnd());
        ResponseUtil.writeJson(response, ResponseData.getSuccess(list));
    }

    /**
     * 访问来源统计
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/25 13:02
     */
    @GetMapping("/source")
    @ActionModel(value = "访问来源统计", need = false)
    public void staticsticSource(HttpServletResponse response) {
        DateParam param = getDateParam();
        List<StatisticDataDTO> list = statisticService.statistic(param.getBegin(), param.getEnd(), StatisticType.SOURCE);
        Stream<StatisticDataDTO> stream = list.stream().filter(item -> {
            if (item.getValue().equals(SiteAccessSource.ENGINE.getName())) {
                item.setValue("搜索引擎");
            } else if (item.getValue().equals(SiteAccessSource.DIRECT.getName())) {
                item.setValue("直接访问");
            } else if (item.getValue().equals(SiteAccessSource.EXTERNAL.getName())) {
                item.setValue("外部链接");
            } else {
                item.setValue("其他");
            }
            return true;
        });
        list = stream.collect(Collectors.toList());
        ResponseUtil.writeJson(response, ResponseData.getSuccess(list));
    }

    /**
     * 根据搜索引擎统计
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/25 19:10
     */
    @GetMapping("/engine")
    @ActionModel(value = "搜索引擎来源统计", need = false)
    public void staticsticEngine(HttpServletResponse response) {
        DateParam param = getDateParam();
        List<StatisticDataDTO> list = statisticService.statistic(param.getBegin(), param.getEnd(), StatisticType.ENGINE);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(list));
    }

    /**
     * 来访外部站点统计
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/25 19:13
     */
    @GetMapping("/site")
    @ActionModel(value = "外部站点来源统计", need = false)
    public void statisticExternalSite(HttpServletResponse response) {
        DateParam param = getDateParam();
        List<StatisticDataDTO> list = statisticService.statistic(param.getBegin(), param.getEnd(), StatisticType.LINK);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(list));
    }

    /**
     * 浏览器来源统计
     *
     * @param response HttpServletResponse
     * @author andy_hulibo@163.com
     * @date 2019/7/25 19:13
     */
    @GetMapping("/browser")
    @ActionModel(value = "浏览器来源统计", need = false)
    public void statisticBrowser(HttpServletResponse response) {
        DateParam param = getDateParam();
        List<StatisticDataDTO> list = statisticService.statistic(param.getBegin(), param.getEnd(), StatisticType.BROWSER);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(list));
    }

    /**
     * 区域来源统计
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/25 19:13
     */
    @GetMapping("/area")
    @ActionModel(value = "区域来源统计", need = false)
    public void statisticArea(HttpServletResponse response) {
        DateParam param = getDateParam();
        List<StatisticDataDTO> list = statisticService.statistic(param.getBegin(), param.getEnd(), StatisticType.AREA);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(list));
    }

    /**
     * 得到时间参数
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/25 13:05
     */
    private DateParam getDateParam() {
        int time = DataConvertUtils.convertToInteger(getRequest().getParameter("time"));
        String beginTime = getRequest().getParameter("begin");
        String endTime = getRequest().getParameter("end");
        Date begin, end;

        switch (time) {
            //指定日期
            case 0:
                if (beginTime == null || endTime == null) {
                    throw new WsResponseException(SysError.PARAM_ERROR, SysError.PARAM_ERROR.getMessage());
                }
                begin = DateUtil.parseStrDate(beginTime);
                end = DateUtil.parseStrDate(endTime);
                if (begin == null || end == null) {
                    throw new WsResponseException(SysError.PARAM_ERROR, SysError.PARAM_ERROR.getMessage());
                }
                break;
            //今天
            case 1:
                begin = DateUtil.formatterDate(new Date());
                end = begin;
                break;
            //昨天
            case 2:
                begin = DateUtil.getYesterday();
                end = begin;
                break;
            //本周
            case 3:
                begin = DateUtil.getCurrentFirstDayOfWeek();
                end = DateUtil.getCurrentLastDayOfWeek();
                break;
            //本月
            case 4:
                //月初
                begin = DateUtil.getCurrentFirstDayOfMonth();
                end = DateUtil.getCurrentLastDayOfMonth();
                break;
            case 5:
                //本年
                begin = DateUtil.getCurrentFirstDayOfYear();
                end = DateUtil.getCurrentLastDayOfYear();
                break;
            default:
                throw new WsResponseException(SysError.PARAM_ERROR, SysError.PARAM_ERROR.getMessage());
        }
        DateParam param = new DateParam();
        param.setBegin(begin);
        param.setEnd(end);
        return param;
    }

    /**
     * 时间包装参数类
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/25 13:04
     */
    class DateParam {
        private Date begin;
        private Date end;

        public Date getBegin() {
            return begin;
        }

        public void setBegin(Date begin) {
            this.begin = begin;
        }

        public Date getEnd() {
            return end;
        }

        public void setEnd(Date end) {
            this.end = end;
        }
    }
}
