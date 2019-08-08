package com.bfly.manage.content;

import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.content.entity.Article;
import com.bfly.cms.content.service.IArticleService;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.DateUtil;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.json.JsonUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.enums.ArticleStatus;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章Controller
 *
 * @author andy_hulibo@163.com
 * @date 2019/8/7 13:12
 */
@RestController
@RequestMapping(value = "/manage/article")
public class ArticleController extends BaseManageController {

    @Autowired
    private IArticleService articleService;

    /**
     * 文章列表
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/7 13:43
     */
    @GetMapping(value = "/list")
    @ActionModel(value = "文章列表", need = false)
    public void listArticle(HttpServletRequest request, HttpServletResponse response) {
        PagerThreadLocal.set(request);

        Map<String, Object> exactMap = new HashMap<>(2);
        Map<String, String> unExactMap = new HashMap<>(1);

        String type = request.getParameter("type");
        String status = request.getParameter("status");
        String title = request.getParameter("title");
        String channelId = request.getParameter("channelId");

        if (channelId != null) {
            exactMap.put("channelId", DataConvertUtils.convertToInteger(channelId));
        }
        if (type != null) {
            exactMap.put("type", DataConvertUtils.convertToInteger(type));
        }
        if (status != null) {
            exactMap.put("status", DataConvertUtils.convertToInteger(status));
        }
        if (title != null) {
            unExactMap.put("title", title);
        }
        Pager pager = articleService.getPage(exactMap, unExactMap, null);
        JSONObject json = JsonUtil.toJsonFilter(pager);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 添加文章
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/7 14:15
     */
    @PostMapping(value = "/add")
    @ActionModel("添加文章")
    public void addArticle(@RequestBody @Valid Article article, BindingResult result, HttpServletResponse response) {
        validData(result);
        article.setUserId(getUser().getId());
        articleService.save(article);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }


    /**
     * 修改文章
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/7 14:15
     */
    @PostMapping(value = "/edit")
    @ActionModel("修改文章")
    public void editArticle(@RequestBody @Valid Article article, BindingResult result, HttpServletResponse response) {
        validData(result);
        article.setUserId(getUser().getId());
        articleService.edit(article);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 查看文章信息
     *
     * @param articleId 文章Id
     * @param response
     */
    @GetMapping(value = "/{articleId}")
    @ActionModel(value = "文章详情", need = false)
    public void viewArticle(@PathVariable("articleId") int articleId, HttpServletResponse response) {
        Article article = articleService.get(articleId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(article));
    }

    /**
     * 删除文章
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/7 14:17
     */
    @PostMapping(value = "/del")
    @ActionModel("删除文章")
    public void delArticle(HttpServletResponse response, @RequestBody Integer... ids) {
        articleService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 审核文章
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/7 16:23
     */
    @PostMapping(value = "/verify/{status}")
    @ActionModel(value = "审核文章")
    public void verifyArticle(HttpServletResponse response, @PathVariable("status") boolean status, @RequestBody Integer... ids) {
        articleService.verifyArticle(status ? ArticleStatus.PASSED : ArticleStatus.UNPASSED, ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 推荐或取消推荐文章
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/7 16:24
     */
    @ActionModel(value = "推荐/取消文章")
    @PostMapping(value = "/recommend/{recommend}-{level}")
    public void recommendArticle(HttpServletResponse response, @PathVariable("recommend") boolean recommend, @PathVariable("level") int level, @RequestBody Integer... ids) {
        articleService.recommendArticle(recommend, level, ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 文章置顶
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/7 16:24
     */
    @ActionModel(value = "文章置顶")
    @PostMapping(value = "/top/{articleId}-{level}")
    public void topArticle(@PathVariable("articleId") int articleId, @PathVariable("level") int level, @RequestBody Map<String, Object> data, HttpServletResponse response) {
        String expired = (String) data.get("expired");
        articleService.editArticleTop(articleId, level, DateUtil.parseStrDate(expired));
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 文章关联专题
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/8 15:59
     */
    @PostMapping(value = "/related/topic")
    @ActionModel(value = "关联专题")
    public void addArticleShipSpecialTopic(HttpServletResponse response, @RequestBody Map<String, List<Integer>> params) {
        List<Integer> articleIds = params.get("article");
        List<Integer> topicIds = params.get("topic");
        articleService.saveRelatedSpecialTopic(articleIds, topicIds);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 删除文章关联专题关系
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/8 15:59
     */
    @GetMapping(value = "/del/{articleId}-{topicId}")
    @ActionModel(value = "删除文章专题关联")
    public void removeArticleShipSpecialTopic(HttpServletResponse response, @PathVariable("articleId") int articleId, @PathVariable("topicId") int topicId) {
        articleService.removeRelatedSpecialTopic(articleId, topicId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}
