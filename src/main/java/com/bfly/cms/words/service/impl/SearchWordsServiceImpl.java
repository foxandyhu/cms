package com.bfly.cms.words.service.impl;

import com.bfly.cms.words.entity.SearchWords;
import com.bfly.cms.words.service.ISearchWordsService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/17 14:46
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class SearchWordsServiceImpl extends BaseServiceImpl<SearchWords, Integer> implements ISearchWordsService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(SearchWords searchWords) {
        long count = getCount(new HashMap<String, Object>(1) {
            private static final long serialVersionUID = -9068200709037205652L;

            {
                put("name", searchWords.getName());
            }
        });
        Assert.isTrue(count == 0, searchWords.getName() + "热词已存在!");
        return super.save(searchWords);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(SearchWords searchWords) {
        SearchWords ssw = get(new HashMap<String, Object>(1) {
            private static final long serialVersionUID = -9068200709037205652L;

            {
                put("name", searchWords.getName());
            }
        });
        boolean flag = ssw != null && ssw.getName().equalsIgnoreCase(searchWords.getName()) && ssw.getId() != searchWords.getId();
        Assert.isTrue(!flag, searchWords.getName() + "热词已存在!");
        return super.edit(searchWords);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editRecommend(int searchId, boolean status) {
        SearchWords words = get(searchId);
        Assert.notNull(words, "搜索词不存在!");

        words.setRecommend(status);
        return super.edit(words);
    }
}
