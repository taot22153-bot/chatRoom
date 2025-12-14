package com.example.chatroom.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 聊天消息模型
 * 用于群聊 / 私聊 / 系统消息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    /** 消息唯一 ID（用于 since 增量拉取） */
    private long id;

    /** 消息类型：SYSTEM / GROUP / PRIVATE */
    private MessageType type;

    /** 发送方 SessionId（系统消息可为 null） */
    private String fromSessionId;

    /** 发送方用户名（系统消息可为 "系统"） */
    private String fromName;

    /** 私聊目标 SessionId（仅 PRIVATE 使用，其余为 null） */
    private String toSessionId;

    /** 消息内容 */
    private String content;

    /** 时间戳（毫秒） */
    private long time;
}