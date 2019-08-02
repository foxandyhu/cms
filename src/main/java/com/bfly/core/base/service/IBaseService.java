package com.bfly.core.base.service;

import com.bfly.common.page.Pager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

/**
 * 公共父类业务接口
 * 所有的业务层接口需要继承该接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/7 11:28
 */
public interface IBaseService<T, ID> extends IBaseJdbcService {

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
     * @param property 多条件精确查询
     * @return 对象
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
    int remove(ID... ids);

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
     * 获得所有的对象
     * map中key是实体类的属性名称 value是属性值
     *
     * @param property 多条件模糊查询
     * @return 对象集合
     * @author andy_hulibo@163.com
     * @date 2018/12/7 13:28
     */
    List<T> getList(Map<String, Object> property);

    /**
     * 获得所有的对象
     * map中key是实体类的属性名称 value是属性值
     *
     * @param exactQueryProperty   多条件精确询 可以为null
     * @param unExactQueryProperty 多条件模糊查询 null值会被忽略
     * @param sortQueryProperty    多条件排序对象 为null时采用默认排序
     * @return 对象集合
     * @author andy_hulibo@163.com
     * @date 2018/12/7 13:28
     */
    List<T> getList(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty, Map<String, Sort.Direction> sortQueryProperty);

    /**
     * 获得所有的对象
     * map中key是实体类的属性名称 value是属性值
     *
     * @param exactQueryProperty   多条件精确询 可以为null
     * @param unExactQueryProperty 多条件模糊查询 null值会被忽略
     * @param sortQueryProperty    多条件排序对象 为null时采用默认排序
     * @param groupProperty        多条件分组对象 null值会被忽略
     * @return 对象集合
     * @author andy_hulibo@163.com
     * @date 2018/12/7 13:28
     */
    List<T> getList(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty, Map<String, Sort.Direction> sortQueryProperty, Map<String, String> groupProperty);

    /**
     * 获得对象分页数据
     * map中key是实体类的属性名称 value是属性值
     *
     * @param property 多条件精确查询 可以为null
     * @return 分页对象
     * @author andy_hulibo@163.com
     * @date 2018/12/8 20:38
     */
    Pager getPage(Map<String, Object> property);

    /**
     * 获得对象分页数据
     * map中key是实体类的属性名称 value是属性值
     *
     * @param exactQueryProperty   多条件精确询 可以为null
     * @param unExactQueryProperty 多条件模糊查询 null值会被忽略
     * @param sortQueryProperty    多条件排序对象 为null时采用默认排序
     * @return 分页对象
     * @author andy_hulibo@163.com
     * @date 2019/6/28 20:50
     */
    Pager getPage(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty, Map<String, Sort.Direction> sortQueryProperty);

    /**
     * 获得对象分页数据
     * map中key是实体类的属性名称 value是属性值
     *
     * @param exactQueryProperty   多条件精确询 可以为null
     * @param unExactQueryProperty 多条件模糊查询 null值会被忽略
     * @param sortQueryProperty    多条件排序对象 为null时采用默认排序
     * @param groupProperty        多条件分组查询 null值会被忽略
     * @return 分页对象
     * @author andy_hulibo@163.com
     * @date 2019/6/28 20:50
     */
    Pager getPage(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty, Map<String, Sort.Direction> sortQueryProperty, Map<String, String> groupProperty);

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
     * @param property 多条件精确查询
     * @return 数量
     * @author andy_hulibo@163.com
     * @date 2018/12/7 22:55
     */
    long getCount(Map<String, Object> property);

    /**
     * 根据多条件查询对象
     * map中key是实体类的属性名称 value是属性值
     *
     * @param exactQueryProperty   多条件精确查询
     * @param unExactQueryProperty 多条件模糊查询
     * @author andy_hulibo@163.com
     * @date 2019/6/28 20:52
     */
    long getCount(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty);

    /**
     * 根据多条件查询对象
     * map中key是实体类的属性名称 value是属性值
     *
     * @param exactQueryProperty   多条件精确查询
     * @param unExactQueryProperty 多条件模糊查询
     * @param groupProperty        多条件分组查询 null值会被忽略
     * @author andy_hulibo@163.com
     * @date 2019/6/28 20:52
     */
    long getCount(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty, Map<String, String> groupProperty);

    /**
     * 默认的分页器
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 13:59
     */
    default PageRequest getPageRequest(Pager pager) {
        PageRequest pageRequest = PageRequest.of(pager.getPageNo() == 0 ? 0 : pager.getPageNo() - 1, pager.getPageSize());
        return pageRequest;
    }
}
