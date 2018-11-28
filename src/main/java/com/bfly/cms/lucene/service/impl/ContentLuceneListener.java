package com.bfly.cms.lucene.service.impl;

import com.bfly.cms.content.entity.Content;
import com.bfly.cms.lucene.service.LuceneContentSvc;
import com.bfly.cms.content.service.impl.SimpleContentListener;
import org.apache.lucene.queryParser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 内容索引监听器
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 13:53
 */
@Component
public class ContentLuceneListener extends SimpleContentListener {
    private static final Logger log = LoggerFactory
            .getLogger(ContentLuceneListener.class);
    /**
     * 是否已审核
     */
    private static final String IS_CHECKED = "isChecked";

    @Override
    public void afterSave(Content content) {
        if (content.isChecked()) {
            try {
                luceneContentSvc.createIndex(content);
            } catch (IOException e) {
                log.error("", e);
            }
        }
    }

    @Override
    public Map<String, Object> preChange(Content content) {
        Map<String, Object> map = new HashMap<>();
        map.put(IS_CHECKED, content.isChecked());
        return map;
    }

    @Override
    public void afterChange(Content content, Map<String, Object> map) {
        boolean pre = (Boolean) map.get(IS_CHECKED);
        boolean curr = content.isChecked();
        try {
            if (pre && !curr) {
                luceneContentSvc.deleteIndex(content.getId());
            } else if (!pre && curr) {
                luceneContentSvc.createIndex(content);
            } else if (pre && curr) {
                luceneContentSvc.updateIndex(content);
            }
        } catch (IOException e) {
            log.error("", e);
        } catch (ParseException e) {
            log.error("", e);
        }
    }

    @Override
    public void afterDelete(Content content) {
        try {
            luceneContentSvc.deleteIndex(content.getId());
        } catch (IOException e) {
            log.error("", e);
        } catch (ParseException e) {
            log.error("", e);
        }
    }

    private LuceneContentSvc luceneContentSvc;

    @Autowired
    public void setLuceneContentSvc(LuceneContentSvc luceneContentSvc) {
        this.luceneContentSvc = luceneContentSvc;
    }
}
