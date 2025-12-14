package com.example.chatroom.filter;

import com.example.chatroom.util.JsonUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;

@WebFilter("/*")
public class AuthFilter implements Filter {

    // ====== 白名单（路径必须与 Controller/JSP 一致） ======
    private static final Set<String> WHITE_LIST = Set.of(
            "/jsp/login.jsp",
            "/jsp/register.jsp",
            "/login",
            "/register"
    );

    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String ctx = request.getContextPath();
        String uri = request.getRequestURI();
        String path = uri.substring(ctx.length());

        // ====== 静态资源直接放行 ======
        if (path.startsWith("/css/") || path.startsWith("/js/")) {
            chain.doFilter(req, res);
            return;
        }

        // ====== 白名单放行 ======
        if (WHITE_LIST.contains(path)) {
            chain.doFilter(req, res);
            return;
        }

        // ====== 登录态校验 ======
        HttpSession session = request.getSession(false);
        boolean loggedIn =
                (session != null && session.getAttribute("username") != null);

        if (loggedIn) {
            chain.doFilter(req, res);
            return;
        }

        // ====== 未登录：分流处理 ======
        boolean isApi = path.startsWith("/api/");

        if (isApi) {
            // API：返回 401 JSON（绝不 redirect）
            JsonUtil.fail(response, 401, "NOT_LOGIN");
        } else {
            // 页面：redirect 到登录页
            response.sendRedirect(ctx + "/jsp/login.jsp");
        }
    }
}