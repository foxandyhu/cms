package com.bfly.cms.message.dao.impl;

import com.bfly.cms.message.dao.CmsReceiverMessageDao;
import com.bfly.cms.message.entity.CmsReceiverMessage;
import com.bfly.common.page.Pagination;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import com.bfly.core.base.dao.impl.Finder;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/25 19:05
 */
@Repository
public class CmsReceiverMessageDaoImpl extends
        AbstractHibernateBaseDao<CmsReceiverMessage, Integer> implements
        CmsReceiverMessageDao {

    @Override
    public Pagination getPage(Integer siteId, Integer sendUserId,
                              Integer receiverUserId, String title, Date sendBeginTime,
                              Date sendEndTime, Boolean status, Integer box, Boolean cacheable,
                              int pageNo, int pageSize) {
        String hql = " select msg from CmsReceiverMessage msg where 1=1 ";
        Finder finder = Finder.create(hql);
        if (siteId != null) {
            finder.append(" and msg.site.id=:siteId")
                    .setParam("siteId", siteId);
        }
        // 垃圾箱
        if (sendUserId != null && receiverUserId != null) {
            finder.append(" and ((msg.msgReceiverUser.id=:receiverUserId  and msg.msgBox =:box) or (msg.msgSendUser.id=:sendUserId  and msg.msgBox =:box) )")
                    .setParam("sendUserId", sendUserId).setParam(
                    "receiverUserId", receiverUserId).setParam("box",
                    box);
        } else {
            if (sendUserId != null) {
                finder.append(" and msg.msgSendUser.id=:sendUserId").setParam(
                        "sendUserId", sendUserId);
            }
            if (receiverUserId != null) {
                finder.append(" and msg.msgReceiverUser.id=:receiverUserId")
                        .setParam("receiverUserId", receiverUserId);
            }
            if (box != null) {
                finder.append(" and msg.msgBox =:box").setParam("box", box);
            }
        }
        if (StringUtils.isNotBlank(title)) {
            finder.append(" and msg.msgTitle like:title").setParam("title",
                    "%" + title + "%");
        }
        if (sendBeginTime != null) {
            finder.append(" and msg.sendTime >=:sendBeginTime").setParam(
                    "sendBeginTime", sendBeginTime);
        }
        if (sendEndTime != null) {
            finder.append(" and msg.sendTime <=:sendEndTime").setParam(
                    "sendEndTime", sendEndTime);
        }
        if (status != null) {
            if (status) {
                finder.append(" and msg.msgStatus =true");
            } else {
                finder.append(" and msg.msgStatus =false");
            }
        }
        finder.append(" order by msg.id desc");

        return find(finder, pageNo, pageSize);
    }

    @Override
    public List<CmsReceiverMessage> getList(Integer siteId, Integer sendUserId,
                                            Integer receiverUserId, String title, Date sendBeginTime,
                                            Date sendEndTime, Boolean status, Integer box, Boolean cacheable
            , Integer first, Integer count) {
        String hql = " select msg from CmsReceiverMessage msg where 1=1  ";
        Finder finder = Finder.create(hql);
        if (siteId != null) {
            finder.append(" and msg.site.id=:siteId")
                    .setParam("siteId", siteId);
        }
        // 垃圾箱
        if (sendUserId != null && receiverUserId != null) {
            finder.append(" and ((msg.msgReceiverUser.id=:receiverUserId  and msg.msgBox =:box) or (msg.msgSendUser.id=:sendUserId  and msg.msgBox =:box) )")
                    .setParam("sendUserId", sendUserId).setParam(
                    "receiverUserId", receiverUserId).setParam("box",
                    box);
        } else {
            if (sendUserId != null) {
                finder.append(" and msg.msgSendUser.id=:sendUserId").setParam(
                        "sendUserId", sendUserId);
            }
            if (receiverUserId != null) {
                finder.append(" and msg.msgReceiverUser.id=:receiverUserId")
                        .setParam("receiverUserId", receiverUserId);
            }
            if (box != null) {
                finder.append(" and msg.msgBox =:box").setParam("box", box);
            }
        }
        if (StringUtils.isNotBlank(title)) {
            finder.append(" and msg.msgTitle like:title").setParam("title",
                    "%" + title + "%");
        }
        if (sendBeginTime != null) {
            finder.append(" and msg.sendTime >=:sendBeginTime").setParam(
                    "sendBeginTime", sendBeginTime);
        }
        if (sendEndTime != null) {
            finder.append(" and msg.sendTime <=:sendEndTime").setParam(
                    "sendEndTime", sendEndTime);
        }
        if (status != null) {
            if (status) {
                finder.append(" and msg.msgStatus =true");
            } else {
                finder.append(" and msg.msgStatus =false");
            }
        }
        finder.append(" order by msg.id desc");
        if (first != null) {
            finder.setFirstResult(first);
        }
        if (count != null) {
            finder.setMaxResults(count);
        }
        return find(finder);
    }

    @Override
    public CmsReceiverMessage find(Integer messageId, Integer box) {
        String hql = " select msg from CmsReceiverMessage msg where 1=1  ";
        Finder finder = Finder.create(hql);
        if (messageId != null) {
            finder.append(" and msg.message.id=:messageId").setParam("messageId", messageId);
        }
        if (box != null) {
            finder.append(" and msg.msgBox =:box").setParam("box", box);
        }
        finder.append(" order by msg.id desc");
        List<CmsReceiverMessage> list = find(finder);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public CmsReceiverMessage findById(Integer id) {
        return super.get(id);
    }

    @Override
    public CmsReceiverMessage save(CmsReceiverMessage bean) {
        getSession().save(bean);
        return bean;
    }

    @Override
    public CmsReceiverMessage update(CmsReceiverMessage bean) {
        getSession().update(bean);
        return bean;
    }

    @Override
    public CmsReceiverMessage deleteById(Integer id) {
        CmsReceiverMessage entity = super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    public CmsReceiverMessage[] deleteByIds(Integer[] ids) {
        CmsReceiverMessage[] messages = new CmsReceiverMessage[ids.length];
        for (int i = 0; i < ids.length; i++) {
            messages[i] = get(ids[i]);
            deleteById(ids[i]);
        }
        return messages;
    }

    @Override
    protected Class<CmsReceiverMessage> getEntityClass() {
        return CmsReceiverMessage.class;
    }

}
