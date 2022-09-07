<%--
  Created by IntelliJ IDEA.
  User: CONAN
  Date: 2022/8/31
  Time: 19:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="roleAddModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加角色</h4>
            </div>
            <div class="modal-body">
                <form id="addRoleForm" class="form-horizontal">
                    <div class="form-group">
                        <label for="roleNameInput" class="col-sm-2 control-label">角色名</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="roleNameInput" placeholder="请输入角色名">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="addModalSaveButton" type="button" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>
