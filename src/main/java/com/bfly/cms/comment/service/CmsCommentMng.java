package com.bfly.cms.comment.service;

import com.bfly.cms.comment.entity.CmsComment;
import com.bfly.cms.comment.entity.CmsCommentExt;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.common.page.Pagination;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/4 14:08
 */
public interface CmsCommentMng {

    Pagination getPage(Integer contentId, Integer greaterThen, Short checked, Boolean recommend, boolean desc, int pageNo, int pageSize);

    Pagination getNewPage(Integer contentId, Short checked, Boolean recommend, int pageNo, int pageSize);

    Pagination getPageForTag(Integer contentId, Integer greaterThen, Short checked, Boolean recommend, boolean desc, int pageNo, int pageSize);

    /**
     * @param contentId
     * @param toUserId    写评论的用户
     * @param fromUserId  投稿的信息接收到的相关评论
     * @param greaterThen
     * @param checked
     * @param recommend
     * @param desc
     * @param pageNo
     * @param pageSize
     * @return
     */
    Pagination getPageForMember(Integer contentId, Integer toUserId, Integer fromUserId, Integer greaterThen, Short checked, Boolean recommend, boolean desc, int pageNo, int pageSize);

    List<CmsComment> getListForMember(Integer contentId, Integer toUserId, Integer fromUserId, Integer greaterThen, Short checked, Boolean recommend, boolean desc, Integer first, Integer count);

    /**
     * @param userId        发表信息用户id
     * @param commentUserId 评论用户id
     * @param ip            评论来访ip
     * @return
     */
    List<CmsComment> getListForDel(Integer userId, Integer commentUserId, String ip);

    List<CmsComment> getListForTag(Integer contentId, Integer parentId, Integer greaterThen, Short checked, Boolean recommend, boolean desc, Integer first, int count);

    CmsComment findById(Integer id);

    CmsComment comment(Integer score, String text, String ip, Integer contentId, Integer userId, short checked, boolean recommend, Integer parentId);

    CmsComment update(CmsComment bean, CmsCommentExt ext);

    int deleteByContentId(Integer contentId);

    CmsComment deleteById(Integer id);

    CmsComment[] deleteByIds(Integer[] ids);

    void ups(Integer id);

    void downs(Integer id);

    CmsComment[] checkByIds(Integer[] ids,short checked);
}