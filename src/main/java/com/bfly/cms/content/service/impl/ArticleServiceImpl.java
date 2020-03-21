package com.bfly.cms.content.service.impl;

import com.bfly.cms.content.dao.IArticleDao;
import com.bfly.cms.content.entity.*;
import com.bfly.cms.content.entity.dto.ArticleAttrDTO;
import com.bfly.cms.content.entity.dto.ArticleLuceneDTO;
import com.bfly.cms.content.service.IArticleService;
import com.bfly.cms.content.service.IChannelService;
import com.bfly.cms.content.service.ILuceneService;
import com.bfly.cms.content.service.IScoreRecordService;
import com.bfly.cms.member.entity.Member;
import com.bfly.cms.system.service.ISysWaterMarkService;
import com.bfly.common.DateUtil;
import com.bfly.common.IDEncrypt;
import com.bfly.common.StringUtil;
import com.bfly.common.ValidateUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.Constants;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.cache.EhCacheUtil;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.context.MemberThreadLocal;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.enums.ArticleStatus;
import com.bfly.core.enums.ContentType;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

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

    private Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    private IArticleDao articleDao;
    @Autowired
    private IChannelService channelService;
    @Autowired
    private IScoreRecordService scoreRecordService;
    @Autowired
    private ISysWaterMarkService waterMarkService;
    @Autowired
    private ILuceneService luceneService;

    /**
     * 条件SQL
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/4 8:15
     */
    private Sqler getConditionSql(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty) {
        Integer sId = exactQueryProperty != null && exactQueryProperty.containsKey("status") ? (Integer) exactQueryProperty.get("status") : null;
        Integer tId = exactQueryProperty != null && exactQueryProperty.containsKey("type") ? (Integer) exactQueryProperty.get("type") : null;
        Integer cId = exactQueryProperty != null && exactQueryProperty.containsKey("channelId") ? (Integer) exactQueryProperty.get("channelId") : null;
        Integer pId = exactQueryProperty != null && exactQueryProperty.containsKey("pId") ? (Integer) exactQueryProperty.get("pId") : null;
        Boolean recommend = exactQueryProperty != null && exactQueryProperty.containsKey("recommend") ? (Boolean) exactQueryProperty.get("recommend") : null;
        String title = (unExactQueryProperty != null && unExactQueryProperty.containsKey("title")) ? unExactQueryProperty.get("title") : null;
        String fileType = exactQueryProperty != null && exactQueryProperty.containsKey("fileType") ? String.valueOf(exactQueryProperty.get("fileType")) : null;

        List<String> params = new ArrayList<>();
        StringBuffer conditionSql = new StringBuffer();
        if (sId != null) {
            conditionSql.append(" and ar.status=" + sId);
        }
        if (tId != null) {
            conditionSql.append(" and ar.type_id=" + tId);
        }
        if (cId != null) {
            conditionSql.append(" and ar.channel_id=" + cId);
        }
        if (pId != null) {
            List<Channel> channels = channelService.getChildren(pId);
            List<Integer> children = new ArrayList<>();
            children.add(pId);
            if (channels != null) {
                channels.forEach(channel -> children.add(channel.getId()));
            }
            conditionSql.append(" and ar.channel_id in(" + StringUtils.collectionToDelimitedString(children, ",") + ")");
        }
        if (recommend != null) {
            conditionSql.append(" and ar.is_recommend=" + recommend);
        }
        if (title != null) {
            conditionSql.append(" and ar_ext.title like CONCAT('%',?,'%')");
            params.add(title);
        }
        if (fileType != null) {
            conditionSql.append(" and file_type=?");
            params.add(fileType);
        }

        // 扩展属性搜索
        if (exactQueryProperty != null) {
            exactQueryProperty.forEach((key, value) -> {
                if (key.startsWith(Article.ATTR_SEARCH_PREFIX)) {
                    if (value instanceof ArticleAttrDTO) {
                        conditionSql.append(" and attr.attr_name=?");
                        conditionSql.append(" and attr.attr_value=?");
                        ArticleAttrDTO attrDTO = (ArticleAttrDTO) value;
                        params.add(attrDTO.getName());
                        params.add(attrDTO.getValue());
                    }
                }
            });
        }

        Sqler sqler = new Sqler();
        sqler.setSql(conditionSql.toString());
        sqler.setParams(params);
        return sqler;
    }

    /**
     * 关联表SQL
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/9 22:30
     */
    private String getTableSql() {
        StringBuffer sql = new StringBuffer(" FROM article as ar");
        sql.append(" LEFT JOIN channel as ch on ar.channel_id=ch.id");
        sql.append(" LEFT JOIN `user` as u on ar.user_id=u.id");
        sql.append(" LEFT JOIN article_ext as ar_ext on ar.id=ar_ext.article_id");
        sql.append(" LEFT JOIN (select article_id,attr_name,attr_value from article_attr GROUP BY article_id) as attr ON ar.id=attr.article_id ");
        return sql.toString();
    }

    @Override
    public long getCount(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty) {
        return getCount(exactQueryProperty, unExactQueryProperty, null);
    }

    @Override
    public long getCount(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty, Map<String, String> groupProperty) {
        String countSql = "SELECT count(1) as count".concat(getTableSql()).concat("where 1=1");
        Sqler sqler = getConditionSql(exactQueryProperty, unExactQueryProperty);
        countSql = countSql.concat(sqler.getSql());

        long total = getCountSql(countSql, sqler.getParams().toArray());
        return total;
    }

    @Override
    public List getList(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty, Map<String, Sort.Direction> sortQueryProperty) {
        Pager pager = PagerThreadLocal.get();
        Assert.notNull(pager, "分页器没有实例化");

        String selectSql = "SELECT ar.id,ar.channel_id as channelId,ch.channel_name as channelName,ch.channel_path as channelPath,ar.user_id as userId,u.username as userName,";
        selectSql = selectSql.concat("ar.type_id as typeId,ar.is_recommend as recommend,ar.recommend_level as recommendLevel,ar.top_level as topLevel,ar.views,ar.comments,ar.downloads,ar.ups,");
        selectSql = selectSql.concat("ar.`status`,ar_ext.post_date as postDate,ar_ext.title,ar_ext.short_title as shortTitle,ar_ext.summary,ar_ext.title_img as titleImg,ar_ext.title_color as titleColor,");
        selectSql = selectSql.concat("ar_ext.file_type as fileType, ar_ext.file_length as fileLength,ar.top_expired as topExpired,ar_ext.tags");
        selectSql = selectSql.concat(getTableSql());

        Sqler sqler = getConditionSql(exactQueryProperty, unExactQueryProperty);
        StringBuffer conditionSql = new StringBuffer(" where 1=1").append(sqler.getSql());

        conditionSql.append(" ORDER BY ");
        if (sortQueryProperty != null) {
            String topLevel = "top_level", recommendLevel = "recommend_level", views = "views", comments = "comments";
            String topLevelSort = "topLevelSort", recommendLevelSort = "recommendLevelSort", viewsSort = "viewsSort", commentsSort = "commentsSort";
            if (sortQueryProperty.containsKey(topLevelSort)) {
                // 置顶排序
                sort(conditionSql, topLevel, sortQueryProperty.get(topLevelSort));
            }
            if (sortQueryProperty.containsKey(recommendLevelSort)) {
                // 推荐排序
                sort(conditionSql, recommendLevel, sortQueryProperty.get(recommendLevelSort));
            }
            if (sortQueryProperty.containsKey(viewsSort)) {
                // 点击率排序
                sort(conditionSql, views, sortQueryProperty.get(viewsSort));
            }
            if (sortQueryProperty.containsKey(commentsSort)) {
                // 评论排序
                sort(conditionSql, comments, sortQueryProperty.get(commentsSort));
            }
        }
        conditionSql.append("ID DESC");
        selectSql = selectSql.concat(conditionSql.toString()).concat(" LIMIT ").concat((pager.getPageNo() - 1) * pager.getPageSize() + ",").concat(pager.getPageSize() + "");
        List<Map<String, Object>> data = querySql(selectSql, sqler.getParams().toArray());

        List<Map<String, Object>> list = new ArrayList<>();

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
        return list;
    }

    @Override
    public Pager getPage(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty, Map<String, Sort.Direction> sortQueryProperty) {
        Pager pager = PagerThreadLocal.get();
        Assert.notNull(pager, "分页器没有实例化");

        long total = getCount(exactQueryProperty, unExactQueryProperty);
        List list = getList(exactQueryProperty, unExactQueryProperty, sortQueryProperty);

        pager.setTotalCount(total);
        pager.setData(list);
        return pager;
    }

    /**
     * 根据指定列排序
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/3 15:13
     */
    private void sort(StringBuffer sql, String column, Sort.Direction sort) {
        sql.append(column + (sort == Sort.Direction.ASC ? " ASC" : " DESC")).append(",");
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

                    //删除索引库
                    luceneService.deleteIndex(id);
                }
            }
        }
        return super.remove(integers);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void verifyArticle(ArticleStatus status, Integer... articleIds) {
        if (articleIds == null || articleIds.length == 0) {
            return;
        }
        Integer[] ids = new Integer[0];
        if (status == ArticleStatus.PASSED) {
            // 发布日期大于当前日期的文章 审核通过状态 会重置为发布中状态
            Article article;
            Integer[] passingIds = new Integer[0];
            for (Integer articleId : articleIds) {
                article = get(articleId);
                if (article != null && DateUtil.formatterDate(article.getArticleExt().getPostDate()).after(DateUtil.formatterDate(new Date()))) {
                    passingIds = ArrayUtils.add(passingIds, articleId);
                } else {
                    ids = ArrayUtils.add(ids, articleId);
                }
            }
            articleDao.verifyArticle(ArticleStatus.PASSING.getId(), passingIds);
        }
        articleDao.verifyArticle(status.getId(), ids);
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

        article = doFile(article, null);
        article = doArticlePic(article, null);
        article = doAttachments(article, null);
        article = doPictures(article, null);

        ArticleExt ext = article.getArticleExt();
        if (ext != null) {
            ext.setArticle(article);
        }
        ArticleTxt txt = article.getArticleTxt();
        if (txt != null) {
            txt.setArticle(article);
        }

        boolean flag = super.save(article);
        if (flag) {
            luceneService.createIndex(article);
        }
        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(Article article) {
        Article dbArticle = get(article.getId());
        Assert.notNull(dbArticle, "不存在的文章对象!");
        verify(article);

        article = doFile(article, dbArticle);
        article = doArticlePic(article, dbArticle);
        article = doAttachments(article, dbArticle);
        article = doPictures(article, dbArticle);

        article.setStatus(dbArticle.getStatus());
        article.setChannelId(dbArticle.getChannelId());

        boolean flag = super.edit(article);
        if (flag) {
            luceneService.createIndex(article);
        }
        return flag;
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

        ArticleExt ext = article.getArticleExt();
        if (ext != null) {
            //如果发布日期没有指定则默认为当前日期
            if (ext.getPostDate() == null) {
                ext.setPostDate(new Date());
            }
        }

        ArticleStatus status = ArticleStatus.getStatus(article.getStatus());
        if (status == null) {
            //默认为审核通过状态
            article.setStatus(ArticleStatus.PASSED.getId());
        }

        if (article.getStatus() == ArticleStatus.PASSED.getId()) {
            if (DateUtil.formatterDate(ext.getPostDate()).after(DateUtil.formatterDate(new Date()))) {
                //  发布日期大于当前日期并且状态是审核通过状态 则重置为发布中状态,系统会根据发布日期自动发布文章
                article.setStatus(ArticleStatus.PASSING.getId());
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
                waterMarkService.waterMarkFile(img);
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
                waterMarkService.waterMarkFile(img);
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
                waterMarkService.waterMarkFile(img);
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
     * 处理文件
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/8 14:14
     */
    private Article doFile(Article article, Article dbArticle) {
        if (article.getArticleExt() != null) {
            String filePath = article.getArticleExt().getFilePath();
            if (!StringUtils.hasLength(filePath)) {
                article.getArticleExt().setFilePath(null);
                article.getArticleExt().setFileType(null);
                article.getArticleExt().setFileLength(null);
                return article;
            }
            String reallyFile= org.apache.commons.lang3.StringUtils.substring(filePath,0, org.apache.commons.lang3.StringUtils.indexOf(filePath, Constants.TEMP_RESOURCE_SUFFIX,0));
            filePath = ResourceConfig.getUploadTempFileToDestDirForRelativePath(filePath, ValidateUtil.isMedia(reallyFile) ? ResourceConfig.getMediaDir() : ResourceConfig.getDocDir());
            if (filePath != null) {
                //新上传的文件
                article.getArticleExt().setFilePath(filePath);
                String fullPath = ResourceConfig.getAbsolutePathForRoot(filePath);
                File file = new File(fullPath);
                String fileLength;
                if (ValidateUtil.isMedia(file)) {
                    fileLength = getMediaLength(file);
                } else {
                    fileLength = StringUtil.getHumanSize(file.length());
                }
                article.getArticleExt().setFileLength(fileLength);
            } else {
                //与数据库比较
                if (dbArticle != null) {
                    ArticleExt dbExt = dbArticle.getArticleExt();
                    if (dbExt != null) {
                        article.getArticleExt().setFilePath(dbExt.getFilePath());
                        article.getArticleExt().setFileLength(dbExt.getFileLength());
                    }
                }
            }
        }
        return article;
    }

    /**
     * 得到多媒体播放时长
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/8 12:29
     */
    private String getMediaLength(File file) {
        Encoder encoder = new Encoder();
        String timeStr = "";
        try {
            MultimediaInfo m = encoder.getInfo(file);
            long time = m.getDuration();
            long hD = 1000 * 60 * 60, mD = 1000 * 60, lD = 10;
            long hours = time / hD;
            long minutes = (time - hours * hD) / mD;
            long seconds = (time - hours * hD - minutes * mD) / 1000;
            if (minutes < lD) {
                timeStr = hours + ":0" + minutes;
            } else {
                timeStr = hours + ":" + minutes;
            }
            timeStr = timeStr + ":" + (seconds < lD ? "0" : "") + seconds;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeStr;
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
                    waterMarkService.waterMarkFile(path);
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

    @Override
    public Map<String, Object> getNext(int currentArticleId, Integer channelId) {
        Map<String, Object> map = articleDao.getNext(currentArticleId, channelId, ArticleStatus.PASSED.getId());
        return mapConvert(map);
    }

    @Override
    public Map<String, Object> getPrev(int currentArticleId, Integer channelId) {
        Map<String, Object> map = articleDao.getPrev(currentArticleId, channelId, ArticleStatus.PASSED.getId());
        return mapConvert(map);
    }

    private Map<String, Object> mapConvert(Map<String, Object> map) {
        Map<String, Object> result = null;
        if (MapUtils.isNotEmpty(map)) {
            result = new HashMap<>(map.size());
            result.putAll(map);
            result.put("idStr", IDEncrypt.encode((int) map.get("id")));
            result.remove("id");
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void incrementArticleComments(int articleId, int setup) {
        Article article = get(articleId);
        Assert.isTrue(article != null && article.isComment(), "该文章禁止评论!");
        articleDao.editArticleComments(articleId, setup);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void incrementArticleViews(int articleId, int setup) {
        articleDao.editArticleViews(articleId, setup);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void incrementArticleDownloads(int articleId, int setup) {
        articleDao.editArticleDownloads(articleId, setup);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void incrementArticleUpDowns(int articleId, boolean isUp) {
        Member member = MemberThreadLocal.get();
        String key = member.getId() + "-UPDOWN-" + articleId;
        if (EhCacheUtil.isExist(EhCacheUtil.ARTICLE_UPDOWN_SCORE_CACHE, key)) {
            //在缓存时间内再次操作无效
            return;
        }
        Article article = get(articleId);
        Assert.isTrue(article != null && article.isUpdown(), "该文章禁止顶踩!");
        EhCacheUtil.setData(EhCacheUtil.ARTICLE_UPDOWN_SCORE_CACHE, key, 1);
        if (isUp) {
            articleDao.editArticleUps(articleId);
        } else {
            articleDao.editArticleDowns(articleId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void incrementArticleScores(int articleId, int scoreItemId) {
        Member member = MemberThreadLocal.get();
        String key = member.getId() + "-SCORE-" + articleId;
        if (EhCacheUtil.isExist(EhCacheUtil.ARTICLE_UPDOWN_SCORE_CACHE, key)) {
            //在缓存时间内再次操作无效
            return;
        }
        Article article = get(articleId);
        Assert.isTrue(article != null && article.isScore(), "该文章禁止评分!");
        EhCacheUtil.setData(EhCacheUtil.ARTICLE_UPDOWN_SCORE_CACHE, key, 1);
        scoreRecordService.saveScoreRecord(articleId, scoreItemId);
        articleDao.editArticleScores(articleId, scoreItemId);
    }

    /**
     * SQL类 封装了查询sql和查询参数
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/10 17:22
     */
    class Sqler {
        private String sql;
        private List<String> params;

        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }

        public List<String> getParams() {
            return params;
        }

        public void setParams(List<String> params) {
            this.params = params;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int resetArticleTopLevelForExpired(Date date) {
        return articleDao.resetArticleTopLevelForExpired(date);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int autoPublishArticle() {
        return articleDao.autoPublishArticle(ArticleStatus.PASSED.getId(), ArticleStatus.PASSING.getId());
    }

    @Override
    @Async
    public void resetArticleIndex() {
        boolean flag = luceneService.deleteAll();
        if (flag) {
            long total = getCount();
            int pageSize = 100;
            long totalPage = (total + pageSize - 1) / pageSize;
            logger.info("开始重构索引库!");
            for (int i = 0; i < totalPage; i++) {
                List<Map<String, Object>> list = articleDao.getArticleLuceneDTO(i * pageSize, pageSize);
                ArticleLuceneDTO[] articles = convertToArticleLucene(list);

                long begin = System.currentTimeMillis();
                flag = luceneService.createIndex(articles);
                long end = System.currentTimeMillis();
                if (flag) {
                    logger.info("成功建立索引" + articles.length + "条,耗时" + (end - begin) / 1000 + "秒!");
                } else {
                    logger.warn("重构索引库失败!");
                }
            }
            logger.info("结束重构索引库!");
        }
    }

    private ArticleLuceneDTO[] convertToArticleLucene(List<Map<String, Object>> list) {
        if (list == null) {
            return new ArticleLuceneDTO[0];
        }
        ArticleLuceneDTO[] articles = new ArticleLuceneDTO[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ArticleLuceneDTO dto = new ArticleLuceneDTO();
            Map<String, Object> map = list.get(i);
            dto.setId((int) map.get("id"));
            dto.setStatus((int) map.get("status"));
            dto.setTitle((String) map.get("title"));
            dto.setTitleImg((String) map.get("titleImg"));
            dto.setChannelPath((String) map.get("channelPath"));
            dto.setTxt((String) map.get("txt"));
            dto.setSummary((String) map.get("summary"));
            dto.setPostDate((Date) map.get("postDate"));
            articles[i] = dto;
        }
        return articles;
    }

    @Override
    public Pager<ArticleLuceneDTO> searchFromIndex(String keyWord) {
        if (!StringUtils.hasLength(keyWord)) {
            return null;
        }
        return luceneService.query(keyWord);
    }


}