package com.sardine.common.context;

import java.util.HashMap;
import java.util.Map;

public class UserContext {
    /**
     * tokenKey
     */
    public static final String CONTEXT_KEY_USER_TOKEN = "token";

    private static ThreadLocal<Map<String, Object>> threadLocal;

    static {
        threadLocal = new ThreadLocal<>();
    }

    /**
     * 设置数据
     *
     * @param key   键
     * @param value 值
     */
    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>(6);
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    /**
     * 获取数据
     *
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>(6);
            threadLocal.set(map);
        }
        return map.get(key);
    }

    /**
     * 清除数据
     */
    public static void remove() {
        threadLocal.remove();
    }

    public static String getToken() {
        Object value = get(CONTEXT_KEY_USER_TOKEN);
        return String.valueOf(value);
    }

    public static void setToken(String token) {
        set(CONTEXT_KEY_USER_TOKEN, token);
    }

}
