package com.bfly.manage.comment;

import com.bfly.cms.comment.entity.CommentConfig;
import com.bfly.cms.comment.service.ICommentConfigService;
import com.bfly.cms.comment.service.ICommentService;
import com.bfly.core.context.ContextUtil;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.context.PagerThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 内容评论Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/12 14:14
 */
@RestController
@RequestMapping(value = "/manage/comment")
public class CommentController extends BaseManageController {

    @Autowired
    private ICommentService commentService;

    @Autowired
    private ICommentConfigService configService;

    /**
     * 评论列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 14:14
     */
    @GetMapping("/list")
    public void listComment(HttpServletRequest request, HttpServletResponse response) {
        PagerThreadLocal.set(request);
        Map<String, Object> property = new HashMap<String, Object>(3) {
            private static final long serialVersionUID = -9126101626116724049L;

            {
                put("status", DataConvertUtils.convertToInteger(request.getParameter("status")));
                put("recommend", DataConvertUtils.convertToBoolean(request.getParameter("recommend")));
            }
        };
        Pager pager = commentService.getPage(property);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 管理员修改评论
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 14:19
     */
    @PostMapping(value = "/edit")
    public void editComment(HttpServletRequest request, HttpServletResponse response) {
        int commentId = DataConvertUtils.convertToInteger(request.getParameter("commentId"));
        String content = request.getParameter("content");
        commentService.edit(commentId, content);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 管理员审核评论
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 14:16
     */
    @PostMapping(value = "/verify")
    public void verifyComment(HttpServletRequest request, HttpServletResponse response) {
        String commendIdsStr = request.getParameter("commentIds");
        Assert.notNull(commendIdsStr, "未选择要审核的评论!");
        String[] commendIds = commendIdsStr.split(",");
        int status = DataConvertUtils.convertToInteger(request.getParameter("status"));
        for (String id : commendIds) {
            commentService.verifyComment(status, DataConvertUtils.convertToInteger(id));
        }
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 管理员回复评论
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 14:16
     */
    @PostMapping(value = "/reply")
    public void replyComment(HttpServletRequest request, HttpServletResponse response) {
        int commentId = DataConvertUtils.convertToInteger(request.getParameter("commentId"));
        String content = request.getParameter("content");
        commentService.replyComment(commentId, content, getUser().getId());
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 推荐评论
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 14:23
     */
    @GetMapping(value = "/recommend/{commentId}")
    public void viewComment(@PathVariable("commentId") int commentId, HttpServletResponse response) {
        commentService.recommendComment(commentId, true);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 删除评论
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 14:25
     */
    @PostMapping(value = "/del")
    public void removeComment(HttpServletRequest request, HttpServletResponse response) {
        String commentIdStr = request.getParameter("ids");
        Integer[] commentIds = DataConvertUtils.convertToIntegerArray(commentIdStr.split(","));
        commentService.remove(commentIds);
        ResponseUtil.writeJson(response, "");
    }


    /**
     * 获得评论配置信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/20 10:14
     */
    @GetMapping(value = "/config/info")
    public void getCommentConfig(HttpServletResponse response) {
        CommentConfig config = configService.getCommentConfig();
        ResponseUtil.writeJson(response, config);
    }

    /**
     * 修改评论配置
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/20 10:16
     */
    @PostMapping(value = "/config/edit")
    public void editCommentConfig(@Valid CommentConfig config, BindingResult result, HttpServletResponse response) {
        validData(result);
        configService.edit(config);
        ResponseUtil.writeJson(response, "");
    }
}
