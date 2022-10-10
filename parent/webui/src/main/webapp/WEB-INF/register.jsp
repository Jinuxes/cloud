<%--
  Created by IntelliJ IDEA.
  User: CONAN
  Date: 2022/9/27
  Time: 15:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
    String backgroundUrl = basePath + "static/image/bg.jpg";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <base href="<%=basePath%>"/>
    <title>用户注册</title>
    <link rel="stylesheet" href="static/css/registerStyle.css">
    <script src="static/jquery/jquery-2.1.1.min.js"></script>
    <script src="static/layer/layer.js"></script>
    <script type="text/javascript" src="static/js/register.js"></script>
    <script type="text/javascript">
        $(function(){
           registerClick();
        });
    </script>
</head>
<body style="background: url(<%=backgroundUrl%>) no-repeat">

<form action="register" method="post">
    <div class="box">
        <h2>用户注册</h2>
        <p>${SPRING_SECURITY_LAST_EXCEPTION.message}</p>
        <div class="input-box">
            <input name="account" type="text" placeholder="账号"/>
        </div>
        <div class="input-box">
            <input name="password" type="password" placeholder="密码"/>
        </div>
        <div class="input-box">
            <input name="passwordConfirmation" type="password" placeholder="再次输入密码"/>
        </div>
        <div class="input-box">
            <input name="userName" type="text" placeholder="用户名"/>
        </div>
        <div class="input-box">
            <input name="email" type="text" placeholder="邮箱"/>
        </div>
        <div class="btn-box">
            <div>
                <button type="button" onclick="window.location.href='login/page'">返回登录页</button>
                <button id="registerBtn" type="button">注册</button>
            </div>
        </div>
    </div>
</form>
</body>
</html>
