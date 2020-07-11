var vm = new Vue({
    el: '#ScrollContent_RQF9yx',
    data: {
        //供电所
        dataList: [],
        pageNum: 1,
        deptNo: "",
        userName: ""
    },
    //watch: {},
    computed: {},
    mounted: function () {
        var _ = this;
        _.initEvents();

        if (sessionStorage.getItem('arrears_bool') != '1') {
            sessionStorage.setItem("arrears_userName", _.getParams("userName"));
            sessionStorage.setItem("arrears_deptNo", _.getParams("deptNo"));
            // sessionStorage.setItem("userName", 'test');
            // sessionStorage.setItem("deptNo", '21409');
        }

        // 供电单位编号
        _.deptNo = sessionStorage.getItem('arrears_deptNo');
        // 操作用户
        _.userName = sessionStorage.getItem('arrears_userName');

        _.reloadSearchParam();
    },
    methods: {
        // 初始化控件时间
        initEvents: function () {
            var _ = this;

            $("#searchBtn").click(
                function () {
                    if ($("#searchSelect").val() == ""
                        || $("#searchInput").val() == "") {
                        alert("请填写查询条件！");
                        return;
                    }
                    _.getBasicData();
                }
            );
            // 月份选择显隐
            $("#searchSelect").change(function (event) {
                var type = $(this).val();
                var searchMonth = document.getElementById("searchMonth");
                if (type != 1) {
                    // 设置初始化值（当前月）
                    var searchDate = new Date();
                    var year = searchDate.getFullYear();
                    var month = searchDate.getMonth() + 1;// 从0开始
                    // 单月拼接0
                    if (month < 10) {
                        searchMonth.value = year + "-0" + month;
                    } else {
                        searchMonth.value = year + "-" + month;
                    }
                    // 显示
                    searchMonth.className = "dateSelect show";
                } else {
                    searchMonth.className = "dateSelect hide";
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
                window.location.href = "./login.html";
            });
            // 取消按钮
            $("#LoginOutCancel").on('click', function (event) {
                var mask = document.getElementById("mask");
                event = event || window.event;
                // 兼容获取触动事件时被传递过来的对象
                var aaa = event.target ? event.target : event.srcElement;
                if (aaa.id !== "delConfirm") {
                    mask.style.display = "none";
                }
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
        searchClick: function (consNo, ym) {

            sessionStorage.setItem("arrears_consNo", consNo);
            sessionStorage.setItem("arrears_month", ym);

            window.location.href = "/app/YXArrears/userDetail.html";

        },
        //获取数据
        getBasicData: function () {
            var _ = this;

            var type = $("#searchSelect").val();
            var param = $("#searchInput").val();
            var month = $("#searchMonth").val();
            $.ajax({
                url: "/arrears/getUserArrearsList",
                type: "POST",
                data: {
                    "type": type,
                    "param": param,
                    "month": month,
                    "userName": _.userName ,
                    "deptNo": _.deptNo,
                    "pageNum": _.pageNum,
                    "pageSize": "10"
                },
                dataType: "json",
                contentType: "application/x-www-form-urlencoded",
                success: function (data) {
                    if (data.code != "1" || data.data.length == 0) {
                        alert("未查到欠费用户信息，请核对查询条件！");
                        $("#ScrollContent_RQF9yx").css("display", "none");
                    } else {

                        $("#ScrollContent_RQF9yx").css("display", "");

                        sessionStorage.setItem("arrears_search_type", type);
                        sessionStorage.setItem("arrears_search_param", param);
                        sessionStorage.setItem("arrears_search_month", month);

                        _.dataList = data.data.list;
                        $("#arrearsMoney").html(data.data.moneyNum.toFixed(2) + "元");
                        $("#userNum").html(data.data.userNum + "户");
                    }
                },
                error: function (e, err) {
                }
            });
        },
        reloadSearchParam: function () {
            var _ = this;
            // 查询条件重载
            var search_type = sessionStorage.getItem('arrears_search_type');
            var search_param = sessionStorage.getItem('arrears_search_param');
            var search_month = sessionStorage.getItem('arrears_search_month');

            if (search_type != null && search_type != "" && search_param != null && search_param != "") {
                document.getElementById("searchSelect").value = search_type;
                document.getElementById("searchInput").value = search_param;
                if (search_type != 1) {
                    var searchMonth = document.getElementById("searchMonth");
                    searchMonth.className = "dateSelect show";
                    if (search_month != null && search_month != "") {
                        // 获取后没有-，拼接上
                        // search_month = search_month.substring(0, 4) + "-" + search_month.substring(4, search_month.length);
                        searchMonth.value = search_month;
                    }
                }
                //重新加载数据
                _.getBasicData();
            }

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
    }
});
