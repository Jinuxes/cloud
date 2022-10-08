// 注册按钮点击事件
function registerClick(){
    $("#registerBtn").click(function(){
        // 收集表单参数
        var account = $("input[name='account']").val();
        var password = $("input[name='password']").val();
        var passwordConfirmation = $("input[name='passwordConfirmation']").val();
        var userName = $("input[name='userName']").val();
        var email = $("input[name='email']").val();

        // 判断两次输入的密码是否一致
        if(account == null || account == ""){
            layer.msg("账号不能为空");
            return;
        }if(passwordConfirmation == null || passwordConfirmation == ""){
            layer.msg("确认密码不能为空");
            return;
        }if(userName == null || userName == ""){
            layer.msg("用户名不能为空");
            return;
        }if(email == null || email == ""){
            layer.msg("邮箱不能为空");
            return;
        }if(password != passwordConfirmation){
            layer.msg("两次密码输入不一致");
            return;
        }
        // 发送登录请求
        $.ajax({
            "url":"register/save",
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
                    layer.msg("注册成功",{time:1000},function (){
                        window.location.href="login/page";
                    });
                }
                if(code == "1"){
                    layer.msg(response.msg);
                }
            },
            "error":function(error){
                layer.msg(error.status+" "+error.statusText);
            }
        });
    });
}