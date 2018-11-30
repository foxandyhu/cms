package com.bfly.cms.comment.action;

import com.bfly.cms.comment.entity.CmsComment;
import com.bfly.cms.comment.service.CmsCommentMng;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.system.entity.MemberConfig;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.common.page.Pagination;
import com.bfly.common.web.CookieUtils;
import com.bfly.core.base.action.RenderController;
import com.bfly.core.web.WebErrors;
import com.bfly.core.web.util.FrontUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.bfly.common.page.SimplePage.cpn;

/**
 * 会员中心获取评论Action
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/30 15:54
 */
@Controller
public class CommentMemberAct extends RenderController {
    private static final Logger log = LoggerFactory.getLogger(CommentMemberAct.class);

    /**
     * 校验
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 15:57
     */
    private String check(ModelMap model) {
        MemberConfig mcfg = getSite().getConfig().getMemberConfig();
        if (!mcfg.isMemberOn()) {
            return renderMessagePage(model, "member.memberClose");
        }
        if (getUser() == null) {
            return renderLoginPage(model);
        }
        return null;
    }

    /**
     * 我的评论
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 15:54
     */
    @RequestMapping(value = "/member/mycomments.html")
    public String myComments(Integer pageNo, ModelMap model) {
        String result = check(model);
        if (result != null) {
            return result;
        }
        CmsSite site = getSite();
        CmsUser user = getUser();
        Pagination pagination = commentMng.getPageForMember(site.getId(), null, user.getId(), null, null, null, null, true, cpn(pageNo), CookieUtils.getPageSize(getRequest()));
        model.addAttribute("pagination", pagination);
        return renderPage("comment/comment_list.html", model);
    }

    /**
     * 查看评论回复
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 15:56
     */
    @RequestMapping(value = "/member/comment_replay.html")
    public String guestBookReplay(Integer id, ModelMap model) {
        String result = check(model);
        if (result != null) {
            return result;
        }
        CmsComment comment = commentMng.findById(id);
        if (!comment.getCommentUser().equals(getUser())) {
            WebErrors errors = WebErrors.create(getRequest());
            errors.addErrorCode("error.noPermissionsView");
            return renderErrorPage(model, errors);
        }
        model.addAttribute("comment", comment);
        return renderPage("comment/comment_reply.html", model);
    }

    /**
     * 我的信息所有的评论
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 16:01
     */
    @RequestMapping(value = "/member/news_comments.html")
    public String newsComments(Integer pageNo, ModelMap model) {
        String result = check(model);
        if (result != null) {
            return result;
        }
        Pagination pagination = commentMng.getPageForMember(getSite().getId(), null, null, getUser().getId(), null, null, null, true, cpn(pageNo), CookieUtils.getPageSize(getRequest()));
        model.addAttribute("pagination", pagination);
        return renderPage("comment/comment_manager.html");
    }

    /**
     * 删除评论（id，评论人id，来访ip）
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 16:01
     */
    @RequestMapping(value = "/member/comment_delete.html")
    public String delete(Integer commentId, Integer userId, String ip, String nextUrl, HttpServletRequest request, ModelMap model) {
        String result = check(model);
        if (result != null) {
            return result;
        }
        // 删除单条评论
        CmsUser user = getUser();
        if (commentId != null) {
            CmsComment cmsComment = commentMng.findById(commentId);
            if (cmsComment == null) {
                return renderMessagePage(model, "comment.notFound");
            }
            if (!canDeleteComment(cmsComment, user)) {
                return renderMessagePage( model, "comment.deleteError");
            }
            CmsComment bean = commentMng.deleteById(commentId);
            log.info("delete CmsComment id={}", bean.getId());
        } else {
            // 依据评论人或者评论ip删除评论
            List<CmsComment> comments = commentMng.getListForDel(getSite().getId(), user.getId(), userId, ip);
            for (CmsComment comment : comments) {
                if (!canDeleteComment(comment, user)) {
                    return renderMessagePage(model, "comment.deleteError");
                }
                commentMng.deleteById(comment.getId());
                log.info("delete CmsComment id={}", comment.getId());
            }
        }
        return renderSuccessPage(model, nextUrl);
    }

    private boolean canDeleteComment(CmsComment comment, CmsUser user) {
        //匿名用户评论文章的所有者可以删除
        if (comment.getCommentUser() == null && !comment.getContent().getUser().equals(user)) {
            return false;
        }
        if (comment.getCommentUser() == null && comment.getContent().getUser().equals(user)) {
            return true;
        }
        //非匿名用户评论 文章的所有者可以删除，评论者也可以删除
        return comment.getCommentUser().equals(user) || comment.getContent().getUser().equals(user);
    }

    @Autowired
    private CmsCommentMng commentMng;
}
