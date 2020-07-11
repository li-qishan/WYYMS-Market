var vm = new Vue({
    el: '#ScrollContent_Omp5LP',
    data: {
        //供电所
        dataList: [],
        consNo: "",
        userName: "",
        spId: "",
        prcCode: "",
        MeterFlagYes: false,
        MeterFlagNo: false
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
        _.userName = sessionStorage.getItem('UserInfoUserName');
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
                window.location.href = "prcType.html";
            });


        },
        //是否装表


        //获取数据
        getBasicData: function () {
            var _ = this;
			//debugger
			
			params = {
                //"consNo": '8119296765',
                //"prcCode": '40201004',
                //"spId": '802507144',
                "consNo": _.consNo,
                "prcCode": _.prcCode,
                "spId": _.spId,
                   "userName" : _.userName
                }

            var apiAddress = 'http://10.160.84.188:18081/spPrc/getMpData';

            //var apiAddress = 'http://192.168.29.238:18080/spPrc/getMpData';
			
			mui.getJSON(apiAddress, params, function(data) {
                debugger
			       if (data.code != "1" || data.data.length == 0) {
                       alert("无效电价码，未关联到可用计量点信息！");
                       $('#userInfo').css('display','none');

                   } else {
                       $("#userInfo").css("display", "");
                       _.dataList = data.data;
                       //当前计量点详细中取出是否装表
                       $.each(data.data, function (index, obj) {
                           //是否装表
                           var isMeterFlag = "";
                           //测试动态按钮
                           isMeterFlag = obj.meterFlag;
                           //isMeterFlag = "是";

                           if (isMeterFlag === "否") {
                               //设置按钮样式不可编辑
                               _.MeterFlagNo = true;
                           }
                           else if (isMeterFlag === "是") {
                               _.MeterFlagYes = true;
                           }

                       })


                   }
			});
			
            //$.ajax({
            //    /* url: "http://192.168.29.246:18080/spPrc/getMpData", */
            //    url: "http://10.160.84.188:18080/spPrc/getMpData",
            //    type : "POST",
            //    data : {
            //        "consNo" : _.consNo,
            //        "prcCode" : _.prcCode ,
            //        "spId" : _.spId,
            //        "userName" : _.userName
            //    },
            //    dataType : "json",
            //    contentType: "application/x-www-form-urlencoded",
            //    success: function (data) {
            //        if (data.code != "1" || data.data.length == 0) {
            //            alert("未查到计量点信息，请返回上一层级！");
            //            $('#userInfo').css('display','none');

            //        } else {
            //            $("#userInfo").css("display", "");
            //            _.dataList = data.data;
             //       }
            //    },
            //    error: function (e, err) {
            //    }
            //});
        },
        toMeterDetail:function() {
            sessionStorage.setItem("userInfo_mpId", $("#mpId").html());
            window.location.href = "meterDetail.html";
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
