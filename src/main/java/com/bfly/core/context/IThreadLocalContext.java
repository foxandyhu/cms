package com.bfly.core.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ThreadLocal上下文抽象接口,提供公共的获取ThreadLocal中数据方法
 *
 * @author andy_hulibo@163.com
 * @date 2019/6/26 18:47
 */
public interface IThreadLocalContext {

    Logger logger = LoggerFactory.getLogger(IThreadLocalContext.class);

    /**
     * 默认方法从threadlocal获取数据,并从threadlocal删除且返回数据
     *
     * @param
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/6/26 19:02
     */
    default <T> T getData(ThreadLocal<T> threadLocal) {
        T data = null;
        try {
            data = threadLocal.get();
        } catch (Exception ex) {
            logger.error("从threadlocal获取数据出错", ex);
        } finally {
            threadLocal.remove();
        }
        return data;
    }
}
