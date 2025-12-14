package com.example.chatroom.model;

/**
 * 聊天消息类型
 */
public enum MessageType {

    /** 系统消息：进入/离开/超时 */
    SYSTEM,

    /** 群聊消息：对所有在线用户可见 */
    GROUP,

    /** 私聊消息：仅发送方与接收方可见 */
    PRIVATE
}