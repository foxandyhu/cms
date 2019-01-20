package com.bfly.cms.words.service.impl;

import com.bfly.cms.words.entity.Keywords;
import com.bfly.cms.words.service.IKeyWordsService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/17 14:46
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class KeyWordsServiceImpl extends BaseServiceImpl<Keywords, Integer> implements IKeyWordsService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Keywords keywords) {
        long count = getCount(new HashMap<String, Object>(1) {
            private static final long serialVersionUID = 8339547721914214248L;

            {
                put("name", keywords.getName());
            }
        });
        Assert.isTrue(count == 0, keywords.getName() + " 关键字已存在!");
        return super.save(keywords);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(Keywords keywords) {
        Keywords kw = get(new HashMap<String, Object>(1) {
            private static final long serialVersionUID = 8339547721914214248L;

            {
                put("name", keywords.getName());
            }
        });
        boolean flag = kw != null && kw.getName().equalsIgnoreCase(keywords.getName()) && kw.getId() != keywords.getId();
        Assert.isTrue(!flag, keywords.getName() + " 关键字已存在!");
        return super.edit(keywords);
    }
}
