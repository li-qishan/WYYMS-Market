var vm = new Vue({
    el: '#ScrollContent_n2w5sg',
    data: {
        //供电所
        dataList: [],
        barCode: "",
        tFactor: "",
        instDate: "",
        lastCheckDate: "",
        userName: "",
        mpId: " ",
        consNo:""
    },
    computed: {},
    mounted: function () {
        var _ = this;
        _.initEvents();

        _.consNo = sessionStorage.getItem("UserInfo_consNo");
        // 操作用户
        _.userName = sessionStorage.getItem('UserInfoDeptNo');
        _.mpId = sessionStorage.getItem("userInfo_mpId");
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
                window.location.href = "/app/YxUserInfo/mpDetail.html";
            });
        },
        //获取数据
        getBasicData: function () {
            var _ = this;

            $.ajax({
                url: "/spPrc/getMeterData",
                type: "POST",
                data: {
                    "consNo" :_.consNo,
                    "mpId" : _.mpId ,
                    "userName": _.userName
                },
                dataType: "json",
                contentType: "application/x-www-form-urlencoded",
                success: function (data) {
                    if (data.code != "1" || data.data.length == 0) {
                        alert("未查到流程信息，请核对查询条件！");
                        $('#userInfo').css('display','none');

                    } else {
                        $("#userInfo").css("display", "");
                        _.dataList = data.data[0].meterRead;
                        _.barCode = data.data[0].barCode;
                        _.tFactor = data.data[0].tFactor;
                        _.instDate = data.data[0].instDate;
                        _.lastCheckDate = data.data[0].lastCheckDate;
                    }
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
