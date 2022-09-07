function getUserInfo(pageNum, pageSize, keyword){
    $.ajax({
        "url":"user/info",
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
                window.userPageNum = response.data.pageNum;
                window.userPageSize = response.data.pageSize;
                window.userKeyword = keyword;
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

function fillTableBody(pageInfo){
    // 清空表格体中的数据跟分页导航条中的数据
    $("#userPageBody").empty();
    $("#userInfoPagination").empty();
    // 表头复选框如果选择就取消选中
    $("#userInfoTable thead input").prop("checked", false);

    if(pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0){
        $("#userPageBody").append("<tr><td colspan='7' align='center'>抱歉！没有查询到您搜索的数据！</td></tr>")
        return;
    }

    // 使用pageInfo的list属性填充tbody
    for(var i=0;i<pageInfo.list.length;i++){
        var user = pageInfo.list[i];

        var numberTd = "<td id='userIdTd' userId='"+ user.id +"'>"+ (i+1) +"</td>";
        var checkboxTd = "<td><input type='checkbox'/></td>";
        var accountTd = "<td id='userAccountTd'>"+ user.account +"</td>";
        var userNameTd = "<td id='userNameTd'>"+ user.userName + "</td>";
        var emailTd = "<td id='userEmailTd'>"+ user.email + "</td>";
        var createTimeTd = "<td>"+ user.createTime + "</td>";

        var assignBtn = "<button id='userAssignRoleBtn' type='button' class='btn btn-success btn-xs'><i class=' glyphicon glyphicon-check'></i></button>";
        var editBtn = "<button id='userEditBtn' type='button' class='btn btn-primary btn-xs'><i class=' glyphicon glyphicon-pencil'></i></button>";
        var deleteBtn = "<button id='singleDeleteBtn' type='button' class='btn btn-danger btn-xs'><i class=' glyphicon glyphicon-remove'></i></button>";

        var buttonTd = "<td>"+ assignBtn + " " + editBtn + " " + deleteBtn + "</td>";

        var rowData = "<tr>" + numberTd + checkboxTd + accountTd + userNameTd + emailTd + createTimeTd + buttonTd + "</tr>";
        $("#userPageBody").append(rowData);
    }
}

// 生成分页导航条
function generatePagination(pageInfo){
    if(pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0){
        return;
    }
    var userInfoPagination = $("#userInfoPagination")

    // 生成首页按钮
    if(pageInfo.isFirstPage){
        var firstTag = "<li class='disabled'><a clickable='false' page='"+ 1 + "'>首页</a></li>";
    }else{
        var firstTag = "<li><a page='"+ 1 + "'>首页</a></li>";
    }
    userInfoPagination.append(firstTag);

    // 生成上一页按钮
    if(pageInfo.pageNum == 1){
        var preTag = "<li class='disabled'><a clickable='false' page='"+ (pageInfo.pageNum-1) +"'>上一页</a></li>";
    }else{
        var preTag = "<li><a page='"+ (pageInfo.pageNum-1) +"'>上一页</a></li>";
    }
    userInfoPagination.append(preTag);

    // 生成分页导航页码
    var pageList = pageInfo.navigatepageNums;
    for(var i=0;i<pageList.length;i++){
        if(pageList[i] == pageInfo.pageNum){
            var pageTag ="<li class='active'><a page='"+ pageList[i] +"'>"+ pageList[i] +"</a></li>";
        }else{
            var pageTag ="<li><a page='"+ pageList[i] +"'>"+ pageList[i] +"</a></li>";
        }
        userInfoPagination.append(pageTag);
    }

    // 生成下一页按钮
    if(pageInfo.hasNextPage){
        var nextTag = "<li><a page='"+ (pageInfo.pageNum+1) +"'>下一页</a></li>";

    }else{
        var nextTag = "<li class='disabled'><a clickable='false' page='"+ (pageInfo.pageNum+1) +"'>下一页</a></li>";
    }
    userInfoPagination.append(nextTag);

    // 生成末页按钮
    if(pageInfo.isLastPage){
        var lastTag = "<li class='disabled'><a clickable='false' page='"+ pageInfo.pages + "'>末页</a></li>";
    }else{
        var lastTag = "<li><a page='"+ pageInfo.pages + "'>末页</a></li>";
    }
    userInfoPagination.append(lastTag);
}

// 事件委托，绑定分页导航条的点击事件
function evenDelegation(pageInfo){
    // 解除绑定事件，如果不先解除绑定，后面多次换页之后，请求此时会成倍增加，这是重复绑定导致的。
    $("#userInfoTable").off("click","tfoot li a");
    // 一定要指定子选择器，要不然像下面这一行，因为是异步执行，所以可能会导致问题，如果下面checkbox也是使用$("#userInfoTable")
    // 来进行事件委托绑定，那么这个不指定子选择器的解绑事件会把$("#userInfoTable")下的子元素所有绑定“click”事件的都解绑，
    // 这样会导致checkbox单选失效。
    // $("#userInfoTable").off("click");

    var currentPageNum = pageInfo.pageNum;
    // 重新绑定click事件
    $("#userInfoTable").on("click","tfoot li a",function(){
        var pageNum = $(this).attr("page");
        var clickable = $(this).attr("clickable");
        // 如果点击当前页，则不发送请求。
        // 如果点击上一页，且上一页有不能点击标记（已经再第一页），也不发送请求
        // 如果点击下一页，且下一页有不能点击标记（已经再第一页），也不发送请求
        if(pageNum == currentPageNum || clickable == "false"){
            return;
        }
        var keyword = $("#searchText").val();
        getUserInfo(pageNum, pageInfo.pageSize, keyword);
    })
}

// 对其它各种事件进行绑定
function evenBinding(){
    searchButtonClick();
    addButtonClick();
    saveUserButtonClick();
    selectAll();
    selectOne();
    batchDelete();
    batchDeleteConfirm();
    singleDelete();
    EditButtonClick();
    EditSaveButtonClick();
    assignRoleClick();
    toRightClick();
    toLeftClick();
    assignRoleSaveButtonClick();
}

// // 查询输入框变化事件
// function searchChange(){
//     // input是当输入框变化时就触发的事件，这样对数据库消耗很大
//     // $(".panel-body").on("input","#searchText",function(){
//     //     var keyword = $(this).val();
//     //     getUserInfo(null,null,keyword);
//     // });
//
//     // change是当输入框失去焦点时触发的事件
//     $("#searchText").change(function(){
//         var keyword = $(this).val();
//         getUserInfo(null,null,keyword);
//     })
// }

// 查询按钮点击事件
function searchButtonClick(){
    $("#searchBtn").click(function(){
        var keyword = $("#searchText").val();
        getUserInfo(null,null,keyword);
    });
}

// 新增按钮点击事件
function addButtonClick(){
    $("#addBtn").click(function(){
        $("#addUserForm")[0].reset();
        $("#userAddModal").modal("show");
    });
}

// 新增模态框，保存按钮点击事件
function saveUserButtonClick(){
    $("#addModalSaveButton").click(function(){
        // 获取输入数据
        var account = $("#accountInput").val();
        var password = $("#passwordInput").val();
        var userName = $("#userNameInput").val();
        var email = $("#emailInput").val();

        // 发送Ajax请求
        $.ajax({
            "url":"user/save",
            "type":"post",
            "data":{
                "account":account,
                "password":password,
                "userName":userName,
                "email":email
            },
            "dataType":"json",
            "success":function(response){
                var code = response.code;
                if(code == "0"){
                    layer.msg("保存成功！");
                    // 跳转到最后一页
                    getUserInfo(999999,null,null);

                    // 如果查询框中有查询条件，清空keyword查询框中的查询条件
                    $("#searchText").val("");

                    //关闭模态框
                    $("#userAddModal").modal("hide");
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
    $("#userInfoTable thead input").click(function(){
        $("#userPageBody input[type='checkbox']").prop("checked",$("#userInfoTable thead input").prop("checked"));
    });
}

// 给动态生成的checkbox添加点击事件
function selectOne(){
    $("#userInfoTable").on("click","#userPageBody input[type='checkbox']",function(){
        if($("#userPageBody input[type='checkbox']:checked").size() == $("#userPageBody input[type='checkbox']").size()){
            $("#userInfoTable thead input").prop("checked", true);
        }else{
            $("#userInfoTable thead input").prop("checked", false);
        }
    });
}

// 批量删除按钮点击事件
function batchDelete(){
    $("#batchDeleteBtn").click(function(){
        // 清空删除确认模态框中表格体中的内容
        var deleteTbody = $("#deleteConfirmModalTbody")
        deleteTbody.empty();

        var chedkedElement = $("#userPageBody input[type='checkbox']:checked")
        var checkedNum = chedkedElement.size();
        if(checkedNum < 1){
            layer.msg("请选择要删除的用户");
            return;
        }

        // 获取userId
        var userIds = [];
        chedkedElement.parent().parent().children("#userIdTd").each(function(){
            userIds.push($(this).attr("userId"));
        });

        // 获取账号
        var accounts = [];
        chedkedElement.parent().parent().children("#userAccountTd").each(function(){
            accounts.push($(this).text());
        });

        // 获取用户名
        var usernames = [];
        chedkedElement.parent().parent().children("#userNameTd").each(function(){
            usernames.push($(this).text());
        });

        // 在删除确认模态框里的表格中显示数据
        for(var i=0;i<accounts.length;i++){
            deleteTbody.append(
                "<tr><td>"+ accounts[i] +"</td><td>"+ usernames[i] +"</td></tr>"
            );
        }

        // 在删除确认按钮中绑定userids数组
        $("#deleteConfirmBtn").attr("userIds",userIds);

        // 显示模态框
        $("#deleteConfirmModal").modal("show");
    });
}

// 删除确认按钮点击事件
function batchDeleteConfirm(){
    $("#deleteConfirmBtn").click(function(){
        // 获取userIds
        var userIds = $(this).attr("userIds").split(",");
        // console.log(userIds);

        // 发送Ajax请求
        $.ajax({
            "url":"user/delete",
            "type":"post",
            "traditional":true,
            "data":{
                "userIds":userIds
            },
            "dataType":"json",
            "success":function(response){
                var code = response.code;
                if(code == "0"){
                    layer.msg("删除成功");

                    // 关闭模态框
                    $("#deleteConfirmModal").modal("hide");

                    // 重新请求分页数据
                    getUserInfo(window.userPageNum,window.userPageSize,window.userKeyword);
                }
                if(code == "1"){
                    layer.msg("异常："+response.msg);
                }
            },
            "error":function(error){
                layer.msg(error.status+" "+error.statusText);
            }
        });
        // console.log(userIds);
    });
}

// 单个删除按钮点击事件
function singleDelete(){
    $("#userPageBody").on("click","#singleDeleteBtn",function(){
        // 清空删除确认模态框中表格体中的内容
        var deleteTbody = $("#deleteConfirmModalTbody")
        deleteTbody.empty();

        var parentElement = $(this).parent().parent();
        // 获取userid
        var userId = parentElement.children("#userIdTd").attr("userid");
        // 获取account
        var account = parentElement.children("#userAccountTd").text();
        // 获取userName
        var userName = parentElement.children("#userNameTd").text();

        // 在删除确认模态框里的表格中显示数据
        deleteTbody.append(
            "<tr><td>"+ account +"</td><td>"+ userName +"</td></tr>"
        );

        // 将单个id封装成数组
        var userIds = [];
        userIds.push(userId);

        // 在删除确认按钮中绑定userids数组
        $("#deleteConfirmBtn").attr("userIds",userIds);

        $("#deleteConfirmModal").modal("show");
    })
}

// 用户编辑按钮点击事件
function EditButtonClick(){
    $("#userInfoTable").on("click","#userEditBtn",function(){
        // 重置编辑模态框的表单
        var EditTbody = $("#EditUserForm")
        EditTbody[0].reset();

        // 回显表单，不需要回显密码
        // 支持修改用户名、邮箱。不支持修改密码、userid、创建时间
        // 后端使用账号搜索对应用户修改，不使用userid搜索，避免客户绕过前端，直接使用工具请求，从而间接修改账号。
        var parentElement = $(this).parent().parent();
        // 获取account
        var account = parentElement.children("#userAccountTd").text();
        // 获取userName
        var userName = parentElement.children("#userNameTd").text();
        // 获取email
        var email = parentElement.children("#userEmailTd").text();

        $("#EditModalAccountInput").val(account);
        $("#EditModalUserNameInput").val(userName);
        $("#EditModalEmailInput").val(email);

        // 显示模态框
        $("#userEditModal").modal("show");
    })
}

// 编辑用户信息的模态框的保存修改按钮点击事件
function EditSaveButtonClick(){
    $("#EditModalSaveButton").click(function(){
        // 收集表单数据
        var account = $("#EditModalAccountInput").val();
        var username = $("#EditModalUserNameInput").val();
        var email = $("#EditModalEmailInput").val();

        // 发送ajax请求
        $.ajax({
            "url":"user/update",
            "type":"post",
            "data":{
                "account":account,
                "userName":username,
                "email":email
            },
            "dataType":"json",
            "success":function(response){
                var code = response.code;
                if(code == "0"){
                    layer.msg("修改成功");

                    // 关闭模态框
                    $("#userEditModal").modal("hide");

                    // 重新请求分页数据
                    getUserInfo(window.userPageNum,window.userPageSize,window.userKeyword);
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

// 分配角色按钮点击事件
function assignRoleClick(){
    $("#userInfoTable").on("click","#userAssignRoleBtn",function(){

        // 清空用户分配角色模态框中两个<select>中的内容
        $("#unAssignRoleList").empty();
        $("#assignedRoleList").empty();


        // 获取用户id、account，显示到模态框标题
        var parentElement = $(this).parent().parent();
        // 1.获取id
        var userId = parentElement.children("#userIdTd").attr("userId");
        // 2.获取account
        var UserAccount = parentElement.children("#userAccountTd").text();
        // 3.显示
        $("#userAssignRoleModal h4").text("用户分配角色 - " + UserAccount);

        // 绑定userId到保存按钮
        $("#userAssignRoleModalSaveButton").attr("userId",userId);

        // 查询所有的已分配角色信息以及未分配角色信息，显示在模态框上
        $.ajax({
            "url":"assign/role/info",
            "type":"post",
            "data":{
                "userId":userId
            },
            "dataType":"json",
            "success":function(response){
                var code = response.code;
                if(code == "0"){
                    var data = response.data;
                    // 使用查询的数据填充用户分配角色模态框中的表单
                    // console.log(data.unAssignRoleList);
                    for(var i=0;i<data.unAssignRoleList.length;i++){
                        var option = "<option value='"+ data.unAssignRoleList[i].id +"'>"+ data.unAssignRoleList[i].name + "</option>";
                        $("#unAssignRoleList").append(option);
                    }
                    for(var i=0;i<data.assignedRoleList.length;i++){
                        var option = "<option value='"+ data.assignedRoleList[i].id +"'>"+ data.assignedRoleList[i].name + "</option>";
                        $("#assignedRoleList").append(option);
                    }

                    // 显示用户分配角色模态框
                    $("#userAssignRoleModal").modal("show");
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

// 分配角色模态框中右移点击事件
function toRightClick(){
    $("#roleToRight").click(function(){
        $("#unAssignRoleList>option:selected").appendTo($("#assignedRoleList"));
    });
}

// 分配角色模态框中左移点击事件
function toLeftClick(){
    $("#roleToLeft").click(function(){
        $("#assignedRoleList>option:selected").appendTo($("#unAssignRoleList"));
    });
}

// 分配角色模态框保存按钮点击事件
function assignRoleSaveButtonClick(){
    $("#userAssignRoleModalSaveButton").click(function(){
        // 1.点击保存后，先把已分配角色选择框中的所有option全部设置为选中状态
        $("#assignedRoleList>option").prop("selected","selected");

        // 2.获取已分配角色选择栏标签中所有角色对应的id
        var roleIds = [];
        $("#assignedRoleList>option:selected").each(function(){
            roleIds.push($(this).prop("value"));
        })
        // console.log(roleIds);

        // 3.获取userId
        var userId = $("#userAssignRoleModalSaveButton").attr("userId");

        // 4.发送Ajax请求保存数据
        $.ajax({
           "url":"assign/role/save",
            "type":"post",
            "traditional":true,
            "data":{
                "userId":userId,
                "roleIds":roleIds
            },
            "dataType":"json",
            "success":function(response){
               var code = response.code;
               if(code == "0"){
                   layer.msg("保存成功");

                   // 关闭模态框
                   $("#userAssignRoleModal").modal("hide");

                   // 重新请求分页数据
                   getUserInfo(window.userPageNum,window.userPageSize,window.userKeyword);
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