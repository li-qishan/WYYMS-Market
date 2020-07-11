var vm = new Vue({
    el: '#CustomListView_4sKwrQ',
    data: {
        //后台请求数据集
        dataList: []

    },
    computed: {},
    mounted: function () {
        var _ = this;
        //初始化事件
        _.initEvents();


    },
    methods: {
        //用户明细列表
        addNewData: function () {

            var isSign = "0";

            var _= this;

            // 供电单位编号
            var deptNo = sessionStorage.getItem('YXPC-deptNo');
            // 联系人类型
            var index = sessionStorage.getItem('YXPC-index');
            // 操作人
            var name = sessionStorage.getItem('YXPC-userName');
            // 操作户号
            var consNo = sessionStorage.getItem('YXPC-consNo');
            // 用户名
            var consName = sessionStorage.getItem("YXPC-consName");
            //alert(consName);
            //alert($("#mobile").val());

            $.ajax({
                url : "/phone/userPhoneOpera",
                type : "POST",
                data : {
                    "consNo" : consNo,
                    "consName" : consName,
                    "userName" : name,
                    "index" : index,
                    "operType" : "0",// 0新增，1删除，2更新
                    "isSign" : "0",// 0否,1是
                    "oldPhone" : "####",
                    "phone" : $("#mobile").val(),
                    "deptNo" : deptNo
                },
                dataType : "json",
                contentType : "application/x-www-form-urlencoded",
                success : function(data) {
                    if (data.code == 1) {
                        //alert(data.msg);
                        window.location.href = "/app/YXPhoneChange/renew.html";
                    }
                },
                error : function(e, err) {
                    //option.error(_.doerror(e, err, option));
                }
            });

        },

        initEvents: function () {
            var _= this;
            // 电话号验证
            $("#mobile").on("change",function(){
                var phone = $(this).val();
                //debugger
                if(phone == "" || phone == null || !(/^1(3|4|5|7|8)\d{9}$/.test(phone))
                    && !(/\d{3}-\d{8}|\d{4}-\d{7}/.test(phone))){// 验证固定电话
                    alert("请输入正确号码！");
                    $(this).val("");
                    return;
                }
            })
            $("#commitSave").on("click",function(){
                //var _ = this;
                //触发Ajax 方法
                //alert("触发了保存方法");
				_.addNewData();


            });

            // 返回按钮
            $("#Icon").on('click', function(e) {
                window.location.href = "/app/YXPhoneChange/renew.html";
            });
            // 设置页面数据
            var consName = sessionStorage.getItem('YXPC-consName');
            //alert(consName)
            $("#userConsName").html(consName);



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

            window.location="/phone/gotoPhoneDetail";
        },


    }
});
