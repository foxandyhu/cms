package com.bfly.core.base.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HQL语句分页查询
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/23 15:24
 */
public class Finder {

    protected Finder() {
        hqlBuilder = new StringBuilder();
    }

    protected Finder(String hql) {
        hqlBuilder = new StringBuilder(hql);
    }

    public static Finder create() {
        return new Finder();
    }

    public static Finder create(String hql) {
        return new Finder(hql);
    }

    public Finder append(String hql) {
        hqlBuilder.append(hql);
        return this;
    }

    /**
     * 获得原始hql语句
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 15:24
     */
    public String getOrigHql() {
        return hqlBuilder.toString();
    }

    /**
     * 获得查询数据库记录数的hql语句
     *
     * @return HQL语句
     * @author andy_hulibo@163.com
     * @date 2018/11/23 15:25
     */
    public String getRowCountHql() {
        return getRowCountBaseHql(ORDER_BY);
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    /**
     * 是否使用查询缓存
     *
     * @return true 使用查询缓存 false 没有使用
     * @author andy_hulibo@163.com
     * @date 2018/11/23 15:27
     */
    public boolean isCacheable() {
        return cacheable;
    }

    /**
     * 设置是否使用查询缓存
     *
     * @param cacheable 是否缓存
     * @author andy_hulibo@163.com
     * @date 2018/11/23 15:28
     * @see Query#setCacheable(boolean)
     */
    public void setCacheable(boolean cacheable) {
        this.cacheable = cacheable;
    }

    /**
     * 设置参数
     *
     * @param param 参数名
     * @param value 参数值
     * @return Finder对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 15:29
     * @see Query#setParameter(String, Object)
     */
    public Finder setParam(String param, Object value) {
        return setParam(param, value, null);
    }

    /**
     * 设置参数。与hibernate的Query接口一致。
     *
     * @param param 参数名
     * @param value 参数值
     * @param type  参数类型
     * @return Finder对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 15:30
     * @see Query#setParameter(String, Object, Type)
     */
    public Finder setParam(String param, Object value, Type type) {
        getParams().add(param);
        getValues().add(value);
        getTypes().add(type);
        return this;
    }

    /**
     * 设置参数。与hibernate的Query接口一致。
     *
     * @param paramMap 参数
     * @return Finder对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 15:35
     * @see Query#setProperties(Map)
     */
    public Finder setParams(Map<String, Object> paramMap) {
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            setParam(entry.getKey(), entry.getValue());
        }
        return this;
    }

    /**
     * 设置参数。与hibernate的Query接口一致。
     *
     * @param name 参数名
     * @param val  参数值
     * @param type 参数类型
     * @return Finer对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 15:37
     * @see Query#setParameterList(String, Object[], Type)
     */
    public Finder setParamList(String name, Object[] val, Type type) {
        getParamsArray().add(name);
        getValuesArray().add(val);
        getTypesArray().add(type);
        return this;
    }

    /**
     * 设置参数。与hibernate的Query接口一致。
     *
     * @param name 参数名
     * @param val  参数值
     * @return Finer对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 15:38
     * @see Query#setParameterList(String, Object[])
     */
    public Finder setParamList(String name, Object[] val) {
        return setParamList(name, val, null);
    }

    /**
     * 将finder中的参数设置到query中。
     *
     * @param query Query对象
     * @return Query对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 15:39
     */
    public Query setParamsToQuery(Query query) {
        if (params != null) {
            for (int i = 0; i < params.size(); i++) {
                if (types.get(i) == null) {
                    query.setParameter(params.get(i), values.get(i));
                } else {
                    query.setParameter(params.get(i), values.get(i), types.get(i));
                }
            }
        }
        if (paramsArray != null) {
            for (int i = 0; i < paramsArray.size(); i++) {
                if (typesArray.get(i) == null) {
                    query.setParameterList(paramsArray.get(i), valuesArray
                            .get(i));
                } else {
                    query.setParameterList(paramsArray.get(i), valuesArray
                            .get(i), typesArray.get(i));
                }
            }
        }
        return query;
    }

    public Query createQuery(Session s) {
        Query query = setParamsToQuery(s.createQuery(getOrigHql()));
        if (getFirstResult() > 0) {
            query.setFirstResult(getFirstResult());
        }
        if (getMaxResults() > 0) {
            query.setMaxResults(getMaxResults());
        }
        if (isCacheable()) {
            query.setCacheable(true);
        }
        return query;
    }

    private String getRowCountBaseHql(String indexKey) {
        String hql = hqlBuilder.toString();

        int fromIndex = hql.toLowerCase().indexOf(FROM);
        String projectionHql = hql.substring(0, fromIndex);

        hql = hql.substring(fromIndex);
        String rowCountHql = hql.replace(HQL_FETCH, "");

        int index = rowCountHql.indexOf(indexKey);
        if (index > 0) {
            rowCountHql = rowCountHql.substring(0, index);
        }
        return wrapProjection(projectionHql) + rowCountHql;
    }

    private String wrapProjection(String projection) {
        String str = "select";
        if (!projection.contains(str)) {
            return ROW_COUNT;
        } else {
            return projection.replace("select", "select count(") + ") ";
        }
    }

    private List<String> getParams() {
        if (params == null) {
            params = new ArrayList<>();
        }
        return params;
    }

    private List<Object> getValues() {
        if (values == null) {
            values = new ArrayList<>();
        }
        return values;
    }

    private List<Type> getTypes() {
        if (types == null) {
            types = new ArrayList<>();
        }
        return types;
    }

    private List<String> getParamsArray() {
        if (paramsArray == null) {
            paramsArray = new ArrayList<>();
        }
        return paramsArray;
    }

    private List<Object[]> getValuesArray() {
        if (valuesArray == null) {
            valuesArray = new ArrayList<>();
        }
        return valuesArray;
    }

    private List<Type> getTypesArray() {
        if (typesArray == null) {
            typesArray = new ArrayList<>();
        }
        return typesArray;
    }

    private StringBuilder hqlBuilder;

    private List<String> params;
    private List<Object> values;
    private List<Type> types;

    private List<String> paramsArray;
    private List<Object[]> valuesArray;
    private List<Type> typesArray;

    private int firstResult = 0;

    private int maxResults = 0;

    private boolean cacheable = false;

    public static final String ROW_COUNT = "select count(*) ";
    public static final String FROM = "from";
    public static final String HQL_FETCH = "fetch";
    public static final String ORDER_BY = "order ";
}