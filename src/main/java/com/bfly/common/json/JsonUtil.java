package com.bfly.common.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.*;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * Json工具类
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/6 16:05
 */
public class JsonUtil {

    private static SerializeConfig config = SerializeConfig.getGlobalInstance();

    static {
        config.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
        config.put(Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
    }

    private static PropertyFilter filterProperty(final String... properties) {
        return (source, name, value) -> {
            for (String pro : properties) {
                if (name.equals(pro)) {
                    return false;
                }
            }
            return true;
        };
    }

    /**
     * 返回Json对象
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/6 16:13
     */
    public static JSONObject toJsonFilter(Object object, String... properties) {
        String jsonStr = filter(object, properties);
        return JSON.parseObject(jsonStr);
    }


    /**
     * 返回Json对象
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/6 16:13
     */
    public static JSONArray toJsonFilterForArray(Collection c, String... properties) {
        String jsonStr = filter(c, properties);
        return JSON.parseArray(jsonStr);
    }


    /**
     * 过滤掉指定的属性
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/6 16:13
     */
    private static String filter(Object object, final String... properties) {
        String jsonStr;
        try {
            PropertyFilter filter = filterProperty(properties);
            SerializeWriter writer = new SerializeWriter(SerializerFeature.DisableCircularReferenceDetect);
            JSONSerializer serializer = new JSONSerializer(writer, config);
            serializer.getPropertyFilters().add(filter);
            serializer.write(object);
            jsonStr = writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("json过滤属性出错！");
        }
        return jsonStr;
    }

    /**
     * Json转换为java bean
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/6 16:14
     */
    public static <T> T parse(String jsonStr, Class<T> cls) {
        T obj = null;
        try {
            obj = JSON.parseObject(jsonStr, cls);
        } catch (Exception e) {
        }
        return obj;
    }

    /**
     * Json转换为List java bean
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/6 16:14
     */
    public static <E> List<E> parseStrToList(String jsonStr, Class<E> cls) {
        List<E> list = null;
        try {
            list = JSON.parseArray(jsonStr, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
