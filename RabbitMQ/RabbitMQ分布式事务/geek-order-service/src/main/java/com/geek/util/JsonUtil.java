package com.geek.util;

/**
 * @ClassName JSONUtil
 * @Description TODO
 * @Author Lambert
 * @Date 2021/6/7 20:05
 * @Version 1.0
 **/

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 当前类用于将某个特定的对象转换为JSON对象，或者将JSON对象转换为某个特定的对象
 */
@Component
public class JsonUtil {

    private static ObjectMapper mapper;

    @Autowired
    public void setMapper(ObjectMapper mapper) {
        JsonUtil.mapper = mapper;
    }

    public static <T> String objectToJson(T o) {
        try {
            return JsonUtil.mapper.writeValueAsString(o);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T jsonToObject(String s,  TypeReference<T> tr) {
        if (s == null) {
            return null;
        }
        try {
            T o = JsonUtil.mapper.readValue(s, tr);
            return o;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}