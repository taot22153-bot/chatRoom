let lastId = 0;

// 页面加载完成即启动轮询
window.onload = function () {
    loadMessages();
    loadOnlineUsers();
    setInterval(loadMessages, 2000);
    setInterval(loadOnlineUsers, 3000);
};

// ================== 拉取消息 ==================
function loadMessages() {
    fetch(ctx + "/api/messages?since=" + lastId)
        .then(r => {
            if (r.status === 401) {
                location.href = ctx + "/jsp/login.jsp";
                return;
            }
            return r.json();
        })
        .then(res => {
            if (!res || !res.data) return;
            res.data.forEach(m => {
                appendMessage(m);
                lastId = Math.max(lastId, m.id);
            });
        })
        .catch(console.error);
}

// ================== 发送消息 ==================
function sendMessage() {
    const content = document.getElementById("content").value.trim();
    if (!content) return;

    const toSessionId = document.getElementById("toUser").value;

    const form = new URLSearchParams();
    form.append("content", content);
    if (toSessionId) {
        form.append("toSessionId", toSessionId);
    }

    fetch(ctx + "/api/send", {
        method: "POST",
        body: form
    }).then(() => {
        document.getElementById("content").value = "";
    });
}

// ================== 在线用户 ==================
function loadOnlineUsers() {
    fetch(ctx + "/api/online")
        .then(r => r.json())
        .then(res => {
            if (!res || !res.data) return;

            const ul = document.getElementById("onlineList");
            const sel = document.getElementById("toUser");

            // ===== 关键：记住当前选择 =====
            const currentTarget = sel.value;

            ul.innerHTML = "";
            sel.innerHTML = '<option value="">群聊</option>';

            res.data.forEach(u => {
                // 在线列表
                const li = document.createElement("li");
                li.innerText = u.username;
                ul.appendChild(li);

                // 私聊下拉（不包含自己）
                if (u.sessionId !== mySessionId) {
                    const opt = document.createElement("option");
                    opt.value = u.sessionId;
                    opt.innerText = "私聊：" + u.username;
                    sel.appendChild(opt);
                }
            });

            // ===== 关键：恢复用户选择 =====
            sel.value = currentTarget;
        });
}

// ================== 消息渲染 ==================
function appendMessage(m) {
    const box = document.getElementById("messages");
    const div = document.createElement("div");

    if (m.type === "SYSTEM") {
        div.className = "msg system";
        div.innerText = "[系统] " + m.content;
    } else if (m.type === "GROUP") {
        div.className = "msg group";
        div.innerText = m.fromName + "：" + m.content;
    } else if (m.type === "PRIVATE") {
        div.className = "msg private";
        const tag = (m.fromSessionId === mySessionId) ? "我 → " : "私聊 ← ";
        div.innerText = tag + m.fromName + "：" + m.content;
    }

    box.appendChild(div);
    box.scrollTop = box.scrollHeight;
}