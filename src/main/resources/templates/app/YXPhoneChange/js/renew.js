var vm = new Vue({
    el: '#CustomListView_4sKwrQ',
    data: {
        //后台请求数据集
        dataList: []

    },
    computed: {},
    mounted: function () {
        var _ = this;
        //初始化事件
        _.initEvents();
        _.getBasicData();


    },
    methods: {

    	getBasicData:function () {
            // 供电单位编号
            var deptNo = sessionStorage.getItem('YXPC-deptNo');
            // 联系人类型
            var index = "";
            // 操作用户
            var name = sessionStorage.getItem('YXPC-userName');
            var _ = this;
            // 户号
            var consNo = sessionStorage.getItem('YXPC-consNo');
            // var consNo = sessionStorage.getParams("consNo");
            // 点击联系人
            var clickHtml = "";
            $(".active").each(function() {
                clickHtml = $(this).find(".phone").html();
                if (clickHtml.indexOf("电气联系人") != -1) {
                    index = "01";
                } else if (clickHtml.indexOf("账务联系人") != -1) {
                    index = "02";
                } else if (clickHtml.indexOf("停送电联系人") != -1) {
                    index = "03";
                }
            });
            sessionStorage.setItem('index', index);
            // debugger
            $.ajax({
                url : "/phone/getUserDetail",
                type : "POST",
                dataType : "json",
                data : {
                    "consNo" : consNo,
                    "index" : index,
                    "userName": name,
                    "deptNo": deptNo
                },
                contentType : "application/x-www-form-urlencoded",
                success : function(data) {
                	//debugger
//				 alert("success:"+JSON.stringify(data.data));
                    $("#user_consName").html(data.data.userInfo.CONS_NAME);
                    $("#user_consNo").html(data.data.userInfo.CONS_NO);
                    $("#user_code").html(data.data.userInfo.CUST_QUERY_NO);
                    $("#user_type").html(data.data.userInfo.ELEC_TYPE_CODE);
                    $("#user_addr").html(data.data.userInfo.ELEC_ADDR);
                    $("#org_name").html(data.data.userInfo.ORG_NAME);

                    if (data.data.userPhones.length > 0) {
                        _.dataList=data.data.userPhones;
                        // id重复取消
                        _.setId();

                        // 账务联系人和停送电联系人不显示催费短信选择框
                        if (index == "02" || index == "03") {
                            $(".selectBox").addClass("visibility-hidden");
                        } else {
                            $(".selectBox").addClass("visibility");
                        }
                    } else {
                        alert("该用户无" + clickHtml);
                     
                        _.dataList=data.data.userPhones;
                    }

                },
                error : function(e, err) {
                   // option.error(self.doerror(e, err, option));
                }
            });

        },


        // 事件提交方法
        commitOpera: function (operType, isSign, mobile) {

            // 用户名 sessionStorage.getItem('clickHtml');
            var consName = sessionStorage.getItem('YXPC-consName');
            // 供电单位编号
            var deptNo = sessionStorage.getItem('YXPC-deptNo');
            // 联系人类型
            var index = sessionStorage.getItem('YXPC-index');
            // 操作人
            var name = sessionStorage.getItem('YXPC-userName');
            // 操作户号
            var consNo = sessionStorage.getItem('YXPC-consNo');
            // 获取操作手机号
            var phone = "";
            var contactId = "";
            $("input[name='check']:checked").each(function() {
                var lineData = $(this).parent().parent().find("#mobile");
                phone = lineData.html();
                var contactData = $(this).parent().parent().find("#contactIdHid");
                contactId = contactData.html();
                return false;
            });

            // 设置催费信息时，设置phone为mobile
            if (operType == "2") {
                phone = mobile;
            }
            $.ajax({
                url : "/phone/userPhoneOpera",
                type : "POST",
                data : {
                    "consNo" : consNo,
                    "consName" : consName,
                    "userName" : name,
                    "index" : index,
                    "operType" : operType,// 0新增，1删除，2更新
                    "isSign" : isSign,// 0否,1是
                    "oldPhone" : mobile,
                    "phone" : phone,
                    "deptNo" : deptNo,
                    "contactId" : contactId
                },
                dataType : "json",
                contentType : "application/x-www-form-urlencoded",
                success : function(data) {
                    if (data.code == 1) {

                        // 更新页面数据
                        _.getBasicData();
                    }
                },
                error : function(e, err) {
                    //option.error(self.doerror(e, err, option));
                }
            });


        },

        initEvents: function () {
            var _ = this;
            // 供电单位编号
            var deptNo = sessionStorage.getItem('YXPC-deptNo');
            // 操作用户
            var name = sessionStorage.getItem('YXPC-userName');


            $(".mobileText").on("click",
                function(e) {
                    $(".mobileText").removeClass("active");
                    $(this).addClass("active");
                    // 更新页面数据
                    // $("#CustomListView_4sKwrQ").trigger("reload", this);
                    _.getBasicData();
                    // 修改图片路径
                    var val = $(this).find(".phone").html();
                    // 点击类型
                    var clickHtml = "";
                    if (val.indexOf("电气联系人") != -1) {
                        clickHtml = "电气联系人";

                        $(".mobile").find(".user").attr("src",
                            "/app/YXPhoneChange/css/img/user_active.png");
                        $(".mobile").find(".money").attr("src",
                            "/app/YXPhoneChange/css/img/money.png");
                        $(".mobile").find(".elec").attr("src",
                            "/app/YXPhoneChange/css/img/elec.png");
                    } else if (val.indexOf("账务联系人") != -1) {
                        clickHtml = "账务联系人";

                        $(".mobile").find(".money").attr("src",
                            "/app/YXPhoneChange/css/img/money_active.png");
                        $(".mobile").find(".user").attr("src",
                            "/app/YXPhoneChange/css/img/user.png");
                        $(".mobile").find(".elec").attr("src",
                            "/app/YXPhoneChange/css/img/elec.png");
                    } else if (val.indexOf("停送电联系人") != -1) {
                        clickHtml = "停送电联系人";

                        $(".mobile").find(".elec").attr("src",
                            "/app/YXPhoneChange/css/img/elec_active.png");
                        $(".mobile").find(".user").attr("src",
                            "/app/YXPhoneChange/css/img/user.png");
                        $(".mobile").find(".money").attr("src",
                            "/app/YXPhoneChange/css/img/money.png");
                    }

                    //	uap.locStorage.setVal('clickHtml', clickHtml);
                    sessionStorage.setItem('clickHtml',clickHtml);
                });
            // 获取用户操作前选择的类型
            var clickHtml = sessionStorage.getItem('clickHtml');
            var aaa = document.getElementsByClassName('mobileText');
            // alert(clickHtml);
            if (clickHtml == null || clickHtml == "") {
                $(aaa[0]).trigger('click');
            } else {
                if (clickHtml.indexOf("电气联系人") != -1) {
                    $(aaa[0]).trigger('click');
                } else if (clickHtml.indexOf("账务联系人") != -1) {
                    $(aaa[1]).trigger('click');
                } else if (clickHtml.indexOf("停送电联系人") != -1) {
                    $(aaa[2]).trigger('click');
                }
            }

            $("#Icon").on("click", function(e) {

                sessionStorage.setItem("YXPC-bool", '1');

                // 页面跳转
                window.location.href = "/app/YXPhoneChange/userList.html";
            });

            document.onclick = function(event) {
                // 删除遮罩层隐藏
                var mask = document.getElementById("mask");
                event = event || window.event;
                // 兼容获取触动事件时被传递过来的对象
                var aaa = event.target ? event.target : event.srcElement;
                if (aaa.id !== "delConfirm") {
                    mask.style.display = "none";
                }
                // 催费遮罩层隐藏
                var urgeMask = document.getElementById("urgeMask");
                event = event || window.event;
                // 兼容获取触动事件时被传递过来的对象
                var aaa = event.target ? event.target : event.srcElement;
                if (aaa.id !== "urgeConfirm") {
                    urgeMask.style.display = "none";
                }

            }

            /**
             * 页面按钮
             */
            $("#btnAdd").on("click", function(event) {
                //debugger
                var consName =$("#user_consName").html();
                 // alert(consName);
                sessionStorage.setItem("YXPC-consName", consName);

                // window.location="/phone/gotoPhoneAddMobile";

                window.location.href = "/app/YXPhoneChange/addMobile.html";
            });

            // AddClick:function(value){
            //
            // },

            $("#btnUpdate").on("click", function(event) {
                var consName =$("#user_consName").html();
                var phone = "";
                var isSign = "";
                var i = 0;
                $("input[name='check']:checked").each(function() {
                    var lineData = $(this).parent().parent().find("#mobile");
                    phone = lineData.html();
                    i++;
                });
                sessionStorage.setItem('YXPC-oldPhone',phone);
                //alert(phone);
                //*********
                if (i == 1) {
                    window.location="/phone/gotoPhoneUpdateMobile";
                } else {
                    alert("请选择一条要操作的数据！");
                }
                //*******

            });
            $("#btnDelete").on("click", function(event) {
                var i = 0;
                $("input[name='check']:checked").each(function() {
                    i++;
                });
                if (i == 1) {
                    var mask = document.getElementById("mask");
                    mask.style.display = "block";
                    // 阻止冒泡
                    event = event || window.event;
                    if (event || event.stopPropagation()) {
                        event.stopPropagation();
                    } else {
                        event.cancelBubble = true;
                    }
                } else {
                    alert("请选择一条要操作的数据！");
                    var mask = document.getElementById("mask");
                    mask.style.display = "block";
                    // 阻止冒泡
                    event = event || window.event;
                    if (event || event.stopPropagation()) {
                        event.stopPropagation();
                    } else {
                        event.cancelBubble = true;
                    }
                }

            });

            /**
             * 删除弹出框按钮
             */
            // 确认按钮
            $("#delSubmit").on("click", function(event) {
                var _ = this;
                //alert("del")
                _.commitOpera("1", "0", "####");
            });
            // 取消按钮
            $("#delCancel").on("click", function(event) {
            });

            /**
             * 催费弹出框按钮
             */
            // 确认按钮
            $("#urgeSubmit").on("click", function(event) {
                var _ = this;
                // 设置催费短信
                _.commitOpera("2", "1", checkedMobile);
            });
            // 取消按钮
            $("#urgeCancel").on("click", function(event) {
                checkedBox.checked = false;
            });






        },

        //获取上一个页面携带的参数
        getParams: function (key) {
            var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) {
                return decodeURI(r[2]);
            }
            return null;
        },

		//设置Id
        setId: function () {
    	// 这是多选框的id方便选中
    	$("li").each(function() {
        var checkBox = $(this).find(".checked");
        var label = $(this).find(".label");
        // 用户号
        var consNo = $(this).find(".consNo").html();
        var index = $(this).index();
        checkBox.attr('id', 'checkBox' + index);
        // 设置多选框值为用户号
        checkBox.html(consNo);
        label.attr('for', 'checkBox' + index);

        // 如果号码为座机号，不可设置为催费短信，隐藏选择框
        var mobile = $(this).find("#mobile").html();
        // 正则验证固定号码
        var result = mobile.match(/\d{3}-\d{8}|\d{4}-\d{7}/);
        if (result == null){
            //	return false;
            $(this).find(".selectBox").addClass("visibility");
        } else {
            $(this).find(".selectBox").addClass("visibility-hidden");
            //	return true;
        }

    });
},


        // 选框
        switchBoxChange: function (value, e) {
            if (value.checked) {
                var lineDataOrder = value.parentElement.parentElement.parentElement.children[2].children[0];
                var checkedMobile = lineDataOrder.innerHTML;
                // 设置催费短信
                commitOpera("2", "1", checkedMobile);

                // var mask = document.getElementById("urgeMask");
                // mask.style.display = "block";
                // // 阻止冒泡
                // // var event = e||window.event;
                // if (e || e.stopPropagation()) {
                // e.stopPropagation();
                // } else {
                // e.cancelBubble = true;
                // }
            } else {
                // 取消设置催费短信
                alert(" 取消设置催费短信");
            }
        },
        // 出发跳转页面 新增手机号码
        // AddClick:function(value){
        //     //alert(value);
        //     sessionStorage.setItem("YXPC-consNo", value);
        //     // 页面跳转
        //     window.location.href = "/app/YXPhoneChange/addMobile.html";
        //     //window.location="/phone/gotoPhoneAddMobile";
        // },



}});

