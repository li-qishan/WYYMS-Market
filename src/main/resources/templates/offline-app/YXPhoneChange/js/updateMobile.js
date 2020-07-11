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
    },
    methods: {
        //提交数据//事件提交方法
        commitBatchOpera: function () {
            var _ = this;
            // 用户名
            var consName = sessionStorage.getItem("YXPC-consName");
            // 供电单位编号
            var deptNo = sessionStorage.getItem("YXPC-deptNo");
            // 联系人类型
            var index = sessionStorage.getItem("YXPC-index");
            //操作人
            var name = sessionStorage.getItem("YXPC-userName");
            //操作户号
            var consNo = sessionStorage.getItem("YXPC-consNo");
            // 获取操作手机号
            var phone = $("#newMobile").val();
            //alert(phone);
            //var oldPhone = $("#oldPhone").innerText;
            var oldPhone = $("#oldPhone")[0].innerText;

            $.ajax({
                url: httpUrl + "/phone/userPhoneBatchOpera",
                type: "POST",
                data: {
                    "consNo": consNo,
                    "consName": consName,
                    "userName": name,
                    "operType": "2",//0新增，1删除，2更新
                    "oldPhone": oldPhone,
                    "phone": phone,
                    "deptNo": deptNo
                },
                dataType: "json",
                contentType: "application/x-www-form-urlencoded",
                success: function (data) {
                    if (data.code == 1) {
                        alert(data.msg);
                        // 回到用户信息页
                        window.location.href = "renew.html";
                    }
                },
                error: function (e, err) {
                }
            });

        },
        commitOpera: function () {
            var _ = this;
            // 供电单位编号
            var deptNo = sessionStorage.getItem('YXPC-deptNo');
            // 联系人类型
            var index = sessionStorage.getItem('YXPC-index');
            // 操作人
            var name = sessionStorage.getItem('YXPC-userName');
            // 操作户号
            var consNo = sessionStorage.getItem('YXPC-consNo');
            // 用户名
            var consName = sessionStorage.getItem("YXPC-consName");

            var contactId = sessionStorage.getItem("YXPC-contactId");

            var oldPhone = $("#oldPhone")[0].innerText;
            // 修改后手机号
            var phone = $("#newMobile").val();
            $.ajax({
                url: httpUrl + "/phone/userPhoneOpera",
                type: "POST",
                data: {
                    "consNo": consNo,
                    "consName": consName,
                    "userName": name,
                    "index": index,
                    "operType": "2",// 0新增，1删除，2更新
                    "isSign": "0",// 0否,1是
                    "oldPhone": oldPhone,
                    "phone": phone,
                    "deptNo": deptNo
                },
                dataType: "json",
                contentType: "application/x-www-form-urlencoded",
                success: function (data) {
                    if (data.code == 1) {
                        if (index == '01') {
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
                            //页面重载
                                alert(data.msg);
                                window.location.href = "renew.html";
                        }

                    }
                },
                error: function (e, err) {
                }
            });
        },

        initEvents: function () {

            var _ = this;

            // 返回按钮
            $("#Icon").on('click', function (e) {
                window.location.href = "renew.html";
            });
            // 设置页面数据
            var consName = sessionStorage.getItem('YXPC-consName')
            var phone = sessionStorage.getItem('YXPC-oldPhone')

            $("#userConsName").html(consName);
            $("#oldPhone").html(phone)

            // 保存事件
            $("#commitSave").on("click", function () {
                var phone = $("#newMobile").val();
                //alert("触发提交方法");
                if (phone == "" || phone == null || !(/^1(3|4|5|7|8)\d{9}$/.test(phone)) && !(/\d{3}-\d{8}|\d{4}-\d{7}/.test(phone))) {// 验证固定电话
                    alert("请输入正确11位手机号码！");
                    $("#newMobile").val("");
                    return;
                }

                //触发提交方法
                _.commitOpera();
            });

            // 电话号验证
            $("#newMobile").on("change", function () {
                var phone = $(this).val();
                if (phone == "" || phone == null || !(/^1(3|4|5|7|8)\d{9}$/.test(phone))
                    && !(/\d{3}-\d{8}|\d{4}-\d{7}/.test(phone))) {// 验证固定电话
                    alert("请输入正确号码！");
                    $(this).val("");
                    return;
                }
            });

            // 遮罩层隐藏
            document.onclick = function (event) {
                var mask = document.getElementById("mask");
                event = event || window.event;
                // 兼容获取触动事件时被传递过来的对象
                var aaa = event.target ? event.target : event.srcElement;
                if (aaa.id !== "updateConfirm") {
                    mask.style.display = "none";
                }
            }

            // 确认更新按钮
            $("#updateSubmit").on('click', function (event) {
                _.commitBatchOpera();
            });
            // 取消按钮
            $("#updateCancel").on('click', function (event) {
                window.location.href = "renew.html";
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
        }

    }
});


