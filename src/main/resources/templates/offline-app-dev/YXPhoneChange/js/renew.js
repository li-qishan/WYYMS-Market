var vm = new Vue({
    el: '#CustomListView_4sKwrQ',
    data: {
        //后台请求数据集
        dataList: [],
        checkedMobile: "",
        deptNo: "",
        userName: "",
        // 联系人类型
        index: "",
        checkboxObj: ""
    },
    computed: {},
    mounted: function () {
        var _ = this;
        //初始化事件
        _.initEvents();
        //_.getBasicData();


    },
    // beforeUpdate: function () {
    //     var _ = this;
    //     if (_.index == "02" || _.index == "03") {
    //         $(".selectBox").addClass("visibility-hidden");
    //     } else {
    //         $(".selectBox").removeClass("visibility-hidden");
    //     }
    // },
    updated: function () {
        var _ = this;
        // id重复取消
        _.setId();
        //
        if (_.index == "02" || _.index == "03") {
            $(".selectBox").addClass("visibility-hidden");
        } else {
            $(".selectBox").removeClass("visibility-hidden");
        }
    },
    methods: {

        getBasicData: function () {
            // 供电单位编号
            var deptNo = sessionStorage.getItem('YXPC-deptNo');
            // 操作用户
            var name = sessionStorage.getItem('YXPC-userName');
            var _ = this;
            // 户号
            var consNo = sessionStorage.getItem('YXPC-consNo');
            // var consNo = sessionStorage.getParams("consNo");
            // 点击联系人
            var clickHtml = "";
            $(".active").each(function () {
                clickHtml = $(this).find(".phone").html();
                if (clickHtml.indexOf("电气联系人") != -1) {
                    _.index = "01";
                } else if (clickHtml.indexOf("账务联系人") != -1) {
                    _.index = "02";
                } else if (clickHtml.indexOf("停送电联系人") != -1) {
                    _.index = "03";
                }
            });
            sessionStorage.setItem('YXPC-index', _.index);

            params = {
                "consNo": consNo,
                "index": _.index,
                "userName": name,
                "deptNo": deptNo

            }

            var apiAddress = httpUrl + '/phone/getUserDetail';

            mui.getJSON(apiAddress, params, function (data) {

                $("#user_consName").html(data.data.userInfo.CONS_NAME);
                $("#user_consNo").html(data.data.userInfo.CONS_NO);
                $("#user_code").html(data.data.userInfo.CUST_QUERY_NO);
                $("#user_type").html(data.data.userInfo.ELEC_TYPE_CODE);
                $("#user_addr").html(data.data.userInfo.ELEC_ADDR);
                $("#org_name").html(data.data.userInfo.ORG_NAME);

                if (data.data.userPhones.length > 0) {
                    debugger
                    // //vm.$forceUpdate();
                    // vm.dataList = [];
                    // var datas = data.data.userPhones;
                    // for (var i = 0; i < datas.length; i++) {
                    //     var itemLen = vm.dataList.length;
                    //     Vue.set(vm.dataList, itemLen, datas[i]);
                    // }
                    // //
                    // // _.dataList.concat(data.data.userPhones);

                    _.dataList = data.data.userPhones;

                } else {
                    alert("该用户无" + clickHtml);
                    //vm.$forceUpdate();
                    //_.dataList = [];
                    //_.dataList.concat(data.data.userPhones);
                    _.dataList = data.data.userPhones;
                }
            });

        },

        // 事件提交方法
        commitOpera: function (operType, isSign, mobile) {
            var _ = this;
            // 用户名 sessionStorage.getItem('clickHtml');
            var consName = sessionStorage.getItem('YXPC-consName');
            // 供电单位编号
            var deptNo = sessionStorage.getItem('YXPC-deptNo');
            // 操作人
            var name = sessionStorage.getItem('YXPC-userName');
            // 操作户号
            var consNo = sessionStorage.getItem('YXPC-consNo');

            // 获取操作手机号
            var phone = "";
            var contactId = "";
            $("input[name='check']:checked").each(function () {
                //debugger
                var lineData = $(this).parent().parent().find("#mobile");
                phone = lineData[0].innerText;
            });

            // 设置催费信息时，设置phone为mobile
            if (operType == "2") {
                phone = mobile;
            }

            $.ajax({
                url: httpUrl + "/phone/userPhoneOpera",
                type: "POST",
                data: {
                    "consNo": consNo,
                    "consName": consName,
                    "userName": name,
                    "index": _.index,
                    "operType": operType,// 0新增，1删除，2更新
                    "isSign": isSign,// 0否,1是
                    "oldPhone": mobile,
                    "phone": phone,
                    "deptNo": deptNo
                },
                dataType: "json",
                contentType: "application/x-www-form-urlencoded",
                success: function (data) {
                    debugger
                    if (data.code == 1) {
                            alert(data.msg);
                        // 更新页面数据
                        _.getBasicData();
                        //页面重载
                        self.location.reload();



                    }
                },
                error: function (e, err) {
                }
            });


        },

        initEvents: function () {
            var _ = this;
            // 供电单位编号
            var deptNo = sessionStorage.getItem('YXPC-deptNo');
            // 操作用户
            var name = sessionStorage.getItem('YXPC-userName');

            $(".mobileText").on("click", function (e) {
                $(".mobileText").removeClass("active");
                $(this).addClass("active");
                // 更新页面数据
                // $("#CustomListView_4sKwrQ").trigger("reload", this);
                _.getBasicData();
                // 修改图片路径
                var val = $(this).find(".phone").html();
                // 点击类型
                // 账务联系人和停送电联系人不显示催费短信选择框
                var clickHtml = "";
                if (val.indexOf("电气联系人") != -1) {
                    clickHtml = "电气联系人";
                    $(".mobile").find(".user").attr("src", "css/img/user_active.png");
                    $(".mobile").find(".money").attr("src", "css/img/money.png");
                    $(".mobile").find(".elec").attr("src", " css/img/elec.png");
                } else if (val.indexOf("账务联系人") != -1) {
                    clickHtml = "账务联系人";
                    $(".mobile").find(".money").attr("src", "css/img/money_active.png");
                    $(".mobile").find(".user").attr("src", "css/img/user.png");
                    $(".mobile").find(".elec").attr("src", "css/img/elec.png");
                } else if (val.indexOf("停送电联系人") != -1) {
                    clickHtml = "停送电联系人";
                    $(".mobile").find(".elec").attr("src", "css/img/elec_active.png");
                    $(".mobile").find(".user").attr("src", "css/img/user.png");
                    $(".mobile").find(".money").attr("src", "css/img/money.png");
                }

                sessionStorage.setItem('clickHtml', clickHtml);
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

            document.onclick = function (event) {
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
            $("#btnAdd").on("click", function (event) {
                //debugger
                var consName = $("#user_consName").html();
                // alert(consName);

                window.location.href = "addMobile.html";
            });

            $("#btnUpdate").on("click", function (event) {
                var phone = "";
                var contactId = "";
                var isSign = "";
                var i = 0;
                $("input[name='check']:checked").each(function () {
                    var lineData = $(this).parent().parent().find("#mobile");
                    phone = lineData.html();
                    var contactData = $(this).parent().parent().find("#contactIdHid");
                    contactId = contactData.html();
                    i++;
                });
                sessionStorage.setItem('YXPC-oldPhone', phone);
                sessionStorage.setItem('YXPC-contactId', contactId);
                if (i == 1) {
                    window.location.href = "updateMobile.html";
                } else {
                    alert("请选择一条要操作的数据！");
                }
            });
            $("#btnDelete").on("click", function (event) {
                var i = 0;
                $("input[name='check']:checked").each(function () {
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
                }

            });

            /**
             * 删除弹出框按钮
             */
            // 确认按钮
            $("#delSubmit").on("click", function (event) {
                _.commitOpera("1", "0", "####");
            });
            // 取消按钮
            $("#delCancel").on("click", function (event) {
            });

            /**
             * 催费弹出框按钮
             */
            // 确认按钮
            $("#urgeSubmit").on("click", function (event) {
                // 设置催费短信
                _.commitOpera("2", "1", _.checkedMobile);
            });
            // 取消按钮
            $("#urgeCancel").on("click", function (event) {
                _.checkboxObj.target.checked = false;
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
            $(".sasasa").each(function () {
                debugger
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
                if (result == null) {
                    //	return false;
                    $(this).find(".selectBox").removeClass("visibility-hidden");
                } else {
                    $(this).find(".selectBox").addClass("visibility-hidden");
                    //	return true;
                }

            });
        },

        // 催费选框
        switchBoxChange: function (value, e) {
            var _ = this;
            _.checkboxObj = e;

            var checked = _.checkboxObj.target.checked;
            if (checked) {
                _.checkedMobile = value;

                _.commitOpera("2", "1", _.checkedMobile);
                // var mask = document.getElementById("urgeMask");
                // mask.style.display = "block";
                // // 阻止冒泡
                // // var event = e||window.event;
                // if (e || e.stopPropagation()) {
                //     e.stopPropagation();
                // } else {
                //     e.cancelBubble = true;
                // }
            } else {
                // 取消设置催费短信
                alert(" 取消设置催费短信");
            }
        }
    }
});

