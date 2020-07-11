/**
 * Created by SERENA on 2018/7/31.
 */
var PageRenderInit = {
    renderPeriodChart: function (title, data) {
        var dom2 = document.getElementById("chart2");
        var myChart2 = echarts.init(dom2);

        var option2 = {
            title: {
                text: title
            },
            tooltip : {
                trigger: 'axis'
            },
            calculable : true,
            xAxis: {
                type: 'category',
                data: []
            },
            yAxis: {
                type: 'value'
            },
            series: [{
                data: [],
                type: 'line',
                label: {
                    normal: {
                        show: true,
                        position: 'top',
                        textStyle: {
                            color: 'black'
                        }
                    }
                }
            }]
        };

        for (var i = 0; i < data.length; i++) {
            option2.series[0].data.push(data[i].IND_VALUE);
            option2.xAxis.data.push(data[i].YMD);
        }

        if (option2 && typeof option2 === "object") {
            myChart2.setOption(option2, true);
        }
    },
    renderDeptChart: function (title, data) {
        var dom3 = document.getElementById("chart3");
        var myChart3 = echarts.init(dom3);

        var option3 = {
            title: {
                text: title
            },
            //图例
            // legend:{
            //     data:['销量']
            // },
            calculable : true,
            xAxis: {
                type: 'category',
                data: [],
                axisLabel: {
                    interval:0,
                    rotate:15
                }
            },
            yAxis: {
                type: 'value'
            },
            series: [{
                // name:'销量',
                data: [],
                type: 'bar',
                label: {
                    normal: {
                        show: true,
                        position: 'top',
                        textStyle: {
                            color: 'black'
                        }
                    }
                }
            }]
        };

        for (var i = 0; i < data.length; i++) {
            option3.series[0].data.push(data[i].IND_VALUE);
            option3.xAxis.data.push(data[i].DEPT_NAME);
        }

        if (option3 && typeof option3 === "object") {
            myChart3.setOption(option3, true);
        }
    }
};

function setDeptDivDisplay() {
    var deptNo = jsHelp.getQueryString("deptNo");
    if (deptNo.length > 5) {
        document.getElementById("deptStatDiv").style.display = 'none';
    } else {
        document.getElementById("deptStatDiv").style.display = '';
    }
}
var vm;

$(document).ready(function () {
    // InitBtnEvent();

    vm = new Vue({
        el: '#indexBody',
        data: {
            // 记录子窗体
            lw: null,
            // 登录用户名密码
            userInfo: {username: "", userpwd: "", rusername: ""},
            //记录登录状态
            login: false,
            // 指标ID
            detail: {id: 0, deptNo: ''},
            // 最近关注数据
            zjgzData: {},
            // 重点指标数据
            zdzbData: {},
            // 关注排名数据
            gzpmData: {},
            // 指标明细
            indData: {},
            // 日、月统计数据
            detailData: {},
            // 部门统计数据
            detailDeptData: {},
            // 日月对比统计数据
            detailCompareData: {},
            // 部门对比统计数据
            detailDeptCompareData: {},
            // 指标显示列头
            indTitle: {}
        },
        mounted: function () {
            var _ = this;

            _.detail.id = jsHelp.getQueryString("id");
            _.detail.deptNo = jsHelp.getQueryString("deptNo");

            _.getIndData(_.detail.id);
            _.refreshStatPeriod(_.detail.id, _.detail.deptNo);
            _.refreshStatPeriodCompare(_.detail.id, _.detail.deptNo);
            _.refreshStatDept(_.detail.id, _.detail.deptNo);
            _.refreshStatDeptCompare(_.detail.id, _.detail.deptNo);

            PageAttentionInit.zjgzAjaxFun(function (data) {
                _.zjgzData = data;
            });
            PageAttentionInit.zdzbAjaxFun(function (data) {
                _.zdzbData = data;
            });
            PageAttentionInit.gzpmAjaxFun(function (data) {
                _.gzpmData = data;
            });

            _.getIndTitle(_.detail.id);

            setDeptDivDisplay();
        },
        methods: {
            logOut: function () {
                var _ = this;
                _.login = false;
                jsHelp.deleteCookie("uesrName");
            },
            getIndData: function (id) {
                var _ = this;
                $.ajax({
                    type: "POST",
                    url: "/ind/selectIndData",
                    dataType: "json",
                    data: {indId: id},
                    success: function (result) {
                        if (result != null) {
                            _.indData = result.data;
                        }
                    }
                });
            },
            getIndTitle: function (id) {
                var _ = this;
                $.ajax({
                    type: "POST",
                    url: "/ind/getIndTitle",
                    dataType: "json",
                    data: {indId: id},
                    success: function (result) {
                        if (result != null) {
                            _.indTitle = result.data;
                        }
                    }
                });
            },
            refreshStatPeriod: function (id, deptNo, ymd) {
                var _ = this;
                PageStatisticsInit.statByPeriodAjaxFun(id, deptNo, ymd, function (result) {
                    _.detailData = result.data;
                    PageRenderInit.renderPeriodChart(_.indData.IND_NAME, _.detailData);
                });
            },
            refreshStatPeriodCompare: function (id, deptNo, ymd) {
                var _ = this;
                PageStatisticsInit.statByPeriodCompareAjaxFun(id, deptNo, ymd, function (result) {
                    _.detailCompareData = result.data;
                });
            },
            refreshStatDept: function (id, deptNo, ymd) {
                var _ = this;
                PageStatisticsInit.statByDeptAjaxFun(id, deptNo, ymd, function (result) {
                    _.detailDeptData = result.data;
                    PageRenderInit.renderDeptChart(_.indData.IND_NAME, _.detailDeptData);
                });
            },
            refreshStatDeptCompare: function (id, deptNo, ymd) {
                var _ = this;
                PageStatisticsInit.statByDeptCompareAjaxFun(id, deptNo, ymd, function (result) {
                    _.detailDeptCompareData = result.data;
                });
            }
        }
    });
});

function InitBtnEvent() {
    $('#searchByPeriod').click(function () {
        var id = jsHelp.getQueryString("id");
        var dept_no = jsHelp.getQueryString("deptNo");
        var date = $('#timeInputByPeriod').val();
        vm.refreshStatPeriod(id, dept_no, date);
    });

    $('#searchByPeriodCompare').click(function () {
        var id = jsHelp.getQueryString("id");
        var dept_no = jsHelp.getQueryString("deptNo");
        var date = $('#timeInputByPeriodCompare').val();
        vm.refreshStatPeriodCompare(id, dept_no, date);
    });

    $('#searchByDept').click(function () {
        var id = jsHelp.getQueryString("id");
        var dept_no = jsHelp.getQueryString("deptNo");
        var date = $('#timeInputByDept').val();
        vm.refreshStatDept(id, dept_no, date);
    });

    $('#searchByDeptCompare').click(function () {
        var id = jsHelp.getQueryString("id");
        var dept_no = jsHelp.getQueryString("deptNo");
        var date = $('#timeInputByDeptCompare').val();
        vm.refreshStatDeptCompare(id, dept_no, date);
    });
}
