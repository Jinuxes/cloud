function personalInformation(){
    $("#personalInformationBtn").click(function(){
        // 个人信息直接从session中获取了，不发请求。不过要重置表单
        $.ajax({
            "url":"personal/info",
            "type":"post",
            "dataType":"json",
            "success":function(response){
                var code = response.code;
                if(code == "0"){
                    // 回显数据到模态框
                    var data = response.data;
                    $("#personalInformationNameInput").val(data.userName);
                    $("#personalInformationEmailInput").val(data.email);

                }
                if(code == "1"){
                    layer.msg(response.msg);
                }
            },
            "error":function(error){
                layer.msg(error.status+" "+error.statusText);
            }
        })
        // $("#personalInformationForm")[0].reset();
        $("#personalInformationModal").modal("show");
    });
}

function savePersonalInformation(){
    $("#personalInformationSaveButton").click(function(){
        // 收集表单参数
        var userName = $("#personalInformationNameInput").val();
        var email = $("#personalInformationEmailInput").val();

        // 发送ajax请求
        $.ajax({
            "url":"personal/info/update",
            "type":"post",
            "data":{
                "userName":userName,
                "email":email
            },
            "dataType":"json",
            "success":function(response){
                var code = response.code;
                if(code == "0"){
                    layer.msg("修改成功");
                    $("#personalInformationModal").modal("hide");
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

function updatePassword(){
    $("#updatePasswordBtn").click(function(){
        // 重置表单，显示模态框
        $("#updatePasswordForm")[0].reset();
        $("#updatePasswordModal").modal("show");
    });
}

function saveUpdatePassword(){
    $("#updatePasswordSaveButton").click(function(){
        var password = $("#updatePasswordInput").val();
        var passwordConfirm = $("#updatePasswordConfirmInput").val();
        if(password != passwordConfirm){
            layer.msg("前后密码输入不一致");
            return;
        }else{
            $.ajax({
                "url":"personal/password/update",
                "type":"post",
                "data":{
                    "password":password
                },
                "dataType":"json",
                "success":function(response){
                    var code = response.code;
                    if(code == "0"){
                        $("#updatePasswordModal").modal("hide");
                        layer.msg("修改成功,请重新登录！",{time:1000},function(){
                            window.location.href="logout";
                        });
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

    });
}