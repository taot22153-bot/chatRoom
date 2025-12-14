package com.example.chatroom.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 在线用户视图模型（供前端展示）
 */
@Data
@AllArgsConstructor
public class OnlineUser {

    /** SessionId */
    private String sessionId;

    /** 用户名 */
    private String username;
}