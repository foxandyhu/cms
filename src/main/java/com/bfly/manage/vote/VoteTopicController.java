package com.bfly.manage.vote;

import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.vote.entity.VoteItem;
import com.bfly.cms.vote.entity.VoteSubTopic;
import com.bfly.cms.vote.entity.VoteTopic;
import com.bfly.cms.vote.service.IVoteTopicService;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.json.JsonUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 问卷调查Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/12 15:06
 */
@RestController
@RequestMapping(value = "/manage/vote")
public class VoteTopicController extends BaseManageController {

    @Autowired
    private IVoteTopicService voteTopicService;

    /**
     * 问卷调查列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 15:07
     */
    @GetMapping("/list")
    @ActionModel(value = "问卷调查列表", need = false)
    public void listVoteTopic(HttpServletRequest request, HttpServletResponse response) {
        PagerThreadLocal.set(request);
        Map<String, Object> property = new HashMap<>(1);
        String status = request.getParameter("status");
        if (status != null) {
            property.put("status", DataConvertUtils.convertToInteger(status));
        }
        Pager pager = voteTopicService.getPage(property);
        JSONObject json = JsonUtil.toJsonFilter(pager, "subtopics");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 新增问卷调查
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 15:09
     */
    @PostMapping(value = "/add")
    @ActionModel("新增问卷调查")
    public void addVoteTopic(@RequestBody @Valid VoteTopic voteTopic, BindingResult result, HttpServletResponse response) {
        validData(result);
        voteTopicService.save(voteTopic);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 删除问卷调查
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 15:11
     */
    @PostMapping(value = "/del")
    @ActionModel("删除问卷调查")
    public void removeVoteTopic(HttpServletResponse response, @RequestBody Integer... ids) {
        voteTopicService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 暂停或取消暂停状态的投票
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 15:42
     */
    @GetMapping(value = "/{voteId}/{enabled}")
    @ActionModel("暂停或开启问卷调查")
    public void setEnableVoteTopic(@PathVariable("voteId") int voteId, @PathVariable("enabled") boolean enabled, HttpServletResponse response) {
        voteTopicService.setEnableVoteTopic(voteId, enabled);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 查看问卷调查详情
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/31 10:52
     */
    @GetMapping(value = "/{voteId}")
    @ActionModel(value = "查看问卷调查详情", need = false)
    public void viewVoteTopic(HttpServletResponse response, @PathVariable("voteId") int voteId) {
        VoteTopic topic = voteTopicService.get(voteId);
        List<VoteSubTopic> subTopicList = topic.getSubtopics();
        if (subTopicList != null) {
            subTopicList.forEach(voteSubTopic -> {
                List<VoteItem> items = voteSubTopic.getVoteItems();
                if (items != null) {
                    items.forEach(voteItem -> {
                        if (StringUtils.hasLength(voteItem.getPicture())) {
                            voteItem.setPicture(ResourceConfig.getServer() + voteItem.getPicture());
                        }
                    });
                }
            });
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(topic));
    }
}
