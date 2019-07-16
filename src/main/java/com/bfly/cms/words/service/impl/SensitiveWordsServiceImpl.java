package com.bfly.cms.words.service.impl;

import com.bfly.cms.words.entity.SensitiveWords;
import com.bfly.cms.words.service.ISensitiveWordsService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashMap;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/17 14:46
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class SensitiveWordsServiceImpl extends BaseServiceImpl<SensitiveWords, Integer> implements ISensitiveWordsService {

    private final String REPLACE_CHAR = "*";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(SensitiveWords sensitiveWords) {
        long count = getCount(new HashMap<String, Object>(1) {
            private static final long serialVersionUID = 620804106478527857L;

            {
                put("word", sensitiveWords.getWord());
            }
        });
        Assert.isTrue(count == 0, sensitiveWords.getWord() + " 敏感词已存在!");
        if (!StringUtils.hasLength(sensitiveWords.getReplace())) {
            sensitiveWords.setReplace(String.join("", Collections.nCopies(sensitiveWords.getWord().length(), REPLACE_CHAR)));
        }
        return super.save(sensitiveWords);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(SensitiveWords sensitiveWords) {
        SensitiveWords ssw = get(new HashMap<String, Object>(1) {
            private static final long serialVersionUID = 620804106478527857L;

            {
                put("word", sensitiveWords.getWord());
            }
        });
        boolean flag = ssw != null && ssw.getWord().equalsIgnoreCase(sensitiveWords.getWord()) && ssw.getId() != sensitiveWords.getId();
        Assert.isTrue(!flag, sensitiveWords.getWord() + " 敏感词已存在!");
        if (!StringUtils.hasLength(sensitiveWords.getReplace())) {
            sensitiveWords.setReplace(String.join("", Collections.nCopies(sensitiveWords.getWord().length(), REPLACE_CHAR)));
        }
        return super.edit(sensitiveWords);
    }
}
