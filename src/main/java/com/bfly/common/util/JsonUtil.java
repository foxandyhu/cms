package com.bfly.common.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 * Json 工具类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/23 16:59
 */
public class JsonUtil {

    /**
     * Map转为JsonArray对象 jsonobject attribte:key,value
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 17:00
     */
    public static JSONArray mapToJsonArray(Map<String, String> map) {
        JSONArray array = new JSONArray();
        JSONObject json;
        for (String key : map.keySet()) {
            json = new JSONObject();
            json.put("key", "attr_" + key);
            json.put("value", map.get(key));
            array.put(json);
        }
        return array;
    }
}
