package com.example.chatroom.service;

import com.example.chatroom.model.Message;
import com.example.chatroom.model.MessageType;
import com.example.chatroom.util.TimeUtil;
import jakarta.servlet.ServletContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ChatService {

    // ====== ServletContext 中的 Key（统一定义，避免写错） ======
    private static final String KEY_MESSAGES = "messages";
    private static final String KEY_ONLINE = "onlineUsers";
    private static final String KEY_SEQ = "msgSeq";

    // ================== 用户进入聊天室（系统消息） ==================

    /**
     * 登录成功后调用：用户正式进入聊天室
     */
    @SuppressWarnings("unchecked")
    public void userEnter(ServletContext ctx, String sessionId, String username) {
        try {
            Object msgObj = ctx.getAttribute(KEY_MESSAGES);
            Object seqObj = ctx.getAttribute(KEY_SEQ);
            Object onlineObj = ctx.getAttribute(KEY_ONLINE);

            if (!(msgObj instanceof List) ||
                    !(seqObj instanceof AtomicLong) ||
                    !(onlineObj instanceof Map)) {
                return;
            }

            List<Message> messages = (List<Message>) msgObj;
            AtomicLong seq = (AtomicLong) seqObj;
            Map<String, String> online = (Map<String, String>) onlineObj;

            // 加入在线用户
            online.put(sessionId, username);

            // 生成系统进入消息
            long id = seq.incrementAndGet();
            Message sysMsg = new Message(
                    id,
                    MessageType.SYSTEM,
                    null,
                    "系统",
                    null,
                    username + " 进入聊天室",
                    TimeUtil.now()
            );

            messages.add(sysMsg);

        } catch (Exception e) {
            // 防御式：绝不抛异常
            e.printStackTrace();
        }
    }

    // ================== 群聊消息 ==================

    /**
     * 发送群聊消息
     */
    @SuppressWarnings("unchecked")
    public Message sendGroup(ServletContext ctx,
                             String sessionId,
                             String username,
                             String content) {

        if (content == null || content.isBlank()) return null;

        try {
            Object msgObj = ctx.getAttribute(KEY_MESSAGES);
            Object seqObj = ctx.getAttribute(KEY_SEQ);

            if (!(msgObj instanceof List) || !(seqObj instanceof AtomicLong)) {
                return null;
            }

            List<Message> messages = (List<Message>) msgObj;
            AtomicLong seq = (AtomicLong) seqObj;

            long id = seq.incrementAndGet();
            Message msg = new Message(
                    id,
                    MessageType.GROUP,
                    sessionId,
                    username,
                    null,
                    content.trim(),
                    TimeUtil.now()
            );

            messages.add(msg);
            return msg;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ================== 私聊消息 ==================

    /**
     * 发送私聊消息
     */
    @SuppressWarnings("unchecked")
    public Message sendPrivate(ServletContext ctx,
                               String sessionId,
                               String username,
                               String toSessionId,
                               String content) {

        if (content == null || content.isBlank()) return null;
        if (toSessionId == null || toSessionId.isBlank()) return null;

        try {
            Object msgObj = ctx.getAttribute(KEY_MESSAGES);
            Object seqObj = ctx.getAttribute(KEY_SEQ);

            if (!(msgObj instanceof List) || !(seqObj instanceof AtomicLong)) {
                return null;
            }

            List<Message> messages = (List<Message>) msgObj;
            AtomicLong seq = (AtomicLong) seqObj;

            long id = seq.incrementAndGet();
            Message msg = new Message(
                    id,
                    MessageType.PRIVATE,
                    sessionId,
                    username,
                    toSessionId,
                    content.trim(),
                    TimeUtil.now()
            );

            messages.add(msg);
            return msg;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ================== 拉取“当前 Session 可见消息” ==================

    /**
     * 拉取对当前 Session 可见的消息（解决私聊泄露）
     */
    @SuppressWarnings("unchecked")
    public List<Message> listVisibleMessages(ServletContext ctx,
                                             String currentSessionId,
                                             long since) {

        List<Message> result = new ArrayList<>();

        try {
            Object msgObj = ctx.getAttribute(KEY_MESSAGES);
            if (!(msgObj instanceof List)) {
                return result;
            }

            List<Message> messages = (List<Message>) msgObj;

            for (Message m : messages) {

                // 增量拉取
                if (m.getId() <= since) continue;

                // 系统消息：所有人可见
                if (m.getType() == MessageType.SYSTEM) {
                    result.add(m);
                    continue;
                }

                // 群聊：所有人可见
                if (m.getType() == MessageType.GROUP) {
                    result.add(m);
                    continue;
                }

                // 私聊：只对发送方和接收方可见
                if (m.getType() == MessageType.PRIVATE) {
                    if (currentSessionId.equals(m.getFromSessionId()) ||
                            currentSessionId.equals(m.getToSessionId())) {
                        result.add(m);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // ================== 获取在线用户 ==================

    @SuppressWarnings("unchecked")
    public Map<String, String> getOnlineUsers(ServletContext ctx) {
        Object obj = ctx.getAttribute(KEY_ONLINE);
        if (obj instanceof Map) {
            return (Map<String, String>) obj;
        }
        return Map.of();
    }
}