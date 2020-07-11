//初始化加载事件
var vm = new Vue({
    el: '#CustomListView_KE5wR5',
    data: {
        //后台请求数据集
        dataList: []

    },
    computed: {},
    mounted: function () {
        var _ = this;
        //初始化事件
        _.initDetailEvents();


    },
    methods: {
        //工单列表
        getBasicData: function () {
            var _ = this;
            var appNo = sessionStorage.getItem('YXBP-appNo');
            //alert(appNo)
            // 供电单位编号
            var deptNo = sessionStorage.getItem('YXBP-deptNo');
            // 操作用户
            var name = sessionStorage.getItem('YXBP-userName');
			
			params = {
                    "orderNum": appNo,
                    "userName": name,
                    "searchType": 2,// 1 粗略 2 详细
                    "selectType": 1,
                    "deptNo":deptNo
                }
			
			//var apiAddress = 'http://192.168.29.238:18080/businessProcess/getMoreBusinessProcessByOrderNum';
			 var apiAddress = 'http://10.160.84.188:18081/businessProcess/getMoreBusinessProcessByOrderNum';
			
			mui.getJSON(apiAddress, params, function(data) {
				//debugger
				if (data.code != "1" || data.data.length == 0) {
					alert("未查到数据信息，请核对查询条件！");
				}else{
					_.dataList = data.data;
				}
			
			});
			
			
            //alert("工单编号："+appNo);
            //alert("编号："+deptNo);
            // alert(deptNo);
            // alert(name);
            /* $.ajax({
                url: "http://10.160.84.188:18080/businessProcess/getMoreBusinessProcessByOrderNum",
                type: "POST",
                dataType: "json",
                data: {
                    "orderNum": appNo,
                    "userName": name,
                    "searchType": 2,// 1 粗略 2 详细
                    "selectType": 1,
                    "deptNo":deptNo
                },
                contentType: "application/x-www-form-urlencoded",
                success: function (data) {
                    if (data.code != "1" || data.data.length == 0) {
                        alert("未查到流程信息，请核对查询条件！");
                        $('#detailData').css('display', 'none');

                    } else {
                        $("#detailData").css("display", "");
                            _.dataList = data.data;
                    }

                },
                error: function (data, e, err) {
                    //
                }
            }); */
        },


        initDetailEvents: function () {
            var _ = this;
            var appNo = sessionStorage.getItem('YXBP-appNo');
            var consNo = sessionStorage.getItem('YXBP-consNo');
            var SelectType = sessionStorage.getItem('YXBP-SelectType');


            // 供电单位编号
            var deptNo = sessionStorage.getItem('YXBP-deptNo');
            // 操作用户
            var name = sessionStorage.getItem('YXBP-userName');
			
			sessionStorage.setItem('YXBP-appNoReload', appNo);
            sessionStorage.setItem('YXBP-consNoReload', consNo);
            sessionStorage.setItem('YXBP-SelectTypeReload', SelectType);


            $("#Text_kxHJDJ").html(appNo);
            //加载数据
            _.getBasicData();

            // 触发事件
            // $("#Icon").click(function () {
                //数据传递
                // sessionStorage.setItem('YXBP-appNoReload', appNo);
                // sessionStorage.setItem('YXBP-consNoReload', consNo);
                // sessionStorage.setItem('YXBP-SelectTypeReload', SelectType);


                // sessionStorage.setItem("YXBP-bool", '1');

                //页面跳转
                // window.location.href = "index.html";

            // });


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
        }


    }


});










