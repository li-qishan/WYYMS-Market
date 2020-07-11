/**
 * Created by SERENA on 2018/7/31.
 */
$(function () {
    var button = $('#loginButton');
    var box = $('#loginBox');
    var form = $('#loginForm');
    button.removeAttr('href');
    button.mouseup(function (login) {
        box.toggle();
        button.toggleClass('active');
    });
    form.mouseup(function () {
        return false;
    });
    $(this).mouseup(function (login) {
        if (!($(login.target).parent('#loginButton').length > 0)) {
            button.removeClass('active');
            box.hide();
        }
    });

    setLoginDiv();
});

var PageAuthorityInit = {
    login: function () {
        var username = $("#username").val();//取框中的用户名
        var password = $("#password").val();//取框中的密码
        $.ajax({
            type: "POST",
            async: false,
            url: "login",
            data: {username: username, password: password},
            dataType: "json",
            success: function (result) {
                if (result.code == "0") {
                    document.getElementById("refresh").click();
                } else {
                    alert(result.msg);
                }
            },
            error: function (result) {
                document.getElementById("refresh").click();
            }
        });
    }
}
