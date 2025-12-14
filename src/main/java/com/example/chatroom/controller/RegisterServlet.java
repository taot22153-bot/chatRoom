package com.example.chatroom.controller;

import com.example.chatroom.util.EncodingUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        // 统一编码（防中文乱码）
        EncodingUtil.ensureUTF8(req);

        String username = req.getParameter("username");

        // 基本校验
        if (username == null || username.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/jsp/register.jsp");
            return;
        }

        // 本项目不使用数据库，注册仅用于流程完整性
        resp.sendRedirect(req.getContextPath() + "/jsp/login.jsp");
    }
}