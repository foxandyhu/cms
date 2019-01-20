package com.bfly.manage.content;

import com.bfly.cms.content.entity.ScoreItem;
import com.bfly.cms.content.service.IScoreItemService;
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
import java.util.HashMap;

/**
 * 评分组Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/17 11:59
 */
@RestController
@RequestMapping(value = "/manage/score/item")
public class ScoreItemController extends BaseManageController {

    @Autowired
    private IScoreItemService scoreItemService;

    /**
     * 根据评分组ID查看评分项列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 12:00
     */
    @GetMapping(value = "/list-{scoreGroupId}")
    public void listScoreItem(@PathVariable("scoreGroupId") int scoreGroupId, HttpServletResponse response) {
        ContextUtil.initPager(getRequest());
        Pager pager = scoreItemService.getPage(new HashMap<String, Object>() {
            private static final long serialVersionUID = -3370682715204052757L;

            {
                put("group.id", scoreGroupId);
            }
        });
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 添加评分项
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 13:47
     */
    @PostMapping(value = "/add")
    public void addScoreItem(@Valid ScoreItem scoreItem, BindingResult result, HttpServletResponse response) {
        validData(result);
        scoreItemService.save(scoreItem);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 编辑评分项
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 13:48
     */
    @PostMapping(value = "/edit")
    public void editScoreItem(@Valid ScoreItem scoreItem,BindingResult result, HttpServletResponse response) {
        validData(result);
        scoreItemService.edit(scoreItem);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 评分项详情信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 13:50
     */
    @GetMapping(value = "/{scoreItemId}")
    public void viewScoreItem(@PathVariable("scoreItemId") int scoreItemId, HttpServletResponse response) {
        ScoreItem scoreItem = scoreItemService.get(scoreItemId);
        ResponseUtil.writeJson(response, scoreItem);
    }

    /**
     * 删除评分项
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 13:51
     */
    @PostMapping(value = "/del")
    public void delScoreItem(HttpServletResponse response) {
        String scoreItemIdStr = getRequest().getParameter("ids");
        Integer[] scoreItemIds = DataConvertUtils.convertToIntegerArray(scoreItemIdStr.split(","));
        scoreItemService.remove(scoreItemIds);
        ResponseUtil.writeJson(response, "");
    }
}
