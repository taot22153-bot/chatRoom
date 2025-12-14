package com.example.chatroom.util;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public final class JsonUtil {

    private static final Gson gson = new Gson();

    private JsonUtil() {}

    public static void write(HttpServletResponse resp, Object obj) {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.write(gson.toJson(obj));
        } catch (IOException e) {
            // 防御式：不向外抛，避免 Tomcat 500
            e.printStackTrace();
        }
    }

    public static void ok(HttpServletResponse resp, Object data) {
        resp.setStatus(200);
        write(resp, Resp.ok(data));
    }

    public static void fail(HttpServletResponse resp, int status, String msg) {
        resp.setStatus(status);
        write(resp, Resp.fail(status, msg));
    }
}