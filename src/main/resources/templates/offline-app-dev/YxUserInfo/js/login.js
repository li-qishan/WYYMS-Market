(function($) {
	uap.button("#nav-left", "btn-act", function() {
	});
	uap.button("#nav-right", "btn-act", function() {
	});

	uap.ready(function() {
		$("#Page").trigger("reload", this);

		$("#apkVersion").html("版本号:" + apkVersion);

		// 遮罩层隐藏
		document.onclick = function(event) {
			var mask = document.getElementById("mask");
			event = event || window.event;
			// 兼容获取触动事件时被传递过来的对象
			var aaa = event.target ? event.target : event.srcElement;
			if (aaa.id !== "delConfirm") {
				mask.style.display = "none";
			}
		}
		
		if (isFileExist()) {
			readFile();
			// 读取文件回调函数，赋值输入框
			uexFileMgr.cbReadFile = function(opCode, dataType, data) {
				var array = data.split(",");
				document.getElementById("switchBox").checked = array[0];// 是否保存密码
				if (array[1] != "" && array[1] != null
						&& array[1] != undefined) {
					document.getElementById("uid").value = array[1];// 用户名
				}
				if (array[2] != "" && array[2] != null
						&& array[2] != undefined) {
					document.getElementById("upwd").value = array[2];// 密码
				}
			}
		} else {
			createFile();
		}

		// 确认更新按钮
		$("#updateSubmit").on('click', function(event) {
			beihai365_app_update();
		});
		// 取消按钮
		$("#updateCancel").on('click', function(event) {
			// alert("delCancel");
		});
	});

	uap.button("#submit", "ani-act", function() {
		var name = $("#uid").val();
		var pwd = $("#upwd").val();
		var isSavaPwd = document.getElementById("switchBox").checked;
		if (name == "") {
			uap.window.openToast('账号不能为空', '1000');
			return;
		} else if (pwd == "") {
			uap.window.openToast('密码不能为空', '1000');
			return;
		} else {
			// $('form').attr('action',httpUrl+'/user/login/loginValidate');
			uap.request.ajax({
				url : httpUrl + 'user/login/loginValidate',
				type : "POST",
				data : {
					"userName" : name,
					"password" : pwd
				},
				dataType : "json",
				contentType : "application/x-www-form-urlencoded",
				success : function(data) {
					console.log(data);
					uap.window.openToast(data.msg, '2000');
					if (data.code != 1) {
						return;
					} else {
						var deptNo = data.data.deptNo;
						window.location.href = "./userSearch.html";
						uap.locStorage.setVal('deptNo', deptNo);
						uap.locStorage.setVal('userName', name);

						// 是否保存密码
						if (isSavaPwd) {
							// 文件写入
							writeFile(isSavaPwd, name, pwd);
						} else {
							writeFile(isSavaPwd, "", "");
						}
					}
				},
				error : function(e, err) {
					alert("error:" + JSON.stringify(e))
					option.error(self.doerror(e, err, option));
				}
			});
		}
	})

	$("form").on('submit', function() {
		uap.request.postForm($("form"), function() {
			// uap.execScriptInWin('login','closeLogin()');
			uap.window.alert({
				title : "提醒",
				content : "您已经提交了表单:)",
				buttons : '确定',
				callback : function(err, data, dataType, optId) {
					console.log(err, data, dataType, optId);
				}
			});
		}, function(err) {

		});
		return false;
	});

})($);

function hideShowPsw() {
	var guiImg = document.getElementById("gui_img");
	var guiInput = document.getElementById("upwd");

	if (guiInput.type == "password") {
		guiInput.type = "text";
		guiImg.src = "/static/mobile/img/icon-s.png";
	} else {
		guiInput.type = "password";
		guiImg.src = "/static/mobile/img/icon-h.png";
	}
}
