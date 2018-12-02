package com.bfly.web.vote.action;

import com.bfly.cms.vote.entity.CmsVoteItem;
import com.bfly.cms.vote.entity.CmsVoteSubTopic;
import com.bfly.cms.vote.entity.CmsVoteTopic;
import com.bfly.cms.vote.service.CmsVoteRecordMng;
import com.bfly.cms.vote.service.CmsVoteReplyMng;
import com.bfly.cms.vote.service.CmsVoteSubTopicMng;
import com.bfly.cms.vote.service.CmsVoteTopicMng;
import com.bfly.cms.words.service.CmsSensitivityMng;
import com.bfly.common.page.Pagination;
import com.bfly.common.util.ArrayUtils;
import com.bfly.common.web.CookieUtils;
import com.bfly.common.web.RequestUtils;
import com.bfly.core.base.action.RenderController;
import com.bfly.core.web.WebErrors;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.bfly.common.page.SimplePage.cpn;

/**
 * 投票Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/30 9:19
 */
@Controller
public class VoteAct extends RenderController {

    private static final Logger log = LoggerFactory.getLogger(VoteAct.class);

    @GetMapping(value = "/vote_result.html")
    public String result(Integer voteId, ModelMap model) {
        CmsVoteTopic vote = null;
        if (voteId != null) {
            vote = cmsVoteTopicMng.findById(voteId);
        }
        if (vote == null) {
            WebErrors errors = WebErrors.create(getRequest());
            errors.addError("error.vote.novotefind");
            return renderErrorPage(model, errors);

        }
        model.addAttribute("vote", vote);
        return renderPage("special/vote_result.html", model);
    }

    @GetMapping(value = "/vote_reply_view.html")
    public String replyView(Integer subId, Integer pageNo, ModelMap model) {
        CmsVoteSubTopic subTopic = null;
        if (subId != null) {
            subTopic = cmsVoteSubTopicMng.findById(subId);
        }
        if (subTopic == null) {
            WebErrors errors = WebErrors.create(getRequest());
            errors.addError("error.vote.novotesubfind");
            return renderErrorPage(model, errors);
        }
        Pagination pagination = cmsVoteReplyMng.getPage(subId, cpn(pageNo), CookieUtils.getPageSize(getRequest()));
        model.addAttribute("subTopic", subTopic);
        model.addAttribute("pagination", pagination);
        return renderPagination("special/vote_reply_result.html", model);
    }

    @GetMapping(value = "/vote.html")
    public String input(Integer voteId, ModelMap model) {
        CmsVoteTopic vote = null;
        if (voteId != null) {
            vote = cmsVoteTopicMng.findById(voteId);
        }
        if (vote == null) {
            WebErrors errors = WebErrors.create(getRequest());
            errors.addError("error.vote.novotefind");
            return renderErrorPage(model, errors);
        }
        model.addAttribute("vote", vote);
        return renderPage("special/vote_input.html", model);
    }


    @PostMapping(value = "/vote.html")
    public String submit(Integer voteId, Integer[] subIds, String[] reply, HttpServletResponse response, ModelMap model) {
        CmsVoteTopic vote = vote(voteId, subIds, reply, response, model);
        if (vote != null) {
            log.info("vote CmsVote id={}, name={}", vote.getId(), vote.getTitle());
        }
        return renderPage("special/vote_result.html", model);
    }

    protected CmsVoteTopic vote(Integer voteId, Integer[] subIds, String[] reply, HttpServletResponse response, ModelMap model) {
        String ip = RequestUtils.getIpAddr(getRequest());
        String cookieName = CmsVoteTopic.VOTE_COOKIE_PREFIX + voteId;
        Cookie cookie = CookieUtils.getCookie(getRequest(), cookieName);
        String cookieValue;
        if (cookie != null && !StringUtils.isBlank(cookie.getValue())) {
            cookieValue = cookie.getValue();
        } else {
            cookieValue = null;
        }
        List<Integer[]> itemIds = getItemIdsParam(subIds);
        Integer[] subTxtIds = null;
        if (reply != null && reply.length > 0) {
            subTxtIds = new Integer[reply.length];
            List<Integer> subTxtIdList = new ArrayList<>();
            for (int i = 0; i < itemIds.size(); i++) {
                if (itemIds.get(i) == null) {
                    subTxtIdList.add(subIds[i]);
                }
            }
            //投票文本选项和题目id数组对应相同大小
            subTxtIds = subTxtIdList.toArray(subTxtIds);
        }
        CmsVoteTopic vote = null;
        if (!validateSubmit(voteId, subIds, itemIds, reply, ip, cookieValue, model)) {
            if (cookieValue == null) {
                // 随机cookie
                cookieValue = StringUtils.remove(UUID.randomUUID().toString(), "-");
                // 写cookie
                CookieUtils.addCookie(getRequest(), response, cookieName, cookieValue, Integer.MAX_VALUE, null);
            }
            vote = cmsVoteTopicMng.vote(voteId, subTxtIds, itemIds, reply, getUser(), ip, cookieValue);
            model.addAttribute("status", 0);
            model.addAttribute("vote", vote);
        }
        return vote;
    }

    private List<Integer[]> getItemIdsParam(Integer[] subIds) {
        List<Integer[]> itemIds = new ArrayList<>();
        for (Integer subId : subIds) {
            itemIds.add(getSubItemIdsParam(subId));
        }
        return itemIds;
    }

    private Integer[] getSubItemIdsParam(Integer subId) {
        String[] paramValues = getRequest().getParameterValues("itemIds" + subId);
        return ArrayUtils.convertStrArrayToInt(paramValues);
    }

    private boolean validateSubmit(Integer topicId, Integer[] subIds, List<Integer[]> itemIds, String[] replys, String ip, String cookie, ModelMap model) {
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
        List<Integer> itemTotalIds = new ArrayList<>();
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
        if (topic.getRestrictMember() && getUser() == null) {
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
                vtime = cmsVoteRecordMng.lastVoteTimeByUserId(getUser().getId(), topicId);
                boolean flag = (hour == null && vtime != null) || (hour != null && vtime.getTime() + hour * 60 * 60 * 1000 > now);
                if (flag) {
                    model.addAttribute("status", CmsVoteTopic.VOTE_STATUS_LIMIT_USER_REPEAT);
                    return true;
                }
            }
            // 规定时间内，同一IP不能重复投票
            if (topic.getRestrictIp()) {
                vtime = cmsVoteRecordMng.lastVoteTimeByIp(ip, topicId);
                boolean flag = (hour == null && vtime != null) || (hour != null && vtime.getTime() + hour * 60 * 60 * 1000 > now);
                if (flag) {
                    model.addAttribute("status", CmsVoteTopic.VOTE_STATUS_LIMIT_IP_REPEAT);
                    return true;
                }
            }
            // 规定时间内，同一COOKIE不能重复投票
            if (topic.getRestrictCookie() && cookie != null) {
                vtime = cmsVoteRecordMng.lastVoteTimeByCookie(cookie, topicId);
                boolean flag = (hour == null && vtime != null) || (hour != null && vtime.getTime() + hour * 60 * 60 * 1000 > now);
                if (flag) {
                    model.addAttribute("status", CmsVoteTopic.VOTE_STATUS_LIMIT_COOKIE_REPEAT);
                    return true;
                }
            }
        }
        return false;
    }

    @Autowired
    protected CmsVoteRecordMng cmsVoteRecordMng;
    @Autowired
    private CmsVoteTopicMng cmsVoteTopicMng;
    @Autowired
    private CmsVoteSubTopicMng cmsVoteSubTopicMng;
    @Autowired
    private CmsVoteReplyMng cmsVoteReplyMng;
    @Autowired
    protected CmsSensitivityMng cmsSensitivityMng;
}
