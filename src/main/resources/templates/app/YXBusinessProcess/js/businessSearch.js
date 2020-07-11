var vm = new Vue({
    el: '#CustomListView_NMhIw9',
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
        //工单列表
        getBasicData: function () {
            //debugger

            var _ = this;
            // 供电单位编号
            var deptNo = sessionStorage.getItem('YXBP-deptNo');
            // 操作用户
            var name = sessionStorage.getItem('YXBP-userName');
            var orderNum = $("#searchInput").val();
            var SelectType = $("#searchSelect").val();
            //alert("工单编号或户号：" + orderNum);
            //alert("查询类别：" + SelectType);
            $.ajax({
                url: "/businessProcess/getBusinessProcessByOrderNum",
                type: "POST",
                dataType: "json",
                data: {
                    "orderNum": orderNum,
                    "userName": name,
                    "searchType": 1,
                    "selectType": SelectType,
                    "type": 4,
                    "deptNo": deptNo//非写死
                },
                contentType: "application/x-www-form-urlencoded",
                success: function (data) {
                    if (data.code != "1") {
                        alert("未查到数据信息，请核对查询条件！");
                    } else {
                        if (data.data.length == 0) {
                            alert("未查到数据信息，请核对查询条件！");
                        } else {
                            debugger
                            _.dataList = data.data;

                        }
                    }

                },
                error: function (e, err) {

                }
            });


        },

        initEvents: function () {

            var _ = this;

            // alert(_.getParams("userName"));
            // alert(_.getParams("deptNo"));

            if (sessionStorage.getItem('YXBP-bool') != '1') {
                sessionStorage.setItem("YXBP-userName", _.getParams("userName"));
                sessionStorage.setItem("YXBP-deptNo", _.getParams("deptNo"));
            }


            // 供电单位编号
            var deptNo = sessionStorage.getItem('YXBP-deptNo');
            // 操作用户
            var name = sessionStorage.getItem('YXBP-userName');

            //alert(deptNo + name);

            // 页面回退时
            var appNoReload = sessionStorage.getItem('YXBP-appNoReload');
            var consNoReload = sessionStorage.getItem('YXBP-consNoReload');
            var SelectTypeReload = sessionStorage.getItem('YXBP-SelectTypeReload');
            // 判断 第一次加载 为空 回显不为空
            if (!_.isEmpty(appNoReload) && !_.isEmpty(SelectTypeReload)) {
                if (SelectTypeReload == "1") {// 户号查询
                    document.getElementById("searchSelect").value = SelectTypeReload;
                    document.getElementById("searchInput").value = consNoReload;
                }
                if (SelectTypeReload == "4") {// 工单编号查询
                    document.getElementById("searchSelect").value = SelectTypeReload;
                    document.getElementById("searchInput").value = appNoReload;
                }


                // 调用重新加载
                _.getBasicData();
                // 清除变量
                sessionStorage.removeItem('YXBP-appNoReload')
                sessionStorage.removeItem('YXBP-consNoReload')
                sessionStorage.removeItem('YXBP-SelectTypeReload')

            }

            $("#searchBtn").click(
                function () {
                    // alert("reload");
                    if ($("#searchSelect").val() == ""
                        || $("#searchInput").val() == "") {
                        alert("查询条件未填选！");
                        return;
                    }
                    else {
                        //触发调用
                        _.getBasicData();
                    }
                });

            $("#Icon").click(function () {
                var mask = document.getElementById("mask");
                mask.style.display = "block";
                // 阻止冒泡
                event = event || window.event;
                if (event || event.stopPropagation()) {
                    event.stopPropagation();
                } else {
                    event.cancelBubble = true;
                }
            });

            $("#LoginOutSubmit").on('click', function (event) {
                // 存在的问题： 当点击返回时怎么处理
                //alert("返回事件触发");
                window.location.href = "";
            });


            // 遮罩层隐藏
            $("#mask").click(function (event) {
                var mask = document.getElementById("mask");
                event = event || window.event;
                // 兼容获取触动事件时被传递过来的对象
                var aaa = event.target ? event.target : event.srcElement;
                if (aaa.id !== "delConfirm") {
                    mask.style.display = "none";
                }
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


        //判断字符是否为空的方法
        isEmpty: function (obj) {
            if (typeof obj == "undefined" || obj == null || obj == "") {
                return true;
            } else {
                return false;
            }
        },

        operate: function (item, event) {
            var _ = this;
            // 供电单位编号
            var deptNo = sessionStorage.getItem('YXBP-deptNo');
            // 操作用户
            var name = sessionStorage.getItem('YXBP-userName');

            sessionStorage.setItem("YXBP-appNo", item.APPNO);
            sessionStorage.setItem("YXBP-SelectType", $("#searchSelect").val());
            sessionStorage.setItem("YXBP-consNo", item.CONSNO);

            sessionStorage.setItem("YXBP-userName", name);
            sessionStorage.setItem("YXBP-deptNo", deptNo);
            window.location.href = "/app/YXBusinessProcess/businessDetail.html";
        },


    }


});
