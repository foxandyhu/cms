package com.bfly.cms.vote.action;

import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.vote.entity.CmsVoteItem;
import com.bfly.cms.vote.entity.CmsVoteTopic;
import com.bfly.cms.vote.service.CmsVoteRecordMng;
import com.bfly.cms.vote.service.CmsVoteSubTopicMng;
import com.bfly.cms.vote.service.CmsVoteTopicMng;
import com.bfly.cms.words.service.CmsSensitivityMng;
import com.bfly.common.web.CookieUtils;
import com.bfly.common.web.RequestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public abstract class AbstractVote {


    protected CmsVoteTopic voteByApi(CmsUser user, Integer voteId, Integer[] subIds, List<Integer[]> itemIds, Integer[] subTxtIds, String[] reply, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String ip = RequestUtils.getIpAddr(request);
        String cookieName = CmsVoteTopic.VOTE_COOKIE_PREFIX + voteId;
        Cookie cookie = CookieUtils.getCookie(request, cookieName);
        String cookieValue;
        if (cookie != null && !StringUtils.isBlank(cookie.getValue())) {
            cookieValue = cookie.getValue();
        } else {
            cookieValue = null;
        }
        CmsVoteTopic vote = null;
        if (!validateSubmit(voteId, subIds, itemIds, reply, user, ip, cookieValue, model)) {
            if (cookieValue == null) {
                // 随机cookie
                cookieValue = StringUtils.remove(UUID.randomUUID().toString(),
                        "-");
                // 写cookie
                CookieUtils.addCookie(request, response, cookieName,
                        cookieValue, Integer.MAX_VALUE, null);
            }
            vote = cmsVoteTopicMng.vote(voteId, subTxtIds, itemIds, reply,
                    user, ip, cookieValue);
            model.addAttribute("status", 0);
            model.addAttribute("vote", vote);
        }
        return vote;
    }


    protected boolean validateSubmit(Integer topicId, Integer[] subIds, List<Integer[]> itemIds, String[] replys, CmsUser user, String ip, String cookie, ModelMap model) {
        // 投票ID不能为空
        if (topicId == null) {
            model.addAttribute("status", CmsVoteTopic.VOTE_STATUS_ID_NULL);
            return true;
        }
        // 投票项不能为空
        if (subIds == null || subIds.length <= 0 || itemIds == null || itemIds.size() <= 0) {
            model.addAttribute("status", CmsVoteTopic.VOTE_STATUS_ITEM_NULL);
            return true;
        }
        // 非文本选项 投票项不能为空
        if (itemIds.size() == subIds.length) {
            for (int i = 0; i < subIds.length; i++) {
                if (!cmsVoteSubTopicMng.findById(subIds[i]).getIsText()) {
                    if (itemIds.get(i) == null) {
                        model.addAttribute("status", CmsVoteTopic.VOTE_STATUS_ITEM_NULL);
                        return true;
                    }
                }
            }
        } else {
            model.addAttribute("status", CmsVoteTopic.VOTE_STATUS_ITEM_NULL);
            return true;
        }
        if (replys != null && replys.length > 0) {

        }
        if (replys != null && replys.length > 0) {
            boolean hasSensitive = cmsSensitivityMng.haveSensitivity(replys);
            if (hasSensitive) {
                model.addAttribute("status", CmsVoteTopic.VOTE_STATUS_HAS_SENSITIVE);
                return true;
            }
        }
        CmsVoteTopic topic = cmsVoteTopicMng.findById(topicId);
        // 投票主题不存在
        if (topic == null) {
            model.addAttribute("status", CmsVoteTopic.VOTE_STATUS_NOT_FOUND);
            return true;
        }
        // 投票项不合法
        List<Integer> itemTotalIds = new ArrayList<Integer>();
        for (Integer[] ids : itemIds) {
            if (ids != null && ids.length > 0) {
                for (Integer id : ids) {
                    itemTotalIds.add(id);
                }
            }
        }
        boolean contains;
        for (Integer itemId : itemTotalIds) {
            contains = false;
            for (CmsVoteItem item : topic.getItems()) {
                if (item.getId().equals(itemId)) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                model.addAttribute("status", CmsVoteTopic.VOTE_STATUS_ITEM_ILLEGAL);
                return true;
            }
        }

        // 需要登录才能投票
        if (topic.getRestrictMember() && user == null) {
            model.addAttribute("status", CmsVoteTopic.VOTE_STATUS_NEED_LOGIN);
            return true;
        }

        // 投票主题已经关闭
        if (topic.getDisabled()) {
            model.addAttribute("status", CmsVoteTopic.VOTE_STATUS_CLOSED);
            return true;
        }
        long now = System.currentTimeMillis();
        // 投票还没有开始
        Date start = topic.getStartTime();
        if (start != null && now < start.getTime()) {
            model.addAttribute("status", CmsVoteTopic.VOTE_STATUS_NOT_BEGIN);
            model.addAttribute("startTime", start);
            return true;
        }
        // 投票已经结束
        Date end = topic.getEndTime();
        if (end != null && now > end.getTime()) {
            model.addAttribute("status", CmsVoteTopic.VOTE_STATUS_ENDED);
            model.addAttribute("endTime", end);
            return true;
        }
        Integer hour = topic.getRepeateHour();
        if (hour == null || hour > 0) {
            Date vtime;
            // 规定时间内，同一会员不能重复投票
            if (topic.getRestrictMember()) {
                vtime = cmsVoteRecordMng.lastVoteTimeByUserId(user.getId(),
                        topicId);
                if ((hour == null && vtime != null) || (hour != null && vtime.getTime() + hour * 60 * 60 * 1000 > now)) {
                    model.addAttribute("status", CmsVoteTopic.VOTE_STATUS_LIMIT_USER_REPEAT);
                    return true;
                }
            }
            // 规定时间内，同一IP不能重复投票
            if (topic.getRestrictIp()) {
                vtime = cmsVoteRecordMng.lastVoteTimeByIp(ip, topicId);
                if ((hour == null && vtime != null) || (hour != null && vtime.getTime() + hour * 60 * 60 * 1000 > now)) {
                    model.addAttribute("status", CmsVoteTopic.VOTE_STATUS_LIMIT_IP_REPEAT);
                    return true;
                }
            }
            // 规定时间内，同一COOKIE不能重复投票
            if (topic.getRestrictCookie() && cookie != null) {
                vtime = cmsVoteRecordMng.lastVoteTimeByCookie(cookie, topicId);
                if ((hour == null && vtime != null) || (hour != null && vtime.getTime() + hour * 60 * 60 * 1000 > now)) {
                    model.addAttribute("status", CmsVoteTopic.VOTE_STATUS_LIMIT_COOKIE_REPEAT);
                    return true;
                }
            }
        }
        return false;
    }

    @Autowired
    protected CmsVoteTopicMng cmsVoteTopicMng;
    @Autowired
    protected CmsVoteSubTopicMng cmsVoteSubTopicMng;
    @Autowired
    protected CmsVoteRecordMng cmsVoteRecordMng;
    @Autowired
    protected CmsSensitivityMng cmsSensitivityMng;
}
