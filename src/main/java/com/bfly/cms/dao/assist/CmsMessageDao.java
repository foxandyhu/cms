package com.bfly.cms.dao.assist;

import com.bfly.cms.entity.assist.CmsMessage;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.Date;
import java.util.List;

/**
 * 江西金磊科技发展有限公司jeecms研发
 */
public interface CmsMessageDao {

    Pagination getPage(Integer siteId, Integer sendUserId,
                       Integer receiverUserId, String title, Date sendBeginTime,
                       Date sendEndTime, Boolean status, Integer box, Boolean cacheable,
                       int pageNo, int pageSize);

    List<CmsMessage> getList(Integer siteId, Integer sendUserId,
                             Integer receiverUserId, String title, Date sendBeginTime,
                             Date sendEndTime, Boolean status, Integer box, Boolean cacheable,
                             Integer first, Integer count);

    CmsMessage findById(Integer id);

    CmsMessage save(CmsMessage bean);

    CmsMessage updateByUpdater(Updater<CmsMessage> updater);

    CmsMessage update(CmsMessage bean);

    CmsMessage deleteById(Integer id);

    CmsMessage[] deleteByIds(Integer[] ids);
}