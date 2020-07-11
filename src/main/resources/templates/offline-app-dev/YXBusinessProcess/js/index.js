(function($) {
	uap.button("#nav-left", "btn-act", function() {
	});
	uap.button("#nav-right", "btn-act", function() {
	});

	uap.ready(function() {
//		$("#btnSubmit").click(function() {
//			window.location.href = "./userList.html";
//		});
		// 设置展示的版本号
		$("#apkVersion").html("版本号:" + apkVersion);

		$("#Page").trigger("reload", this);
		/**
		 * 升级提示
		 */
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

		// 确认更新按钮
		$("#updateSubmit").on('click', function(event) {
			beihai365_app_update();
		});
		// 取消按钮
		$("#updateCancel").on('click', function(event) {
			// alert("delCancel");
		});

		/**
		 * 密码保存
		 */
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

	});
	
	// 登录
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
						window.location.href = "./businessSearch.html";
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
