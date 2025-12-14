package com.example.chatroom.listener;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;

@WebListener
public class RequestLogListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        try {
            HttpServletRequest req = (HttpServletRequest) sre.getServletRequest();
            HttpSession session = req.getSession(false);

            String sessionId = (session == null) ? "NO_SESSION" : session.getId();
            String username = (session == null) ? "ANONYMOUS" :
                    String.valueOf(session.getAttribute("username"));

            String uri = req.getRequestURI();

            System.out.println(
                    "[REQ] " + LocalDateTime.now() +
                            " | sid=" + sessionId +
                            " | user=" + username +
                            " | uri=" + uri
            );
        } catch (Exception ignored) {
        }
    }
}