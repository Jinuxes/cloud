<%--
  Created by IntelliJ IDEA.
  User: CONAN
  Date: 2022/9/1
  Time: 14:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="authorityAddModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加权限</h4>
            </div>
            <div class="modal-body">
                <form id="addAuthorityForm" class="form-horizontal">
                    <div class="form-group">
                        <label for="authorityNameInput" class="col-sm-2 control-label">权限名称</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="authorityNameInput" placeholder="请输入权限名称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="authorityTitleInput" class="col-sm-2 control-label">权限标题</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="authorityTitleInput" placeholder="请输入权限标题">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="addAuthorityModalSaveButton" type="button" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>
