package com.bfly.web.message.action;

import com.bfly.cms.message.entity.Comment;
import com.bfly.cms.message.entity.CommentExt;
import com.bfly.cms.message.service.ICommentService;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.IDEncrypt;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.RenderController;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.enums.CommentStatus;
import com.bfly.core.security.ActionModel;
import com.bfly.core.security.Login;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 文章评论 Controller
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/4 17:10
 */
@Controller("webCommentController")
@RequestMapping(value = "/comment")
public class CommentController extends RenderController {

    @Autowired
    private ICommentService commentService;
    @Autowired
    private ImageCaptchaService captchaService;

    /**
     * 文章评论
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/4 17:07
     */
    @GetMapping(value = "/article-{idStr}")
    public String getArticleComments(@PathVariable("idStr") String idStr) {
        Long articleId = IDEncrypt.decode(idStr);
        if (articleId != null) {
            PagerThreadLocal.set(getRequest(), 10);
            Map<String, Object> params = new HashMap<>(3);
            params.put("parentId", 0);
            params.put("status", CommentStatus.PASSED.getId());
            params.put("articleId", articleId.intValue());

            Map<String, Sort.Direction> sortMap = new HashMap<>(1);
            sortMap.put("postDate", Sort.Direction.DESC);
            Pager pager = commentService.getPage(params, null, sortMap);
            getRequest().setAttribute("commentPager", pager);
        }
        return renderTplPath("comment_list.html", "common");
    }

    /**
     * 回复评论
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/5 14:41
     */
    @PostMapping(value = "/reply")
    @ActionModel(value = "回复评论")
    @Login
    public void reply(HttpServletResponse response) {
        HttpServletRequest request = getRequest();
        int articleId = IDEncrypt.decode(request.getParameter("articleId")).intValue();
        int parentId = DataConvertUtils.convertToInteger(request.getParameter("parentId"));
        String content = request.getParameter("content");

        Comment comment = new Comment();
        comment.setMemberUserName(getMember().getUserName());
        comment.setArticleId(articleId);
        comment.setParentId(parentId);

        CommentExt ext = new CommentExt();
        ext.setText(content);

        comment.setCommentExt(ext);
        commentService.save(comment);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 发表评论
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/5 23:05
     */
    @PostMapping(value = "/post")
    @ActionModel(value = "发表评论")
    @Login
    public void post(HttpServletResponse response) {
        HttpServletRequest request = getRequest();
        String code = request.getParameter("captcha");
        Boolean success = captchaService.validateResponseForID(getSession().getId(), code);
        Assert.isTrue(success==null?false:success.booleanValue(),"验证码不正确!");
        reply(response);
    }

    /**
     * 顶踩评论
     * @author andy_hulibo@163.com
     * @date 2019/9/6 18:22
     */
    @GetMapping(value = "/updown-{commentId}-{up}")
    @ActionModel(value = "评论顶踩")
    @Login
    public void upDown(HttpServletResponse response,@PathVariable("commentId")int commentId,@PathVariable("up")boolean isUp){
        commentService.upDownComment(commentId,isUp);
        ResponseUtil.writeJson(response,ResponseData.getSuccess(""));
    }
}
