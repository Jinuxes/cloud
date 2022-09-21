<%--
  Created by IntelliJ IDEA.
  User: CONAN
  Date: 2022/8/28
  Time: 19:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="col-sm-3 col-md-2 sidebar">
    <div class="tree">
        <ul style="padding-left:0px;" class="list-group">
            <%--<li class="list-group-item tree-closed">--%>
            <li class="list-group-item">
                <span><i class="glyphicon glyphicon-triangle-right"></i> 用户管理 <span class="badge" style="float:right">3</span></span>
                <ul style="margin-top:10px;display:block;">
                    <li style="height:30px;">
                        <a href="user"><i class="glyphicon glyphicon-user"></i> 用户维护</a>
                    </li>
                    <li style="height:30px;">
                        <a href="role"><i class="glyphicon glyphicon-king"></i> 角色维护</a>
                    </li>
                    <li style="height:30px;">
                        <a href="authority"><i class="glyphicon glyphicon-th-list"></i> 权限维护</a>
                    </li>
                </ul>
            </li>
            <li class="list-group-item">
                <span><i class="glyphicon glyphicon-triangle-right"></i> 我的文件 <span class="badge" style="float:right">4</span></span>
                <ul style="margin-top:10px;display:block;">
                    <li style="height:30px;">
                        <a href="file"><i class="glyphicon glyphicon-list"></i> 全部文件</a>
                    </li>
                    <li style="height:30px;">
                        <a href="#"><i class="glyphicon glyphicon-file"></i> 文档</a>
                    </li>
                    <li style="height:30px;">
                        <a href="#"><i class="glyphicon glyphicon-picture"></i> 图片</a>
                    </li>
                    <li style="height:30px;">
                        <a href="#"><i class="glyphicon glyphicon-facetime-video"></i> 视频</a>
                    </li>
                </ul>
            </li>
            <li class="list-group-item tree-closed">
                <span><i class="glyphicon glyphicon-triangle-right"></i><a style="text-decoration: none;color: #000000" href="file/recycle"> 回收站</a></span>
            </li>
        </ul>
    </div>
</div>
