<%--
  Created by IntelliJ IDEA.
  User: CONAN
  Date: 2022/10/9
  Time: 21:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="updatePasswordModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改个人信息</h4>
            </div>
            <div class="modal-body">
                <form id="updatePasswordForm" class="form-horizontal">
                    <div class="form-group">
                        <label for="updatePasswordInput" class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="updatePasswordInput" placeholder="请输入密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="updatePasswordConfirmInput" class="col-sm-2 control-label">确认密码</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="updatePasswordConfirmInput" placeholder="请再次输入密码">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="updatePasswordSaveButton" type="button" class="btn btn-primary">保存修改</button>
            </div>
        </div>
    </div>
</div>
