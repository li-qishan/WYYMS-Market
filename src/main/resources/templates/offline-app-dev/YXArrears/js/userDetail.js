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

        // sessionStorage.setItem('arrears_bool', "1");
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
                window.location.href = "index.html";
            });
        },
        //获取数据
        getBasicData: function () {
            var _ = this;
			// 供电单位编号
			_.deptNo = sessionStorage.getItem('arrears_deptNo');
			// 操作用户
			_.userName = sessionStorage.getItem('arrears_userName');
			// 供电单位编号
			_.month = sessionStorage.getItem('arrears_month');
			// 操作用户
			_.consNo = sessionStorage.getItem('arrears_consNo');
						
			params = {
                    "consNo": _.consNo,
                    "month": _.month,
                    "userName": _.userName,
                    "deptNo": _.deptNo
					
                }
			
			//var apiAddress = 'http://192.168.29.246:18080/arrears/getUserArrearsDetail';

            var apiAddress = 'http://10.160.84.188:18081/arrears/getUserArrearsDetail';
			
			mui.getJSON(apiAddress, params, function(data) {
				debugger
				if (data.code != "1" || data.data.length == 0) {
					alert("未查到数据信息，请核对查询条件！");
				}else{
					_.dataList = data.data;
				}
			});
			
			
  /*           $.ajax({
                url: "http://10.160.84.188:18080/arrears/getUserArrearsDetail",
                type: "POST",
                data: {
                    "consNo": _.consNo,
                    "month": _.month,
                    "userName": _.userName,
                    "deptNo": _.deptNo,
					 //"deptNo": 21409
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
                    //option.error(self.doerror(e, err, option));
                }
            }); */
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

