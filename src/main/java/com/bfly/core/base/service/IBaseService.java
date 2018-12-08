package com.bfly.core.base.service;

import java.util.List;
import java.util.Map;

/**
 * 公共父类业务接口
 * 所有的业务层接口需要继承该接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/7 11:28
 */
public interface IBaseService<T, ID> {

    /**
     * 根据主键ID获得对应的对象
     *
     * @param id 主键
     * @return 对象
     * @author andy_hulibo@163.com
     * @date 2018/12/7 13:15
     */
    T get(ID id);

    /**
     * 根据多条件查询对象
     * map中key是实体类的属性名称 value是属性值
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/7 22:55
     */
    T get(Map<String, Object> property);

    /**
     * 根据主键删除对象
     *
     * @param ids 对象主键集合
     * @return 删除总数
     * @author andy_hulibo@163.com
     * @date 2018/12/7 13:27
     */
    int del(ID... ids);

    /**
     * 以主键为条件修改对象
     *
     * @param t 对象
     * @return true修改成功 false修改失败
     * @author andy_hulibo@163.com
     * @date 2018/12/7 13:27
     */
    boolean edit(T t);

    /**
     * 保存对象
     *
     * @param t 对象
     * @return true保存成功 false保存失败
     * @author andy_hulibo@163.com
     * @date 2018/12/7 13:28
     */
    boolean save(T t);

    /**
     * 获得所有的对象
     *
     * @return 对象集合
     * @author andy_hulibo@163.com
     * @date 2018/12/7 13:28
     */
    List<T> getList();


    /**
     * 获得所有的总数
     *
     * @return 总数
     * @author andy_hulibo@163.com
     * @date 2018/12/7 13:28
     */
    long getCount();

    /**
     * 根据多条件查询对象
     * map中key是实体类的属性名称 value是属性值
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/7 22:55
     */
    long getCount(Map<String, Object> property);
}
