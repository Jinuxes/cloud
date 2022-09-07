<%--
  Created by IntelliJ IDEA.
  User: CONAN
  Date: 2022/9/1
  Time: 14:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="authorityDeleteConfirmModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">是否要删除以下权限</h4>
            </div>
            <div class="modal-body">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>权限名称</th>
                        <th>权限标题</th>
                    </tr>
                    </thead>
                    <tbody id="authorityDeleteConfirmModalTbody"></tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button id="authorityDeleteConfirmBtn" type="button" class="btn btn-primary">确认删除</button>
            </div>
        </div>
    </div>
</div>
