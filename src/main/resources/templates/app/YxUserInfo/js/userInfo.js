var vm = new Vue({
    el: '#ScrollContent_yDAbi8',
    data: {
        //供电所
        dataList: [],
        consNo: "",
        deptNo: "",
        userName: "",
        index: ""
    },
    //watch: {},
    computed: {},
    mounted: function () {
        var _ = this;
        _.initEvents();
        // 切换页
        _.changeTab();
        // 户号
        _.consNo = sessionStorage.getItem("UserInfo_consNo");
        // 供电单位编号
        _.deptNo = sessionStorage.getItem('UserInfoDeptNo');
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
                // window.location = "/userInfo/gotoUserList";
                window.location.href = "/app/YxUserInfo/userSearch.html";
            });
            // 受电点点击按钮
            $("#spSearch").click(function () {
                // window.location = "/userInfo/gotoPrcType";
                window.location.href = "/app/YxUserInfo/prcType.html";
            });
            /* 弹出框方法开始 */
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
            /* 弹出框方法结束 */
        },
        //获取指标
        getBasicData: function () {
            var _ = this;

            $(".active").each(function () {
                var val = this.innerText;
                if (val.indexOf("用户基本信息") != -1) {
                    _.index = "userInfo";
                } else if (val.indexOf("电费电量") != -1) {
                    _.index = "electric";
                } else if (val.indexOf("缴费信息") != -1) {
                    _.index = "payment";
                }
            });

            $.ajax({
                url: "/userInfo/getData",
                type: "POST",
                dataType: "json",
                data: {
                    "type": "1",
                    "param": _.consNo,
                    "index": _.index,
                    "deptNo": _.deptNo,
                    "userName": _.userName,
                    "dataGetNumber": '0'
                },
                contentType: "application/x-www-form-urlencoded",
                success: function (data) {
                    if (_.index == "userInfo") {
                        getUserData(data.data.userInfo);
                    } else if (_.index == "electric") {
                        getElectricData(data.data.electric);
                    } else if (_.index == "payment") {
                        getPaymentData(data.data.payment);
                    }
                },
                error: function (e, err) {
                }
            });
        },

        changeTab: function () {
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
                e.preventDefault()
                $(".tabs .active").removeClass('active')
                $(this).addClass('active')
                tabsSwiper.slideTo($(this).index())
                // 加载数据
                vm.getBasicData();
            })
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