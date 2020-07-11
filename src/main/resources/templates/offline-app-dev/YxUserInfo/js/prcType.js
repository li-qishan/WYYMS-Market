var vm = new Vue({
    el: '#ScrollContent_8RVEl2',
    data: {
        //供电所
        dataList: [],
        consNo: "",
        userName: ""
    },
    computed: {},
    mounted: function () {
        var _ = this;
        _.initEvents();

        _.consNo = sessionStorage.getItem("UserInfo_consNo");
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
                window.location.href = "userInfo.html";
            });
        },
        liClick:function (spId,prcCode) {
            // window.location = "/userInfo/gotoMpDetail";
            window.location.href = "mpDetail.html";

            // 受电点id，电价码

            sessionStorage.setItem("UserInfo_spId", spId);
            sessionStorage.setItem("UserInfo_prcCode", prcCode);
        },
        //获取数据
        getBasicData: function () {
            var _ = this;

            // $.ajax({
            //    /* url: "http://192.168.29.246:18080/spPrc/getSpPrcData", */
            //    url: "http://10.160.84.188:18080/spPrc/getSpPrcData",
            //    type: "POST",
            //    data: {
            //        "consNo":'8119296765',
            //        "userName": _.userName
            //    },
            //    contentType: "application/x-www-form-urlencoded",
            //    success: function (data) {
			//		 if (data.code != "1" || data.data.length == 0) {
            //            alert("未查到受电点信息，请返回上一层级！");
            //            $('#userInfo').css('display','none');

            //        } else {
            //           $("#userInfo").css("display", "");
            //        _.dataList = data.data;
            //        }
            //    },
            //    error: function (e, err) {
            //    }
            //}); 
			
			params = {
                "consNo": _.consNo,
                //"consNo": '8119296765',
					"userName": _.userName
                }

            var apiAddress = 'http://10.160.84.188:18081/spPrc/getSpPrcData';
            //var apiAddress = 'http://192.168.29.238:18080/spPrc/getSpPrcData';
			
			mui.getJSON(apiAddress, params, function(data) {
				//debugger
				 if (data.code != "1" || data.data.length == 0) {
                        alert("未查到受电点信息，请返回上一层级！");
                        $('#userInfo').css('display','none');

                    } else {
                       $("#userInfo").css("display", "");
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
