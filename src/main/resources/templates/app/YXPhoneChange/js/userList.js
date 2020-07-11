var vm = new Vue({
    el: '#CustomListView_4sKwrQ',
    data: {
        //后台请求数据集
        dataList: [],
        dataListUpdate:[],
        index:""
    },
    computed: {},
    mounted: function () {
        var _ = this;
        //初始化事件
        _.initEvents();


    },
    methods: {
        //用户明细列表
        getBasicData: function () {

            var _ = this;
            // 供电单位编号
            var deptNo = sessionStorage.getItem('YXPC-deptNo');
            // 操作用户
            var name = sessionStorage.getItem('YXPC-userName');
            //获取数值
            var type = $("#searchSelect").val();
            var param = $("#searchInput").val();

            //获取index
            var clickHtml = "";
            // var index = "";
            $(".active").each(function() {
                var val = this.innerText;
                clickHtml = val;
                if (val.indexOf("用户明细列表") != -1) {
                    index = "userInfo";
                } else if (val.indexOf("已更改列表") != -1) {
                    index = "userUpdate";
                }
            });


            if(index==="userInfo"){
                $.ajax({
                    url: "/phone/getUserList",
                    type: "POST",
                    dataType: "json",
                    data: {
                        "type": type,
                        "param": param,
                        "index" : index,
                        "userName": name,
                        "deptNo": deptNo
                    },
                    contentType: "application/x-www-form-urlencoded",
                    success: function (data) {

                        if (data.code != "1") {
                            alert("未查到用户明细列表信息，请核对查询条件！");
                        } else {
                            if (data.data.length == 0) {
                                alert("未查到用户明细列表信息，请核对查询条件！");
                            } else {
                                // alert("1"+data.data);
                                _.dataList=data.data;
                            }
                        }

                    },
                    error: function (e, err) {


                    }
                });

            }else if(index==="userUpdate") {
                $.ajax({
                    url :"/phone/getUpdatePhoneList",
                    type : "POST",
                    dataType : "json",
                    data : {
                        "userName": name
                    },
                    contentType : "application/x-www-form-urlencoded",
                    success : function(data) {
                        if (data.code != "1") {
                            alert("未查到已更新列表信息！");
                        } else {
                            if (data.data.length == 0) {
                                alert("未查到已更新列表信息！");
                            } else {
                                // alert("2"+data.data);
                                _.dataListUpdate=data.data;
                            }
                        }

                    },
                    error : function(e, err) {


                    }
                });

            }



        },

        initEvents: function () {

            var _ = this;

            if (sessionStorage.getItem('YXPC-bool') != '1') {
                sessionStorage.setItem("YXPC-userName", _.getParams("userName"));
                sessionStorage.setItem("YXPC-deptNo", _.getParams("deptNo"));
            }


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
                    alert("click-reload");
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

        //获取上一个页面携带的参数
        getParams: function (key) {
            var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) {
                return decodeURI(r[2]);
            }
            return null;
        },


        optionClick:function(value){

            // alert(value);
            sessionStorage.setItem("YXPC-consNo", value);

            // 供电单位编号
            var deptNo = sessionStorage.getItem('YXPC-deptNo');
            // 操作用户
            var name = sessionStorage.getItem('YXPC-userName');


            sessionStorage.setItem("YXPC-userName", name);
            sessionStorage.setItem("YXPC-deptNo", deptNo);

            window.location.href = "/app/YXPhoneChange/renew.html";
        },

    }
});
