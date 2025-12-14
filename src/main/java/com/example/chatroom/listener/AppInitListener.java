package com.example.chatroom.listener;

import com.example.chatroom.model.Message;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@WebListener
public class AppInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ServletContext ctx = sce.getServletContext();

            // 全局消息列表
            ctx.setAttribute("messages", new CopyOnWriteArrayList<Message>());

            // 在线用户：sessionId -> username
            ctx.setAttribute("onlineUsers", new ConcurrentHashMap<String, String>());

            // 消息 ID 自增器（用于 since 增量拉取）
            ctx.setAttribute("msgSeq", new AtomicLong(0));

            ctx.log("[chatroom] AppInitListener 初始化完成");
        } catch (Exception e) {
            // 防御式：不抛异常，避免 Tomcat 启动失败
            e.printStackTrace();
        }
    }
}