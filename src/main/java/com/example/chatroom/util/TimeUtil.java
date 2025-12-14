package com.example.chatroom.util;

public final class TimeUtil {

    private TimeUtil() {}

    /** 当前服务器时间戳（毫秒） */
    public static long now() {
        return System.currentTimeMillis();
    }
}