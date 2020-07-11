var vm = new Vue({
    el: '#ScrollContent_dj4sYO',
    data: {
        //供电所
        dataList: [],
        pageNum: 1,
        deptNo: "",
        requestUrl: 'http://192.168.29.238:18081/',
        userName: ""
    },
    //watch: {},
    computed: {},
    mounted: function () {
        var _ = this;
        // 获取平台参数
        //_.getUserBaseData();
        // 事件初始化
        _.initEvents();
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


        },
        searchClick: function () {

            var type = document.getElementById("searchSelect").value;
            var param = document.getElementById("searchInput").value;


            sessionStorage.setItem("address_search_type", type);
            sessionStorage.setItem("address_search_param", param);
            //
            // Arrears_bool = Arrears_bool + 1;
            alert('111');

            window.location.href = "detail.html";
        },


        //用户明细列表
        getBasicData: function () {
            //var requestUrl = 'http://192.168.29.238:18081/';
            //var httpUrl = 'http://10.160.84.188:18081/';
            var _ = this;
            // 供电单位编号
            // var deptNo = sessionStorage.getItem('YXPC-deptNo');
            // 操作用户
            // var name = sessionStorage.getItem('YXPC-userName');
            //获取数值
            var type = $("#searchSelect").val();
            var param = $("#searchInput").val();


            // 用户列表
            // if (index === "userInfo") {
            // 请求参数
            params = {
                "type": type,
                "param": param,
                "index": "userInfo",
                "userName": _.userName,
                "deptNo": ''

            }
            // 服务地址
            //var apiAddress = 'http://10.160.84.188:18080/phone/getUserList';
            var apiAddress = _.requestUrl + '/phone/getUserList';
            // 获取数据
            mui.getJSON(apiAddress, params, function (data) {
                if (data.code != "1" || data.data.length == 0) {
                    alert("未查到用户地址信息，请核对查询条件！");
                } else {
                    _.dataList = data.data;
                }
            });
            // }

        }
        ,
        // getUserBaseData: function () {
        //
        //     if (sessionStorage.getItem("arrears_bool") != '1') {
        //         var _ = this;
        //         // 获取路径中参数
        //         var base_data = _.getParams("userObj");
        //         //alert(base_data);
        //         // 因为双引号的原因，需转两次json
        //         var obj = JSON.parse(base_data);
        //         //alert(obj);
        //         obj = JSON.parse(obj);
        //         //alert(obj);
        //         // 获取平台用户名
        //         //alert(obj.data.namecode);
        //         _.userName = obj.data.namecode;
        //         // 只有在第一次加载才去获取
        //         if (Arrears_bool != '1') {
        //             // 供电单位编号
        //             sessionStorage.setItem("arrears_userName", _.userName);
        //
        //         }
        //
        //     }
        //
        //
        // },
        reloadSearchParam: function () {
            debugger
            var _ = this;
            // 查询条件重载
            var search_type = sessionStorage.getItem('address_search_type');
            var search_param = sessionStorage.getItem('address_search_param');
            //alert(search_param)
            if (search_type != null && search_type != "" && search_param != null && search_param != "") {
                document.getElementById("searchSelect").value = search_type;
                document.getElementById("searchInput").value = search_param;
                //重新加载数据
                _.getBasicData();
            }

        }
        ,
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


