package com.bfly.manage.vote;

import com.bfly.cms.vote.entity.VoteTopic;
import com.bfly.cms.vote.service.IVoteTopicService;
import com.bfly.core.context.ContextUtil;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.context.PagerThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
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
    public void listVoteTopic(HttpServletRequest request, HttpServletResponse response) {
        PagerThreadLocal.set(request);
        Map<String, Object> property = new HashMap<String, Object>(3) {
            private static final long serialVersionUID = -9126101626116724049L;

            {
                put("status", DataConvertUtils.convertToInteger(request.getParameter("status")));
            }
        };
        Pager pager = voteTopicService.getPage(property);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 新增问卷调查
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 15:09
     */
    @PostMapping(value = "/add")
    public void addVoteTopic(@Valid VoteTopic voteTopic, BindingResult result, HttpServletResponse response) {
        validData(result);
        voteTopicService.save(voteTopic);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 修改问卷调查
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 15:09
     */
    @PostMapping(value = "/edit")
    public void editVoteTopic(VoteTopic voteTopic, HttpServletResponse response) {
        voteTopicService.edit(voteTopic);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 获取问卷调查基本信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:50
     */
    @GetMapping(value = "/{voteId}")
    public void viewVoteTopic(@PathVariable("voteId") int voteId, HttpServletResponse response) {
        VoteTopic voteTopic = voteTopicService.get(voteId);
        ResponseUtil.writeJson(response, voteTopic);
    }

    /**
     * 删除问卷调查
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 15:11
     */
    @PostMapping(value = "/del")
    public void removeVoteTopic(HttpServletRequest request, HttpServletResponse response) {
        String voteTopicIdStr = request.getParameter("ids");
        Integer[] voteTopicIds = DataConvertUtils.convertToIntegerArray(voteTopicIdStr.split(","));
        voteTopicService.remove(voteTopicIds);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 设置默认的问卷主题
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 15:40
     */
    @GetMapping(value = "/setdef")
    public void setDefVoteTopic(HttpServletRequest request, HttpServletResponse response) {
        int voteId = DataConvertUtils.convertToInteger(request.getParameter("voteId"));
        voteTopicService.setDefaultVoteTopic(voteId);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 暂停或取消暂停状态的投票
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 15:42
     */
    @GetMapping(value = "/setenable")
    public void setEnableVoteTopic(HttpServletRequest request, HttpServletResponse response) {
        int voteId = DataConvertUtils.convertToInteger(request.getParameter("voteId"));
        boolean enable = DataConvertUtils.convertToBoolean(request.getParameter("enable"));
        voteTopicService.setEnableVoteTopic(voteId, enable);
        ResponseUtil.writeJson(response, "");
    }
}
