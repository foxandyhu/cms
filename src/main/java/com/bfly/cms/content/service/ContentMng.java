package com.bfly.cms.content.service;

import com.bfly.cms.content.entity.Content;
import com.bfly.cms.content.entity.Content.ContentStatus;
import com.bfly.cms.content.entity.ContentExt;
import com.bfly.cms.content.entity.ContentRecord.ContentOperateType;
import com.bfly.cms.content.entity.ContentTxt;
import com.bfly.cms.user.entity.CmsAdmin;
import com.bfly.common.page.Pagination;
import com.bfly.core.exception.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/4 16:42
 */
public interface ContentMng extends ChannelDeleteChecker {
    Pagination getPageByRight(Integer share, String title, Integer typeId,
                              Integer currUserId, Integer inputUserId, boolean topLevel, boolean recommend,
                              ContentStatus status, Byte checkStep,
                              Integer channelId, Integer userId, int orderBy, int pageNo,
                              int pageSize);

    Pagination getPageCountByRight(Integer share, String title, Integer typeId,
                                   Integer currUserId, Integer inputUserId, boolean topLevel, boolean recommend,
                                   ContentStatus status, Byte checkStep,
                                   Integer channelId, Integer userId, int orderBy, int pageNo,
                                   int pageSize);


    Pagination getPageBySite(String title, Integer typeId, Integer currUserId, Integer inputUserId, boolean topLevel,
                             boolean recommend, ContentStatus status, int orderBy, int pageNo, int pageSize);

    Pagination getPageCountBySite(String title, Integer typeId, Integer currUserId, Integer inputUserId, boolean topLevel,
                                  boolean recommend, ContentStatus status, int orderBy, int pageNo, int pageSize);

    /**
     * 获得文章分页。供会员中心使用。
     *
     * @param title     文章标题
     * @param channelId 栏目ID
     * @param memberId  会员ID
     * @param pageNo    页码
     * @param pageSize  每页大小
     * @return 文章分页对象
     */
    Pagination getPageForMember(String title, Integer channelId, Integer modelId, Integer memberId, int pageNo, int pageSize);

    List<Content> getListForMember(String title, Integer channelId, Integer modelId, Integer memberId, int first, int count);


    /**
     * 根据内容ID数组获取文章列表
     *
     * @param ids
     * @param orderBy
     * @return
     */
    List<Content> getListByIdsForTag(Integer[] ids, int orderBy);

    /**
     * 查询固顶级别大于topLevel 且到期内容
     *
     * @param topLevel   固顶级别
     * @param expiredDay 固顶到期日期
     * @return
     */
    List<Content> getExpiredTopLevelContents(byte topLevel, Date expiredDay);

    /**
     * 查询到了归档日期内容
     *
     * @param pigeonholeDay
     * @return
     */
    List<Content> getPigeonholeContents(Date pigeonholeDay);

    Content getSide(Integer id, Integer channelId,
                    boolean next);

    Pagination getPageBySiteIdsForTag(Integer[] typeIds, Boolean titleImg, Boolean recommend,
                                      String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize);

    List<Content> getListBySiteIdsForTag(Integer[] typeIds, Boolean titleImg, Boolean recommend,
                                         String title, Map<String, String[]> attr, int orderBy, Integer first, Integer count);

    Pagination getPageByChannelIdsForTag(Integer[] channelIds,
                                         Integer[] typeIds, Boolean titleImg, Boolean recommend,
                                         String title, Map<String, String[]> attr, int orderBy, int option, int pageNo, int pageSize);

    List<Content> getListByChannelIdsForTag(Integer[] channelIds,
                                            Integer[] typeIds, Boolean titleImg, Boolean recommend,
                                            String title, Map<String, String[]> attr, int orderBy, int option, Integer first, Integer count);

    Pagination getPageByChannelPathsForTag(String[] paths, Integer[] typeIds, Boolean titleImg,
                                           Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize);

    List<Content> getListByChannelPathsForTag(String[] paths, Integer[] typeIds, Boolean titleImg,
                                              Boolean recommend, String title, Map<String, String[]> attr, int orderBy, Integer first, Integer count);

    Pagination getPageByTopicIdForTag(Integer topicId, Integer[] channelIds, Integer[] typeIds,
                                      Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo,
                                      int pageSize);

    List<Content> getListByTopicIdForTag(Integer topicId, Integer[] channelIds, Integer[] typeIds,
                                         Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, Integer first,
                                         Integer count);

    Pagination getPageByTagIdsForTag(Integer[] tagIds, Integer[] channelIds, Integer[] typeIds,
                                     Integer excludeId, Boolean titleImg, Boolean recommend,
                                     String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize);

    List<Content> getListByTagIdsForTag(Integer[] tagIds, Integer[] channelIds, Integer[] typeIds,
                                        Integer excludeId, Boolean titleImg, Boolean recommend,
                                        String title, Map<String, String[]> attr, int orderBy, Integer first, Integer count);

    Content findById(Integer id);

    Content save(Content bean, ContentExt ext, ContentTxt txt, Integer[] channelIds,
                 Integer[] topicIds, Integer[] viewGroupIds, String[] tagArr,
                 String[] attachmentPaths, String[] attachmentNames,
                 String[] attachmentFilenames, String[] picPaths,
                 String[] picDescs, Integer channelId, Integer typeId,
                 Boolean draft, Boolean contribute, Short charge,
                 Double chargeAmount, Boolean rewardPattern,
                 Double rewardRandomMin, Double rewardRandomMax,
                 Double[] rewardFix, CmsAdmin admin,
                 boolean forMember);

    Content save(Content bean, ContentExt ext, ContentTxt txt, Integer channelId,
                 Integer typeId, Boolean draft, CmsAdmin admin, boolean forMember);

    Content update(Content bean, ContentExt ext, ContentTxt txt, String[] tagArr,
                   Integer[] channelIds, Integer[] topicIds, Integer[] viewGroupIds,
                   String[] attachmentPaths, String[] attachmentNames,
                   String[] attachmentFilenames, String[] picPaths,
                   String[] picDescs, Map<String, String> attr, Integer channelId,
                   Integer typeId, Boolean draft, Short charge,
                   Double chargeAmount, Boolean rewardPattern,
                   Double rewardRandomMin, Double rewardRandomMax,
                   Double[] rewardFix, CmsAdmin admin,
                   boolean forMember);

    Content update(Content bean);

    Content update(CmsAdmin admin, Content bean, ContentOperateType operate);

    Content updateByChannelIds(Integer contentId, Integer[] channelIds, Integer operate);

    Content addContentToTopics(Integer contentId, Integer[] topicIds);

    Content check(Integer id, CmsAdmin admin);

    Content[] check(Integer[] ids, CmsAdmin admin);

    Content submit(Integer id, CmsAdmin admin);

    Content[] submit(Integer[] ids, CmsAdmin admin);

    Content reject(Integer id, CmsAdmin admin, Byte step, String opinion);

    Content[] reject(Integer[] ids, CmsAdmin admin, Byte step, String opinion);

    Content cycle(CmsAdmin admin, Integer id);

    Content[] cycle(CmsAdmin admin, Integer[] ids);

    Content recycle(Integer id);

    Content[] recycle(Integer[] ids);

    Content deleteById(Integer id);

    Content[] deleteByIds(Integer[] ids);

    Content[] contentStatic(CmsAdmin admin, Integer[] ids)
            throws TemplateNotFoundException, TemplateParseException,
            GeneratedZeroStaticPageException, StaticPageNotOpenException,
            ContentNotCheckedException;

    Pagination getPageForCollection(Integer memberId, int pageNo, int pageSize);

    List<Content> getListForCollection(Integer memberId, Integer first, Integer count);

    void updateFileByContent(Content bean, Boolean valid);

    List<ContentListener> getListenerList();

    List<Map<String, Object>> preChange(Content content);

}