<%--
  Created by IntelliJ IDEA.
  User: CONAN
  Date: 2022/10/9
  Time: 20:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="personalInformationModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改个人信息</h4>
            </div>
            <div class="modal-body">
                <form id="personalInformationForm" class="form-horizontal">
                    <div class="form-group">
                        <label for="personalInformationNameInput" class="col-sm-2 control-label">用户名</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="personalInformationNameInput" placeholder="请输入用户名">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="personalInformationEmailInput" class="col-sm-2 control-label">邮箱</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="personalInformationEmailInput" placeholder="请输入邮箱">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="personalInformationSaveButton" type="button" class="btn btn-primary">保存修改</button>
            </div>
        </div>
    </div>
</div>
