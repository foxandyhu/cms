package com.bfly.cms.dictionary.service.impl;

import com.bfly.cms.dictionary.dao.IDictionaryDao;
import com.bfly.cms.dictionary.entity.Dictionary;
import com.bfly.cms.dictionary.service.IDictionaryService;
import com.bfly.common.page.Pager;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/14 11:11
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class DictionaryServiceImpl extends BaseServiceImpl<Dictionary, Integer> implements IDictionaryService {

    @Autowired
    private IDictionaryDao dictionaryDao;

    @Override
    public Pager getPage(Map<String, Object> property) {
        Pager pager = super.getPage(property);
        if (pager.getData() != null && pager.getData() instanceof List) {
            List<Dictionary> list = (List<Dictionary>) pager.getData();
            //按照类型分类排序
            Collections.sort(list, Comparator.comparing(Dictionary::getType));
        }
        return pager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Dictionary dictionary) {
        boolean exist = checkExist(dictionary);
        Assert.isTrue(!exist, "该数据字典数据已重复!");
        return super.save(dictionary);
    }

    @Override
    public boolean edit(Dictionary dictionary) {
        boolean exist = checkExist(dictionary);
        Assert.isTrue(!exist, "该数据字典数据已重复!");
        return super.edit(dictionary);
    }

    /**
     * 检查是否存在相同的数据字典
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 11:34
     */
    private boolean checkExist(Dictionary dictionary) {
        Dictionary dic = get(new HashMap<String, Object>() {
            private static final long serialVersionUID = 6340025449740664103L;

            {
                put("name", dictionary.getName().trim());
                put("value", dictionary.getValue());
                put("type", dictionary.getType().trim());
            }
        });
        return dic != null;
    }

    @Override
    public List<String> getTypes() {
        return dictionaryDao.getTypes();
    }
}
