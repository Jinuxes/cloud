<%--
  Created by IntelliJ IDEA.
  User: CONAN
  Date: 2022/8/28
  Time: 18:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar navbar-inverse navbar-default navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:25px;" href="javascript:0;"><i class="glyphicon glyphicon-cloud"></i>  云存储</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li style="padding-top:8px;">
                    <div class="btn-group">
                        <button type="button" class="btn btn-default btn-success dropdown-toggle" data-toggle="dropdown">
                            <i class="glyphicon glyphicon-user"></i> ${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.username} <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" role="menu">
                            <li><a id="personalInformationBtn" href="javascript:void(0)"><i class="glyphicon glyphicon-cog"></i> 个人信息</a></li>
                            <li><a id="updatePasswordBtn" href="javascript:void(0)"><i class="glyphicon glyphicon-lock"></i> 修改密码</a></li>
                            <li class="divider"></li>
                            <li><a href="logout"><i class="glyphicon glyphicon-off"></i> 退出系统</a></li>
                        </ul>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</nav>
<%@include file="/WEB-INF/modal/include-nav-personal-info-modal.jsp"%>
<%@include file="/WEB-INF/modal/include-nav-personal-password-modal.jsp"%>
<script type="text/javascript" src="static/js/include-nav.js"></script>
<script type="text/javascript">
    $(function(){
        personalInformation();
        savePersonalInformation();
        updatePassword();
        saveUpdatePassword();
    });
</script>
