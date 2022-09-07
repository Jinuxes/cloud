<%--
  Created by IntelliJ IDEA.
  User: CONAN
  Date: 2022/9/1
  Time: 20:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="userAssignRoleModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-inline" align="center">
                    <div class="form-group">
                        <label>未分配角色列表</label><br>
                        <select id="unAssignRoleList" class="form-control" multiple="" size="10" style="width:200px;overflow-y:auto;">
                        </select>
                    </div>
                    <div class="form-group">
                        <ul>
                            <li id="roleToRight" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                            <br>
                            <li id="roleToLeft" class="btn btn-default glyphicon glyphicon-chevron-left" style="margin-top:20px;"></li>
                        </ul>
                    </div>
                    <div class="form-group" style="margin-left:40px;">
                        <label>已分配角色列表</label><br>
                        <select id="assignedRoleList" class="form-control" multiple="" size="10" style="width:200px;overflow-y:auto;">
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="userAssignRoleModalSaveButton" type="button" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>
