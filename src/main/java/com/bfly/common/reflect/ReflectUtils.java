package com.bfly.common.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.bfly.core.security.ActionModel;

/**
 * 反射工具类
 *
 * @author 胡礼波
 * 2012-4-25 下午05:34:43
 */
public class ReflectUtils {
    private ReflectUtils() {
    }

    /**
     * 获得执行的方法
     *
     * @param c          类
     * @param methodName 方法名
     * @return 方法对象
     * @author 胡礼波
     * 2012-4-25 下午05:34:54
     */
    public static Method getMethod(Class<?> c, String methodName) {
        Method method = null;
        try {
            method = c.getMethod(methodName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return method;
    }

    /**
     * 获得权限枚举
     *
     * @param mth
     * @return
     * @author 胡礼波
     * 2012-4-25 下午05:36:38
     */
    public static <T extends Annotation> T getActionAnnotationValue(Method mth, Class<T> cls) {
        return mth.isAnnotationPresent(cls) == false ? null : mth.getAnnotation(cls);
    }

    /**
     * 对象复制
     *
     * @param dest
     * @param src
     * @author 胡礼波
     * 2012-4-25 下午05:36:54
     */
    public static void copyValueInto(Object dest, Object src) {
        try {
            if (dest == null || src == null) {
                return;
            }
            org.apache.commons.beanutils.BeanUtils.copyProperties(dest, src);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 返回类或者方法的描述信息
     *
     * @param obj 可以是class也可以是method
     * @return
     * @author 胡礼波
     * 2012-11-1 上午11:18:37
     */
    public static String getModelDescription(Object obj) {
        ActionModel model = getModel(obj);
        return model == null ? "" : model.value();
    }

    /**
     * 返回类或方法是否需要记录日志
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/27 17:45
     */
    public static boolean getModelNeedLog(Object obj) {
        ActionModel model = getModel(obj);
        return model == null ? true : model.need();
    }

    /**
     * 返回ActionModel注解对象
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/27 17:46
     */
    private static ActionModel getModel(Object obj) {
        ActionModel model = null;
        if (obj instanceof Class) {
            model = getClassActionModel((Class<?>) obj);
        } else if (obj instanceof Method) {
            Method method = (Method) obj;
            model = getMethodActionModel(method);
        }
        if (model != null) {
            return model;
        }
        return null;
    }

    /**
     * 获取方法或类的描述注解
     *
     * @param c
     * @return
     * @author 胡礼波
     * 2012-11-1 上午11:18:53
     */
    private static ActionModel getClassActionModel(Class<?> c) {
        ActionModel model = c.isAnnotationPresent(ActionModel.class) == false ? null : c.getAnnotation(ActionModel.class);
        return model;
    }

    /**
     * 获取方法或类的描述注解
     *
     * @return
     * @author 胡礼波
     * 2012-11-1 上午11:18:53
     */
    private static ActionModel getMethodActionModel(Method method) {
        ActionModel model = method.isAnnotationPresent(ActionModel.class) == false ? null : method.getAnnotation(ActionModel.class);
        return model;
    }
}
