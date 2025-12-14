<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户登录</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="login-container">

    <h2>聊天室登录</h2>

    <!-- 登录表单 -->
    <form method="post"
          action="${pageContext.request.contextPath}/login">

        <input type="text"
               name="username"
               placeholder="请输入用户名"
               required />

        <button type="submit">进入聊天室</button>
    </form>

    <!-- 注册入口（关键！） -->
    <p style="margin-top: 12px; font-size: 14px;">
        还没有账号？
        <a href="${pageContext.request.contextPath}/jsp/register.jsp">
            去注册
        </a>
    </p>

</div>

</body>
</html>