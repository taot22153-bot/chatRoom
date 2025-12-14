<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>聊天室</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="container">

    <!-- ================= 左侧：在线用户 ================= -->
    <div class="sidebar">

        <h3>在线用户</h3>

        <!-- 退出聊天室 -->
        <a href="${pageContext.request.contextPath}/logout"
           class="logout-link">
            退出聊天室
        </a>

        <ul id="onlineList"></ul>
    </div>

    <!-- ================= 右侧：聊天区域 ================= -->
    <div class="chat">

        <!-- 消息显示区 -->
        <div id="messages" class="messages"></div>

        <!-- 输入区 -->
        <div class="input-area">
            <select id="toUser">
                <option value="">群聊</option>
            </select>

            <input type="text"
                   id="content"
                   placeholder="请输入消息"
                   autocomplete="off"/>

            <button onclick="sendMessage()">发送</button>
        </div>

    </div>

</div>

<!-- ================= JSP 向 JS 注入必要变量 ================= -->
<script>
    const ctx = "${pageContext.request.contextPath}";
    const mySessionId = "${pageContext.session.id}";
    const myUsername = "${sessionScope.username}";
</script>

<script src="${pageContext.request.contextPath}/js/chat.js"></script>

</body>
</html>