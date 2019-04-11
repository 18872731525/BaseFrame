package com.bgn.baseframe.utils.jsontool;

import com.bgn.baseframe.network.bean.TDataBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 作者：wl on 2017/9/14 14:40
 * 邮箱：wanglun@stosz.com
 */
public class GsonUtil {
    private static Gson mGson = new Gson();

    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        return mGson.fromJson(jsonString, clazz);
    }

//    String json = ...; //对应上面的User info的json
//    Type type = new TypeToken<InfoBean<UserInfo>>(){}.getType();
//    InfoBean<UserInfo> userInfo = new Gson.fromJson(json, type);

    public static <T> TDataBean<T> fromJsonObject(String jsonString, Class<T> clazz) {
        Type type = new ParameterizedTypeImpl(TDataBean.class, new Class[]{clazz});
        return mGson.fromJson(jsonString, type);
    }

    public static <T> List<T> fromJsonList(String jsonString, Class<T> clazz) {
        Type listType = new ParameterizedTypeImpl(List.class, new Class[]{clazz});
        return mGson.fromJson(jsonString, listType);
    }

    public static <T> Map<String, T> fromMapObject(String jsonString, Class<T> clazz) {
        Type type = new TypeToken<Map<String, T>>() {
        }.getType();
        return mGson.fromJson(jsonString, type);
    }

    public static <T> Map<String, List<T>> fromMapList(String jsonString, Class<T> clazz) {
        Type type = new TypeToken<Map<String, List<T>>>() {
        }.getType();
        return mGson.fromJson(jsonString, type);
    }

    public static <T> List<Map<String, List<T>>> fromMapList2(String jsonString, Class<T> clazz) {
        Type type = new TypeToken<List<Map<String, List<T>>>>() {
        }.getType();
        return mGson.fromJson(jsonString, type);
    }

    public static String toJson(Object obj) {
        return mGson.toJson(obj);
    }

}
