# 💬 Java Web 在线聊天室系统（ChatRoom）

## 一、项目简介

本项目是一个基于 **Java Web（Servlet + JSP）** 的在线聊天室系统，支持  
**用户登录、注册、群聊、私聊、在线用户管理、权限过滤** 等功能。

系统采用 **MVC 分层架构**，通过 `ServletContext` / `HttpSession` / `Listener` / `Filter`  
等机制，实现了一个 **功能完整、逻辑清晰、可直接运行与答辩** 的 Java Web 实战项目。

---

## 二、技术栈

| 技术 | 说明 |
|----|----|
| Java | JDK 17 |
| Web 容器 | Tomcat 10.x |
| 后端 | Servlet + JSP |
| 前端 | HTML / CSS / JavaScript |
| 会话管理 | HttpSession |
| 全局数据 | ServletContext |
| 监听器 | Listener |
| 过滤器 | Filter |
| 构建工具 | Maven |
| 版本控制 | Git / GitHub |

---

## 三、系统功能概述

### 1️⃣ 用户功能
- 用户注册  
- 用户登录  
- 用户退出  
- 登录状态校验（Filter）

### 2️⃣ 聊天功能
- 群聊消息发送与展示  
- 私聊消息发送（点对点）  
- 私聊消息可见性控制（仅发送方与接收方可见）  
- 历史消息拉取（支持增量）  
- 用户进入 / 离开聊天室的系统提示  

### 3️⃣ 系统功能
- 全局消息列表维护  
- 在线用户 Map 管理  
- 会话监听（用户上下线）  
- 请求日志监听  

---

## 四、项目整体结构

chatroom
├── src
│   └── main
│       ├── java
│       │   └── com.example.chatroom
│       │       ├── controller        # Servlet 控制层
│       │       │   ├── ApiMessagesServlet
│       │       │   ├── ApiOnlineServlet
│       │       │   ├── ApiSendServlet
│       │       │   ├── LoginServlet
│       │       │   ├── LogoutServlet
│       │       │   ├── PageChatServlet
│       │       │   └── RegisterServlet
│       │       │
│       │       ├── filter             # 过滤器
│       │       │   └── AuthFilter
│       │       │
│       │       ├── listener           # 监听器
│       │       │   ├── AppInitListener
│       │       │   ├── RequestLogListener
│       │       │   └── SessionListener
│       │       │
│       │       ├── model              # 实体模型
│       │       │   ├── Message
│       │       │   ├── MessageType
│       │       │   └── OnlineUser
│       │       │
│       │       ├── service            # 业务逻辑层
│       │       │   └── ChatService
│       │       │
│       │       └── util               # 工具类
│       │           └── TimeUtil
│       │
│       └── webapp
│           ├── WEB-INF
│           │   └── web.xml
│           ├── css
│           ├── js
│           └── jsp 页面
│
├── pom.xml
└── README.md


---

## 五、核心模块说明（答辩重点）

### 1️⃣ Controller（Servlet 控制层）

- **ApiMessagesServlet**  
  拉取当前用户可见消息列表（JSON），支持增量加载  

- **ApiSendServlet**  
  统一处理群聊 / 私聊消息发送请求  

- **ApiOnlineServlet**  
  获取当前在线用户列表  

- **LoginServlet / RegisterServlet / LogoutServlet**  
  用户身份相关操作  

- **PageChatServlet**  
  聊天页面跳转控制  

**职责原则：**  
Servlet 只负责请求分发与参数接收，不直接编写业务逻辑。

---

## 六、Service（业务层）

### ChatService（系统核心）

- 维护全局消息列表（ServletContext）  
- 管理在线用户 Map  
- 生成系统消息（用户进入提示）  
- 处理群聊消息发送  
- 处理私聊消息发送  
- 按 Session 控制消息可见性，防止私聊泄露  

**私聊可见性控制说明：**

- 系统消息：所有用户可见  
- 群聊消息：所有用户可见  
- 私聊消息：  
  - 仅发送方 Session 可见  
  - 或接收方 Session 可见  

该设计保证了私聊的 **数据隔离性与安全性**。

---

## 七、Model（实体层）

- **Message**  
  聊天消息实体，包含：  
  - 消息 ID  
  - 消息类型（SYSTEM / GROUP / PRIVATE）  
  - 发送方 Session  
  - 接收方 Session（私聊）  
  - 消息内容  
  - 时间戳  

- **MessageType**  
  消息类型枚举，明确区分系统 / 群聊 / 私聊  

- **OnlineUser**  
  在线用户实体抽象  

---

## 八、Listener（监听器）

- **AppInitListener**  
  项目启动时初始化：  
  - 全局消息列表  
  - 在线用户 Map  
  - 消息自增序列  

- **SessionListener**  
  监听 Session 创建与销毁，维护用户在线状态  

- **RequestLogListener**  
  请求日志监听（用于调试与扩展）  

体现对 **Servlet 生命周期与容器机制** 的理解。

---

## 九、Filter（过滤器）

- **AuthFilter**
  - 拦截未登录请求  
  - 防止未认证用户访问聊天接口  

展示基础的权限控制能力。

---

## 十、运行方式

### 1️⃣ 环境要求
- JDK 17  
- Tomcat 10.x  
- Maven 3.8+

### 2️⃣ 启动步骤

http://10.100.164.16/chatroom
