/**
 * Created by SERENA on 2018/8/1.
 * 通用方法，查询关注相关指标列表
 */
var PageAttentionInit = {
    // 最近关注集合
    zjgzAjaxFun: function (callback) {
        $.ajax({
            type: "POST",
            url: "/ind/selectRecentAttentionList",
            dataType: "json",
            data: {},
            success: function (data) {
                if (data != null) {
                    if (callback)
                        callback(data);
                }
            }
        });
    },
    // 重点指标集合
    zdzbAjaxFun: function (callback) {
        $.ajax({
            type: "POST",
            url: "/ind/selectImportAttentionList",
            dataType: "json",
            data: {},
            success: function (data) {
                if (data != null) {
                    if (callback)
                        callback(data);
                }
            }
        });
    },
    // 关注排名集合
    gzpmAjaxFun: function (callback) {
        $.ajax({
            type: "POST",
            url: "/ind/selectIndData3",
            dataType: "json",
            data: {},
            success: function (data) {
                if (data != null) {
                    if (callback)
                        callback(data);
                }
            }
        });
    },

    // 部门分类指标-修改andele
    bmflzbAjaxFun: function (callback) {
        $.ajax({
            type: "POST",
            url: "/ind/selectIndDepart",
            dataType: "json",
            data: {},
            success: function (data) {
                if (data != null) {
                    if (callback)
                        callback(data);
                }
            }
        });
    },
    // 初始单位数据-修改andele
    setDepartDataAjaxFun: function (callback) {
        $.ajax({
            type: "POST",
            url: "/ind/initIndDepart",
            dataType: "json",
            data: {},
            success: function (data) {
                if (data != null) {
                    if (callback)
                        callback(data);
                }
            }
        });
    },

    // 我的报表-增加andele
    myReportAjaxFun: function (callback) {
        $.ajax({
            type: "POST",
            url: "/ind/myReportList",
            dataType: "json",
            data: {},
            success: function (data) {
                if (data != null) {
                    if (callback)
                        callback(data);
                }
            }
        });
    },


};