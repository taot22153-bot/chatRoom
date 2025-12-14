package com.example.chatroom.listener;

import com.example.chatroom.model.Message;
import com.example.chatroom.model.MessageType;
import com.example.chatroom.util.TimeUtil;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@WebListener
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // ⚠️ 不在这里生成系统消息
        // 原因：此时用户尚未登录，还没有用户名
    }

    @Override
    @SuppressWarnings("unchecked")
    public void sessionDestroyed(HttpSessionEvent se) {
        try {
            HttpSession session = se.getSession();
            ServletContext ctx = session.getServletContext();

            String sessionId = session.getId();
            String username = (String) session.getAttribute("username");

            Object onlineObj = ctx.getAttribute("onlineUsers");
            Object msgObj = ctx.getAttribute("messages");
            Object seqObj = ctx.getAttribute("msgSeq");

            if (!(onlineObj instanceof Map) ||
                    !(msgObj instanceof List) ||
                    !(seqObj instanceof AtomicLong)) {
                return;
            }

            Map<String, String> onlineUsers = (Map<String, String>) onlineObj;
            List<Message> messages = (List<Message>) msgObj;
            AtomicLong seq = (AtomicLong) seqObj;

            // 从在线用户中移除
            onlineUsers.remove(sessionId);

            if (username == null || username.isBlank()) {
                return;
            }

            // 生成系统下线消息
            long id = seq.incrementAndGet();
            Message sysMsg = new Message(
                    id,
                    MessageType.SYSTEM,
                    null,
                    "系统",
                    null,
                    username + " 离开聊天室",
                    TimeUtil.now()
            );

            messages.add(sysMsg);

        } catch (Exception e) {
            // 防御式：绝不抛异常
            e.printStackTrace();
        }
    }
}