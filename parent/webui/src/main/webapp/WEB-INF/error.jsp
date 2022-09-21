<%--
  Created by IntelliJ IDEA.
  User: CONAN
  Date: 2022/8/28
  Time: 22:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>请求异常</title>
</head>
<body>
    <h1>发生错误</h1>
    <h3>${requestScope.exception.message}</h3>
</body>
</html>
