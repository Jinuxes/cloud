<%--
  Created by IntelliJ IDEA.
  User: CONAN
  Date: 2022/8/29
  Time: 20:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="userAddModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加用户</h4>
            </div>
            <div class="modal-body">
                <form id="addUserForm" class="form-horizontal">
                    <div class="form-group">
                        <label for="accountInput" class="col-sm-2 control-label">账号</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="accountInput" placeholder="请输入账号">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="passwordInput" class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-10">
                            <input type="password" class="form-control" id="passwordInput" placeholder="请输入密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="userNameInput" class="col-sm-2 control-label">用户名</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="userNameInput" placeholder="请输入用户名">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="emailInput" class="col-sm-2 control-label">邮箱</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="emailInput" placeholder="请输入邮箱">
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
