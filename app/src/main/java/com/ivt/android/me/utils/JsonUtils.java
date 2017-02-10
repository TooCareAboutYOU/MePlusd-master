package com.ivt.android.me.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * Created by zhangshuai on 2017-02-10.
 */

public class JsonUtils {

    /**
     * 对象转化为json
     */
    public static String objectToJsonForFastJson(Object object) {
        if (object == null) {
            return "";
        }
        try {
            return JSON.toJSONString(object);
        } catch (JSONException e) {
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * json转化为对象
     */
    public static <T> T jsonToObjectForFastJson(String jsonData, Class<T> clazz) {
        if (TextUtils.isEmpty(jsonData)) {
            return null;
        }
        try {
            return parseObject(jsonData, clazz);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     *json转化为List
     */
    public static List jsonToListForFastJson(String jsonData) {
        if (TextUtils.isEmpty(jsonData)) {
            return null;
        }
        List arrayList = null;
        try {
            arrayList =  parseObject(jsonData,new TypeReference<ArrayList>(){});
        } catch (Exception e) {
        }
        return arrayList;

    }


    /**
     *json转化为Map
     */
    public static Map jsonToMapForFastJson(String jsonData){
        if (TextUtils.isEmpty(jsonData)) {
            return null;
        }
        Map map = null;
        try{
            map =  parseObject(jsonData,new TypeReference<Map>(){});
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

}
