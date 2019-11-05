package com.bfly.web.content.action;

import com.bfly.cms.content.entity.Article;
import com.bfly.cms.content.entity.Channel;
import com.bfly.cms.content.entity.Model;
import com.bfly.cms.content.entity.dto.ArticleAttrDTO;
import com.bfly.cms.content.entity.dto.ArticleLuceneDTO;
import com.bfly.cms.content.service.IArticleService;
import com.bfly.cms.content.service.IChannelService;
import com.bfly.cms.content.service.IModelService;
import com.bfly.common.*;
import com.bfly.common.microsoft.MicroSoftConvert;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.RenderController;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.enums.ArticleStatus;
import com.bfly.core.security.ActionModel;
import com.bfly.core.security.Login;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章Controller
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/4 16:26
 */
@Controller("webArticleController")
public class ArticleController extends RenderController {

    private Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private IArticleService articleService;
    @Autowired
    private IChannelService channelService;
    @Autowired
    private IModelService modelService;

    /**
     * 验证栏目 返回栏目对应的模型对象
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/19 17:03
     */
    private Model verifyChannel(Channel channel) {
        if (channel == null) {
            throw new RuntimeException("不存在的栏目信息!");
        }
        Model model = modelService.get(channel.getModelId());
        if (model == null) {
            throw new RuntimeException("未知的模型信息!");
        }
        return model;
    }

    /**
     * 内容信息
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/19 16:48
     */
    @GetMapping(value = "/{channelPath}/id_{articleId}")
    public String contentDetail(@PathVariable("channelPath") String channelPath, @PathVariable("articleId") String articleId) {
        Channel channel = channelService.getChannelByPath(channelPath);
        Model model = verifyChannel(channel);

        Long aId = IDEncrypt.decode(articleId);
        Assert.notNull(aId, "不存在的内容信息!");

        articleService.incrementArticleViews(aId.intValue(), 1);

        Article article = articleService.get(aId.intValue());
        Assert.notNull(article, "不存在的内容信息!");

        Assert.isTrue(channel.getId() == article.getChannelId(), "不存在的内容信息!");

        convertDoc(article);
        String tpl = isMobileRequest() ? article.getArticleExt().getTplMobile() : article.getArticleExt().getTplPc();
        if (!StringUtils.hasLength(tpl)) {
            //没有特定指定模板 则采用栏目默认的内容模板
            tpl = isMobileRequest() ? channel.getTplMobileContent() : channel.getTplPcContent();
        }
        getRequest().setAttribute("article", article);
        return renderTplPath(tpl, model.getTplDir());
    }

    /**
     * 文档转换为PDF
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/25 12:58
     */
    private void convertDoc(Article article) {
        if (article == null) {
            return;
        }
        String filePath = article.getArticleExt().getFilePath();
        if (StringUtils.hasLength(filePath)) {
            File file = new File(ResourceConfig.getAbsolutePathForRoot(filePath));
            if (!ValidateUtil.isConvertPDF(file)) {
                return;
            }
            if (file.exists()) {
                String sourcePath=file.getAbsolutePath();
                String dirName= FilenameUtils.getBaseName(sourcePath).concat("_").concat(FilenameUtils.getExtension(sourcePath));
                String htmlPath = ResourceConfig.getDocHtmlDir().concat(File.separator).concat(dirName).concat("//index.html");
                file=new File(htmlPath);
                if(!file.exists()) {
                    try {
                        // 如果没有转换 则转换文件写入临时目录
                        MicroSoftConvert.convert(sourcePath, ResourceConfig.getDocHtmlDir());
                    } catch (Exception ex) {
                        logger.error("文档转换失败", ex);
                    }
                }
                filePath = ResourceConfig.getRelativePathForRoot(htmlPath);
            }
            article.getArticleExt().setFilePath(ResourceConfig.getServer() + filePath);
        }
    }

    /**
     * 栏目内容列表
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/20 12:41
     */
    @GetMapping(value = "/{channelPath}/list")
    public String channelArticleList(@PathVariable("channelPath") String channelPath) {
        Channel channel = channelService.getChannelByPath(channelPath);
        verifyChannel(channel);
        getRequest().setAttribute("channel", channel);
        boolean fromMobileAjax = DataConvertUtils.convertToBoolean(getRequest().getParameter("mobileAjax"));
        if (fromMobileAjax) {
            // 移动端分页操作
            return "/search/mobile_ajax_list.html";
        }
        return renderTplPath("list.html", "search");
    }

    /**
     * 下载文档
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/7 17:09
     */
    @ActionModel(value = "下载文件")
    @Login
    @GetMapping(value = "/download/file_{idStr}")
    public void downLoadDoc(@PathVariable("idStr") String idStr, HttpServletResponse response) {
        Long articleId = IDEncrypt.decode(idStr);
        Assert.notNull(articleId, "不存在的下载文件!");

        Article article = articleService.get(articleId.intValue());
        Assert.notNull(article, "不存在的下载文件!");

        String filePath = article.getArticleExt().getFilePath();
        Assert.isTrue(StringUtils.hasLength(filePath), "不存在的下载文件!");

        filePath = ResourceConfig.getAbsolutePathForRoot(article.getArticleExt().getFilePath());

        Assert.isTrue(FileUtil.checkExist(filePath), "不存在的下载文件!");

        articleService.incrementArticleDownloads(article.getId(), 1);
        ResponseUtil.writeDownload(response, new File(filePath));
    }

    /**
     * 顶踩文章
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/8 13:57
     */
    @GetMapping(value = "/updown-{idStr}-{up}")
    @ActionModel(value = "评论顶踩")
    @Login
    public void upDownArticle(HttpServletResponse response, @PathVariable("idStr") String idStr, @PathVariable("up") boolean isUp) {
        Long articleId = IDEncrypt.decode(idStr);
        if (articleId != null) {
            articleService.incrementArticleUpDowns(articleId.intValue(), isUp);
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 文档搜索
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/9 22:47
     */
    @GetMapping(value = "/doc/search")
    public String docSearch() {
        HttpServletRequest request = getRequest();
        PagerThreadLocal.set(request);

        Map<String, Object> exactMap = new HashMap<>(2);
        exactMap.put("status", ArticleStatus.PASSED.getId());

        int channelId = DataConvertUtils.convertToInteger(request.getParameter("channelId"));
        exactMap.put("channelId", channelId);
        request.setAttribute("channelId", channelId);

        String fileType = request.getParameter("category");
        if (StringUtils.hasLength(fileType)) {
            // 文章类型搜索
            exactMap.put("fileType", fileType);
            request.setAttribute("category", fileType);
        }
        String attrName = request.getParameter("attrName");
        String attrValue = request.getParameter("attrValue");
        if (StringUtils.hasLength(attrName) && StringUtils.hasLength(attrValue)) {
            // 文章扩展属性查询
            ArticleAttrDTO attrDTO = new ArticleAttrDTO();
            attrDTO.setName(attrName);
            attrDTO.setValue(attrValue);
            exactMap.put(Article.ATTR_SEARCH_PREFIX + "industry", attrDTO);

            request.setAttribute("attrDTO", attrDTO);
        }

        Pager pager = articleService.getPage(exactMap, null, null);
        idConvert(pager.getData());
        getRequest().setAttribute("pager", pager);
        return renderTplPath("search.html", "doc");
    }

    private void idConvert(List list) {
        if (list != null) {
            list.forEach(item -> {
                Map<String, Object> m = (Map<String, Object>) item;
                Integer id = (Integer) m.get("id");
                m.put("idStr", IDEncrypt.encode(id));
            });
        }
    }

    /**
     * 内容搜索
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/19 21:52
     */
    @GetMapping(value = "/search")
    public String search() {
        PagerThreadLocal.set(getRequest());
        String word = getRequest().getParameter("word");
        Pager<ArticleLuceneDTO> pager = articleService.searchFromIndex(word);
        getRequest().setAttribute("pager", pager);
        getRequest().setAttribute("word", word);
        boolean fromMobileAjax = DataConvertUtils.convertToBoolean(getRequest().getParameter("mobileAjax"));
        if (fromMobileAjax) {
            // 移动端分页操作
            return "/search/mobile_ajax_search.html";
        }
        return renderTplPath("search.html", "search");
    }
}