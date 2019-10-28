package com.bfly.web.vote.action;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.vote.entity.VoteItem;
import com.bfly.cms.vote.entity.VoteSubTopic;
import com.bfly.cms.vote.entity.VoteTopic;
import com.bfly.cms.vote.entity.dto.VoteSubmitDTO;
import com.bfly.cms.vote.service.IVoteTopicService;
import com.bfly.common.IDEncrypt;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.StringUtil;
import com.bfly.core.Constants;
import com.bfly.core.base.action.RenderController;
import com.bfly.core.enums.VoteType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 投票主题Controller
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/11 11:56
 */
@Controller("webVoteTopicController")
@RequestMapping(value = "/vote")
public class VoteTopicController extends RenderController {

    @Autowired
    private IVoteTopicService voteTopicService;

    /**
     * 投票
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/11 19:04
     */
    @PostMapping(value = "/submit/{idStr}")
    public void voteSubmit(HttpServletResponse response, @PathVariable("idStr") String idStr, @RequestBody List<VoteSubmitDTO> dtos) {
        Long voteTopicId = IDEncrypt.decode(idStr);
        if (voteTopicId == null) {
            return;
        }
        Cookie[] cookies = getRequest().getCookies();
        String value = StringUtil.getRandomString(32);
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 如果本机存在投票cookie则使用旧cookie
                if (cookie.getName().equalsIgnoreCase(Constants.VOTE_COOKIE_NAME)) {
                    value = cookie.getValue();
                    break;
                }
            }
        }
        voteTopicService.voteSubmit(voteTopicId.intValue(), dtos, value);
        Cookie voteCookie = new Cookie(Constants.VOTE_COOKIE_NAME, value);
        voteCookie.setMaxAge(60 * 60 * 24 * 365);
        response.addCookie(voteCookie);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 得到投票结果
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/11 11:57
     */
    @GetMapping(value = "/result/id_{idStr}")
    public void getVoteResult(HttpServletResponse response, @PathVariable("idStr") String idStr) {
        Long topicId = IDEncrypt.decode(idStr);
        VoteTopic voteTopic = voteTopicService.get(topicId == null ? 0 : topicId.intValue());
        Map<String, JSONArray> voteItems = new LinkedHashMap<>(4);
        if (voteTopic != null) {
            List<VoteSubTopic> subTopics = voteTopic.getSubtopics();
            if (subTopics != null) {
                subTopics.forEach(voteSubTopic -> {
                    JSONArray array = new JSONArray();
                    if (voteSubTopic.getType() == VoteType.TEXT.getId()) {
                        long count = voteTopicService.getVoteReplyCount(voteSubTopic.getId());
                        JSONObject json = new JSONObject();
                        json.put("id", 0);
                        json.put("name", "回复数");
                        json.put("value", count);
                        array.add(json);
                    } else {
                        List<VoteItem> list = voteSubTopic.getVoteItems();
                        if (list != null) {
                            list.forEach(voteItem -> {
                                JSONObject json = new JSONObject();
                                json.put("id", voteItem.getId());
                                json.put("name", voteItem.getTitle());
                                json.put("value", voteItem.getVoteCount());
                                array.add(json);
                            });
                        }
                    }
                    voteItems.put("vote_" + voteSubTopic.getId(), array);
                });
            }
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(voteItems));
    }

    /**
     * 投票详情
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/11 18:00
     */
    @GetMapping(value = "/id_{idStr}")
    public String getVoteDetail(@PathVariable("idStr") String idStr) {
        Long vId = IDEncrypt.decode(idStr);
        VoteTopic vote = voteTopicService.get(vId == null ? 0 : vId.intValue());
        getRequest().setAttribute("vote", vote);
        return renderTplPath("detail.html", "vote");
    }
}
