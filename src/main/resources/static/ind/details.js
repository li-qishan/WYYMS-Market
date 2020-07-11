/**
 * Created by Liangzhu on 2018/7/10.
 */
$(document).ready(function () {
    //获取窗口的高度
    var windowHeight;
//获取窗口的宽度
    var windowWidth;
//获取弹窗的宽度
    var popWidth;
//获取弹窗高度
    var popHeight;

    function init() {
        windowHeight = $(window).height();
        windowWidth = $(window).width();
        popHeight = $(".window").height();
        popWidth = $(".window").width();
    }
//关闭窗口的方法
    function closeWindow() {
        $(".close_btn").click(function () {
            $('.window').hide("slow");
            $('.mask').css('display', 'none');
        });
        $(".save").click(function () {
            $('.window').hide("slow");
            $('.mask').css('display', 'none');
        });
    }

    //定义弹出居中窗口的方法
    function popCenterWindow() {
        init();
        //计算弹出窗口的左上角X的偏移量
        var popX = (windowWidth - popWidth) / 2;
        // 计算弹出窗口的左上角Y的偏移量为窗口的高度 - 弹窗高度 / 2 + 被卷去的页面的top
        var popY = (windowHeight - popHeight) / 2 + $(document).scrollTop();
        //设定窗口的位置
        $("#center").css("top", popY).css("left", popX).slideToggle("fast");
        closeWindow();
        //save();
    }

    var vm = new Vue({
        el: '#indexBody',
        data: {  //记录子窗体
            lw: null,
            //登录用户名密码
            userInfo: {username: "", userpwd: "", rusername: ""},
            //记录登录状态
            login: false,
            pageNum: 1,
            parms: {indId: 0, deptId: '', ymd: '',total:''},
            detailData: {},
            dataList:{},
            indData: {},
            checkedExportCols: [],
            checkedShowCols: []
        },
        mounted: function () {
            var _ = this;
            _.parms.ymd = jsHelp.getQueryString("ymd");
            _.parms.deptId = jsHelp.getQueryString("dept");
            _.parms.indId = parseInt(jsHelp.getQueryString("id"));
            _.parms.total = jsHelp.getQueryString("total");

            // this.detailListAjaxFun(_.parms.indId, _.parms.deptId, _.parms.ymd, _.pageNum, function (result) {
            //     _.detailData = result;
            // });
            this.detailListAjaxFun(_.parms.indId, _.parms.deptId, _.parms.ymd, _.pageNum, _.parms.total);

            this.getIndData(_.parms.indId, _.parms.deptId, _.parms.ymd, function (result) {
                _.indData = result.data;
            });

            //this.exportConsoleFun(_.detailData);

        },
        methods: {
            getIndData: function (indID, deptNo, ymd, callback) {
                $.ajax({
                    type: "POST",
                    url: "/ind/selectOneInd",
                    dataType: "json",
                    data: {indID: indID, deptNo: deptNo},
                    success: function (result) {
                        if (result != null) {
                            if (callback)
                                callback(result);
                        }
                    }
                });
            },
            detailListAjaxFun: function (indID, deptNo, ymd, pageNum,total) {
                var d1 = {indID: indID, deptNo: deptNo, ymd: ymd, pageNum: pageNum,totala:total};
                $.ajax({
                    type: "POST",
                    url: "/ind/selectDynamicTables?r=" + (new Date()).getTime(),
                    data: d1,
                    dataType: "json",
                    beforeSend: function () {
                        // 禁用按钮防止重复提交
                        $("#background").show();
                        $("#progressBar").show();

                    },
                    success: function (result) {
                        $("#background").hide();
                        $("#progressBar").hide();
                        if (result != null) {
                            // if (callback)
                            //     callback(result);
                            vm.detailData = result;
                            vm.dataList = result.info;

                            $.each(result.cols,function (k,n) {
                                // alert(k)
                                // alert(n)
                                //vm.checkedExportCols.push(c)
                                vm.checkedShowCols.push(n)

                            })
                            //vm.checkedShowCols = result.cols;
                        }
                        scrollTo(0,0);
                    }
                });

            },
            logOut: function () {
                var _ = this;
                _.login = false;
                jsHelp.deleteCookie("uesrName");

            },
            exportConsoleFun: function () {
                var data = vm.detailData;
                var indData = vm.indData;
                var cols = vm.detailData.cols;
                if (data.data == undefined) {
                    alert("没有明细");
                    return;
                }
                var url = "/ind/exportDetailsData";
                //判断是否有id为_exportForm的form表单，如果没有则创建一个隐藏的form，把url放入，然后submit
                var exportForm = document.getElementById("_exportForm");
                var exportInput = document.getElementById("_exportInput");
                var exportIndName = document.getElementById("_exportIndName");
                var checkedCols = document.getElementById("_checkedCols");

                if (!exportForm) {
                    exportForm = document.createElement("form");
                    exportForm.setAttribute('id', "_exportForm");
                    exportForm.setAttribute("action", url);
                    exportForm.setAttribute("method", "post");

                    exportInput = document.createElement("input");
                    exportInput.setAttribute("id", "_exportInput");
                    exportInput.setAttribute("name", "obj");

                    exportIndName = document.createElement("input");
                    exportIndName.setAttribute("id", "_exportIndName");
                    exportIndName.setAttribute("name", "indName");

                    checkedCols = document.createElement("input");
                    checkedCols.setAttribute("id", "_checkedCols");
                    checkedCols.setAttribute("name", "checkedCols");

                    exportForm.appendChild(exportInput);
                    exportForm.appendChild(exportIndName);
                    exportForm.appendChild(checkedCols);

                    document.body.appendChild(exportForm);
                }

                exportInput.setAttribute("value", JSON.stringify(data));
                exportIndName.setAttribute("value",indData.IND_NAME);
                checkedCols.setAttribute("value",vm.checkedExportCols)
                exportForm.submit();
            },

            exportConsoleFun1: function () {
                var cols = vm.detailData.cols;
                var data = vm.detailData;
                if (data.data == undefined) {
                    alert("没有明细");
                    return;
                }
                var checkedExportCols = vm.checkedExportCols;
                var d1 = {"indID":vm.parms.indId, "deptNo": vm.parms.deptId, "ymd": vm.parms.ymd, "pageNum": vm.pageNum,"indName":vm.indData.IND_NAME,"checkedCols":checkedExportCols,"cols":cols};
                var url = "/ind/exportDetailsData1";
                //判断是否有id为_exportForm的form表单，如果没有则创建一个隐藏的form，把url放入，然后submit
                var exportForm = document.getElementById("_exportForm");
                var exportInput = document.getElementById("_exportInput");
                if (!exportForm) {
                    exportForm = document.createElement("form");
                    exportForm.setAttribute('id', "_exportForm");
                    exportForm.setAttribute("action", url);
                    exportForm.setAttribute("method", "post");
                    exportInput = document.createElement("input");
                    exportInput.setAttribute("id", "_exportInput");
                    exportInput.setAttribute("name", "obj");
                    exportForm.appendChild(exportInput);
                    document.body.appendChild(exportForm);
                }

                exportInput.setAttribute("value", JSON.stringify(d1));
                exportForm.submit();

            }
        }
    });
    $("#export").click(function () {
        //前台传数据方式导出
        vm.exportConsoleFun();
        //后台直接查询导出
        //vm.exportConsoleFun1();
    });

    //选择导出字段
    $("#btn_center").click(function () {
        $('.mask').css('width', $(window).width())
        $('.mask').css('height', $(document).height())

        init();

        //计算弹出窗口的左上角X的偏移量
        var popX = (windowWidth - popWidth) / 2;

        // 计算弹出窗口的左上角Y的偏移量为窗口的高度 - 弹窗高度 / 2 + 被卷去的页面的top
        var popY = (windowHeight - popHeight) / 2 + $(document).scrollTop();

        //设定窗口的位置
        $("#chooseExportCols").css("top", popY).css("left", popX).slideToggle("fast");

        $(".close_btn").click(function () {
            $('.window').hide("slow");
            $('.mask').css('display', 'none');
        });

        $("#exportSave").click(function () {
            $('.window').hide("slow");
            $('.mask').css('display', 'none');
        });

        $("#chooseExportColsCancel").click(function () {
            vm.checkedExportCols = [];
        });
    });

    //显示或隐藏列
    $("#showOrHidden").click(function () {
        $('.mask').css('width', $(window).width())
        $('.mask').css('height', $(document).height())

        init();

        //计算弹出窗口的左上角X的偏移量
        var popX = (windowWidth - popWidth) / 2;

        // 计算弹出窗口的左上角Y的偏移量为窗口的高度 - 弹窗高度 / 2 + 被卷去的页面的top
        var popY = (windowHeight - popHeight) / 2 + $(document).scrollTop();

        //设定窗口的位置
        $("#chooseShowCols").css("top", popY).css("left", popX).slideToggle("fast");

        $(".close_btn").click(function () {
            $('.window').hide("slow");
            $('.mask').css('display', 'none');
        });

        $("#showSave").click(function () {
            $('.window').hide("slow");
            $('.mask').css('display', 'none');
            //vm.detailData.cols = vm.checkedShowCols;
            //document.getElementById('dateTable').updateUI();
            //location.reload()
        });


        $("#chooseShowColsCancel").click(function () {
            vm.checkedShowCols = [];
        });
    });

});



