<%--
  Created by IntelliJ IDEA.
  User: CONAN
  Date: 2022/8/28
  Time: 19:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<div class="col-sm-3 col-md-2 sidebar">
    <div class="tree">
        <ul style="padding-left:0px;" class="list-group">
            <%--<li class="list-group-item tree-closed">--%>
            <security:authorize access="hasRole('超级管理员') or hasRole('管理员')">
                <li class="list-group-item">
                    <%--<span><i class="glyphicon glyphicon-triangle-right"></i> 用户管理 <span class="badge" style="float:right">3</span></span>--%>
                    <span><i class="glyphicon glyphicon-triangle-right"></i> 用户管理 </span>
                    <ul style="margin-top:10px;display:block;">
                        <li style="height:30px;">
                            <a href="user"><i class="glyphicon glyphicon-user"></i> 用户维护</a>
                        </li>
                        <security:authorize access="hasRole('超级管理员')">
                            <li style="height:30px;">
                                <a href="role"><i class="glyphicon glyphicon-king"></i> 角色维护</a>
                            </li>
                            <li style="height:30px;">
                                <a href="authority"><i class="glyphicon glyphicon-th-list"></i> 权限维护</a>
                            </li>
                        </security:authorize>
                    </ul>
                </li>
            </security:authorize>
            <li class="list-group-item">
                <%--<span><i class="glyphicon glyphicon-triangle-right"></i> 我的文件 <span class="badge" style="float:right">1</span></span>--%>
                <span><i class="glyphicon glyphicon-triangle-right"></i> 我的文件 </span>
                <ul style="margin-top:10px;display:block;">
                    <li style="height:30px;">
                        <a href="file"><i class="glyphicon glyphicon-list"></i> 全部文件</a>
                    </li>
                    <%--下面的相当于文件名搜索--%>
                    <%--<li style="height:30px;">--%>
                    <%--    <a href="#"><i class="glyphicon glyphicon-file"></i> 文档</a>--%>
                    <%--</li>--%>
                    <%--<li style="height:30px;">--%>
                    <%--    <a href="#"><i class="glyphicon glyphicon-picture"></i> 图片</a>--%>
                    <%--</li>--%>
                    <%--<li style="height:30px;">--%>
                    <%--    <a href="#"><i class="glyphicon glyphicon-facetime-video"></i> 视频</a>--%>
                    <%--</li>--%>
                </ul>
            </li>
            <li class="list-group-item tree-closed">
                <span><i class="glyphicon glyphicon-triangle-right"></i><a style="text-decoration: none;color: #000000" href="file/recycle"> 回收站</a></span>
            </li>
        </ul>
    </div>

    <%--<label id="storageCapacityLabel">容量：5.6G/100G[已使用：61.25%]</label>--%>
    <label id="storageCapacityLabel"></label>
    <div class="progress" style="height:5px">
        <div id="storageCapacityBar" class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"></div>
    </div>
</div>
<script type="text/javascript" src="static/js/include-sidebar.js"></script>
<script type="text/javascript">
    $(function(){
        getCapacity();
    });
</script>
