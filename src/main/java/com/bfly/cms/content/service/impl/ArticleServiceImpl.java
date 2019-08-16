package com.bfly.cms.content.service.impl;

import com.bfly.cms.content.dao.IArticleDao;
import com.bfly.cms.content.entity.*;
import com.bfly.cms.content.service.IArticleService;
import com.bfly.cms.content.service.IChannelService;
import com.bfly.common.page.Pager;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.enums.ArticleStatus;
import com.bfly.core.enums.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.File;
import java.math.BigInteger;
import java.util.*;

/**
 * @author andy_hulibo@163.com
 * @date 2019/8/1 13:45
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class ArticleServiceImpl extends BaseServiceImpl<Article, Integer> implements IArticleService {

    @Autowired
    private IArticleDao articleDao;
    @Autowired
    private IChannelService channelService;

    @Override
    public Pager getPage(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty, Map<String, Sort.Direction> sortQueryProperty) {
        Pager pager = PagerThreadLocal.get();
        Assert.notNull(pager, "分页器没有实例化");

        Pageable pageable = getPageRequest(pager);
        Page<Map<String, Object>> page = articleDao.getArticlePager((Integer) exactQueryProperty.get("status"),
                (Integer) exactQueryProperty.get("type"), (Integer) exactQueryProperty.get("channelId"), unExactQueryProperty.get("title"), pageable);
        List<Map<String, Object>> list = new ArrayList<>();

        List<Map<String, Object>> data = page.getContent();
        if (data != null) {
            data.forEach(itemMap -> {
                Map<String, Object> item = new HashMap<>(itemMap.size() + 2);
                int typeId = (int) itemMap.get("typeId");
                ContentType type = ContentType.getType(typeId);
                item.put("typeName", type == null ? "" : type.getName());

                int status = (int) itemMap.get("status");
                ArticleStatus s = ArticleStatus.getStatus(status);
                item.put("statusName", s == null ? "" : s.getName());
                item.putAll(itemMap);
                list.add(item);
            });
        }

        pager.setTotalCount(page.getTotalElements());
        pager.setData(list);
        return pager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRelatedSpecialTopic(Collection<? extends Integer> articleIds, Collection<? extends Integer> topicIds) {
        if (articleIds != null && topicIds != null) {
            articleIds.forEach(articleId -> {
                topicIds.forEach(topicId -> {
                    //查询是否存在关联 不存在则添加关联
                    boolean exist = articleDao.isExistSpecialTopicArticleShip(articleId, topicId);
                    if (!exist) {
                        articleDao.saveSpecialTopicArticleShip(articleId, topicId);
                    }
                });
            });
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeRelatedSpecialTopic(Integer articleId, Integer topicId) {
        articleDao.removeSpecialTopicArticleShip(articleId, topicId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int remove(Integer... integers) {
        if (integers != null) {
            for (Integer id : integers) {
                if (id != null) {
                    //解除文章和会员组之间的浏览关系
                    articleDao.removeArticleGroupViewShip(id);
                    //清除文章的评分记录
                    articleDao.removeArticleScore(id);
                    //清除文章和专题的关联
                    articleDao.clearSpecialTopicArticleShip(id);
                }
            }
        }
        return super.remove(integers);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void verifyArticle(ArticleStatus status, Integer... articleIds) {
        articleDao.verifyArticle(status.getId(), articleIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recommendArticle(boolean recommend, int recommendLevel, Integer... articleIds) {
        if (!recommend) {
            //取消推荐 则级别为0
            recommendLevel = 0;
        }
        articleDao.recommendArticle(recommend, recommendLevel, articleIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editArticleTop(int articleId, int topLevel, Date expired) {
        //不置顶 有效期则为null
        if (topLevel == 0) {
            expired = null;
        }
        articleDao.editArticleTop(articleId, topLevel, expired);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Article article) {
        verify(article);

        //新增文章默认数据
        article.setViews(0);
        article.setDownloads(0);
        article.setUps(0);
        article.setDowns(0);
        article.setComments(0);

        article = doMedia(article, null);
        article = doDoc(article, null);
        article = doArticlePic(article, null);
        article = doAttachments(article,null);
        article = doPictures(article,null);

        ArticleExt ext = article.getArticleExt();
        if (ext != null) {
            ext.setArticle(article);
        }
        ArticleTxt txt = article.getArticleTxt();
        if (txt != null) {
            txt.setArticle(article);
        }

        return super.save(article);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(Article article) {
        Article dbArticle = get(article.getId());
        Assert.notNull(dbArticle, "不存在的文章对象!");
        verify(article);

        article = doMedia(article, dbArticle);
        article = doDoc(article, dbArticle);
        article = doArticlePic(article, dbArticle);
        article = doAttachments(article, dbArticle);
        article = doPictures(article, dbArticle);

        article.setStatus(dbArticle.getStatus());
        article.setChannelId(dbArticle.getChannelId());

        return super.edit(article);
    }

    /**
     * 校验
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/8 14:47
     */
    private Article verify(Article article) {
        ContentType contentType = ContentType.getType(article.getType());
        if (contentType == null) {
            //默认为普通的内容类型
            article.setType(ContentType.NORMAL.getId());
        }

        ArticleStatus status = ArticleStatus.getStatus(article.getStatus());
        if (status == null) {
            //默认为审核通过状态
            article.setStatus(ArticleStatus.PASSED.getId());
        }

        ArticleExt ext = article.getArticleExt();
        if (ext != null) {
            //如果发布日期没有指定则默认为当前日期
            if (ext.getPostDate() == null) {
                ext.setPostDate(new Date());
            }
        }
        if (article.getTopLevel() <= 0) {
            //如果置顶等级为0 则设置失效日期无效
            article.setTopLevel(0);
            article.setTopExpired(null);
        }

        Channel channel = channelService.get(article.getChannelId());
        Assert.notNull(channel, "未指定文章所属栏目!");
        return article;
    }

    /**
     * 处理文章图片
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/8 14:27
     */
    private Article doArticlePic(Article article, Article dbArticle) {
        ArticleExt ext = article.getArticleExt();
        if (ext != null) {
            String img = ResourceConfig.getUploadTempFileToDestDirForRelativePath(ext.getTitleImg(), ResourceConfig.getContentDir());
            if (img != null) {
                //新增
                ext.setTitleImg(img);
            } else {
                //与数据库比较
                if (dbArticle != null) {
                    ArticleExt dbExt = dbArticle.getArticleExt();
                    if (dbExt != null) {
                        ext.setTitleImg(dbExt.getTitleImg());
                    }
                }
            }
            img = ResourceConfig.getUploadTempFileToDestDirForRelativePath(ext.getContentImg(), ResourceConfig.getContentDir());
            if (img != null) {
                //新增
                ext.setContentImg(img);
            } else {
                //与数据库比较
                if (dbArticle != null) {
                    ArticleExt dbExt = dbArticle.getArticleExt();
                    if (dbExt != null) {
                        ext.setContentImg(dbExt.getContentImg());
                    }
                }
            }
            img = ResourceConfig.getUploadTempFileToDestDirForRelativePath(ext.getTypeImg(), ResourceConfig.getContentDir());
            if (img != null) {
                //新增
                ext.setTypeImg(img);
            } else {
                //与数据库比较
                if (dbArticle != null) {
                    ArticleExt dbExt = dbArticle.getArticleExt();
                    if (dbExt != null) {
                        ext.setTypeImg(dbExt.getTypeImg());
                    }
                }
            }
        }
        return article;
    }

    /**
     * 处理多媒体
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/8 14:14
     */
    private Article doMedia(Article article, Article dbArticle) {
        if (article.getArticleExt() != null) {
            String media = article.getArticleExt().getMediaPath();
            media = ResourceConfig.getUploadTempFileToDestDirForRelativePath(media, ResourceConfig.getMediaDir());
            if (media != null) {
                //新上传的多媒体
                article.getArticleExt().setMediaPath(media);
            } else {
                //与数据库比较
                if (dbArticle != null) {
                    ArticleExt dbExt = dbArticle.getArticleExt();
                    if (dbExt != null) {
                        article.getArticleExt().setMediaPath(dbExt.getMediaPath());
                    }
                }
            }
        }
        return article;
    }

    /**
     * 处理文档
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/9 20:44
     */
    private Article doDoc(Article article, Article dbArticle) {
        if (article.getArticleExt() != null) {
            String doc = article.getArticleExt().getDocPath();
            doc = ResourceConfig.getUploadTempFileToDestDirForRelativePath(doc, ResourceConfig.getDocDir());
            if (doc != null) {
                //新上传的多媒体
                article.getArticleExt().setDocPath(doc);
            } else {
                //与数据库比较
                if (dbArticle != null) {
                    ArticleExt dbExt = dbArticle.getArticleExt();
                    if (dbExt != null) {
                        article.getArticleExt().setDocPath(dbExt.getDocPath());
                    }
                }
            }
        }
        return article;
    }

    /**
     * 处理文章附件
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/8 13:55
     */
    private Article doAttachments(Article article, Article dbArticle) {
        List<ArticleAttachment> attachments = article.getAttachments();
        if (attachments != null) {
            attachments.forEach(item -> {
                String path = ResourceConfig.getUploadTempFileToDestDirForRelativePath(item.getPath(), ResourceConfig.getAttachmentDir());
                //新增的附件
                if (path != null) {
                    item.setPath(path);
                    item.setCount(0);

                    File file = new File(ResourceConfig.getRootDir() + path);
                    item.setName(file.getName());
                } else {
                    //与数据库比较
                    if (dbArticle != null) {
                        List<ArticleAttachment> attachmentList = dbArticle.getAttachments();
                        if (attachmentList != null) {
                            attachmentList.forEach(dbAtt -> {
                                if (dbAtt.getId() == item.getId()) {
                                    item.setPath(dbAtt.getPath());
                                }
                            });
                        }
                    }
                }
            });
        }
        return article;
    }

    /**
     * 处理文章图片集
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/8 13:55
     */
    private Article doPictures(Article article, Article dbArticle) {
        List<ArticlePicture> pictures = article.getPictures();
        if (pictures != null) {
            pictures.forEach(item -> {
                String path = ResourceConfig.getUploadTempFileToDestDirForRelativePath(item.getImgPath(), ResourceConfig.getContentDir());
                //新增的图片
                if (path != null) {
                    item.setImgPath(path);
                } else {
                    //与数据库比较
                    if (dbArticle != null) {
                        List<ArticlePicture> pictureList = dbArticle.getPictures();
                        if (pictureList != null) {
                            pictureList.forEach(dbPic -> {
                                if (dbPic.getId() == item.getId()) {
                                    item.setImgPath(dbPic.getImgPath());
                                }
                            });
                        }
                    }
                }
            });
        }
        return article;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delArticlePicture(int picId) {
        articleDao.delArticlePicture(picId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delArticleAttachment(int attachmentId) {
        articleDao.delArticleAttachment(attachmentId);
    }

    @Override
    public Map<String, BigInteger> getTodayAndTotalArticle() {
        return articleDao.getTodayAndTotalArticle();
    }

    @Override
    public List<Map<String, Object>> getClickTopArticle(int limit) {
        return articleDao.getClickTopArticle(limit);
    }

    @Override
    public List<Map<String, Object>> getCommentsTopArticle(int limit) {
        return articleDao.getCommentsTopArticle(limit);
    }
}
