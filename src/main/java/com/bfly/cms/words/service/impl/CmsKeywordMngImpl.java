package com.bfly.cms.words.service.impl;

import com.bfly.cms.words.dao.CmsKeywordDao;
import com.bfly.cms.words.entity.CmsKeyword;
import com.bfly.cms.words.service.CmsKeywordMng;
import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.ParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/26 15:19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsKeywordMngImpl implements CmsKeywordMng {

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<CmsKeyword> getListBySiteId(Integer siteId,
                                            boolean onlyEnabled, boolean cacheable) {
        List<CmsKeyword> list = dao.getListGlobal(onlyEnabled, cacheable);
        return list;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public String attachKeyword(Integer siteId, String txt) {
        if (StringUtils.isBlank(txt)) {
            return txt;
        }
        List<CmsKeyword> list = getListBySiteId(siteId, true, true);
        int len = list.size();
        if (len <= 0) {
            return txt;
        }
        String[] searchArr = new String[len];
        String[] replacementArr = new String[len];
        int i = 0;
        for (CmsKeyword k : list) {
            searchArr[i] = k.getName();
            replacementArr[i] = k.getUrl();
            i++;
        }
        try {
            Lexer lexer = new Lexer(txt);
            Node node;
            StringBuilder sb = new StringBuilder((int) (txt.length() * 1.2));
            while ((node = lexer.nextNode()) != null) {
                if (node instanceof TextNode) {
                    sb.append(StringUtils.replaceEach(node.toHtml(), searchArr,
                            replacementArr));
                } else {
                    sb.append(node.toHtml());
                }
            }
            return sb.toString();
        } catch (ParserException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CmsKeyword findById(Integer id) {
        CmsKeyword entity = dao.findById(id);
        return entity;
    }

    @Override
    public CmsKeyword save(CmsKeyword bean) {
        bean.init();
        dao.save(bean);
        return bean;
    }

    @Override
    public void updateKeywords(Integer[] ids, String[] names, String[] urls,
                               Boolean[] disableds) {
        CmsKeyword keyword;
        for (int i = 0, len = ids.length; i < len; i++) {
            keyword = findById(ids[i]);
            keyword.setName(names[i]);
            keyword.setUrl(urls[i]);
            keyword.setDisabled(disableds[i]);
        }
    }

    @Override
    public CmsKeyword deleteById(Integer id) {
        CmsKeyword bean = dao.deleteById(id);
        return bean;
    }

    @Override
    public CmsKeyword[] deleteByIds(Integer[] ids) {
        CmsKeyword[] beans = new CmsKeyword[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    private CmsKeywordDao dao;
}