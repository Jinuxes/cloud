<%--
  Created by IntelliJ IDEA.
  User: CONAN
  Date: 2022/8/28
  Time: 18:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";%>
<head>
    <meta charset="utf-8">
    <base href="<%=basePath%>"/>
    <link rel="stylesheet" href="static/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="static/css/main.css">
    <title></title>

    <script src="static/jquery/jquery-2.1.1.min.js"></script>
    <script src="static/bootstrap/js/bootstrap.min.js"></script>
    <script src="static/layer/layer.js"></script>
    <script type="text/javascript">
        // $(function () {
        //     $(".list-group-item").click(function(){
        //         if ( $(this).find("ul") ) {
        //             $(this).toggleClass("tree-closed");
        //             if ( $(this).hasClass("tree-closed") ) {
        //                 $("ul", this).hide("fast");
        //             } else {
        //                 $("ul", this).show("fast");
        //             }
        //         }
        //     });
        // });
    </script>
</head>
