package com.bfly.manage.content;

import com.bfly.cms.content.entity.ScoreGroup;
import com.bfly.cms.content.service.IScoreGroupService;
import com.bfly.common.ContextUtil;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 评分组Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/17 11:59
 */
@RestController
@RequestMapping(value = "/manage/score/group")
public class ScoreGroupController extends BaseManageController {

    @Autowired
    private IScoreGroupService scoreGroupService;

    /**
     * 评分组列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 12:00
     */
    @GetMapping(value = "/list")
    public void listScoreGroup(HttpServletResponse response) {
        ContextUtil.initPager(getRequest());
        Pager pager = scoreGroupService.getPage(null);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 添加评分组
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 13:47
     */
    @PostMapping(value = "/add")
    public void addScoreGroup(@Valid ScoreGroup scoreGroup, BindingResult result, HttpServletResponse response) {
        validData(result);
        scoreGroupService.save(scoreGroup);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 编辑评分组
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 13:48
     */
    @PostMapping(value = "/edit")
    public void editScoreGroup(@Valid ScoreGroup scoreGroup,BindingResult result, HttpServletResponse response) {
        validData(result);
        scoreGroupService.edit(scoreGroup);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 评分组详情信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 13:50
     */
    @GetMapping(value = "/{scoreGroupId}")
    public void viewScoreGroup(@PathVariable("scoreGroupId") int scoreGroupId, HttpServletResponse response) {
        ScoreGroup scoreGroup = scoreGroupService.get(scoreGroupId);
        ResponseUtil.writeJson(response, scoreGroup);
    }

    /**
     * 删除评分组
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 13:51
     */
    @PostMapping(value = "/del")
    public void delScoreGroup(HttpServletResponse response) {
        String scoreGroupIdStr = getRequest().getParameter("ids");
        Integer[] scoreGroupIds = DataConvertUtils.convertToIntegerArray(scoreGroupIdStr.split(","));
        scoreGroupService.remove(scoreGroupIds);
        ResponseUtil.writeJson(response, "");
    }
}
