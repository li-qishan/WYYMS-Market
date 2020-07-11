var vm = new Vue({
    el: '#ScrollContent_RQF9yq',
    data: {
        //供电所
        dataList: [],
        deptNo: "",
        userName: "",
        type: ""
    },
    //watch: {},
    computed: {},
    mounted: function () {
        var _ = this;
        // 事件初始化
        _.initEvents();
        _.getBasicData();

    },
    methods: {
        // 初始化控件时间
        initEvents: function () {
            var _ = this;
            //当前是抄表员还是催费员
            _.type = _.getParams("type");
            // sessionStorage.setItem('arrears_search_type', vm.type);

            //alert(type);
            $("#searchBtn").click(
                function () {
                    debugger
                    if ($("#seletor_input").val() === "" || $("#seletor_input").val() === null) {
                        alert("请填写查询条件！");
                        return;
                    }
                    _.getBasicData();

                }
            );


        },

        searchClick: function (REAL_NAME, USER_NAME) {
            //var_ = this;
            //debugger
            sessionStorage.setItem("arrears_USERNAME", USER_NAME);
            sessionStorage.setItem("arrears_REAL_NAME", REAL_NAME);
            sessionStorage.setItem("arrears_isSign", '1');
            sessionStorage.setItem("isReloadType", vm.type);
            debugger
            //设置表示进行区分
            sessionStorage.setItem("arrears_bool", '1');
            window.location.href = "index.html";

        },


        //获取数据
        getBasicData: function () {

            var user_Name = sessionStorage.getItem("arrears_userName");
            //debugger
            var inputName = $("#seletor_input").val();
            var _ = this;
            params = {
                "selectType": _.type,
                "userName": user_Name,
                "inputName": inputName
                //"deptNo": 21409
            }

            //var apiAddress = 'http://192.168.29.238:18081/arrears/getCBUser';
            var apiAddress = 'http://10.160.84.188:18081/arrears/getCBUser';

            mui.getJSON(apiAddress, params, function (data) {
                debugger
                if (data.code != "1" || data.data.length == 0) {
                    alert("未查到抄表员信息，请核对查询条件！");

                } else {
                    _.dataList = data.data;

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
    }
});
