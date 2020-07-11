var vm = new Vue({
    el: '#CustomListView_4sKwrQ',
    data: {
        //后台请求数据集
        dataList: [],
        dataListUpdate: [],
        index: "",
        deptNo: "",
        userName: ""
    },
    computed: {},
    mounted: function () {
        var _ = this;
        //初始化事件
        _.initEvents();
        //reload
        _.reloadBaseData();
        // 获取平台参数
        _.getUserBaseData();


    },
    methods: {
        //用户明细列表
        getBasicData: function () {
            //var httpUrl = 'http://192.168.29.238:18081/';
            var httpUrl = 'http://10.160.84.188:18081/';
            var _ = this;
            // 供电单位编号
            // var deptNo = sessionStorage.getItem('YXPC-deptNo');
            // 操作用户
            // var name = sessionStorage.getItem('YXPC-userName');
            //获取数值
            var type = $("#searchSelect").val();
            var param = $("#searchInput").val();

            //获取index
            var clickHtml = "";
            // var index = "";
            $(".active").each(function () {
                var val = this.innerText;
                clickHtml = val;
                if (val.indexOf("用户明细列表") != -1) {
                    index = "userInfo";
                } else if (val.indexOf("已更改列表") != -1) {
                    index = "userUpdate";
                }
            });
            // 用户列表
            if (index === "userInfo") {
                // 请求参数
                params = {
                    "type": type,
                    "param": param,
                    "index": index,
                    "userName": _.userName,
                    "deptNo": '21409'

                }
                // 服务地址
                //var apiAddress = 'http://10.160.84.188:18080/phone/getUserList';
                var apiAddress = httpUrl + '/phone/getUserList';
                // 获取数据
                mui.getJSON(apiAddress, params, function (data) {
                    if (data.code != "1" || data.data.length == 0) {
                        alert("未查到用户明细列表信息，请核对查询条件！");
                    } else {
                        _.dataList = data.data;
                    }
                });
            }
            // 已变更列表
            else if (index === "userUpdate") {

                params = {
                    "userName": _.userName
                    //"userName": 'lmj2_cy'
                }

                var apiAddress = httpUrl + '/phone/getUpdatePhoneList';

                //var apiAddress = 'http://192.168.29.238:18080/phone/getUpdatePhoneList';

                mui.getJSON(apiAddress, params, function (data) {
                    if (data.code != "1" || data.data.length == 0) {
                        alert("未查到已更新列表信息！");
                    } else {
                        _.dataListUpdate = data.data;
                    }
                });
            }
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

                    // 供电单位编号
                    sessionStorage.setItem("YXPC-userName", _.userName);
        },

        initEvents: function () {
            var _ = this;
            // 供电单位编号
            var deptNo = sessionStorage.getItem('YXPC-deptNo');
            // 操作用户
            var name = sessionStorage.getItem('YXPC-userName');

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


            $("#searchBtn").click(
                function () {
                    //alert("click-reload");
                    if ($("#searchSelect").val() == ""
                        || $("#searchInput").val() == "") {
                        alert("请填写查询条件！");
                        return;
                    } else {
                        _.getBasicData();

                    }


                }
            );

            var tabsSwiper = new Swiper('#tabs-container', {
                speed: 500,
                autoHeight: true,
                observer: true,
                observeParents: true,
                on: {
                    slideChangeTransitionStart: function () {
                        $(".tabs .active").removeClass('active');
                        $(".tabs a").eq(this.activeIndex).addClass('active');
                    }
                }
            })

            $(".tabs a").on('click', function (e) {
                // alert("reload");

                e.preventDefault()
                $(".tabs .active").removeClass('active')
                $(this).addClass('active')
                tabsSwiper.slideTo($(this).index())
                var tabHtml = $(".tabs .active")[0].innerHTML;
                if (tabHtml == "用户明细列表") {
                    _.getBasicData();
                } else if (tabHtml == "已更改列表") {
                    _.getBasicData();
                }
            });


        },

        reloadBaseData: function () {
            var _ = this;
            debugger
            var type = sessionStorage.getItem('YXPC-typeReload');
            var param = sessionStorage.getItem('YXPC-paramReload');
            var isSign = sessionStorage.getItem('YXPC-isSign');
            //alert(isSign) 强制 input 不为空 才 reload
            if (isSign === "1" & ($("#searchInput").val() != null || $("#searchInput").val() != "")) {
                document.getElementById("searchSelect").value = type;
                document.getElementById("searchInput").value = param;
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


        optionClick: function (consNo, consName) {

            var _ = this;
            // alert(value);
            sessionStorage.setItem("YXPC-consNo", consNo);
            sessionStorage.setItem("YXPC-consName", consName);

            sessionStorage.setItem("YXPC-userName", _.userName);
            sessionStorage.setItem("YXPC-deptNo", _.deptNo);
            sessionStorage.setItem("YXPC-bool", '1');

            var type = $("#searchSelect").val();
            var param = $("#searchInput").val();
            sessionStorage.setItem("YXPC-typeReload", type);
            sessionStorage.setItem("YXPC-paramReload", param);
            sessionStorage.setItem("YXPC-isSign", "1");
            //alert(type + "" + param);
            window.location.href = "renew.html";
        }

    }
});
