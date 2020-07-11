/**
 * Created by SERENA on 2018/8/1.
 * 通用方案，查询统计数据
 */
var PageStatisticsInit = {
    statByPeriodAjaxFun: function (id, deptNo, ymd, callback) {
        $.ajax({
            type: "POST",
            url: "/ind/indDataListByPeriod",
            dataType: "json",
            data: {indId: id, deptNo: deptNo, ymd: ymd},
            success: function (data) {
                if (data != null) {
                    if (callback)
                        callback(data);
                }
            }
        });
    },
    statByDeptAjaxFun: function (id, deptNo, ymd, callback) {
        $.ajax({
            type: "POST",
            url: "/ind/indDataListGroupDept",
            data: {indId: id, deptNo: deptNo, ymd: ymd},
            success: function (data) {
                if (data != null) {
                    if (callback)
                        callback(data);
                }
            }
        });
    },
    statByPeriodCompareAjaxFun: function (id, deptNo, ymd, callback) {
        $.ajax({
            type: "POST",
            url: "/ind/indDataListCompare",
            data: {indId: id, deptNo: deptNo, ymd: ymd},
            success: function (data) {
                if (data != null) {
                    if (callback)
                        callback(data);
                }
            }
        });
    },
    statByDeptCompareAjaxFun: function (id, deptNo, ymd, callback) {
        $.ajax({
            type: "POST",
            url: "/ind/indDataListGroupDeptCompare",
            data: {indId: id, deptNo: deptNo, ymd: ymd},
            success: function (data) {
                if (data != null) {
                    if (callback)
                        callback(data);
                }
            }
        });
    },
    statMyReportAjaxFun: function (id) {
        var date = $('#queryTime').val();
        if(date==null||date=="") {
            alert("请选择报表月份");
            return;
        }

        var url = "/ind/exportIndReport";

        //判断是否有id为_exportForm的form表单，如果没有则创建一个隐藏的form，把url放入，然后submit
        var exportForm = document.getElementById("_exportForm");
        var exportInput = document.getElementById("_exportInput");
        var exportDate = document.getElementById("_exportDate");
        if (!exportForm) {
            exportForm = document.createElement("form");
            exportForm.setAttribute('id', "_exportForm");
            exportForm.setAttribute("action", url);
            exportForm.setAttribute("method", "post");
            exportInput = document.createElement("input");
            exportInput.setAttribute("id", "_exportInput");
            exportInput.setAttribute("name", "reportId");
            exportInput.setAttribute("hidden","true");
            exportDate = document.createElement("input");
            exportDate.setAttribute("id", "_exportDate");
            exportDate.setAttribute("name", "ymd");
            exportDate.setAttribute("hidden","true");


            exportForm.appendChild(exportInput);
            exportForm.appendChild(exportDate);


            var div = document.getElementById("reportDiv");
            div.appendChild(exportForm);
        }

        exportInput.setAttribute("value", id);
        exportDate.setAttribute("value",date);
        exportForm.submit();

    },
}
