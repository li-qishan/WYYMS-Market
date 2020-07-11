var vm = new Vue({
    el: '#ScrollContent_yUbgeu',
    data: {
        //供电所
        dataList: [],
        deptNo: "",
        userName: "",
        month: "",
        consNo: ""
    },
    //watch: {},
    computed: {},
    mounted: function () {
        var _ = this;
        _.initEvents();

        sessionStorage.setItem('arrears_bool', "1");
        // 供电单位编号
        _.deptNo = sessionStorage.getItem('arrears_deptNo');
        // 操作用户
        _.userName = sessionStorage.getItem('arrears_userName');
        // 供电单位编号
        _.month = sessionStorage.getItem('arrears_month');
        // 操作用户
        _.consNo = sessionStorage.getItem('arrears_consNo');
        _.getBasicData();
    },
    methods: {
        // 初始化控件时间
        initEvents: function () {
            var _ = this;

            $("#Icon").click(function () {
                sessionStorage.setItem('arrears_month',"");
                sessionStorage.setItem('arrears_consNo',"");
                window.location.href = "/app/YXArrears/userInfo.html";
            });
        },
        //获取数据
        getBasicData: function () {
            var _ = this;

            var month = $("#searchMonth").val();
            $.ajax({
                url: "/arrears/getUserArrearsDetail",
                type: "POST",
                data: {
                    "consNo": _.consNo,
                    "month": _.month,
                    "userName": _.userName,
                    "deptNo": _.deptNo,
                },
                dataType: "json",
                contentType: "application/x-www-form-urlencoded",
                success: function (data) {
                    if (data.code != "1" || data.data.length == 0) {
                        alert("未查到数据！");
                        $("#ScrollContent_yUbgeu").css("display", "none");
                    } else {
                        // debugger
                        $("#ScrollContent_RQF9yx").css("display", "");
                        _.dataList = data.data;
                    }
                },
                error: function (e, err) {
                    option.error(self.doerror(e, err, option));
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
