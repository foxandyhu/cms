package com.bfly.common.hibernate4;

import com.bfly.cms.entity.main.Channel;
import org.apache.commons.lang.StringUtils;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;

@Component
public class TreeIntercptor {
    private static SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        TreeIntercptor.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @PrePersist
    public void onSave(Object entity) {
        if (entity instanceof HibernateTree) {
            HibernateTree<?> tree = (HibernateTree<?>) entity;
            Number parentId = tree.getParentId();
            String beanName = tree.getClass().getName();
            Session session = getSession();
            FlushMode model = session.getFlushMode();
            session.setFlushMode(FlushMode.MANUAL);
            Integer myPosition;
            if (parentId != null) {
                // 如果父节点不为null，则获取节点的右边位置
                String hql = "select bean." + tree.getRgtName() + " from "
                        + beanName + " bean where bean.id=:pid";
                myPosition = ((Number) session.createQuery(hql).setParameter(
                        "pid", parentId).uniqueResult()).intValue();
                String hql1 = "update " + beanName + " bean set bean."
                        + tree.getRgtName() + " = bean." + tree.getRgtName()
                        + " + 2 WHERE bean." + tree.getRgtName()
                        + " >= :myPosition";
                String hql2 = "update " + beanName + " bean set bean."
                        + tree.getLftName() + " = bean." + tree.getLftName()
                        + " + 2 WHERE bean." + tree.getLftName()
                        + " >= :myPosition";
                if (!StringUtils.isBlank(tree.getTreeCondition())) {
                    hql1 += " and (" + tree.getTreeCondition() + ")";
                    hql2 += " and (" + tree.getTreeCondition() + ")";
                }
                session.createQuery(hql1)
                        .setParameter("myPosition", myPosition).executeUpdate();
                session.createQuery(hql2)
                        .setParameter("myPosition", myPosition).executeUpdate();
            } else {
                // 否则查找最大的右边位置
                String hql = "select max(bean." + tree.getRgtName() + ") from "
                        + beanName + " bean";
                if (!StringUtils.isBlank(tree.getTreeCondition())) {
                    hql += " where " + tree.getTreeCondition();
                }
                Number myPositionNumber = (Number) session.createQuery(hql)
                        .uniqueResult();
                // 如不存在，则为0
                if (myPositionNumber == null) {
                    myPosition = 1;
                } else {
                    myPosition = myPositionNumber.intValue() + 1;
                }
            }
            session.setFlushMode(model);
            if (entity instanceof Channel) {
                Channel channel = (Channel) entity;
                channel.setLft(myPosition);
                channel.setRgt(myPosition + 1);
            }
        }
    }

    @PreRemove
    public void onDelete(Object entity) {
        if (entity instanceof HibernateTree) {
            HibernateTree<?> tree = (HibernateTree<?>) entity;
            String beanName = tree.getClass().getName();
            Session session = getSession();
            FlushMode model = session.getFlushMode();
            session.setFlushMode(FlushMode.MANUAL);
            String hql = "select bean." + tree.getLftName() + " from "
                    + beanName + " bean where bean.id=:id";
            Integer myPosition = ((Number) session.createQuery(hql)
                    .setParameter("id", tree.getId()).uniqueResult())
                    .intValue();
            String hql1 = "update " + beanName + " bean set bean."
                    + tree.getRgtName() + " = bean." + tree.getRgtName()
                    + " - 2 WHERE bean." + tree.getRgtName() + " > :myPosition";
            String hql2 = "update " + beanName + " bean set bean."
                    + tree.getLftName() + " = bean." + tree.getLftName()
                    + " - 2 WHERE bean." + tree.getLftName() + " > :myPosition";
            if (!StringUtils.isBlank(tree.getTreeCondition())) {
                hql1 += " and (" + tree.getTreeCondition() + ")";
                hql2 += " and (" + tree.getTreeCondition() + ")";
            }
            session.createQuery(hql1).setInteger("myPosition", myPosition)
                    .executeUpdate();
            session.createQuery(hql2).setInteger("myPosition", myPosition)
                    .executeUpdate();
            session.setFlushMode(model);
        }
    }
}