var vm = new Vue({
    el: '#ScrollContent_Omp5LP',
    data: {
        //供电所
        dataList: [],
        consNo: "",
        userName: "",
        spId: "",
        prcCode: "",
    },
    computed: {},
    mounted: function () {
        var _ = this;
        _.initEvents();

        _.consNo = sessionStorage.getItem("UserInfo_consNo");

        // 受电点id，电价码
        _.spId = sessionStorage.getItem("UserInfo_spId");
        _.prcCode = sessionStorage.getItem("UserInfo_prcCode");
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
                window.location.href = "/app/YxUserInfo/prcType.html";
            });
        },
        //获取数据
        getBasicData: function () {
            var _ = this;
            $.ajax({
                url : "/spPrc/getMpData",
                type : "POST",
                data : {
                    "consNo" : _.consNo,
                    "prcCode" : _.prcCode ,
                    "spId" : _.spId,
                    "userName" : _.userName
                },
                dataType : "json",
                contentType: "application/x-www-form-urlencoded",
                success: function (data) {
                    if (data.code != "1" || data.data.length == 0) {
                        alert("未查到流程信息，请核对查询条件！");
                        $('#userInfo').css('display','none');

                    } else {
                        $("#userInfo").css("display", "");
                        _.dataList = data.data;
                    }
                },
                error: function (e, err) {
                }
            });
        },
        toMeterDetail:function() {
            sessionStorage.setItem("userInfo_mpId", $("#mpId").html());
            window.location.href = "/app/YxUserInfo/meterDetail.html";
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
