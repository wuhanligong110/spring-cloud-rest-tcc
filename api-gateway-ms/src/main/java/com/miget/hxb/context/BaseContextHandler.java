package com.miget.hxb.context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ace on 2017/9/8.
 */
public class BaseContextHandler {
    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key){
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        return map.get(key);
    }

    public static String getUserID(){
        Object value = get("userId");
        return returnObjectValue(value);
    }

    public static String getUsername(){
        Object value = get("userName");
        return returnObjectValue(value);
    }

    public static String getName(){
        Object value = get("name");
        return returnObjectValue(value);
    }

    public static String getToken(){
        Object value = get("token");
        return returnObjectValue(value);
    }
    public static void setToken(String token){set("token",token);}

    public static void setName(String name){set("name",name);}

    public static void setUserID(String userID){
        set("userId",userID);
    }

    public static void setUsername(String username){
        set("userName",username);
    }

    private static String returnObjectValue(Object value) {
        return value==null?null:value.toString();
    }

    public static void remove(){
        threadLocal.remove();
    }

}
