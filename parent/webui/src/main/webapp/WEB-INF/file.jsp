<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/include/include-head.jsp" %>
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
                <div class="panel-body">
                    <button type="button" class="btn btn-default" style="float:left;margin-right:10px;"><i class="glyphicon glyphicon-folder-open"></i> 新建文件夹</button>
                    <button type="button" class="btn btn-default" style="float:left;margin-right:10px;"><i class="glyphicon glyphicon-cloud-download"></i> 下载</button>
                    <button type="button" class="btn btn-default" style="float:left;margin-right:10px;"><i class="glyphicon glyphicon-cloud-upload"></i> 上传</button>
                    <button type="button" class="btn btn-danger" style="float:left;"><i class="glyphicon glyphicon-floppy-remove"></i> 批量删除</button>
                    <form class="form-inline" role="form" style="float:right;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">搜索文件</div>
                                <input class="form-control has-success" type="text" placeholder="请输入文件名关键字">
                            </div>
                        </div>
                        <button type="button" class="btn btn-default"><i class="glyphicon glyphicon-search"></i> 搜索</button>
                    </form>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table table-condensed table-hover">
                            <thead>
                            <tr>
                                <th width="30"><input type="checkbox"></th>
                                <th>文件名</th>
                                <th>修改时间</th>
                                <th>大小</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="filePageBody">
                            <tr>
                                <td><input type="checkbox"></td>
                                <td>
                                    <div>
                                        <img id="iconFolderAndFile" src="static/image/icon_folder.png" alt="folder"/>
                                        <a href="#">Java教学视频</a>
                                    </div>
                                </td>
                                <td>2022-09-06 14:05:21</td>
                                <td>66.1M</td>
                                <td>
                                    <button type="button" title="下载" class="btn btn-primary btn-xs"><i class="glyphicon glyphicon-download-alt"></i></button>
                                    <button type="button" title="移动" class="btn btn-primary btn-xs"><i class="glyphicon glyphicon-move"></i></button>
                                    <button type="button" title="删除" class="btn btn-danger btn-xs"><i class="glyphicon glyphicon-trash"></i></button>
                                </td>
                            </tr>
                            <tr>
                                <td><input type="checkbox"></td>
                                <td>amet</td>
                                <td>consectetur</td>
                                <td>adipiscing</td>
                                <td>
                                    <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>
                                    <button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>
                                    <button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>
                                </td>
                            </tr>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <ul class="pagination">
                                        <li class="disabled"><a href="#">上一页</a></li>
                                        <li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
                                        <li><a href="#">2</a></li>
                                        <li><a href="#">3</a></li>
                                        <li><a href="#">4</a></li>
                                        <li><a href="#">5</a></li>
                                        <li><a href="#">下一页</a></li>
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
</html>