function getRecycleFiles(){
    $.ajax({
        "url":"file/recycle/info",
        "type":"post",
        "dataType":"json",
        "success":function(response){
            var code = response.code;
            if(code == "0"){
                fillRecycleTableBody(response.data);
            }
            if(code == "1"){
                layer.msg(response.msg);
            }
        },
        "error":function(error){
            layer.msg(error.status+" "+error.statusText);
        }
    })
}

function fillRecycleTableBody(fileList){
    $("#fileRecyclePageBody").empty();
    $("#fileRecycleInfoTable thead input[type='checkbox']").prop("checked",false);

    if(fileList == null || fileList.length == 0){
        $("#fileRecyclePageBody").append("<tr><td colspan='5' align='center'>没有可以恢复的文件</td></tr>")
        return;
    }
    for(var i=0;i<fileList.length;i++){
        var file = fileList[i];
        var is_directory = file.isDirectory;

        var checkboxTd = "<td><input type='checkbox'/></td>";
        if(is_directory == true){
            var fileNameTd = "<td class='fileNameTd' id='"+ file.fileId + "'" + "path='" + file.path.replace('\\' + file.owner,'') + file.name+ '\\' +"'>" + "<div>" + "<img class='icon-folder-and-file' src='static/image/icon/icon_folder.png' alt='folder'/>" + file.name + "</div>" + "</td>";
        }else{
            var fileNameTd = "<td class='fileNameTd' id='"+ file.fileId + "'" + "path='" + file.path.replace('\\' + file.owner,'') + file.name +"'>" + "<div>" + "<img class='icon-folder-and-file' src='static/image/icon/icon_file.png' alt='file'/>" + file.name + "</div>" + "</td>";
        }
        var createTimeTd = "<td>" + file.trashTime + "</td>";
        if(is_directory == true){
            var sizeTd = "<td>" + "-" + "</td>";
        }else{
            var sizeTd = "<td>" + fileUnitConversion(file.size) + "</td>";
        }
        var recoveryBtn = "<button id='fileRecoveryBtn' type='button' title='恢复' class='btn btn-primary btn-xs'><i class='glyphicon glyphicon-repeat'></i></button>";
        var deleteBtn = "<button id='fileDeleteBtn' type='button' title='删除' class='btn btn-danger btn-xs'><i class='glyphicon glyphicon-remove'></i></button>";

        var buttonTd = "<td>" + recoveryBtn + " " + deleteBtn + "</td>";
        var rowData = "<tr>" + checkboxTd + fileNameTd + createTimeTd + sizeTd + buttonTd + "</tr>";
        $("#fileRecyclePageBody").append(rowData);
    }
}

// 文件大小换算展示，传入单位为字节（B）的文件大小整数
function fileUnitConversion(fileSize){
    var resultSize = null;

    var kbSize = 1024;
    var mbSize = 1024*1024;
    var gbSize = 1024*1024*1024;

    // 如果大于1024字节且小于1024*1024，换算成KB
    if(fileSize <= kbSize){
        return fileSize + " B"
    }else if(fileSize > kbSize && fileSize <= mbSize){
        resultSize = (fileSize / kbSize).toFixed(2);
        return resultSize + " KB";
    }else if(fileSize > mbSize && fileSize < gbSize){
        resultSize = (fileSize / mbSize).toFixed(2);
        return resultSize + " MB";
    }else{
        resultSize = (fileSize / gbSize).toFixed(2);
        return resultSize +" GB";
    }
}

function evenBinding(){
    selectAll();
    selectOne();
    recoveryButtonClick();
    fileBatchRecoveryButtonClick();
    fileDeleteButtonClick();
    recycleDeleteConfirmButtonClick();
    fileBatchDeleteButtonClick();
}

// checkbox全选事件
function selectAll(){
    $("#fileRecycleInfoTable thead input[type='checkbox']").click(function(){
        $("#fileRecyclePageBody input[type='checkbox']").prop("checked",$("#fileRecycleInfoTable thead input[type='checkbox']").prop("checked"));
    })
}

function selectOne(){
    $("#fileRecycleInfoTable").on("click","#fileRecyclePageBody input[type='checkbox']",function(){
        if($("#fileRecyclePageBody input[type='checkbox']:checked").size() == $("#fileRecyclePageBody input[type='checkbox']").size()){
            $("#fileRecycleInfoTable thead input").prop("checked", true);
        }else{
            $("#fileRecycleInfoTable thead input").prop("checked", false);
        }
    });
}

// 单行恢复按钮
function recoveryButtonClick(){
    $("#fileRecyclePageBody").on("click","#fileRecoveryBtn",function(){
        var currentRowElement = $(this).parent().parent();
        var fileId = currentRowElement.find(".fileNameTd").prop("id");
        var fileIds = [];
        fileIds.push(fileId);
        recoveryAjaxRequest(fileIds);
    });
}

function recoveryAjaxRequest(fileIds){
    $.ajax({
        "url":"file/recovery",
        "type":"post",
        "traditional":true,
        "data":{
            "fileIds":fileIds
        },
        "dataType":"json",
        "success":function(response){
            var code = response.code;
            if(code == "0"){
                layer.msg("恢复成功");
                getRecycleFiles();
            }
            if(code == "1"){
                layer.msg(response.msg);
                // 因为是批量操作，失败了也刷新一下列表。因为可能存在部分成功的情况
                getRecycleFiles();
            }
        },
        "error":function(error){
            layer.msg(error.status+" "+error.statusText);
        }
    });
}

// 批量恢复按钮
function fileBatchRecoveryButtonClick(){
    $("#fileBatchRecoveryBtn").click(function(){
        var checkedRowElements = $("#fileRecyclePageBody input[type='checkbox']:checked");
        if(checkedRowElements.length == 0){
            layer.msg("请选择需要恢复的文件");
            return;
        }

        // 获取选中的文件的fileId封装成数组
        var fileIds = [];
        checkedRowElements.parent().parent().find(".fileNameTd").each(function(){
            fileIds.push($(this).prop("id"));
        })

        // 发送请求
        recoveryAjaxRequest(fileIds);
    });
}

// 单行删除按钮点击事件
function fileDeleteButtonClick(){
    $("#fileRecyclePageBody").on("click","#fileDeleteBtn",function(){
        var currentRowElement = $(this).parent().parent();
        var fileId = currentRowElement.find(".fileNameTd").prop("id");
        var fileIds = [];
        fileIds.push(fileId);

        // 绑定fileIds数组到回收站删除确认模态框的删除按钮上
        $("#recycleDeleteConfirmBtn").attr("fileIds",fileIds);

        // 弹出删除确认模态框
        $("#recycleDeleteConfirmModal").modal("show");
    });
}

// 批量删除按钮点击事件
function fileBatchDeleteButtonClick(){
    $("#fileBatchDeleteBtn").click(function(){
        var checkedRowElements = $("#fileRecyclePageBody input[type='checkbox']:checked");
        if(checkedRowElements.length == 0){
            layer.msg("请选择需要删除的文件");
            return;
        }

        // 获取选中的文件的fileId封装成数组
        var fileIds = [];
        checkedRowElements.parent().parent().find(".fileNameTd").each(function(){
            fileIds.push($(this).prop("id"));
        })

        // 将fileIds数组绑定到模态框删除按钮上
        $("#recycleDeleteConfirmBtn").attr("fileIds",fileIds);

        // 弹出删除确认模态框
        $("#recycleDeleteConfirmModal").modal("show");
    })
}

// 回收站删除确认模态框中的删除按钮点击事件
function recycleDeleteConfirmButtonClick(){
    $("#recycleDeleteConfirmBtn").click(function(){
        var fileIds =  $("#recycleDeleteConfirmBtn").attr("fileIds");

        // 发送请求
        deleteFileAjaxRequest(fileIds);
    });
}

// 删除回收站文件请求
function deleteFileAjaxRequest(fileIds){
    $.ajax({
        "url":"file/delete/permanently",
        "type":"post",
        "traditional":true,
        "data":{
            "fileIds":fileIds
        },
        "dataType":"json",
        "success":function(response){
            var code = response.code;
            if(code == "0"){
                layer.msg("删除成功");
                // 关闭模态框
                $("#recycleDeleteConfirmModal").modal("hide");
                // 刷新回收站
                getRecycleFiles();
                // 调用include-sidebar.js文件的getCapacity()方法刷新页面容量显示
                getCapacity();
            }
            if(code == "1"){
                layer.msg(response.msg);
            }
        },
        "error":function(error){
            layer.msg(error.status+" "+error.statusText);
        }
    })
}