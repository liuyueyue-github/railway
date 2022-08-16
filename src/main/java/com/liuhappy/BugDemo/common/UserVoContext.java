package com.liuhappy.BugDemo.common;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yueyue.liu
 */
@Slf4j
public class UserVoContext {

    /**
     * 创建线程局部变量，并初始化值
     */
    private static ThreadLocal<String> userIdThreadLocal = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return null;
        }
    };

    /**
     * 提供线程局部变量set方法
     * @param userVoString String
     */
    public static void setUserVoString(String userVoString) {
        userIdThreadLocal.set(userVoString);
    }

    /**
     * 提供线程局部变量get方法
     * @return -
     */
    public static String getUserVoString() {
        try {
            return userIdThreadLocal.get();
        } catch (Exception e) {
            return "";
        }

    }
}