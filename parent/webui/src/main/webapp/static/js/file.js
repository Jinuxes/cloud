function getHomePathInfo(){
    $.ajax({
        "url":"file/home/info",
        "type":"post",
        "dataType":"json",
        "success":function(response){
            var code = response.code;
            if(code == "0"){
                homePathInfo = response.data;
                if(homePathInfo == null || homePathInfo == undefined){
                    layer.msg("异常："+"用户没有存储目录");
                }else{
                    window.homePathInfo = homePathInfo;
                    window.currentPath = homePathInfo.path;
                    window.currentFileId = homePathInfo.fileId;
                    window.pathAndIdMap.set(homePathInfo.path,homePathInfo.fileId);
                    // console.log(window.pathAndIdMap);
                    getPathFilesByPath(homePathInfo.path);
                }
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

// 根据路径获取目录下的文件，两个字段选其一即可。
// function getPathFiles(path, fileId)
// path：是目录的路径，t_file表中path字段等于目录路径的就是这个目录下的文件
// fileId：是目录的fileId，t_file表中parentId字段为目录的fileId的，就是这个目录下的文件。
function getPathFilesByPath(path){
    $.ajax({
        "url":"file/home",
        "type":"post",
        "dataType":"json",
        "data":{
            "path":path
        },
        "success":function(response){
            var code = response.code;
            if(code == "0"){
                // 获取返回数据，渲染页面
                var fileList = response.data;
                // console.log(window.pathAndIdMap);
                fillTableBody(fileList);
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

function fillTableBody(fileList){
    // 先清空表格体
    $("#filePageBody").empty();
    // 取消表头选择框的选中状态
    $("#fileInfoTable thead tr th input").prop("checked",false);

    // 填充路径导航栏
    fillPathNavigationBar();

    if(fileList == null || fileList.length == 0){
        $("#filePageBody").append("<tr><td colspan='5' align='center'>当前文件夹为空</td></tr>")
        return;
    }
    for(var i=0;i<fileList.length;i++){
        var file = fileList[i];
        var is_directory = file.isDirectory;

        var checkboxTd = "<td><input type='checkbox'/></td>";
        if(is_directory == true){
            var fileNameTd = "<td class='fileNameTd' id='"+ file.fileId + "'" + "path='" + file.path.replace('\\' + file.owner,'') + file.name+ '\\' +"'>" + "<div>" + "<img class='icon-folder-and-file' src='static/image/icon/icon_folder.png' alt='folder'/>" + " <a fileType='folder' href='javascript:;'>" + file.name + "</a>" + "</div>" + "</td>";
        }else{
            var fileNameTd = "<td class='fileNameTd' id='"+ file.fileId + "'" + "path='" + file.path.replace('\\' + file.owner,'') + file.name +"'>" + "<div>" + "<img class='icon-folder-and-file' src='static/image/icon/icon_file.png' alt='file'/>" + " <a fileType='file' href='javascript:;'>" + file.name + "</a>" + "</div>" + "</td>";
        }
        var createTimeTd = "<td>" + file.createTime + "</td>";
        if(is_directory == true){
            var sizeTd = "<td>" + "-" + "</td>";
        }else{
            var sizeTd = "<td>" + fileUnitConversion(file.size) + "</td>";
        }
        var downloadBtn = "<button id='fileDownloadBtn' type='button' title='下载' class='btn btn-primary btn-xs'><i class='glyphicon glyphicon-download-alt'></i></button>";
        var moveBtn = "<button id='fileMoveBtn' type='button' title='移动' class='btn btn-primary btn-xs'><i class='glyphicon glyphicon-move'></i></button>";
        var deleteBtn = "<button id='fileDeleteBtn' type='button' title='删除' class='btn btn-danger btn-xs'><i class='glyphicon glyphicon-trash'></i></button>";

        var buttonTd = "<td>" + downloadBtn + " " + moveBtn + " " + deleteBtn + "</td>";
        var rowData = "<tr>" + checkboxTd + fileNameTd + createTimeTd + sizeTd + buttonTd + "</tr>";
        $("#filePageBody").append(rowData);
    }
}

// 填充路径导航栏
function fillPathNavigationBar(){
    var path = window.currentPath;
    // 清空路径导航栏
    $("#filePathNavigationBar").empty();

    if(path == "\\"){
        $("#filePathNavigationBar").append("<li class='active'>首页</li>");
    }
    else{
        $("#filePathNavigationBar").append("<li path='\\'><a href='javascript:;'>首页</a></li>");
        var splitPath = path.split("\\");
        splitPath.pop();  // 删除分割的数组最后一个""空元素
        splitPath.shift();  // 删除分割的数组第一个""空元素
        // console.log(splitPath);
        if(splitPath.length <= 5){
            for(var i=0;i<splitPath.length;i++){
                if(splitPath[i] != ""){
                    if(i==splitPath.length-1){
                        $("#filePathNavigationBar").append("<li class='active'>"+ splitPath[i] +"</li>");
                    }else{
                        var pathAttrValue = joinPath(splitPath.slice(0,i+1));
                        $("#filePathNavigationBar").append("<li path='" + pathAttrValue + "'><a href='javascript:;'>"+ splitPath[i] +"</a></li>");
                    }
                }
            }
        }else{
            $("#filePathNavigationBar").append("<li>...</li>");
            for(var j=splitPath.length-3;j<splitPath.length;j++){
                if(splitPath[j] != ""){
                    if(j==splitPath.length-1){
                        $("#filePathNavigationBar").append("<li class='active'>"+ splitPath[j] +"</li>");
                    }else{
                        var pathAttrValue = joinPath(splitPath.slice(0,j+1));
                        $("#filePathNavigationBar").append("<li path='" + pathAttrValue + "'><a href='javascript:;'>"+ splitPath[j] +"</a></li>");
                    }
                }
            }
        }
    }
}

// 根据分割的路径列表拼接成完整的路径
function joinPath(splitPath){
    var path = splitPath.join("\\");
    return "\\"+path+"\\";

}

// 调用函数绑定各种事件
function evenBinding(){
    createFolderClick();
    createFolderModalCreateButtonClick();
    folderNameClick();
    filePathNavigationBarClick();
    fileUploadClick();
    fileUploadModalUploadButtonClick();
    selectAll();
    selectOne();
    filesDownloadButtonClick();
    fileDownloadButtonClick();
    moveButtonClick();
    fileMoveToButtonClick();
    deleteButtonClick();
    fileDeleteConfirmButtonClick();
    fileBatchDeleteButtonClick();
}

// 创建文件夹按钮点击事件
function createFolderClick(){
    $("#createFolderBtn").click(function(){
        // 清空模态框输入框中的内容
        $("#createFolderInput").val("");

        // 获取当前路径，绑定到创建文件夹模态框中的创建按钮上
        var path = window.currentPath;
        $("#createFolderModalCreateButton").attr("path",path);
        // 获取当前路径的id，也绑定到创建文件夹模态框中的创建按钮上
        var fileId = window.currentFileId;
        $("#createFolderModalCreateButton").attr("fileId",fileId);

       // 打开新建文件夹模态框
       $("#createFolderModal").modal("show");
    });
}

// 创建文件夹模态框创建按钮点击事件
function createFolderModalCreateButtonClick(){
    $("#createFolderModalCreateButton").click(function(){
        // 获取绑定在按钮上的当前目录路径及当前目录的fileId，当前目录的fileId作为创建目录的parentId
        var path = $("#createFolderModalCreateButton").attr("path");
        var fileId = $("#createFolderModalCreateButton").attr("fileId");
        // 获取目录名
        var name = $("#createFolderInput").val();

        $.ajax({
            "url":"file/create/folder",
            "type":"post",
            "data":{
                "name":name,
                "path":path,
                "parentId":fileId
            },
            "dataType":"json",
            "success":function(response){
                var code = response.code;
                if(code == "0"){
                    layer.msg("创建成功");
                    getPathFilesByPath(path);

                    // 关闭新建文件夹模态框
                    $("#createFolderModal").modal("hide");
                    // console.log(window.currentPath);
                }
                if(code == "1"){
                    layer.msg(response.msg);
                }
            },
            "error":function(error){
                layer.msg(error.status+" "+error.statusText);
            }
        })
    });
}

// 目录名中<a>标签点击事件
function folderNameClick(){
    $("#filePageBody").on("click","tr div a[fileType='folder']", function(){
        // 获取所点击的目录的路径
        window.currentPath = $(this).parent().parent().attr("path");
        window.currentFileId = $(this).parent().parent().attr("id");
        window.pathAndIdMap.set(window.currentPath,window.currentFileId);
        getPathFilesByPath(window.currentPath);
    });
}

// 路径导航栏绑定点击事件
function filePathNavigationBarClick(){
    $("#filePathNavigationBar").on("click","li a", function(){
        var currentClickPath = $(this).parent().attr("path");
        window.currentPath = currentClickPath;
        window.currentFileId = window.pathAndIdMap.get(currentClickPath);

        // 重设window.pathAndIdMap里路径的访问次序
        let newPathAndIdMap = new Map();
        if(currentClickPath == "\\"){
            newPathAndIdMap.set(currentClickPath,window.pathAndIdMap.get(currentClickPath));
            window.pathAndIdMap = newPathAndIdMap;
        }else{
            for(let item of window.pathAndIdMap){
                if(item[0] == currentClickPath){
                    newPathAndIdMap.set(item[0], item[1]);
                    window.pathAndIdMap = newPathAndIdMap;
                    break;
                }else{
                    newPathAndIdMap.set(item[0], item[1]);
                }
            }
        }
        getPathFilesByPath(window.currentPath);
    });
}

// 文件上传按钮点击事件
function fileUploadClick(){
    $("#fileUploadBtn").click(function(){
        // 重置表单内容
        $("#fileUploadForm")[0].reset();

        // 获取当前路径，绑定到文件上传模态框中的上传按钮上
        $("#fileUploadModalUploadButton").attr("path",window.currentPath);

        // 显示模态框
        $("#fileUploadModal").modal("show");
    });
}

// 文件上传模态框上传按钮点击事件
function fileUploadModalUploadButtonClick(){
    $("#fileUploadModalUploadButton").click(function(){
        var files = $("#fileUploadInput").prop("files");
        var currentPath = $("#fileUploadModalUploadButton").attr("path");
        var currentPathId = window.pathAndIdMap.get(currentPath);
        // console.log(files);
        // console.log(files.length);

        // 判断是否有选择文件
        if(files.length <= 0){
            layer.msg("请选择要上传的文件");
            return;
        }

        // 判断所选择的文件的总大小
        var sumSize = 0;
        for(var i=0;i<files.length;i++){
            sumSize += files[i].size;
        }
        if (sumSize >= 4294967296){
            layer.msg("当前文件总大小："+fileUnitConversion(sumSize)+" ,请上传大小在4GB以下的文件");
            return;
        }

        // 方式二：一个请求发送所有文件
        var formData = new FormData();
        for(var i=0;i<files.length;i++){
            formData.append("multipartFiles",files[i]);
        }
        formData.append("path", currentPath);
        formData.append("parentId", currentPathId);
        sendFile(formData);

        // //方式一：这种方式会发送多次请求，后台会创建多个线程去处理。
        // // 循环创建formData对象
        // for(var i=0; i<files.length;i++){
        //     var formData = new FormData();
        //     // 把单个文件信息添加到formData中
        //     formData.append("multipartFile",files[i]);
        //     formData.append("path",currentPath);
        //     formData.append("parentId", currentPathId);
        //     // 调用发送文件方法
        //     sendFile(formData);
        // }
    });
}

// 发送文件方法
function sendFile(formData){
    $.ajax({
        "url":"file/upload",
        "type":"post",
        "data":formData,  // 一次请求只能发送一个文件
        // 默认为true，上传的数据以字符串形式上传，当上传文件时不需要转换为字符串，所以改为false
        "dataType":"json",
        "processData":false,
        // "traditional":true,
        // 表示前端发送数据的格式
        // 默认是以字符串的形式 如 id=2019 & password=123456
        // 无法传递复杂数据，所以改为false
        "contentType":false,
        "success":function(response){
            var code = response.code;
            if(code == "0"){
                layer.msg("上传成功");
                // 关闭上传模态框
                $("#fileUploadModal").modal("hide");
                // 重新刷新页面上的当前目录
                getPathFilesByPath(window.currentPath);
            }
            if(code == "1"){
                layer.msg(response.msg);
            }
        },
        "error":function(error){
            layer.msg(error.status+" "+error.statusText);
        }
    });
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

// checkbox全选事件
function selectAll(){
    $("#fileInfoTable thead input").click(function(){
        $("#filePageBody input[type='checkbox']").prop("checked",$("#fileInfoTable thead input[type='checkbox']").prop("checked"));
    });
}

// 给动态生成的checkbox添加点击事件
function selectOne(){
    $("#fileInfoTable").on("click","#filePageBody input[type='checkbox']",function(){
        if($("#filePageBody input[type='checkbox']:checked").size() == $("#filePageBody input[type='checkbox']").size()){
            $("#fileInfoTable thead input").prop("checked", true);
        }else{
            $("#fileInfoTable thead input").prop("checked", false);
        }
    });
}

// 文件下载按钮点击事件
function filesDownloadButtonClick(){
    $("#filesDownloadBtn").click(function(){
        // 获取文件路径
        var checkedElement = $("#filePageBody input[type='checkbox']:checked");
        // console.log(checkedElement);

        if(checkedElement.length < 1){
            layer.msg("请选择要下载的文件");
            return;
        }

        var paths = [];
        checkedElement.parent().parent().find(".fileNameTd").each(function(){
            paths.push($(this).attr("path"));
            // console.log($(this).attr("path"));
        })

        // 调用函数，发送下载请求
        sendDownloadRequest(paths);
        // // 先发送请求查询服务器中文件是否存在，存在的情况下用表单的方式发送下载请求。
        // $.ajax({
        //     "url":"file/checked",
        //     "type":"post",
        //     "traditional":true,
        //     "data":{
        //         "paths":paths,
        //     },
        //     "dataType":"json",
        //     "success":function(response){
        //         var code = response.code;
        //         if(code == "0"){
        //             // 动态生成form，发送下载请求
        //             var form = $('<form id="downFileForm" method="POST" action="file/download" target="_blank">');  // 创建新页面下载，然后自动关闭新页面
        //             // var form = $('<form method="POST" action="file/download">');  // 不创建新页面进行下载
        //             form.append($('<input type="hidden" name="paths" value="'+ paths +'">'));
        //             $('body').append(form);
        //             form.submit();
        //             $("#downFileForm").remove();  // 移除下载临时用的表单
        //         }
        //         if(code == "1"){
        //             layer.msg(response.msg);
        //         }
        //     },
        //     "error":function(error){
        //         layer.msg(error.status+" "+error.statusText);
        //     }
        // })
    })
}

// 每一行中的文件下载按钮点击事件
function fileDownloadButtonClick(){
    $("#filePageBody").on("click","#fileDownloadBtn",function(){
        var currentClickRowElement = $(this).parent().parent();
        var path = currentClickRowElement.find(".fileNameTd").attr("path");
        // 封装路径进路径数组中
        var paths = [];
        paths.push(path);

        // 调用函数，发送下载请求
        sendDownloadRequest(paths);
    })
}

// 发送下载请求函数
function sendDownloadRequest(paths){
    // 先发送请求查询服务器中文件是否存在，存在的情况下用表单的方式发送下载请求。
    $.ajax({
        "url":"file/checked",
        "type":"post",
        "traditional":true,
        "data":{
            "paths":paths,
        },
        "dataType":"json",
        "success":function(response){
            var code = response.code;
            if(code == "0"){
                // 动态生成form，发送下载请求
                var form = $('<form id="downFileForm" method="POST" action="file/download" target="_blank">');  // 创建新页面下载，然后自动关闭新页面
                // var form = $('<form method="POST" action="file/download">');  // 不创建新页面进行下载
                form.append($('<input type="hidden" name="paths" value="'+ paths +'">'));
                $('body').append(form);
                form.submit();
                $("#downFileForm").remove();  // 移除下载临时用的表单
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

// 文件移动按钮点击事件
function moveButtonClick(){
    $("#filePageBody").on("click","#fileMoveBtn",function(){
        // 清空文件移动模态框
        $("#folderUL").empty();

        // 获取当前点击行文件的id和路径以及文件类型，绑定到移动文件模态框的“移动到此”按钮上,还要绑定当前文件所在目录的路径上去
        var currentFileId = $(this).parent().parent().find(".fileNameTd").prop("id");
        var currentFilePath = $(this).parent().parent().find(".fileNameTd").attr("path");
        var currentFileType = $(this).parent().parent().find("a").attr("fileType");
        var currentFileParentPath = window.currentPath;
        $("#fileMoveToButton").attr("fileId",currentFileId);
        $("#fileMoveToButton").attr("path",currentFilePath);
        $("#fileMoveToButton").attr("parentPath", currentFileParentPath);
        $("#fileMoveToButton").attr("fileType", currentFileType);

        // 发送请求，查询用户的所有文件夹，并按创建时间倒序显示在模态框上
        $.ajax({
            "url":"/file/folders",
            "type":"post",
            "dataType":"json",
            "success":function(response){
                var code = response.code;
                if(code == "0"){
                    var data = response.data;
                    fillFileMoveModal(data);
                    // console.log(data);
                    // 显示文件移动模态框
                    $("#fileMoveModal").modal("show");
                }
                if(code == "1"){
                    layer.msg(response.msg);
                }
            },
            "error":function(error){
                layer.msg(error.status+" "+error.statusText);
            }
        })
    })
}

// 填充文件移动模态框内容
function fillFileMoveModal(folders){
    for(var i=0;i<folders.length;i++){
        if(folders[i].path == "\\"){
            var li = "<li class='list-group-item' fileId='"+ folders[i].fileId +"' title='"+ folders[i].path +"'><input type='radio' name='folder'/> HOME</li>"
        }else{
            var li = "<li class='list-group-item' fileId='"+ folders[i].fileId +"' title='"+ folders[i].path.replace('\\' + folders[i].owner,'') + folders[i].name+ '\\' +"'><input type='radio' name='folder'/> " + folders[i].name + "</li>"
        }
        $("#folderUL").append(li);
    }
}

// 移动模态框的确认移动按钮点击事件
function fileMoveToButtonClick(){
    $("#fileMoveToButton").click(function(){
        // 获取需要移动的文件的信息
        var moveToButtonElement = $("#fileMoveToButton")
        var fileId = moveToButtonElement.attr("fileId");
        var path = moveToButtonElement.attr("path");
        var fileType = moveToButtonElement.attr("fileType");
        var parentPath = moveToButtonElement.attr("parentPath");

        // 获取目标目录的信息
        var checkedElement = $("#folderUL li input:checked")
        var toFileId = checkedElement.parent().attr("fileId");
        var toPath = checkedElement.parent().prop("title");

        // 发送移动请求
        $.ajax({
            "url":"file/move",
            "type":"post",
            "data":{
                "fileId":fileId,
                "path":path,
                "fileType":fileType,
                "parentPath":parentPath,
                "toFileId":toFileId,
                "toPath":toPath
            },
            "dataType":"json",
            "success":function(response){
                var code = response.code;
                if(code == "0"){
                    layer.msg("移动成功");
                    // 重新刷新页面上的当前目录
                    getPathFilesByPath(window.currentPath);
                    // 关闭移动模态框
                    $("#fileMoveModal").modal("hide");

                }
                if(code == "1"){
                    layer.msg(response.msg);
                }
            },
            "error":function(error){
                layer.msg(error.status+" "+error.statusText);
            }
        })
    })
}

// 文件删除按钮点击事件
function deleteButtonClick(){
    $("#filePageBody").on("click","#fileDeleteBtn",function(){
        // 获取文件相关参数，绑定到删除确认模态框中
        var currentRowElement = $(this).parent().parent();
        var fileId = currentRowElement.find(".fileNameTd").prop("id");

        var fileIds = [];
        fileIds.push(fileId);

        var fileDeleteConfirmBtnElement = $("#fileDeleteConfirmBtn");
        fileDeleteConfirmBtnElement.attr("fileIds",fileIds);

        // 弹出删除确认模态框
        $("#fileDeleteConfirmModal").modal("show");
    });
}

// 文件删除模态框中的删除按钮点击事件
function fileDeleteConfirmButtonClick(){
    $("#fileDeleteConfirmBtn").click(function(){
        // 获取fileIds数组
        var fileIds = $("#fileDeleteConfirmBtn").attr("fileIds");

       $.ajax({
           "url":"file/delete",
           "type":"post",
           "traditional":true,
           "data":{
               "fileIds":fileIds,
           },
           "dataType":"json",
           "success":function(response){
               var code = response.code;
               if(code == "0"){
                   layer.msg("删除成功");

                   // 关闭删除确认模态框
                   $("#fileDeleteConfirmModal").modal("hide");
                   // 重新刷新页面上的当前目录
                   getPathFilesByPath(window.currentPath);
               }
               if(code == "1"){
                   layer.msg(response.msg);
               }
           },
           "error":function(error){
               layer.msg(error.status+" "+error.statusText);
           }
       })
    });
}

// 批量删除按钮点击事件
function fileBatchDeleteButtonClick(){
    $("#fileBatchDeleteBtn").click(function(){
        // 获取选中的文件的fileId
        var checkedElement = $("#filePageBody input[type='checkbox']:checked");
        if(checkedElement.length == 0){
            layer.msg("请选择需要删除的文件");
            return;
        }

        // 获取选择的文件的fileId封装成数组
        var fileIds = [];
        checkedElement.parent().parent().find(".fileNameTd").each(function(){
            fileIds.push($(this).prop("id"));
        })

        // 将fileIds数组绑定到删除确认模态框的删除按钮上
        $("#fileDeleteConfirmBtn").attr("fileIds", fileIds);

        // 弹出删除确认模态框
        $("#fileDeleteConfirmModal").modal("show");
    });
}