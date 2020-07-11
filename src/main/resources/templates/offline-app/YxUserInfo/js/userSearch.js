var vm = new Vue({
    el: '#ScrollContent_yDAbi8',
    data: {
        //供电所
        dataList: [],
        deptNo:"",
        userName:""

    },
    //watch: {},
    computed: {},
    mounted: function () {
        var _ = this;
		 // 获取平台参数
        _.getUserBaseData();
		// 初始化控件
        _.initEvents();
        // 重载参数
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
        searchClick: function (consNo) {

            sessionStorage.setItem("UserInfo_consNo", consNo);
			
			UserInfo_bool = UserInfo_bool + 1;

            window.location.href = "userInfo.html";

        },

        getUserBaseData: function () {
			//debugger
			var _ = this;
            // 获取路径中参数
            var base_data = _.getParams("userObj")
            // 因为双引号的原因，需转两次json
            var obj = JSON.parse(base_data);
            obj = JSON.parse(obj);
			// 获取平台用户名
            _.userName = obj.data.namecode;
			// 只有在第一次加载才去获取
            if (UserInfo_bool != '1') {
					// 供电单位编号
					sessionStorage.setItem("UserInfoUserName", _.userName);
				/* // 根据用户名获取供电单位编码
				params = {
					"userName": _.userName
				}
				// 请求地址
				var apiAddress = 'http://10.160.84.188:18080/user/login/getUserOrgNo';
				// 获取供电单位编码
				mui.getJSON(apiAddress, params, function (data) {
					_.deptNo = data.data.deptNo;
										
					// 供电单位编号
					sessionStorage.setItem("UserInfoUserName", _.userName);
					// 操作用户
					sessionStorage.setItem("UserInfoDeptNo",  _.deptNo);
					
				}); */
			}
        },
        //获取数据
        getBasicData: function () {
            var _ = this;

			// 供电单位编号
			_.deptNo = sessionStorage.getItem('UserInfoDeptNo');
			// 操作用户
			_.userName = sessionStorage.getItem('UserInfoUserName');

            var type = $("#searchSelect").val();
            var param = $("#searchInput").val();
			
			params = {
                    "type": type,
                    "param": param,
                    "userName": _.userName,
                "deptNo": _.deptNo
                //"deptNo": 21409
                }

            var apiAddress = 'http://10.160.84.188:18081/userInfo/searchData';

            //var apiAddress = 'http://192.168.29.238:18080/userInfo/searchData';
			
			mui.getJSON(apiAddress, params, function(data) {
				//debugger
				  if (data.code != "1" || data.data.length == 0) {
                        alert("未查到用户信息，请核对查询条件！");
                        $("#ScrollContent_yDAbi8").css('display', 'none');

                    } else {
                        sessionStorage.setItem("UserInfo_search_type", type);
                        sessionStorage.setItem("UserInfo_search_param", param);

                        $("#ScrollContent_yDAbi8").css("display", "");
                        // debugger
                        _.dataList = data.data;
                    }
			});


        },
        reloadSearchParam: function () {
            var _ = this;
            // 查询条件重载
            var search_type = sessionStorage.getItem('UserInfo_search_type');
            var search_param = sessionStorage.getItem('UserInfo_search_param');

            if (search_type != null && search_type != "" && search_param != null && search_param != "") {
                document.getElementById("searchSelect").value = search_type;
                document.getElementById("searchInput").value = search_param;
                //重新加载数据
                _.getBasicData();
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
