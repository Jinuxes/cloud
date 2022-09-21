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
        var createTimeTd = "<td>" + file.createTime + "</td>";
        if(is_directory == true){
            var sizeTd = "<td>" + "-" + "</td>";
        }else{
            var sizeTd = "<td>" + fileUnitConversion(file.size) + "</td>";
        }
        var recoveryBtn = "<button id='fileRecoveryBtn' type='button' title='恢复' class='btn btn-primary btn-xs'><i class='glyphicon glyphicon-repeat'></i></button>";
        var deleteBtn = "<button id='fileDeleteBtn' type='button' title='删除' class='btn btn-danger btn-xs'><i class='glyphicon glyphicon-trash'></i></button>";

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