<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/include/include-head.jsp" %>
<script type="text/javascript" src="static/js/recycle.js"></script>
<script type="text/javascript">
    $(function(){
        getRecycleFiles();

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
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 回收站文件</h3>
                </div>
                <div class="panel-body">
                    <button id="fileBatchRecoveryBtn" type="button" class="btn btn-default" style="float:left;margin-right:10px;"><i class="glyphicon glyphicon-repeat"></i> 批量恢复</button>
                    <button type="button" class="btn btn-danger" style="float:left;"><i class="glyphicon glyphicon-remove"></i> 批量删除</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table id="fileRecycleInfoTable" class="table table-condensed table-hover">
                            <thead>
                            <tr>
                                <th width="30"><input type="checkbox"></th>
                                <th>文件名</th>
                                <th>删除时间</th>
                                <th>大小</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="fileRecyclePageBody">
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>