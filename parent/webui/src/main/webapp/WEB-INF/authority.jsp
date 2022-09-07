<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/include/include-head.jsp" %>
<body>
<%@include file="/WEB-INF/include/include-nav.jsp" %>
<script type="text/javascript" src="static/js/authority.js"></script>
<script type="text/javascript">
    $(function(){
        // 将分页数据写入全局变量
        window.authorityPageNum = 1;
        window.authorityPageSize = 5;
        window.authorityKeyword = "";

        getAuthorityInfo(window.authorityPageNum,window.authorityPageSize,window.authorityKeyword);

        evenBinding();
    });
</script>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 权限数据</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="authoritySearchText" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="authoritySearchBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i>
                            查询</button>
                    </form>
                    <button id="authorityBatchDeleteBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button id="authorityAddBtn" type="button" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i>新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div id="abcde" class="table-responsive">
                        <table id="authorityInfoTable" class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>名称</th>
                                <th>标题</th>
                                <th>创建时间</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="authorityPageBody"></tbody>
                            <tfoot>
                            <tr>
                                <td colspan="7" align="center">
                                    <ul id="authorityInfoPagination" class="pagination">
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
<%@include file="/WEB-INF/modal/authority-add-modal.jsp"%>
<%@include file="/WEB-INF/modal/authority-delete-confirm-modal.jsp"%>
<%@include file="/WEB-INF/modal/authority-edit-modal.jsp"%>
</html>