<%--
  Created by IntelliJ IDEA.
  User: CONAN
  Date: 2022/9/20
  Time: 14:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="fileDeleteConfirmModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">确定删除</h4>
      </div>
      <div class="modal-body" style="text-align:center">
        确定要删除所选文件吗？<br/>
        删除的文件可以通过回收站还原
      </div>
      <div class="modal-footer">
        <button id="fileDeleteConfirmBtn" type="button" class="btn btn-primary">删除</button>
      </div>
    </div>
  </div>
</div>
