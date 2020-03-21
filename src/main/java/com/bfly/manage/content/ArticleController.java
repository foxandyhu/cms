package com.bfly.manage.content;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.content.entity.Article;
import com.bfly.cms.content.entity.ArticleAttachment;
import com.bfly.cms.content.entity.ArticleExt;
import com.bfly.cms.content.entity.ArticlePicture;
import com.bfly.cms.content.service.IArticleService;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.DateUtil;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.json.JsonUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.enums.ArticleStatus;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Iterator;
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
    @PostMapping(value = "/list")
    @ActionModel(value = "文章列表", need = false)
    public void listArticle(HttpServletRequest request, HttpServletResponse response) {
        PagerThreadLocal.set(request);

        Map<String, Object> exactMap = new HashMap<>(2);
        Map<String, String> unExactMap = new HashMap<>(1);

        String type = request.getParameter("type");
        String status = request.getParameter("status");
        String title = request.getParameter("title");
        String channelId = request.getParameter("channelId");

        if (StringUtils.hasText(channelId)) {
            exactMap.put("pId", DataConvertUtils.convertToInteger(channelId));
        }
        if (StringUtils.hasText(type)) {
            exactMap.put("type", DataConvertUtils.convertToInteger(type));
        }
        if (StringUtils.hasText(status)) {
            exactMap.put("status", DataConvertUtils.convertToInteger(status));
        }
        if (StringUtils.hasText(title)) {
            unExactMap.put("title", title);
        }

        Map<String, Sort.Direction> sortMap = new HashMap<>(1);
        sortMap.put("topLevelSort", Sort.Direction.DESC);
        Pager pager = articleService.getPage(exactMap, unExactMap, sortMap);
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
        ArticleExt ext = article.getArticleExt();
        if (ext != null) {
            if (StringUtils.hasLength(ext.getTypeImg())) {
                ext.setTypeImg(ResourceConfig.getServer() + ext.getTypeImg());
            }
            if (StringUtils.hasLength(ext.getTitleImg())) {
                ext.setTitleImg(ResourceConfig.getServer() + ext.getTitleImg());
            }
            if (StringUtils.hasLength(ext.getContentImg())) {
                ext.setContentImg(ResourceConfig.getServer() + ext.getContentImg());
            }
            if (StringUtils.hasLength(ext.getFilePath())) {
                ext.setFilePath(ResourceConfig.getServer() + ext.getFilePath());
            }
        }

        List<ArticlePicture> pictures = article.getPictures();
        Map<String, Object> attrPics = new HashMap<>(5);
        if (pictures != null) {
            Iterator<ArticlePicture> it = pictures.iterator();
            while (it.hasNext()) {
                ArticlePicture picture = it.next();
                if (StringUtils.hasLength(picture.getImgPath())) {
                    picture.setImgPath(ResourceConfig.getServer() + picture.getImgPath());
                }
                if (StringUtils.hasLength(picture.getField())) {
                    //属于自定义图片集属性
                    doAttrs(attrPics, article, picture, picture.getField());
                    it.remove();
                }
            }
        }

        List<ArticleAttachment> attachments = article.getAttachments();
        Map<String, Object> attrAtt = new HashMap<>(5);
        if (attachments != null) {
            Iterator<ArticleAttachment> it = attachments.iterator();
            while (it.hasNext()) {
                ArticleAttachment attachment = it.next();
                if (StringUtils.hasLength(attachment.getPath())) {
                    attachment.setPath(ResourceConfig.getServer() + attachment.getPath());
                }
                if (StringUtils.hasLength(attachment.getField())) {
                    //属于自定义附件集属性
                    doAttrs(attrAtt, article, attachment, attachment.getField());
                    it.remove();
                }
            }
        }
        JSONObject json = JsonUtil.toJsonFilter(article);
        JSONObject attr = json.getJSONObject("attr");
        if (attr != null) {
            attr.putAll(attrPics);
            attr.putAll(attrAtt);
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 处理自定义附件集和图片集
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/13 10:28
     */
    private void doAttrs(Map<String, Object> attrPics, Article article, Object obj, String field) {
        if (article.getAttr().containsKey(field)) {
            Object value = attrPics.get(field);
            JSONArray attachmentList = null;
            if (value == null || value instanceof String) {
                attachmentList = new JSONArray(1);
            } else if (value instanceof JSONArray) {
                attachmentList = (JSONArray) attrPics.get(field);
            }
            if (attachmentList != null) {
                attachmentList.add(obj);
                attrPics.put(field, attachmentList);
            }
        }
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

    /**
     * 删除文章图片集
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/13 10:31
     */
    @GetMapping(value = "/picture/del/{picId}")
    @ActionModel("删除文章图片集")
    public void delArticlePic(HttpServletResponse response, @PathVariable("picId") int picId) {
        articleService.delArticlePicture(picId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 删除文章附件集
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/13 10:31
     */
    @GetMapping(value = "/attachment/del/{attachmentId}")
    @ActionModel("删除文章图片集")
    public void delAttachment(HttpServletResponse response, @PathVariable("attachmentId") int attachmentId) {
        articleService.delArticleAttachment(attachmentId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 重构索引库
     *
     * @author andy_hulibo@163.com
     * @date 2019/10/25 14:41
     */
    @GetMapping(value = "/reset/index")
    @ActionModel("重构文章索引库")
    public void resetIndex() {
        articleService.resetArticleIndex();
    }
}
