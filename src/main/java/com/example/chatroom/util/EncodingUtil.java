package com.example.chatroom.util;

import jakarta.servlet.http.HttpServletRequest;

public final class EncodingUtil {
    private EncodingUtil() {}

    public static void ensureUTF8(HttpServletRequest req) {
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (Exception ignored) {
        }
    }
}