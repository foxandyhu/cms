package com.bfly.cms.statistic.workload;

import com.bfly.cms.entity.main.Channel;
import com.bfly.cms.manager.main.ChannelMng;
import com.bfly.cms.statistic.CmsStatistic.*;
import com.bfly.cms.statistic.workload.CmsWorkLoadStatistic.CmsWorkLoadStatisticDateKind;
import com.bfly.cms.statistic.workload.CmsWorkLoadStatistic.CmsWorkLoadStatisticGroup;
import com.bfly.common.util.DateUtils;
import com.bfly.core.entity.CmsUser;
import com.bfly.core.manager.CmsUserMng;
import com.bfly.core.web.util.CmsUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.bfly.cms.statistic.CmsStatistic.*;

/**
 * @author Tom
 */
@Controller
public class CmsWorkLoadStatisticAct {

    @RequiresPermissions("workloadstatistic:v_list")
    @RequestMapping("/workloadstatistic/v_list.html")
    public String contentList(HttpServletRequest request, ModelMap model,
                              Integer channelId, Integer reviewerId, Integer authorId,
                              Date beginDate, Date endDate, String group) {
        if (StringUtils.isBlank(group)) {
            group = "year";
        }
        if (reviewerId != null && reviewerId.equals(0)) {
            reviewerId = null;
        }
        CmsWorkLoadStatisticGroup statisticGroup = initialGrop(group);
        CmsWorkLoadStatisticDateKind kind;
        if (reviewerId != null && !reviewerId.equals(0)) {
            kind = initialDateKind(true);
        } else {
            kind = initialDateKind(false);
        }
        if (authorId != null && authorId.equals(0)) {
            authorId = null;
        }
        Date now = Calendar.getInstance().getTime();
        if (beginDate == null) {
            beginDate = getNextDate(statisticGroup, now, 0);
        }
        if (endDate == null) {
            endDate = now;
        }
        Integer siteId = CmsUtils.getSiteId(request);
        List<Channel> topList = channelMng.getTopList(siteId, true);
        List<Channel> channelList = Channel.getListForSelect(topList, null,
                true);
        List<CmsUser> admins = userMng.getAdminList(siteId, null, CmsUser.USER_STATU_CHECKED, null);
        List<CmsUser> users = userMng.getList(null, null, null, null, CmsUser.USER_STATU_CHECKED, null, null);
        List<CmsWorkLoadStatistic> li = workloadStatisticSvc.statistic(channelId, reviewerId,
                authorId, beginDate, endDate, statisticGroup, kind);
        Date dayBegin = DateUtils.getStartDate(now);
        Date monthBegin = DateUtils.getSpecficMonthStart(now, 0);
        Date yearBegin = DateUtils.getSpecficYearStart(now, 0);
        List<Object[]> dayList, monthList, yearList;
        if (group.equals(CmsStatisticModel.month.toString())) {
            monthList = workloadStatisticSvc.statisticByTarget(STATISTIC_BY_MONTH, channelId, reviewerId, authorId, monthBegin, now);
            model.addAttribute("monthList", monthList);
        } else if (group.equals(CmsStatisticModel.day.toString())) {
            dayList = workloadStatisticSvc.statisticByTarget(STATISTIC_BY_DAY, channelId, reviewerId, authorId, dayBegin, now);
            model.addAttribute("dayList", dayList);
        } else if (group.equals(CmsStatisticModel.year.toString())) {
            yearList = workloadStatisticSvc.statisticByTarget(STATISTIC_BY_YEAR, channelId, reviewerId, authorId, yearBegin, now);
            model.addAttribute("yearList", yearList);
        }
        model.addAttribute("channelId", channelId);
        model.addAttribute("reviewerId", reviewerId);
        model.addAttribute("authorId", authorId);
        model.addAttribute("beginDate", beginDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("group", group);
        model.addAttribute("channelList", channelList);
        model.addAttribute("admins", admins);
        model.addAttribute("users", users);
        model.addAttribute("list", li);
        return "statistic/workload/list";
    }

    private CmsWorkLoadStatisticGroup initialGrop(String group) {
        CmsWorkLoadStatisticGroup statisticGroup = CmsWorkLoadStatisticGroup
                .valueOf(group);
        return statisticGroup;
    }

    private CmsWorkLoadStatisticDateKind initialDateKind(Boolean checkDate) {
        CmsWorkLoadStatisticDateKind kind = CmsWorkLoadStatisticDateKind.valueOf("release");
        if (checkDate) {
            kind = CmsWorkLoadStatisticDateKind.valueOf("check");
        }
        return kind;
    }

    private Date getNextDate(CmsWorkLoadStatisticGroup group, Date date,
                             int amount) {
        Date result = null;
        if (group == CmsWorkLoadStatisticGroup.year) {
            result = DateUtils.getSpecficYearStart(date, amount);
        } else if (group == CmsWorkLoadStatisticGroup.month) {
            result = DateUtils.getSpecficMonthStart(date, amount);
        } else if (group == CmsWorkLoadStatisticGroup.week) {
            result = DateUtils.getSpecficWeekStart(date, amount);
        } else if (group == CmsWorkLoadStatisticGroup.day) {
            result = DateUtils.getSpecficDateStart(date, amount);
        }
        return result;
    }

    @Autowired
    private CmsWorkLoadStatisticSvc workloadStatisticSvc;
    @Autowired
    private ChannelMng channelMng;
    @Autowired
    private CmsUserMng userMng;
}