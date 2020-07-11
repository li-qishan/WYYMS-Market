var vm = new Vue({
    el: '#CustomListView_NMhIw9',
    data: {
        //后台请求数据集
        dataList: [],
		deptNo:"",
        userName:""

    },
    computed: {},
    mounted: function () {
        var _ = this;
        //初始化事件
        _.initEvents();
		//数据重载
		_.reloadBaseData();
		// 获取平台参数
        _.getUserBaseData();
		
    },
    methods: {
        //工单列表
        getBasicData: function () {
            //debugger

            var _ = this;
            // 供电单位编号
            // var deptNo = sessionStorage.getItem('YXBP-deptNo');
            // 操作用户
            // var name = sessionStorage.getItem('YXBP-userName');
            var orderNum = $("#searchInput").val();
            var SelectType = $("#searchSelect").val();
			
			params = {
                    "orderNum": orderNum,
                    "userName": _.userName,
                    "searchType": 1,
                    "selectType": SelectType,
                    "type": 4,
                    "deptNo": _.deptNo//非写死
                }
			
			var apiAddress = 'http://10.160.84.188:18081/businessProcess/getBusinessProcessByOrderNum';
			//var apiAddress = 'http://192.168.29.238:18080/businessProcess/getBusinessProcessByOrderNum';
			
			mui.getJSON(apiAddress, params, function(data) {
				//debugger
				if (data.code != "1") {
                        alert("未查到数据信息，请核对查询条件！");
                    } else {
                        if (data.data.length == 0) {
                            alert("未查到数据信息，请核对查询条件！");
                        } else {
                            //debugger
                            _.dataList = data.data;

                        }
                    }
			});
            //alert("工单编号或户号：" + orderNum);
            //alert("查询类别：" + SelectType);
            /* $.ajax({
                 url: "http://10.160.84.188:18080/businessProcess/getBusinessProcessByOrderNum",
				
				url: "http://192.168.29.246:18080/businessProcess/getBusinessProcessByOrderNum", 
                type: "POST",
                dataType: "json",
                data: {
                    "orderNum": orderNum,
                    "userName": name,
                    "searchType": 1,
                    "selectType": SelectType,
                    "type": 4,
                    "deptNo": deptNo//非写死
                },
                contentType: "application/x-www-form-urlencoded",
                success: function (data) {
                    if (data.code != "1") {
                        alert("未查到数据信息，请核对查询条件！");
                    } else {
                        if (data.data.length == 0) {
                            alert("未查到数据信息，请核对查询条件！");
                        } else {
                            debugger
                            _.dataList = data.data;

                        }
                    }

                },
                error: function (e, err) {

                }
            });  */


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
			
			// 供电单位编号
            sessionStorage.setItem("YXBP-userName", _.userName);
			
            //if (sessionStorage.getItem('YXBP-bool') != '1') {
			//根据用户名获取供电单位编码
              // params = {
               // "userName": _.userName
            //} 

            // 请求地址
            //var apiAddress = 'http://192.168.29.238:18080/user/login/getUserOrgNo';
		    // var apiAddress = 'http://10.160.84.188:18080/user/login/getUserOrgNo';
            //获取供电单位编码
            // mui.getJSON(apiAddress, params, function (data) {
             // _.deptNo = data.data.deptNo;
                    
           
			//操作用户
             //sessionStorage.setItem("YXBP-deptNo",  _.deptNo);
          
           // });
        
           //}

        },
		reloadBaseData: function () {
		//debugger
		var _= this;
		  // 页面回退时
            var appNoReload = sessionStorage.getItem('YXBP-appNoReload');
            var consNoReload = sessionStorage.getItem('YXBP-consNoReload');
            var SelectTypeReload = sessionStorage.getItem('YXBP-SelectTypeReload');
            // 判断 第一次加载 为空 回显不为空
            if (!_.isEmpty(appNoReload) && !_.isEmpty(SelectTypeReload)) {
                if (SelectTypeReload == "1") {// 户号查询
                    document.getElementById("searchSelect").value = SelectTypeReload;
                    document.getElementById("searchInput").value = consNoReload;
                }
                if (SelectTypeReload == "4") {// 工单编号查询
                    document.getElementById("searchSelect").value = SelectTypeReload;
                    document.getElementById("searchInput").value = appNoReload;
                }


                // 调用重新加载
                _.getBasicData();
                // 清除变量
                sessionStorage.removeItem('YXBP-appNoReload')
                sessionStorage.removeItem('YXBP-consNoReload')
                sessionStorage.removeItem('YXBP-SelectTypeReload')

		}
		},

        initEvents: function () {

            var _ = this;

            // alert(_.getParams("userName"));
            // alert(_.getParams("deptNo"));

            // if (sessionStorage.getItem('YXBP-bool') != '1') {
				//alert('aaa');
                /* sessionStorage.setItem("YXBP-userName", _.getParams("userName"));
                sessionStorage.setItem("YXBP-deptNo", _.getParams("deptNo")); */
				// sessionStorage.setItem("YXBP-userName", 'test');
				// sessionStorage.setItem("YXBP-deptNo", '21409');
            // }


            // 供电单位编号
            // var deptNo = sessionStorage.getItem('YXBP-deptNo');
            // 操作用户
            // var name = sessionStorage.getItem('YXBP-userName');

            //alert(deptNo + name);

          

            $("#searchBtn").click(
                function () {
                    // alert("reload");
                    if ($("#searchSelect").val() == ""
                        || $("#searchInput").val() == "") {
                        alert("查询条件未填选！");
                        return;
                    }
                    else {
                        //触发调用
                        _.getBasicData();
                    }
                });

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
                // 存在的问题： 当点击返回时怎么处理
                //alert("返回事件触发");
                window.location.href = "";
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

        //获取上一个页面携带的参数
        getParams: function (key) {
            var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) {
                return decodeURI(r[2]);
            }
            return null;
        },


        //判断字符是否为空的方法
        isEmpty: function (obj) {
            if (typeof obj == "undefined" || obj == null || obj == "") {
                return true;
            } else {
                return false;
            }
        },

        operate: function (item, event) {
            var _ = this;
            // 供电单位编号
            // var deptNo = sessionStorage.getItem('YXBP-deptNo');
            // 操作用户
            // var name = sessionStorage.getItem('YXBP-userName');

            sessionStorage.setItem("YXBP-appNo", item.APPNO);
            sessionStorage.setItem("YXBP-SelectType", $("#searchSelect").val());
            sessionStorage.setItem("YXBP-consNo", item.CONSNO);

            sessionStorage.setItem("YXBP-userName", _.userName);
            sessionStorage.setItem("YXBP-deptNo", _.deptNo);
			sessionStorage.setItem("YXBP-bool", '1');
            window.location.href = "businessDetail.html";
        },


    }


});
