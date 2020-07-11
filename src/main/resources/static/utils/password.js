function validatelogin() {
    var obj = $('#loginButton span');
    var val = obj.text();
    if(val!=null&&val!='登录')
    {
        modify();
    }
    else
    {
        alert('请先登录系统，再进行密码修改!');
    }
}



function modify() {

    layer.open({
        id:1,
        type: 1,
        title:'修改密码',
        skin:'layui-layer-rim',
        area:['450px', 'auto'],
        content: ' <div class="row" style="width: 420px;  margin-left:7px; margin-top:10px;">'
        +'<div class="col-sm-12">'
        +'<div class="input-group">'
        +'<span class="input-group-addon"> 旧 密 码   :</span>'
        +'<input id="firstpwd" type="password" class="form-control" placeholder="请输入密码">'
        +'</div>'
        +'</div>'
        +'<div class="col-sm-12" style="margin-top: 10px">'
        +'<div class="input-group">'
        +'<span class="input-group-addon">新 密 码   :</span>'
        +'<input id="saapwd" type="password" class="form-control" placeholder="请输入密码">'
        +'</div>'
        +'</div>'
        +'<div class="col-sm-12" style="margin-top: 10px">'
        +'<div class="input-group">'
        +'<span class="input-group-addon">确认密码:</span>'
        +'<input id="secondpwd" type="password" class="form-control" placeholder="请再输入一次密码">'
        +'</div>'
        +'</div>'
        +'</div>'
        ,
        btn:['保存','取消'],
        btn1: function (index,layero) {
           var a =  $('#saapwd').val();
           var b =  $('#secondpwd').val();

            if($('#saapwd').val() == $('#secondpwd').val() ){
                $.ajax({
                    url : "/sys/user/password",
                    type : "post",
                    async : false,
                    dataType : "json",
                    data : {"password" : $('#firstpwd').val(),
                        "newPassword": $('#saapwd').val()
                    },
                    success : function(result) {

                        if( result.msg == "success"){
                            alert("修改成功");
                        }else {
                            alert(result.msg);
                        }
                    },
                    error : function(error) {

                    }
                });
            }else {
                alert("两次输入不一致");
            }

        },
        btn2:function (index,layero) {
            layer.close(index);
        }

    });
}