package com.example.chatroom.controller;

import com.example.chatroom.model.OnlineUser;
import com.example.chatroom.service.ChatService;
import com.example.chatroom.util.JsonUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/api/online")
public class ApiOnlineServlet extends HttpServlet {

    private final ChatService service = new ChatService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        Map<String, String> online =
                service.getOnlineUsers(req.getServletContext());

        List<OnlineUser> list = new ArrayList<>();
        online.forEach((sid, name) ->
                list.add(new OnlineUser(sid, name))
        );

        JsonUtil.ok(resp, list);
    }
}