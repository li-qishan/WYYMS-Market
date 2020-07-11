var vm = new Vue({
    el: '#ScrollContent_RQF9yx',
    data: {
        //供电所
        dataList: [],
        pageNum: 1,
        deptNo: "",
        userName: ""
        //isReloadType: ""
    },
    //watch: {},
    computed: {},
    mounted: function () {
        var _ = this;
        // 获取平台参数
        _.getUserBaseData();
        // 事件初始化
        _.initEvents();
        //加载是否选择抄表员
        _.seletor();
        // 参数重载
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

            // $("#searchInput").change(function () {
            //     //获取选中的值
            //     var seleted = $(this).children('option:selected').val();
            //     if (seleted === '5') {
            //         window.location.href = "seletor.html";
            //     } else {
            //         //无操作
            //     }
            // });


            // 月份选择显隐
            $("#searchSelect").change(function (event) {
                var type = $(this).val();
                var searchMonth = document.getElementById("searchMonth");

                sessionStorage.setItem("arrears_search_type", type);

                if (type != 1) {
                    // 新增  设置输入框值为当前用户 监听事件

                    var user_Name = sessionStorage.getItem("arrears_userName");

                    $("#searchInput").val(user_Name);

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
                    //$("input[name='searchInput']").val("").focus(); // 清空并获得焦点
                    $("#searchInput").val("").focus();

                }

                //获取选中的值
                //     var seleted = $(this).children('option:selected').val();
                //     if (seleted === '5') {
                //         window.location.href = "seletor.html";
                //     } else {
                //         //无操作
                //     }
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

            Arrears_bool = Arrears_bool + 1;

            window.location.href = "userDetail.html";

        },

        nextPage: function () {
            //alert("12323");
            vm.pageNum = 2;
            vm.getBasicData();
            //$("#btn_more").attr("disabled", "disabled");
        },

        /*inputClick: function () {
            debugger
            var isReloadType = $("#searchSelect option:selected");

            //var isReloadType = $("#searchSelect").val();
            sessionStorage.setItem("arrears_isReloadType", isReloadType);

            window.location.href = "seletor.html";
        },*/

        seletor: function () {

            var _ = this;
            //debugger
            //设置值
            if (sessionStorage.getItem('arrears_isSign') === '1') {

                var search_month = sessionStorage.getItem('arrears_search_month');

                var a = sessionStorage.getItem('arrears_isSign');

                var name = sessionStorage.getItem("arrears_USERNAME");

                $("#searchInput").val(name);

                    var searchMonth = document.getElementById("searchMonth");
                    searchMonth.className = "dateSelect show";
                    if (search_month != null && search_month != "") {
                        // 获取后没有-，拼接上
                        // search_month = search_month.substring(0, 4) + "-" + search_month.substring(4, search_month.length);
                        searchMonth.value = search_month;
                    }

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


                }


        },


        //获取数据
        getBasicData: function () {
            var _ = this;

            var type = $("#searchSelect").val();
            var param = $("#searchInput").val();
            var month = $("#searchMonth").val();

            params = {
                "type": type,
                "param": param,
                "month": month,
                "userName": _.userName,
                "deptNo": _.deptNo,
                //"deptNo": 21409,
                "pageNum": _.pageNum,
                "pageSize": "10"
            }

            //var apiAddress = 'http://192.168.29.246:18081/arrears/getUserArrearsList';
            var apiAddress = 'http://10.160.84.188:18081/arrears/getUserArrearsList';

            mui.getJSON(apiAddress, params, function (data) {
                if (data.code != "1" || data.data.length == 0) {
                    alert("未查到欠费用户信息，请核对查询条件！");
                    $("#ScrollContent_RQF9yx").css("display", "none");
                } else {
                    $("#ScrollContent_RQF9yx").css("display", "");
                    //加载更多按钮
                    $("#btn_more").css("display", "block");

                    sessionStorage.setItem("arrears_search_type", type);
                    sessionStorage.setItem("arrears_search_param", param);
                    sessionStorage.setItem("arrears_search_month", month);

                    _.dataList = data.data.list;
                    $("#arrearsMoney").html(data.data.moneyNum.toFixed(2) + "元");
                    $("#userNum").html(data.data.userNum + "户");


                }
            });


        },
        getUserBaseData: function () {

            if (sessionStorage.getItem("arrears_bool") != '1') {
                var _ = this;
                // 获取路径中参数
                var base_data =  decodeURIComponent(location.search.split('&')[0].split('=')[1]);
                //alert(base_data);
                // 因为双引号的原因，需转两次json
                var obj = JSON.parse((base_data));
                //alert(obj);
                obj = JSON.parse(obj);
                //alert(obj);
                // 获取平台用户名
                //alert(obj.data.namecode);
                _.userName = obj.data.namecode;
                // 只有在第一次加载才去获取
                if (Arrears_bool != '1') {
                    // 供电单位编号
                    sessionStorage.setItem("arrears_userName", _.userName);

                }

            }


        },
        reloadSearchParam: function () {
            var _ = this;
            var search_param = "";
            // 查询条件重载
            var search_type = sessionStorage.getItem('arrears_search_type');
            var search_param = sessionStorage.getItem('arrears_search_param');
            var search_month = sessionStorage.getItem('arrears_search_month');
            var name = sessionStorage.getItem("arrears_USERNAME");
            if (name != "" || name != null || name != undefined) {
                search_param = name;
            }

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
            if (search_type != null && search_type != "" && search_type != 1) {
                var searchMonth = document.getElementById("searchMonth");
                searchMonth.className = "dateSelect show";
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
