// 获取容量信息，渲染页面上的存储容量显示
function getCapacity(){
    $.ajax({
        "url":"file/capacity",
        "type":"post",
        "dataType":"json",
        "success":function(response){
            var code = response.code;
            if(code == "0"){
                var data = response.data;
                // console.log(data);

                var used = fileUnitConversion(data.usedCapacity);
                var total = fileUnitConversion(data.totalCapacity);
                var userRate = ((data.usedCapacity / data.totalCapacity) * 100).toFixed(2);
                var userRateString = userRate + "%";
                // $("#storageCapacityLabel").text("容量："+used+"/"+total+"[已使用："+userRate+"]");

                // 移除进度条class
                $("#storageCapacityBar").removeClass("progress-bar-success");
                $("#storageCapacityBar").removeClass("progress-bar-info");
                $("#storageCapacityBar").removeClass("progress-bar-warning");
                $("#storageCapacityBar").removeClass("progress-bar-danger");
                // 剩余容量不同，进度条颜色不同
                if(userRate < 25){
                    $("#storageCapacityBar").addClass("progress-bar-success");
                }else if(userRate < 50 && userRate >= 25){
                    $("#storageCapacityBar").addClass("progress-bar-info");
                }else if(userRate < 75 && userRate >= 50){
                    $("#storageCapacityBar").addClass("progress-bar-warning");
                }else if(userRate < 100 && userRate >= 75){
                    $("#storageCapacityBar").addClass("progress-bar-danger");
                }

                $("#storageCapacityLabel").text("容量："+used+"/"+total);
                $("#storageCapacityBar").prop("style","width:"+userRateString+";");
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