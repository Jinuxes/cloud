function getAuthorityInfo(pageNum, pageSize, keyword){
    $.ajax({
        "url":"authority/info",
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
                window.authorityPageNum = response.data.pageNum;
                window.authorityPageSize = response.data.pageSize;
                window.authorityKeyword = keyword;
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
    $("#authorityPageBody").empty();
    $("#authorityInfoPagination").empty();
    // 表头复选框如果选择就取消选中
    $("#authorityInfoTable thead input").prop("checked", false);

    if(pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0){
        $("#authorityPageBody").append("<tr><td colspan='6' align='center'>抱歉！没有查询到您搜索的数据！</td></tr>")
        return;
    }

    // 使用pageInfo的list属性填充tbody
    for(var i=0;i<pageInfo.list.length;i++){
        var authority = pageInfo.list[i];

        var numberTd = "<td id='authorityIdTd' authorityId='"+ authority.id +"'>"+ (i+1) +"</td>";
        var checkboxTd = "<td><input type='checkbox'/></td>";
        var authorityNameTd = "<td id='authorityNameTd'>"+ authority.name + "</td>";
        var titleTd = "<td id='authorityTitleTd'>"+ authority.title + "</td>";
        var createTimeTd = "<td>"+ authority.createTime + "</td>";

        // var assignBtn = "<button type='button' class='btn btn-success btn-xs'><i class=' glyphicon glyphicon-check'></i></button>";
        var editBtn = "<button id='authorityEditBtn' type='button' class='btn btn-primary btn-xs'><i class=' glyphicon glyphicon-pencil'></i></button>";
        var deleteBtn = "<button id='authoritySingleDeleteBtn' type='button' class='btn btn-danger btn-xs'><i class=' glyphicon glyphicon-remove'></i></button>";

        var buttonTd = "<td>"+ editBtn + " " + deleteBtn + "</td>";

        var rowData = "<tr>" + numberTd + checkboxTd + authorityNameTd + titleTd + createTimeTd + buttonTd + "</tr>";
        $("#authorityPageBody").append(rowData);
    }
}

// 生成分页导航条
function generatePagination(pageInfo){
    if(pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0){
        return;
    }
    var authorityInfoPagination = $("#authorityInfoPagination")

    // 生成首页按钮
    if(pageInfo.isFirstPage){
        var firstTag = "<li class='disabled'><a clickable='false' page='"+ 1 + "'>首页</a></li>";
    }else{
        var firstTag = "<li><a page='"+ 1 + "'>首页</a></li>";
    }
    authorityInfoPagination.append(firstTag);

    // 生成上一页按钮
    if(pageInfo.pageNum == 1){
        var preTag = "<li class='disabled'><a clickable='false' page='"+ (pageInfo.pageNum-1) +"'>上一页</a></li>";
    }else{
        var preTag = "<li><a page='"+ (pageInfo.pageNum-1) +"'>上一页</a></li>";
    }
    authorityInfoPagination.append(preTag);

    // 生成分页导航页码
    var pageList = pageInfo.navigatepageNums;
    for(var i=0;i<pageList.length;i++){
        if(pageList[i] == pageInfo.pageNum){
            var pageTag ="<li class='active'><a page='"+ pageList[i] +"'>"+ pageList[i] +"</a></li>";
        }else{
            var pageTag ="<li><a page='"+ pageList[i] +"'>"+ pageList[i] +"</a></li>";
        }
        authorityInfoPagination.append(pageTag);
    }

    // 生成下一页按钮
    if(pageInfo.hasNextPage){
        var nextTag = "<li><a page='"+ (pageInfo.pageNum+1) +"'>下一页</a></li>";

    }else{
        var nextTag = "<li class='disabled'><a clickable='false' page='"+ (pageInfo.pageNum+1) +"'>下一页</a></li>";
    }
    authorityInfoPagination.append(nextTag);

    // 生成末页按钮
    if(pageInfo.isLastPage){
        var lastTag = "<li class='disabled'><a clickable='false' page='"+ pageInfo.pages + "'>末页</a></li>";
    }else{
        var lastTag = "<li><a page='"+ pageInfo.pages + "'>末页</a></li>";
    }
    authorityInfoPagination.append(lastTag);
}

// 事件委托，绑定分页导航条的点击事件
function evenDelegation(pageInfo){
    // 解除绑定事件，如果不先解除绑定，后面多次换页之后，请求此时会成倍增加，这是重复绑定导致的。
    $("#authorityInfoTable").off("click","tfoot li a");

    var currentPageNum = pageInfo.pageNum;
    // 重新绑定click事件
    $("#authorityInfoTable").on("click","tfoot li a",function(){
        var pageNum = $(this).attr("page");
        var clickable = $(this).attr("clickable");
        // 如果点击当前页，则不发送请求。
        // 如果点击上一页，且上一页有不能点击标记（已经再第一页），也不发送请求
        // 如果点击下一页，且下一页有不能点击标记（已经再第一页），也不发送请求
        if(pageNum == currentPageNum || clickable == "false"){
            return;
        }
        var keyword = $("#authoritySearchText").val();
        getAuthorityInfo(pageNum, pageInfo.pageSize, keyword);
    })
}

// 对其它各种事件进行绑定
function evenBinding(){
    searchButtonClick();
    addButtonClick();
    saveAuthorityButtonClick();
    selectAll();
    selectOne();
    batchDelete();
    batchDeleteConfirm();
    singleDelete();
    EditButtonClick();
    EditSaveButtonClick();
}

// 查询按钮点击事件
function searchButtonClick(){
    $("#authoritySearchBtn").click(function(){
        var keyword = $("#authoritySearchText").val();
        getAuthorityInfo(null,null,keyword);
    });
}

// 新增按钮点击事件
function addButtonClick(){
    $("#authorityAddBtn").click(function(){
        $("#addAuthorityForm")[0].reset();
        $("#authorityAddModal").modal("show");
    });
}

// 新增模态框，保存按钮点击事件
function saveAuthorityButtonClick(){
    $("#addAuthorityModalSaveButton").click(function(){
        // 获取输入数据
        var authorityName = $("#authorityNameInput").val();
        var authorityTitle = $("#authorityTitleInput").val();

        // 发送Ajax请求
        $.ajax({
            "url":"authority/save",
            "type":"post",
            "data":{
                "name":authorityName,
                "title":authorityTitle,
            },
            "dataType":"json",
            "success":function(response){
                var code = response.code;
                if(code == "0"){
                    layer.msg("保存成功！");
                    // 跳转到最后一页
                    getAuthorityInfo(999999,null,null);

                    // 如果查询框中有查询条件，清空keyword查询框中的查询条件
                    $("#authoritySearchText").val("");

                    //关闭模态框
                    $("#authorityAddModal").modal("hide");
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
    $("#authorityInfoTable thead input").click(function(){
        $("#authorityPageBody input[type='checkbox']").prop("checked",$("#authorityInfoTable thead input").prop("checked"));
    });
}

// 给动态生成的checkbox添加点击事件
function selectOne(){
    $("#authorityInfoTable").on("click","#authorityPageBody input[type='checkbox']",function(){
        if($("#authorityPageBody input[type='checkbox']:checked").size() == $("#authorityPageBody input[type='checkbox']").size()){
            $("#authorityInfoTable thead input").prop("checked", true);
        }else{
            $("#authorityInfoTable thead input").prop("checked", false);
        }
    });
}

// 批量删除按钮点击事件
function batchDelete(){
    $("#authorityBatchDeleteBtn").click(function(){
        // 清空删除确认模态框中表格体中的内容
        var deleteTbody = $("#authorityDeleteConfirmModalTbody")
        deleteTbody.empty();

        var chedkedElement = $("#authorityPageBody input[type='checkbox']:checked")
        var checkedNum = chedkedElement.size();
        if(checkedNum < 1){
            layer.msg("请选择要删除的用户");
            return;
        }

        // 获取authorityId
        var authorityIds = [];
        chedkedElement.parent().parent().children("#authorityIdTd").each(function(){
            authorityIds.push($(this).attr("authorityId"));
        });

        // 获取权限名称
        var authorityNames = [];
        chedkedElement.parent().parent().children("#authorityNameTd").each(function(){
            authorityNames.push($(this).text());
        });

        // 获取权限标题
        var authorityTitles = [];
        chedkedElement.parent().parent().children("#authorityTitleTd").each(function(){
            authorityTitles.push($(this).text());
        });

        // 在删除确认模态框里的表格中显示数据
        for(var i=0;i<authorityNames.length;i++){
            deleteTbody.append(
                "<tr><td>"+ authorityNames[i] +"</td><td>"+ authorityTitles[i] +"</td></tr>"
            );
        }

        // 在删除确认按钮中绑定authorityIds数组
        $("#authorityDeleteConfirmBtn").attr("authorityIds",authorityIds);

        // 显示模态框
        $("#authorityDeleteConfirmModal").modal("show");
    });
}

// 删除确认按钮点击事件
function batchDeleteConfirm(){
    $("#authorityDeleteConfirmBtn").click(function(){
        // 获取authorityIds
        var authorityIds = $(this).attr("authorityIds").split(",");
        // console.log(authorityIds);

        // 发送Ajax请求
        $.ajax({
            "url":"authority/delete",
            "type":"post",
            "traditional":true,
            "data":{
                "authorityIds":authorityIds,
            },
            "dataType":"json",
            "success":function(response){
                var code = response.code;
                if(code == "0"){
                    layer.msg("删除成功");

                    // 关闭模态框
                    $("#authorityDeleteConfirmModal").modal("hide");

                    // 重新请求分页数据
                    getAuthorityInfo(window.authorityPageNum,window.authorityPageSize,window.authorityKeyword);
                }
                if(code == "1"){
                    layer.msg("异常："+response.msg);
                }
            },
            "error":function(error){
                layer.msg(error.status+" "+error.statusText);
            }
        });
    });
}

// 单个删除按钮点击事件
function singleDelete(){
    $("#authorityPageBody").on("click","#authoritySingleDeleteBtn",function(){
        // 清空删除确认模态框中表格体中的内容
        var deleteTbody = $("#authorityDeleteConfirmModalTbody")
        deleteTbody.empty();

        var parentElement = $(this).parent().parent();
        // 获取authorityId
        var authorityId = parentElement.children("#authorityIdTd").attr("authorityId");
        // 获取authorityName
        var authorityName = parentElement.children("#authorityNameTd").text();
        // 获取authorityTitle
        var authorityTitle = parentElement.children("#authorityTitleTd").text();

        // 在删除确认模态框里的表格中显示数据
        deleteTbody.append(
            "<tr><td>"+ authorityName +"</td><td>"+ authorityTitle +"</td></tr>"
        );

        // 将单个id封装成数组
        var authorityIds = [];
        authorityIds.push(authorityId);

        // 在删除确认按钮中绑定authorityIds数组
        $("#authorityDeleteConfirmBtn").attr("authorityIds",authorityIds);

        $("#authorityDeleteConfirmModal").modal("show");
    })
}

// 权限编辑按钮点击事件
function EditButtonClick(){
    $("#authorityInfoTable").on("click","#authorityEditBtn",function(){
        // 重置编辑模态框的表单
        var EditTbody = $("#EditAuthorityForm")
        EditTbody[0].reset();

        // 回显表单
        var parentElement = $(this).parent().parent();
        // 获取authorityId
        var authorityId = parentElement.children("#authorityIdTd").attr("authorityId");
        // 获取authorityName
        var authorityName = parentElement.children("#authorityNameTd").text();
        // 获取authorityTitle
        var authorityTitle = parentElement.children("#authorityTitleTd").text();

        $("#EditModalAuthorityNameInput").val(authorityName);
        $("#EditModalAuthorityTitleInput").val(authorityTitle);

        // 将authorityId绑定到模态框的保存修改按钮上
        $("#authorityEditModalSaveButton").attr("authorityId",authorityId)

        // 显示模态框
        $("#AuthorityEditModal").modal("show");
    })
}

// 编辑权限信息的模态框的保存修改按钮点击事件
function EditSaveButtonClick(){
    $("#authorityEditModalSaveButton").click(function(){
        // 收集表单数据
        var authorityId = $("#authorityEditModalSaveButton").attr("authorityId");
        var authorityName = $("#EditModalAuthorityNameInput").val();
        var authorityTitle = $("#EditModalAuthorityTitleInput").val();

        // 发送ajax请求
        $.ajax({
            "url":"authority/update",
            "type":"post",
            "data":{
                "id":authorityId,
                "name":authorityName,
                "title":authorityTitle
            },
            "dataType":"json",
            "success":function(response){
                var code = response.code;
                if(code == "0"){
                    layer.msg("修改成功");

                    // 关闭模态框
                    $("#AuthorityEditModal").modal("hide");

                    // 重新请求分页数据
                    getAuthorityInfo(window.authorityPageNum,window.authorityPageSize,window.authorityKeyword);
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