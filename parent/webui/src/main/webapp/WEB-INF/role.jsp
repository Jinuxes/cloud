<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/include/include-head.jsp" %>
<script type="text/javascript" src="static/js/role.js"></script>
<script type="text/javascript">
    $(function(){
        // 将分页数据写入全局变量
        window.rolePageNum = 1;
        window.rolePageSize = 5;
        window.roleKeyword = "";

        getRoleInfo(window.rolePageNum,window.rolePageSize,window.roleKeyword);

        // 各种事件绑定函数调用
        evenBinding();
    });
</script>

<body>
<%@include file="/WEB-INF/include/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 角色数据</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="roleSearchText" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="roleSearchBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button id="roleBatchDeleteBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button id="roleAddBtn" type="button" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table id="roleInfoTable" class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>名称</th>
                                <th>创建时间</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody"></tbody>
                            <tfoot>
                            <tr>
                                <td colspan="5" align="center">
                                    <ul id="roleInfoPagination" class="pagination">
                                    </ul>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<%@include file="/WEB-INF/modal/role-add-modal.jsp"%>
<%@include file="/WEB-INF/modal/role-delete-confirm-modal.jsp"%>
<%@include file="/WEB-INF/modal/role-edit-modal.jsp"%>
<%@include file="/WEB-INF/modal/role-assign-authority-modal.jsp"%>
</html>