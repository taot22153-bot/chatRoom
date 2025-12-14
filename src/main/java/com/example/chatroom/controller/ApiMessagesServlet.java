package com.example.chatroom.controller;

import com.example.chatroom.service.ChatService;
import com.example.chatroom.util.JsonUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/api/messages")
public class ApiMessagesServlet extends HttpServlet {

    private final ChatService service = new ChatService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession(false);
        String sessionId = session.getId();

        long since = 0;
        try {
            String s = req.getParameter("since");
            if (s != null) since = Long.parseLong(s);
        } catch (Exception ignored) {}

        JsonUtil.ok(
                resp,
                service.listVisibleMessages(
                        req.getServletContext(),
                        sessionId,
                        since
                )
        );
    }
}