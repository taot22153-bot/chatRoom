<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户注册</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="login-container">
    <h2>用户注册</h2>

    <form method="post"
          action="${pageContext.request.contextPath}/register">

        <input type="text"
               name="username"
               placeholder="请输入用户名"
               required />

        <button type="submit">注册</button>
    </form>

    <p style="margin-top: 12px;">
        已有账号？
        <a href="${pageContext.request.contextPath}/jsp/login.jsp">
            去登录
        </a>
    </p>
</div>

</body>
</html>