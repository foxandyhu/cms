package com.bfly.web.content.action;

import com.bfly.cms.content.service.IArticleService;
import com.bfly.common.IDEncrypt;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.core.base.action.RenderController;
import com.bfly.core.security.ActionModel;
import com.bfly.core.security.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;

/**
 * 文章评分Controller
 * @author andy_hulibo@163.com
 * @date 2019/9/8 16:28
 */
@Controller("webArticleScoreController")
public class ArticleScoreController extends RenderController {

    @Autowired
    private IArticleService articleService;

    /**
     * 文章评分
     * @author andy_hulibo@163.com
     * @date 2019/9/8 16:29
     */
    @GetMapping(value = "/score-{idStr}-{scoreItemId}")
    @ActionModel(value = "文章评分")
    @Login
    public void scoreArticle(HttpServletResponse response, @PathVariable("idStr") String idStr, @PathVariable("scoreItemId") int scoreItemId) {
        Long articleId = IDEncrypt.decode(idStr);
        if (articleId != null) {
            articleService.incrementArticleScores(articleId.intValue(),scoreItemId);
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}