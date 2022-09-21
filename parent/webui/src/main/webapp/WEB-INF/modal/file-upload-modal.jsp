<%--
  Created by IntelliJ IDEA.
  User: CONAN
  Date: 2022/9/11
  Time: 18:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="fileUploadModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">文件上传</h4>
            </div>
            <div class="modal-body">
                <form id="fileUploadForm" class="form-horizontal">
                    <%--多文件上传，加上multiple="multiple"--%>
                    <input type="file" id="fileUploadInput" multiple="multiple"/>
                    <%--单文件上传--%>
                    <%--<input type="file" id="fileUploadInput"/>--%>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="fileUploadModalUploadButton" type="button" class="btn btn-primary">上传</button>
            </div>
        </div>
    </div>
</div>
