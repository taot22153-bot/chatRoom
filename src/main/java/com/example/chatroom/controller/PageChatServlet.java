package com.example.chatroom.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/chat")
public class PageChatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.sendRedirect(req.getContextPath() + "/jsp/chat.jsp");
    }
}