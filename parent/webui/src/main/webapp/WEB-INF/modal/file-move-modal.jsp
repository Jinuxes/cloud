<%--
  Created by IntelliJ IDEA.
  User: CONAN
  Date: 2022/9/14
  Time: 15:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="fileMoveModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">移动文件到指定目录</h4>
      </div>
      <div class="modal-body">
        <ul id="folderUL" class="list-group pre-scrollable">
          <%--<li class="list-group-item" title="/测试1"><input type="radio" name="folder"/> 测试1</li>--%>
          <%--<li class="list-group-item" title="/测试2"><input type="radio" name="folder"/> 测试2</li>--%>
          <%--<li class="list-group-item" title="/测试3"><input type="radio" name="folder"/> 测试3</li>--%>
          <%--<li class="list-group-item" title="/测试4"><input type="radio" name="folder"/> 测试4</li>--%>
          <%--<li class="list-group-item" title="/测试5"><input type="radio" name="folder"/> 测试5</li>--%>
        </ul>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button id="fileMoveToButton" type="button" class="btn btn-primary">移动到此</button>
      </div>
    </div>
  </div>
</div>
