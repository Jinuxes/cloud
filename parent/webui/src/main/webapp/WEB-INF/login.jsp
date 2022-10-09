<%--
  Created by IntelliJ IDEA.
  User: CONAN
  Date: 2022/8/28
  Time: 15:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <base href="<%=basePath%>"/>
    <title>登录</title>
    <link rel="stylesheet" href="static/css/style.css">
</head>
<body>

<form action="login" method="post">
    <div class="box">
        <h2>登录</h2>
        <p>${SPRING_SECURITY_LAST_EXCEPTION.message}</p>
        <div class="input-box">
            <label>账号</label>
            <input name="account" type="text"/>
        </div>
        <div class="input-box">
            <label>密码</label>
            <input name="password" type="password"/>
        </div>
        <div class="btn-box">
            <%--<a href="#">忘记密码?</a>--%>
            <div>
                <button type="submit">登录</button>
                <button id="registerBtn" type="button" onclick="window.location.href='register'">注册</button>
            </div>
        </div>
    </div>
</form>

<script>
</script>
</body>
</html>
