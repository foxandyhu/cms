package com.bfly.cms.dao.assist;

import com.bfly.cms.entity.assist.CmsReceiverMessage;
import com.bfly.common.page.Pagination;

import java.util.Date;
import java.util.List;

/**
 * 江西金磊科技发展有限公司jeecms研发
 */
public interface CmsReceiverMessageDao {

    Pagination getPage(Integer siteId, Integer sendUserId,
                       Integer receiverUserId, String title, Date sendBeginTime,
                       Date sendEndTime, Boolean status, Integer box, Boolean cacheable,
                       int pageNo, int pageSize);

    List<CmsReceiverMessage> getList(Integer siteId, Integer sendUserId,
                                     Integer receiverUserId, String title, Date sendBeginTime,
                                     Date sendEndTime, Boolean status, Integer box, Boolean cacheable
            , Integer first, Integer count);

    CmsReceiverMessage find(Integer messageId, Integer box);

    CmsReceiverMessage findById(Integer id);

    CmsReceiverMessage save(CmsReceiverMessage bean);

    CmsReceiverMessage update(CmsReceiverMessage bean);

    CmsReceiverMessage deleteById(Integer id);

    CmsReceiverMessage[] deleteByIds(Integer[] ids);
}