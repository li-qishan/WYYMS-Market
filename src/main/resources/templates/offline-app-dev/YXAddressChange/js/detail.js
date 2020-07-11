var vm = new Vue({
    el: '#ScrollContent_dj4sYO',
    data: {
        //供电所
        dataList: [],
        pageNum: 1,
        deptNo: "",
        requestUrl: 'http://192.168.29.238:18081/',
        userName: ""
    },
    //watch: {},
    computed: {},
    mounted: function () {
        var _ = this;
        // 获取平台参数
        //_.getUserBaseData();
        // 事件初始化
        _.initEvents();
        //
        _.getBasicData();
        //设置不可见
        _.setInvisiable();
        // 参数重载
        // _.reloadSearchParam();

    },
    methods: {
        // 初始化控件时间
        initEvents: function () {
            var _ = this;
            var arr = ['province', 'city', 'xian', 'jie'];
            var provinceIndex;
            var province = $('#proviceSelect');
            //var city = $('#city');
            // 修改事件
            $("#btnchange").click(function () {
                alert("修改事件触发");
                //设置为保存
                $("#btnchange").val("保 存");
                //设置文本可编辑
                _.setVisiable();
                //默认将区县全部取出
                _.getCountryAreaData();

                $("#btnchange").click(function () {

                    $(this).val("正在保存...");
                    setTimeout(function () {
                        $("#btnchange").val("保 存");
                        //按钮变成不可点击
                        $("#btnchange").attr("disabled", "disabled");


                    }, 2000);

                    _.setInvisiable();

                    window.location.href = "search.html";

                });


            });

            /*选中区县的方法*/
            $('#areaSelect').change(function () {
                //alert("1111");
                //获取value
                var countryCode = $(this).val();
                //alert(countryCode);
                var thisId = $(this).attr('id');

                alert(thisId);
                sessionStorage.setItem("address_countryCode", countryCode);
                _.getStreetAndRoadData();
                // //清空子元素
                _.clearSelect(thisId);

            });


            /*选中街道的方法 type=Community */
            $('#streetSelect').change(function () {
                //alert("1111");
                //获取value
                var streetCode = $(this).val();
                alert(streetCode);
                //獲取id
                var thisId = $(this).attr('id');
                alert(thisId);
                sessionStorage.setItem("address_streetCode", streetCode);
                var type = 'Community';
                _.getCommunityAndSmallData(type);
                // //清空子元素 只有 社区 小区  道路保留
                _.clearSelectExpect(thisId);

            });


            /*选中社区的方法 type=Community */
            $('#neighborhoodSelect').change(function () {
                //alert("1111");
                //获取value
                var neighborhoodCode = $(this).val();
                //alert(neighborhoodCode);
                //獲取id
                var thisId = $(this).attr('id');
                //alert(thisId);
                sessionStorage.setItem("address_streetCode", neighborhoodCode);
                var type = 'Small';
                _.getCommunityAndSmallData(type);
                // //清空子元素 只有 社区 小区  道路保留
                _.clearSelectExpect(thisId);

            });

            //失去焦点事件
            // $('#doorOrder').onblur(function () {
            //
            //     alert("11");
            // });
            var previous;

            $('.change').on('focus', function () {

                var index = this.selectedIndex; //序号，取当前选中选项的序号
                debugger
                //获取之前的已选的option、
                previous = this.options[index].label;

            }).change(function () {
                // 获取当前操作Id
                var thisId = $(this).attr('id');
                //之前选择的文本值
                alert(previous);

                //判断是否为空
                if ($("#address").val() == "" || $("#address").val() == null) {
                    var city = $("#citySelect").find("option:selected").text();
                    var area = $("#areaSelect").find("option:selected").text();
                    //$("#address").val = city;
                    document.getElementById('address').value = city + area;

                }
                //不爲空将从那个改变的 进行开始分割 删除
                else {
                    //获取当前的地址信息
                    var currentAddress = $("#address").val();
                    alert(currentAddress);

                    var ipos = currentAddress.indexOf(previous);

                    var newAddress = currentAddress.substring(0, ipos);

                    alert("111" + newAddress);
                    //

                    $("#address").val($("#address").val() + $(this).find("option:selected").text());

                }

            });

            //change

            // $('.change').change(function () {
            //
            //     $(".change").each(function () { //jquery取出所有包含class='change'的值
            //
            //
            //     });
            //
            // });

        },


        //获取数据
        getBasicData: function () {
            //debugger
            var _ = this;

            //var consNo = sessionStorage.getItem("address_search_param");

            params = {
                consNo: "0212665080",
                userName: 'cy_khgx'
            }

            var apiAddress = _.requestUrl + '/address/getUserAddressDetail';
            //var apiAddress = 'http://10.160.84.188:18081/arrears/getUserArrearsList';

            mui.getJSON(apiAddress, params, function (data) {
                //debugger
                if (data.code != "1" || data.data.length == 0) {
                    alert("未查到用户地址信息，请核对查询条件！");
                    //$("#ScrollContent_RQF9yx").css("display", "none");
                } else {
                    $("#Text_IHb9o9").html(data.data[0].cons_NO);
                    $("#Text_KtueQw").html(data.data[0].cons_NAME);
                    $("#addressSelect").html("<option >" + data.data[0].addr_TYPE + "</option>");
                    //$("#citySelect").val(data.data[0].city);
                    $("#proviceSelect").html("<option  value= \"" + data.data[0].province_CODE + " \">" + "辽宁省" + "</option>");
                    $("#citySelect").html("<option value=\"" + data.data[0].city_CODE + " \">" + data.data[0].city + "</option>");
                    $("#areaSelect").html("<option value=\"" + data.data[0].county_CODE + " \">" + data.data[0].county + "</option>");
                    $("#streetSelect").html("<option value=\"" + data.data[0].streetcode + " \">" + data.data[0].streetname + "</option>");
                    $("#neighborhoodSelect").html("<option value=\"" + data.data[0].villagecode + " \">" + data.data[0].villagename + "</option>");
                    $("#roadSelect").html("<option value=\"" + data.data[0].rodecode + " \">" + data.data[0].roadname + "</option>");
                    //小区
                    $("#smallSelect").html(data.data[0].communityname === "undefined" || data.data[0].communityname === "null" ?
                        "<option value=\"" + data.data[0].communitycode + " \">" + data.data[0].communityname + "</option>" : "<option>" + "空" + "</option>"
                    );

                    $("#doorOrder").val(data.data[0].plateno);
                    $("#doorSign").val(data.data[0].building);
                    $("#address").val(data.data[0].elec_ADDR);
                }


            });


        },
        //获取区县数据
        getCountryAreaData: function () {
            debugger
            var _ = this;

            //区县id
            var province = $('#areaSelect');

            var consNo = sessionStorage.getItem("address_search_param");

            params = {
                consNo: consNo,
                userName: 'cy_khgx'
            }

            var apiAddress = _.requestUrl + '/address/getUserCountryArea';
            //var apiAddress = 'http://10.160.84.188:18081/arrears/getUserArrearsList';

            mui.getJSON(apiAddress, params, function (data) {
                debugger
                if (data.code != "1" || data.data.length == 0) {
                    alert("未查到区县信息，请核对查询条件！");
                    //$("#ScrollContent_RQF9yx").css("display", "none");
                } else {
                    //alert(data.data);
                    //将数据渲染到 区县标签中
                    var countryData = data.data;

                    //清空原有的数据
                    $('#areaSelect').empty();
                    //拼接后增加默认选择 --请选择--
                    $('#areaSelect').append("<option selected disabled>- - 请选择 - -</option>");

                    countryData.forEach(function (item, index) {
                        //生产option子元素
                        var option = $("<option value='" + item.district_NO + "'>" + item.district_NAME + "</option>");

                        // 添加金省份
                        province.append(option);

                    })
                    //清除无用数据
                    _.clearSelect('areaSelect');


                }


            });


        },

        //获取街道、道路
        getStreetAndRoadData: function () {
            debugger
            var _ = this;

            //街道
            var street = $('#streetSelect');

            //道路
            var road = $('#roadSelect');

            var countryCode = sessionStorage.getItem("address_countryCode");
            var consNo = sessionStorage.getItem("address_search_param");
            //var userName = $("#Text_KtueQw").val();
            //alert(userName);
            //var consNo = sessionStorage.getItem("address_search_param");

            params = {
                consNo: consNo,
                countyCode: countryCode,
                userName: 'cy_khgx'
            }

            var apiAddress = _.requestUrl + '/address/getUserStreetAndRoad';
            //var apiAddress = 'http://10.160.84.188:18081/arrears/getUserArrearsList';

            mui.getJSON(apiAddress, params, function (data) {
                debugger
                if (data.code != "1") {
                    if (data.street.length == 0) {
                        alert("未查到街道信息，请核对查询条件！");
                    }
                    if (data.road.length == 0) {
                        alert("未查到道路信息，请核对查询条件！");
                    }
                    //$("#ScrollContent_RQF9yx").css("display", "none");
                } else {
                    //alert(data.street);
                    //alert(data.road);
                    //将数据渲染到 区县标签中
                    var streetData = data.street;
                    var roadData = data.road;
                    //
                    streetData.forEach(function (item, index) {
                        //生产option子元素
                        var option = $("<option value='" + item.value + "'>" + item.name + "</option>");
                        // 添加金省份
                        street.append(option);

                    });

                    roadData.forEach(function (item, index) {
                        //生产option子元素
                        var option = $("<option value='" + item.value + "'>" + item.name + "</option>");
                        // 添加金省份
                        road.append(option);

                    });


                }


            });


        },


        //获取街道、道路
        getCommunityAndSmallData: function (type) {
            debugger
            var _ = this;

            //社区
            var community = $('#neighborhoodSelect');

            //社区
            var small = $('#smallSelect');

            var countryCode = sessionStorage.getItem("address_countryCode");
            var pCode = sessionStorage.getItem("address_streetCode");
            var consNo = sessionStorage.getItem("address_search_param");

            params = {
                consNo: consNo,
                countyCode: countryCode,
                pCode: pCode,
                type: type,
                userName: 'cy_khgx'
            }

            var apiAddress = _.requestUrl + '/address/getUserCommunityAndSmall';
            //var apiAddress = 'http://10.160.84.188:18081/arrears/getUserArrearsList';

            mui.getJSON(apiAddress, params, function (data) {
                debugger
                if (data.code != "1" || data.data.length == 0) {
                    alert("未查到街道或道路信息，请核对查询条件！");
                    //$("#ScrollContent_RQF9yx").css("display", "none");
                } else {
                    if (type === 'Community') {
                        //将数据渲染到 社区标签中
                        var CommunityData = data.data;

                        CommunityData.forEach(function (item, index) {
                            //生产option子元素
                            var option = $("<option value='" + item.value + "'>" + item.name + "</option>");
                            // 添加金省份
                            community.append(option);

                        });
                    }

                    if (type === 'Small') {
                        //将数据渲染到 社区标签中
                        var SmallData = data.data;

                        SmallData.forEach(function (item, index) {
                            //生产option子元素
                            var option = $("<option value='" + item.value + "'>" + item.name + "</option>");
                            // 添加金省份
                            small.append(option);

                        });
                    }


                    //
                    // roadData.forEach(function (item, index) {
                    //     //生产option子元素
                    //     var option = $("<option value='" + item.value + "'>" + item.name + "</option>");
                    //     // 添加金省份
                    //     road.append(option);
                    //
                    // });


                }


            });


        },

        // getUserBaseData: function () {
        //
        //     if (sessionStorage.getItem("arrears_bool") != '1') {
        //         var _ = this;
        //         // 获取路径中参数
        //         var base_data = _.getParams("userObj");
        //         //alert(base_data);
        //         // 因为双引号的原因，需转两次json
        //         var obj = JSON.parse(base_data);
        //         //alert(obj);
        //         obj = JSON.parse(obj);
        //         //alert(obj);
        //         // 获取平台用户名
        //         //alert(obj.data.namecode);
        //         _.userName = obj.data.namecode;
        //         // 只有在第一次加载才去获取
        //         if (Arrears_bool != '1') {
        //             // 供电单位编号
        //             sessionStorage.setItem("arrears_userName", _.userName);
        //
        //         }
        //
        //     }
        //
        //
        // },


        //获取上一个页面携带的参数
        getParams: function (key) {
            var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) {
                return decodeURI(r[2]);
            }
            return null;
        },


        setInvisiable: function () {

            //当页面加载时 即设置 页面属性 为可读不可写
            $("#addressSelect").attr("disabled", "disabled");
            $("#Select_NFJ3kA").css("background-color", "#cccccc");

            $("#proviceSelect").attr("disabled", "disabled");
            $("#Select_OVDYIX").css("background-color", "#cccccc");

            $("#citySelect").attr("disabled", "disabled");
            $("#Select_BRnsHw").css("background-color", "#cccccc");

            $("#areaSelect").attr("disabled", "disabled");
            $("#Select_T9p8Ba").css("background-color", "#cccccc");

            $("#streetSelect").attr("disabled", "disabled");
            $("#Select_MKCC83").css("background-color", "#cccccc");

            $("#neighborhoodSelect").attr("disabled", "disabled");
            $("#Select_tgVGlk").css("background-color", "#cccccc");

            $("#roadSelect").attr("disabled", "disabled");
            $("#Select_tLg4TP").css("background-color", "#cccccc");

            $("#smallSelect").attr("disabled", "disabled");
            $("#Select_ryObwX").css("background-color", "#cccccc");

            $("#doorOrder").attr("disabled", "disabled");
            $("#Input_XUDwH7").css("background-color", "#cccccc");

            $("#doorSign").attr("disabled", "disabled");
            $("#Input_qEMB6E").css("background-color", "#cccccc");

            $("#address").attr("disabled", "disabled");
            $("#Input_P7BbQb").css("background-color", "#cccccc");

        },

        setVisiable: function () {


            // $("#addressSelect").removeAttr("disabled");
            // $("#Select_NFJ3kA").css("background-color", "");

            $("#proviceSelect").removeAttr("disabled");
            $("#Select_OVDYIX").css("background-color", "");

            $("#citySelect").removeAttr("disabled");
            $("#Select_BRnsHw").css("background-color", "");

            $("#areaSelect").removeAttr("disabled");
            $("#Select_T9p8Ba").css("background-color", "");

            $("#streetSelect").removeAttr("disabled");
            $("#Select_MKCC83").css("background-color", "");

            $("#neighborhoodSelect").removeAttr("disabled");
            $("#Select_tgVGlk").css("background-color", "");

            $("#roadSelect").removeAttr("disabled");
            $("#Select_tLg4TP").css("background-color", "");

            $("#smallSelect").removeAttr("disabled");
            $("#Select_ryObwX").css("background-color", "");

            $("#doorOrder").removeAttr("disabled");
            $("#Input_XUDwH7").css("background-color", "");

            $("#doorSign").removeAttr("disabled");
            $("#Input_qEMB6E").css("background-color", "");

            $("#address").removeAttr("disabled");
            $("#Input_P7BbQb").css("background-color", "");


        },

        /*清空select方法：当当前select被选中的时候要清空之后的几个select的内容**/
        clearSelect: function (thisID) {
            var arr = ['areaSelect', 'streetSelect', 'roadSelect', 'neighborhoodSelect', 'smallSelect', 'doorOrder', 'doorSign', 'address'];
            // 返回arr所用
            var index = arr.indexOf(thisID);
            //判断所有是否存在
            if (index >= 0) {
                //清除index之后的select
                for (var i = index + 1; i < arr.length; i++) {
                    //首先获取类型 判断是什么类型
                    //var type = $('#' + arr[i] + '').attr('type');
                    if (arr[i] === "doorOrder" || arr[i] === "doorSign" || arr[i] === "address") {
                        $('#' + arr[i] + '').val("");
                    }
                    else {
                        $('#' + arr[i] + '').empty();
                        $('#' + arr[i] + '').append("<option selected disabled>- - 请选择 - -</option>");
                    }
                }
            }
        },
        /*清空select方法：社区 小区 **/
        clearSelectExpect: function (thisID) {
            debugger
            var arr = ['streetSelect', 'roadSelect', 'neighborhoodSelect', 'smallSelect', 'roadSelect'];
            // 返回arr所用
            var index = arr.indexOf(thisID);
            //判断所有是否存在
            if (index >= 0) {
                //清除index之后的select
                for (var i = index + 1; i < arr.length; i++) {

                    if (arr[i] === 'roadSelect') {
                        $('#' + arr[i] + '').get(0).selectedIndex = 0;
                    } else {

                        $('#' + arr[i] + '').empty();
                        $('#' + arr[i] + '').append("<option selected disabled>- - 请选择 - -</option>");

                    }

                }
            }
        }
    }
});


