package com.example.chatroom.controller;

import com.example.chatroom.service.ChatService;
import com.example.chatroom.util.EncodingUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final ChatService service = new ChatService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        EncodingUtil.ensureUTF8(req);

        String username = req.getParameter("username");
        if (username == null || username.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/jsp/login.jsp");
            return;
        }

        username = username.trim();

        HttpSession session = req.getSession(true);
        session.setAttribute("username", username);

        // 用户正式进入聊天室（生成系统消息）
        service.userEnter(
                req.getServletContext(),
                session.getId(),
                username
        );

        resp.sendRedirect(req.getContextPath() + "/chat");
    }
}