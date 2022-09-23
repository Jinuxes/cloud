<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/include/include-head.jsp" %>
<script type="text/javascript" src="static/js/file.js"></script>
<script type="text/javascript">
    $(function(){
        window.homePathInfo = null;
        window.currentPath = null;  // 当前文件路径（目录也是文件的一种）
        window.currentFileId = null;  // 当前文件的Id
        window.pathAndIdMap = new Map();  //key是路径Path，value是路径的ID（fileId）。这个是按顺序存放用户所有访问过的路径
        // 获取家目录信息，绑定到window.homePathInfo全局变量中，然后再获取家目录下的文件
        getHomePathInfo();

        // 绑定事件
        evenBinding();
    })
</script>

<body>
<%@include file="/WEB-INF/include/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 文件</h3>
                </div>
                <ol id="filePathNavigationBar" class="breadcrumb" style="margin-bottom:0px">
                    <%--<li><a href="#">Home</a></li>--%>
                    <%--<li><a href="#">Library</a></li>--%>
                    <%--<li class="active">Data</li>--%>
                </ol>
                <div class="panel-body">
                    <button id="createFolderBtn" type="button" class="btn btn-default" style="float:left;margin-right:10px;"><i class="glyphicon glyphicon-folder-open"></i> 新建文件夹</button>
                    <button id="filesDownloadBtn" type="button" class="btn btn-default" style="float:left;margin-right:10px;"><i class="glyphicon glyphicon-cloud-download"></i> 下载</button>
                    <button id="fileUploadBtn" type="button" class="btn btn-default" style="float:left;margin-right:10px;"><i class="glyphicon glyphicon-cloud-upload"></i> 上传</button>
                    <button id="fileBatchDeleteBtn" type="button" class="btn btn-danger" style="float:left;"><i class="glyphicon glyphicon-trash"></i> 批量删除</button>
                    <form class="form-inline" role="form" style="float:right;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">搜索文件</div>
                                <input id="fileSearchInput" class="form-control has-success" type="text" placeholder="请输入文件名关键字">
                            </div>
                        </div>
                        <button id="fileSearchBtn" type="button" class="btn btn-default"><i class="glyphicon glyphicon-search"></i> 搜索</button>
                    </form>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table id="fileInfoTable" class="table table-condensed table-hover">
                            <thead>
                            <tr>
                                <th width="30"><input type="checkbox"></th>
                                <th>文件名</th>
                                <th>创建时间</th>
                                <th>大小</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="filePageBody">
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<%@include file="/WEB-INF/modal/file-create-folder-modal.jsp"%>
<%@include file="/WEB-INF/modal/file-upload-modal.jsp"%>
<%@include file="/WEB-INF/modal/file-move-modal.jsp"%>
<%@include file="/WEB-INF/modal/file-delete-modal.jsp"%>
</html>