var vm = new Vue({
    el: '#ScrollContent_8RVEl2',
    data: {
        //供电所
        dataList: [],
        consNo: "",
        userName: "",
    },
    computed: {},
    mounted: function () {
        var _ = this;
        _.initEvents();

        _.consNo = sessionStorage.getItem("UserInfo_consNo");
        // 操作用户
        _.userName = sessionStorage.getItem('UserInfoDeptNo');
        // 加载数据
        _.getBasicData();
    },
    methods: {
        // 初始化控件时间
        initEvents: function () {
            var _ = this;

            // 返回按钮
            $("#Icon").click(function () {
                // window.location = "/userInfo/gotoUserInfo";
                window.location.href = "/app/YxUserInfo/userInfo.html";
            });
        },
        liClick:function (spId,prcCode) {
            // window.location = "/userInfo/gotoMpDetail";
            window.location.href = "/app/YxUserInfo/mpDetail.html";

            // 受电点id，电价码

            sessionStorage.setItem("UserInfo_spId", spId);
            sessionStorage.setItem("UserInfo_prcCode", prcCode);
        },
        //获取数据
        getBasicData: function () {
            var _ = this;

            $.ajax({
                url: "/spPrc/getSpPrcData",
                type: "POST",
                data: {
                    "consNo":_.consNo,
                    "userName": _.userName
                },
                contentType: "application/x-www-form-urlencoded",
                success: function (data) {
                    _.dataList = data.data;
                },
                error: function (e, err) {
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
