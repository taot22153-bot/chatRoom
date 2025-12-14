package com.example.chatroom.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应结构：
 * 成功：{ok:true, data:..., error:null, code:200}
 * 失败：{ok:false, data:null, error:"...", code:401/400/500}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resp<T> {
    private boolean ok;
    private T data;
    private String error;
    private int code;

    public static <T> Resp<T> ok(T data) {
        return new Resp<>(true, data, null, 200);
    }

    public static <T> Resp<T> fail(int code, String error) {
        return new Resp<>(false, null, error, code);
    }
}