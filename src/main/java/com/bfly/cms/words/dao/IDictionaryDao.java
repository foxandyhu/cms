package com.bfly.cms.words.dao;

import com.bfly.cms.words.entity.Dictionary;
import com.bfly.core.base.dao.IBaseDao;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/14 11:12
 */
public interface IDictionaryDao extends IBaseDao<Dictionary, Integer> {

    /**
     * 获得数据字典的所有类型
     *
     * @return 类型集合
     * @author andy_hulibo@163.com
     * @date 2018/12/14 11:14
     */
    @Query("select d.type from Dictionary as d group by d.type")
    List<String> getTypes();
}
