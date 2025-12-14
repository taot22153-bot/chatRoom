package com.example.chatroom.controller;

import com.example.chatroom.model.Message;
import com.example.chatroom.service.ChatService;
import com.example.chatroom.util.EncodingUtil;
import com.example.chatroom.util.JsonUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/api/send")
public class ApiSendServlet extends HttpServlet {

    private final ChatService service = new ChatService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        EncodingUtil.ensureUTF8(req);

        HttpSession session = req.getSession(false);
        String sessionId = session.getId();
        String username = (String) session.getAttribute("username");

        String content = req.getParameter("content");
        String toSessionId = req.getParameter("toSessionId");

        Message msg;

        if (toSessionId != null && !toSessionId.isBlank()) {
            msg = service.sendPrivate(
                    req.getServletContext(),
                    sessionId,
                    username,
                    toSessionId,
                    content
            );
        } else {
            msg = service.sendGroup(
                    req.getServletContext(),
                    sessionId,
                    username,
                    content
            );
        }

        if (msg == null) {
            JsonUtil.fail(resp, 400, "EMPTY_MESSAGE");
            return;
        }

        JsonUtil.ok(resp, msg);
    }
}