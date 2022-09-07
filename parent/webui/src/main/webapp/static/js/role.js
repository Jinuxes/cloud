function getRoleInfo(pageNum, pageSize, keyword){
    $.ajax({
        "url":"role/info",
        "type":"post",
        "data":{
            "pageNum": pageNum,
            "pageSize": pageSize,
            "keyword": keyword
        },
        "dataType":"json",
        "success":function(response){
            var code = response.code;
            if(code == "0"){
                // console.log(response.data);
                // 生成表格体
                fillTableBody(response.data);

                // 事件委托
                evenDelegation(response.data);
                // 生成分页条
                generatePagination(response.data);

                // 将当前分页的pageNum、pageSize、keyword回写到window中，作为全局数据。方便删除操作后获取，然后刷新页面
                window.rolePageNum = response.data.pageNum;
                window.rolePageSize = response.data.pageSize;
                window.roleKeyword = keyword;
            }
            if(code == "1"){
                layer.msg("异常："+response.msg);
            }
        },
        "error":function(error){
            layer.msg(error.status+" "+error.statusText);
        }
    })
}

// 填充角色数据
function fillTableBody(pageInfo){
    // 清空表格体中的数据跟分页导航条中的数据
    $("#rolePageBody").empty();
    $("#roleInfoPagination").empty();
    // 表头复选框如果选择就取消选中
    $("#roleInfoTable thead input").prop("checked", false);

    if(pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0){
        $("#rolePageBody").append("<tr><td colspan='5' align='center'>抱歉！没有查询到您搜索的数据！</td></tr>")
        return;
    }

    // 使用pageInfo的list属性填充tbody
    for(var i=0;i<pageInfo.list.length;i++){
        var role = pageInfo.list[i];

        var numberTd = "<td id='roleIdTd' roleId='"+ role.id +"'>"+ (i+1) +"</td>";
        var checkboxTd = "<td><input type='checkbox'/></td>";
        var roleNameTd = "<td id='roleNameTd'>"+ role.name +"</td>";
        var createTimeTd = "<td>"+ role.createTime + "</td>";

        var assignBtn = "<button id='roleAssignAuthorityBtn' type='button' class='btn btn-success btn-xs'><i class=' glyphicon glyphicon-check'></i></button>";
        var editBtn = "<button id='roleEditBtn' type='button' class='btn btn-primary btn-xs'><i class=' glyphicon glyphicon-pencil'></i></button>";
        var deleteBtn = "<button id='singleDeleteBtn' type='button' class='btn btn-danger btn-xs'><i class=' glyphicon glyphicon-remove'></i></button>";

        var buttonTd = "<td>"+ assignBtn + " " + editBtn + " " + deleteBtn + "</td>";

        var rowData = "<tr>" + numberTd + checkboxTd + roleNameTd + createTimeTd + buttonTd + "</tr>";
        $("#rolePageBody").append(rowData);
    }
}

// 生成分页导航条
function generatePagination(pageInfo){
    if(pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0){
        return;
    }
    var roleInfoPagination = $("#roleInfoPagination")

    // 生成首页按钮
    if(pageInfo.isFirstPage){
        var firstTag = "<li class='disabled'><a clickable='false' page='"+ 1 + "'>首页</a></li>";
    }else{
        var firstTag = "<li><a page='"+ 1 + "'>首页</a></li>";
    }
    roleInfoPagination.append(firstTag);

    // 生成上一页按钮
    if(pageInfo.pageNum == 1){
        var preTag = "<li class='disabled'><a clickable='false' page='"+ (pageInfo.pageNum-1) +"'>上一页</a></li>";
    }else{
        var preTag = "<li><a page='"+ (pageInfo.pageNum-1) +"'>上一页</a></li>";
    }
    roleInfoPagination.append(preTag);

    // 生成分页导航页码
    var pageList = pageInfo.navigatepageNums;
    for(var i=0;i<pageList.length;i++){
        if(pageList[i] == pageInfo.pageNum){
            var pageTag ="<li class='active'><a page='"+ pageList[i] +"'>"+ pageList[i] +"</a></li>";
        }else{
            var pageTag ="<li><a page='"+ pageList[i] +"'>"+ pageList[i] +"</a></li>";
        }
        roleInfoPagination.append(pageTag);
    }

    // 生成下一页按钮
    if(pageInfo.hasNextPage){
        var nextTag = "<li><a page='"+ (pageInfo.pageNum+1) +"'>下一页</a></li>";

    }else{
        var nextTag = "<li class='disabled'><a clickable='false' page='"+ (pageInfo.pageNum+1) +"'>下一页</a></li>";
    }
    roleInfoPagination.append(nextTag);

    // 生成末页按钮
    if(pageInfo.isLastPage){
        var lastTag = "<li class='disabled'><a clickable='false' page='"+ pageInfo.pages + "'>末页</a></li>";
    }else{
        var lastTag = "<li><a page='"+ pageInfo.pages + "'>末页</a></li>";
    }
    roleInfoPagination.append(lastTag);
}

// 事件委托，绑定分页导航条的点击事件
function evenDelegation(pageInfo){
    // 解除绑定事件，如果不先解除绑定，后面多次换页之后，请求此时会成倍增加，这是重复绑定导致的。
    $("#roleInfoTable").off("click","tfoot li a");
    // 一定要指定子选择器，要不然像下面这一行，因为是异步执行，所以可能会导致问题，如果下面checkbox也是使用$("#roleInfoTable")
    // 来进行事件委托绑定，那么这个不指定子选择器的解绑事件会把$("#roleInfoTable")下的子元素所有绑定“click”事件的都解绑，
    // 这样会导致checkbox单选失效。
    // $("#roleInfoTable").off("click");

    var currentPageNum = pageInfo.pageNum;
    // 重新绑定click事件
    $("#roleInfoTable").on("click","tfoot li a",function(){
        var pageNum = $(this).attr("page");
        var clickable = $(this).attr("clickable");
        // 如果点击当前页，则不发送请求。
        // 如果点击上一页，且上一页有不能点击标记（已经再第一页），也不发送请求
        // 如果点击下一页，且下一页有不能点击标记（已经再第一页），也不发送请求
        if(pageNum == currentPageNum || clickable == "false"){
            return;
        }
        var keyword = $("#roleSearchText").val();
        getRoleInfo(pageNum, pageInfo.pageSize, keyword);
    })
}

// 对其它各种事件进行绑定
function evenBinding(){
    searchButtonClick();
    addButtonClick();
    saveRoleButtonClick();
    selectAll();
    selectOne();
    batchDelete();
    batchDeleteConfirm();
    singleDelete();
    EditButtonClick();
    EditSaveButtonClick();
    assignAuthorityClick();
    toRightClick();
    toLeftClick();
    assignAuthoritySaveButtonClick();
}

// 查询按钮点击事件
function searchButtonClick(){
    $("#roleSearchBtn").click(function(){
        var keyword = $("#roleSearchText").val();
        getRoleInfo(null,null,keyword);
    });
}

// 新增按钮点击事件
function addButtonClick(){
    $("#roleAddBtn").click(function(){
        $("#addRoleForm")[0].reset();
        $("#roleAddModal").modal("show");
    });
}

// 新增模态框，保存按钮点击事件
function saveRoleButtonClick(){
    $("#addModalSaveButton").click(function(){
        // 获取输入数据
        var name = $("#roleNameInput").val();

        // 发送Ajax请求
        $.ajax({
            "url":"role/save",
            "type":"post",
            "data":{
                "name":name,
            },
            "dataType":"json",
            "success":function(response){
                var code = response.code;
                if(code == "0"){
                    layer.msg("保存成功！");
                    // 跳转到最后一页
                    getRoleInfo(999999,null,null);

                    // 如果查询框中有查询条件，清空keyword查询框中的查询条件
                    $("#roleSearchText").val("");

                    //关闭模态框
                    $("#roleAddModal").modal("hide");
                }
                if(code=="1"){
                    layer.msg("异常："+response.msg);
                }
            },
            "error":function(error){
                layer.msg(error.status+" "+error.statusText);
            }
        });
    });
}

// checkbox全选事件
function selectAll(){
    $("#roleInfoTable thead input").click(function(){
        $("#rolePageBody input[type='checkbox']").prop("checked",$("#roleInfoTable thead input").prop("checked"));
    });
}

// 给动态生成的checkbox添加点击事件
function selectOne(){
    $("#roleInfoTable").on("click","#rolePageBody input[type='checkbox']",function(){
        if($("#rolePageBody input[type='checkbox']:checked").size() == $("#rolePageBody input[type='checkbox']").size()){
            $("#roleInfoTable thead input").prop("checked", true);
        }else{
            $("#roleInfoTable thead input").prop("checked", false);
        }
    });
}

// 批量删除按钮点击事件
function batchDelete(){
    $("#roleBatchDeleteBtn").click(function(){
        // 清空删除确认模态框中表格体中的内容
        var deleteTbody = $("#roleDeleteConfirmModalTbody")
        deleteTbody.empty();

        var checkedElement = $("#rolePageBody input[type='checkbox']:checked")
        var checkedNum = checkedElement.size();
        if(checkedNum < 1){
            layer.msg("请选择要删除的用户");
            return;
        }

        // 获取RoleId
        var roleIds = [];
        checkedElement.parent().parent().children("#roleIdTd").each(function(){
            roleIds.push($(this).attr("roleId"));
        });

        // 获取角色名
        var roleNames = [];
        checkedElement.parent().parent().children("#roleNameTd").each(function(){
            roleNames.push($(this).text());
        });

        // 在删除确认模态框里的表格中显示数据
        for(var i=0;i<roleNames.length;i++){
            deleteTbody.append(
                "<tr><td>"+ roleNames[i] +"</td></tr>"
            );
        }

        // 在删除确认按钮中绑定roleIds数组
        $("#roleDeleteConfirmBtn").attr("roleIds",roleIds);

        // 显示模态框
        $("#roleDeleteConfirmModal").modal("show");
    });
}

// 删除确认按钮点击事件
function batchDeleteConfirm(){
    $("#roleDeleteConfirmBtn").click(function(){
        // 获取roleIds
        var roleIds = $(this).attr("roleIds").split(",");
        // console.log(roleIds);

        // 发送Ajax请求
        $.ajax({
            "url":"role/delete",
            "type":"post",
            "traditional":true,
            "data":{
                "roleIds":roleIds
            },
            "dataType":"json",
            "success":function(response){
                var code = response.code;
                if(code == "0"){
                    layer.msg("删除成功");

                    // 关闭模态框
                    $("#roleDeleteConfirmModal").modal("hide");

                    // 重新请求分页数据
                    getRoleInfo(window.rolePageNum,window.rolePageSize,window.roleKeyword);
                }
                if(code == "1"){
                    layer.msg("异常："+response.msg);
                }
            },
            "error":function(error){
                layer.msg(error.status+" "+error.statusText);
            }
        });
        // console.log(roleIds);
    });
}

// 单个删除按钮点击事件
function singleDelete(){
    $("#rolePageBody").on("click","#singleDeleteBtn",function(){
        // 清空删除确认模态框中表格体中的内容
        var deleteTbody = $("#roleDeleteConfirmModalTbody");
        deleteTbody.empty();

        var parentElement = $(this).parent().parent();
        // 获取roleId
        var roleId = parentElement.children("#roleIdTd").attr("roleId");
        // 获取roleName
        var roleName = parentElement.children("#roleNameTd").text();

        // 在删除确认模态框里的表格中显示数据
        deleteTbody.append(
            "<tr><td>"+ roleName +"</td></tr>"
        );

        // 将单个id封装成数组
        var roleIds = [];
        roleIds.push(roleId);

        // 在删除确认按钮中绑定roleIds数组
        $("#roleDeleteConfirmBtn").attr("roleIds",roleIds);

        $("#roleDeleteConfirmModal").modal("show");
    })
}

// 用户编辑按钮点击事件
function EditButtonClick(){
    $("#roleInfoTable").on("click","#roleEditBtn",function(){
        // 重置编辑模态框的表单
        var EditTbody = $("#EditRoleForm")
        EditTbody[0].reset();

        // 回显表单
        var parentElement = $(this).parent().parent();
        // 获取角色id
        var roleId = parentElement.children("#roleIdTd").attr("roleId");
        // 获取roleName
        var roleName = parentElement.children("#roleNameTd").text();

        $("#EditModalRoleNameInput").val(roleName);

        // 将roleId绑定到角色编辑模态框的保存按钮中
        $("#EditModalSaveButton").attr("roleId",roleId);

        // 显示模态框
        $("#roleEditModal").modal("show");
    })
}

// 编辑用户信息的模态框的保存修改按钮点击事件
function EditSaveButtonClick(){
    $("#EditModalSaveButton").click(function(){
        // 收集表单数据
        var roleId = $("#EditModalSaveButton").attr("roleId");
        var roleName = $("#EditModalRoleNameInput").val();
        // console.log(roleId);
        // console.log(roleName);

        // 发送ajax请求
        $.ajax({
            "url":"/role/update",
            "type":"post",
            "data":{
                "id":roleId,
                "name":roleName
            },
            "dataType":"json",
            "success":function(response){
                var code = response.code;
                if(code == "0"){
                    layer.msg("修改成功");

                    // 关闭模态框
                    $("#roleEditModal").modal("hide");

                    // 重新请求分页数据
                    getRoleInfo(window.rolePageNum,window.rolePageSize,window.roleKeyword);
                }
                if(code == "1"){
                    layer.msg("异常："+response.msg);
                }
            },
            "error":function(error){
                layer.msg(error.status+" "+error.statusText);
            }
        })
    });
}

// 角色分配权限按钮点击事件
function assignAuthorityClick(){
    $("#roleInfoTable").on("click","#roleAssignAuthorityBtn",function(){
        // 清空两个<select>框中的内容
        $("#unAssignAuthorityList").empty();
        $("#assignedAuthorityList").empty();
        // 回显表单
        var parentElement = $(this).parent().parent();
        // 获取角色id
        var roleId = parentElement.children("#roleIdTd").attr("roleId");
        // 获取roleName
        var roleName = parentElement.children("#roleNameTd").text();
        $("#roleAssignAuthorityModal h4").text("角色分配权限 - " + roleName);

        // 将roleId绑定到角色分配权限模态框的保存按钮上
        $("#roleAssignAuthorityModalSaveButton").attr("roleId",roleId);

        // 发送请求，查询角色已分配权限跟未分配权限
        $.ajax({
            "url":"assign/authority/info",
            "type":"post",
            "data":{
                "roleId":roleId
            },
            "dataType":"json",
            "success":function(response){
                var code = response.code;
                if(code == "0"){
                    var data = response.data;
                    // 使用查询的数据填充角色分配权限模态框中的表单
                    for(var i=0;i<data.unAssignAuthorityList.length;i++){
                        var option = "<option value='"+ data.unAssignAuthorityList[i].id +"'>"+ data.unAssignAuthorityList[i].title + "</option>";
                        $("#unAssignAuthorityList").append(option);
                    }
                    for(var i=0;i<data.assignedAuthorityList.length;i++){
                        var option = "<option value='"+ data.assignedAuthorityList[i].id +"'>"+ data.assignedAuthorityList[i].title + "</option>";
                        $("#assignedAuthorityList").append(option);
                    }
                }
                if(code == "1"){
                    layer.msg("异常："+response.msg);
                }
            },
            "error":function(error){
                layer.msg(error.status+" "+error.statusText);
            }
        });

        // 显示模态框
        $("#roleAssignAuthorityModal").modal("show");
    });
}

// 分配权限模态框中右移点击事件
function toRightClick(){
    $("#authorityToRight").click(function(){
        $("#unAssignAuthorityList>option:selected").appendTo($("#assignedAuthorityList"));
    });
}

// 分配权限模态框中左移点击事件
function toLeftClick(){
    $("#authorityToLeft").click(function(){
        $("#assignedAuthorityList>option:selected").appendTo($("#unAssignAuthorityList"));
    });
}

// 分配权限模态框保存按钮点击事件
function assignAuthoritySaveButtonClick(){
    $("#roleAssignAuthorityModalSaveButton").click(function(){
        // 1.点击保存后，先把已分配权限选择框中的所有option全部设置为选中状态
        $("#assignedAuthorityList>option").prop("selected","selected");

        // 2.获取已分配权限选择栏标签中所有权限对应的id
        var authorityIds = [];
        $("#assignedAuthorityList>option:selected").each(function(){
            authorityIds.push($(this).prop("value"));
        })
        // console.log(roleIds);

        // 3.获取roleId
        var roleId = $("#roleAssignAuthorityModalSaveButton").attr("roleId");

        // 4.发送Ajax请求保存数据
        $.ajax({
            "url":"assign/authority/save",
            "type":"post",
            "traditional":true,
            "data":{
                "roleId":roleId,
                "authorityIds":authorityIds
            },
            "dataType":"json",
            "success":function(response){
                var code = response.code;
                if(code == "0"){
                    layer.msg("保存成功");

                    // 关闭模态框
                    $("#roleAssignAuthorityModal").modal("hide");

                    // 重新请求分页数据
                    getRoleInfo(window.rolePageNum,window.rolePageSize,window.roleKeyword);
                }
                if(code == "1"){
                    layer.msg("异常："+response.msg);
                }
            },
            "error":function(error){
                layer.msg(error.status+" "+error.statusText);
            }
        });
    })
}