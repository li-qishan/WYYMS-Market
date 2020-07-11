package com.buf.common.utils;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created by mawenguang on 2019/5/14.
 */
public class JSONFormMapUtils
{
    /**
     * json string 转换为 map 对象
     * @param jsonObj
     * @return
     */
    public static Map<String, Object> jsonToMap(Object jsonObj) {
        JSONObject jsonObject = JSONObject.fromObject(jsonObj);
        Map<String, Object> map = (Map)jsonObject;
        return map;
    }

    /**json string 转换为 对象
     * @param jsonObj
     * @param type
     * @return
     */
    public  static <T>  T jsonToBean(Object jsonObj, Class<T> type) {
        JSONObject jsonObject = JSONObject.fromObject(jsonObj);
        T obj =(T) JSONObject.toBean(jsonObject, type);
        return obj;
    }
}
