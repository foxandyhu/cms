package com.bfly.cms.words.service.impl;

import com.bfly.cms.words.dao.ContentTagDao;
import com.bfly.cms.words.entity.ContentTag;
import com.bfly.cms.words.service.ContentTagMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 15:13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ContentTagMngImpl implements ContentTagMng {
    private static final Logger log = LoggerFactory
            .getLogger(ContentTagMngImpl.class);

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<ContentTag> getListForTag(Integer first, Integer count) {
        return dao.getList(first, count, true);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Pagination getPageForTag(int pageNo, int pageSize) {
        Pagination page = dao.getPage(null, pageNo, pageSize, true);
        return page;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Pagination getPage(String name, int pageNo, int pageSize) {
        Pagination page = dao.getPage(name, pageNo, pageSize, false);
        return page;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public ContentTag findById(Integer id) {
        ContentTag entity = dao.findById(id);
        return entity;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public ContentTag findByName(String name) {
        return dao.findByName(name, false);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public ContentTag findByNameForTag(String name) {
        return dao.findByName(name, true);
    }

    /**
     * @see ContentTagMng#saveTags(String[])
     */
    @Override
    public List<ContentTag> saveTags(String[] tagArr) {
        if (tagArr == null || tagArr.length <= 0) {
            return null;
        }
        List<ContentTag> list = new ArrayList<ContentTag>();
        // 用于检查重复
        Set<String> tagSet = new HashSet<String>();
        ContentTag tag;
        for (String name : tagArr) {
            // 检测重复
            for (String t : tagSet) {
                if (t.equalsIgnoreCase(name)) {
                    continue;
                }
            }
            tagSet.add(name);
            tag = saveTag(name);
            list.add(tag);
        }
        return list;
    }

    /**
     * @see ContentTagMng#saveTag(String)
     */
    @Override
    public ContentTag saveTag(String name) {
        ContentTag tag = findByName(name);
        if (tag != null) {
            tag.setCount(tag.getCount() + 1);
        } else {
            tag = new ContentTag();
            tag.setName(name);
            tag = save(tag);
        }
        return tag;
    }

    @Override
    public List<ContentTag> updateTags(List<ContentTag> tags, String[] tagArr) {
        if (tagArr == null) {
            tagArr = new String[0];
        }
        List<ContentTag> list = new ArrayList<ContentTag>();
        ContentTag bean;
        for (String t : tagArr) {
            bean = null;
            for (ContentTag tag : tags) {
                if (t.equalsIgnoreCase(tag.getName())) {
                    bean = tag;
                    break;
                }
            }
            if (bean == null) {
                bean = saveTag(t);
            }
            list.add(bean);
        }
        Set<ContentTag> toBeRemove = new HashSet<ContentTag>();
        boolean contains;
        for (ContentTag tag : tags) {
            contains = false;
            for (String t : tagArr) {
                if (t.equalsIgnoreCase(tag.getName())) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                toBeRemove.add(tag);
            }
        }
        tags.clear();
        tags.addAll(list);
        removeTags(toBeRemove);
        return tags;
    }

    @Override
    public void removeTags(Collection<ContentTag> tags) {
        Set<ContentTag> toRemove = new HashSet<>();
        for (ContentTag tag : tags) {
            if (tag != null) {
                tag.setCount(tag.getCount() - 1);
                if (tag.getCount() <= 0) {
                    toRemove.add(tag);
                }
            }
        }
        for (ContentTag tag : toRemove) {
            //由于事务真正删除关联的sql语句还没有执行，这个时候jc_contenttag里至少还有一条数据。
            if (dao.countContentRef(tag.getId()) <= 1) {
                dao.deleteById(tag.getId());
            } else {
                // 还有引用，不应该出现的情况，此时无法删除。
                log.warn("ContentTag ref to Content > 1,"
                        + " while ContentTag.ref_counter <= 0");
            }
        }
    }

    @Override
    public ContentTag save(ContentTag bean) {
        bean.init();
        dao.save(bean);
        return bean;
    }

    @Override
    public ContentTag update(ContentTag bean) {
        Updater<ContentTag> updater = new Updater<ContentTag>(bean);
        ContentTag entity = dao.updateByUpdater(updater);
        return entity;
    }

    @Override
    public ContentTag deleteById(Integer id) {
        dao.deleteContentRef(id);
        ContentTag bean = dao.deleteById(id);
        return bean;
    }

    @Override
    public ContentTag[] deleteByIds(Integer[] ids) {
        ContentTag[] beans = new ContentTag[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    private ContentTagDao dao;
}