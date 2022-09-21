<%--
  Created by IntelliJ IDEA.
  User: CONAN
  Date: 2022/9/8
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="createFolderModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新建文件夹</h4>
            </div>
            <div class="modal-body">
                <form id="createFolderForm" class="form-horizontal">
                    <div class="form-group">
                        <label for="createFolderInput" class="col-sm-2 control-label">名称</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="createFolderInput" placeholder="请输入文件夹名称">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="createFolderModalCreateButton" type="button" class="btn btn-primary">创建</button>
            </div>
        </div>
    </div>
</div>
