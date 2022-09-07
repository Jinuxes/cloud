<%--
  Created by IntelliJ IDEA.
  User: CONAN
  Date: 2022/8/30
  Time: 15:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="deleteConfirmModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">是否要删除以下用户</h4>
            </div>
            <div class="modal-body">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>账号</th>
                        <th>用户名</th>
                    </tr>
                    </thead>
                    <tbody id="deleteConfirmModalTbody"></tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button id="deleteConfirmBtn" type="button" class="btn btn-primary">确认删除</button>
            </div>
        </div>
    </div>
</div>
