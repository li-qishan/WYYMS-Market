//设置全局controller
var _controllerId = "/parameterController";
//记录左边要删除行
var _lcodeid = "";
//记录右边要删除行
var _rcodeid = "";
var CODE = "";
var dg = "";
var sysfl = "";
$(function () {
    initDataGrid();
    initDialog();
});

function initDataGrid() {

    $("#dgl").datagrid({
        fit: true,
        width: "100%",
        fitColumns: true,// 列是否占满表格区域
        autoRowHeight: false,// 行高自动
        striped: true,// 单双号的行颜色不同
        method: "post",// 加载数据方式
        nowrap: true,// 一行数据太多时是否截取多出部分，false时会全部展示
        idField: "CODE_ID",// 设置主键
        toolbar: "#toolbar",
        checkOnSelect: true,
        selectOnCheck: true,
        columns: [[{
            field: "CODE_ID",
            title: "CODE_ID",
            hidden: true
        }, {
            field: "ORDER_NUM",
            title: "序号",
            width: $(this).width() * 0.11,
            align: "center",
            halign: "center"
        }, {
            field: "CODE",
            title: "参数标识",
            width: $(this).width() * 0.11,
            align: "center",
            halign: "center"
        }, {
            field: "CODE_NAME",
            title: "参数名称",
            width: $(this).width() * 0.11,
            align: "center",
            halign: "center"
        }]],// 设置列名
        pagination: false,// 是否显示分页控件
        rownumbers: true,// 是否显示行号
        singleSelect: true,// 是否只能选择一行
        showFooter: true,// 是否显示表格底部
        showHeader: true,// 是否显示表格头部
        url: _controllerId + "/getroot",
        queryParams: {
            params: "root"
        },
        onClickCell: function (index, field, value) {
            _lcodeid = "";
            var rows_ = $("#dgl").datagrid("getRows");
            CODE = rows_[index].CODE;
            sysfl = rows_[index].SYS_FLAG;
            _lcodeid = rows_[index].CODE_ID;
            getdgr(CODE);

        }
    });

    $("#dgr").datagrid({
        fit: true,
        width: "100%",
        fitColumns: true,// 列是否占满表格区域
        autoRowHeight: false,// 行高自动
        striped: true,// 单双号的行颜色不同
        method: "post",// 加载数据方式
        nowrap: true,// 一行数据太多时是否截取多出部分，false时会全部展示
        idField: "CODE_ID",// 设置主键
        toolbar: "#rtoolbar",
        checkOnSelect: true,
        selectOnCheck: true,
        columns: [[{
            field: "CODE_ID",
            title: "CODE_ID",
            hidden: true
        }, {
            field: "ORDER_NUM",
            title: "序号",
            width: $(this).width() * 0.11,
            align: "center",
            halign: "center"
        }, {
            field: "CODE",
            title: "参数标识",
            width: $(this).width() * 0.11,
            align: "center",
            halign: "center"
        }, {
            field: "CODE_NAME",
            title: "参数名称",
            width: $(this).width() * 0.11,
            align: "center",
            halign: "center"
        }, {
            field: "REMARK",
            title: "参数说明",
            width: $(this).width() * 0.11,
            align: "center",
            halign: "center"
        }]],// 设置列名
        pagination: false,// 是否显示分页控件
        rownumbers: true,// 是否显示行号
        singleSelect: true,// 是否只能选择一行
        showFooter: true,// 是否显示表格底部
        showHeader: true,// 是否显示表格头部
        onClickCell: function (index, field, value) {
            var rows_ = $("#dgr").datagrid("getRows");
            sysfl = rows_[index].SYS_FLAG;
            _rcodeid = rows_[index].CODE_ID;

        }
    });
}

/***
 * 取右侧数据
 */
function getdgr(CODE) {
    $.ajax({
        url: _controllerId + "/getroot",
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "params": CODE
        },
        success: function (result) {
            $("#dgr").datagrid("loadData", result);
        }
    });
}

/**
 * 删除
 */
function remove(dg) {
    var aa = "";
    if (dg == "dgr") {
        if (_rcodeid == "") {
            $.messager.alert("消息提示", "删除前请选择一条信息！", "info");
            return;
        }
        aa = _rcodeid;
    }
    if (dg == "dgl") {
        if (_lcodeid == "") {
            $.messager.alert("消息提示", "删除前请选择一条信息！", "info");
            return;
        }
        aa = _lcodeid
    }
    if(sysfl == "1"){
        $.messager.alert("消息提示", "删除的是系统级别不能删除！", "info");
        return;
    }

    $.messager.confirm("删除确认", "确定删除所选择的信息吗？", function (r) {
        if (r) {
            $.ajax({
                url: _controllerId + "/remove",
                type: "post",
                async: false,
                dataType: "json",
                data: {
                    "params": aa
                },
                success: function (result) {

                    if (result) {
                        $.messager.alert("消息提示", "删除信息成功！", "info", function () {
                            debugger
                            if (dg == "dgr") {
                                $("#dgr").datagrid("reload");
                                getdgr(CODE);
                            } else {
                                CODE = "";
                                $("#dgl").datagrid("reload");
                                $("#dgl").datagrid("clearSelections");
                                $("#dgr").datagrid("loadData", []);
                            }
                        });
                    } else {
                        $.messager.alert("消息提示", "删除信息失败！", "error");
                    }
                }
            });
        }
    });
}

/**
 * 初始化弹窗
 */
function initDialog() {

    var descDiv_ = document.createElement("div");
    document.body.appendChild(descDiv_);
    descDiv_.id = "formDialog";
    descDiv_.className = "easyui-dialog";
    descDiv_.style.width = "600px";//根据内容自定义宽度
    descDiv_.style.height = "325px";//自适应高度
    descDiv_.style.maxHeight = "700px";//固定最大高度
    descDiv_.style.padding = "5px";
    $("#formDialog").dialog({
        title: "新增",
        modal : true,
        autoOpen : false,
        collapsible : false,
        minimizable : false,
        maximizable : false,
        closed : true,
        buttons : [ {
            id : "btnSave",
            text : "保存",
            iconCls : "icon-save",
            plain : true,
            handler : function() {
                save();
            }
        }, {
            id : "btnCancel",
            text : "取消",
            iconCls : "icon-cancel",
            plain : true,
            handler : function() {
                cancel();
            }
        } ],
        closed : true,
        cache : false,
        inline : false,
        constrain : true,
        modal : true,
        iconCls : "icon-add",
        href : "parametermaintenanceform.html",
        onLoad : function() {

        },
        onClose : function() {
            $("#dgl").datagrid("clearSelections");
            $("#dgr").datagrid("clearSelections");
            //销毁对话框
            $("#formDialog").dialog("destroy");
            //每次销毁后需要重新初始化对话框
            initDialog();

        },
        onBeforeOpen : function() {
        }
    });

}

function openDialog(dga) {

    dg = dga
    if(dg == "dgr"){
        if(CODE == ""){
            $.messager.alert("消息提示", "请选择左侧信息进行添加！", "error");
            return;
        }else {
            $("#formDialog").dialog("open");
        }
    }else {
        $("#formDialog").dialog("open");
    }
}
/**
 * 表单取消操作
 */
function cancel() {
    $("#formDialog").dialog("close");
};
function save() {
    $("#paramForm").form("submit", {
        url : _controllerId + "/add",
        onSubmit : function(param) {
            var bb = "";
            if(dg == "dgr"){
                bb = CODE;
            }else {
                bb = "root";
            }

            var count_ = existCode(bb);
            if(count_.rows > 0){
                $.messager.alert("消息提示", "已存在相同的序号或参数标识，请重新输入！", "warning");
                return false;
            }


            var isValidate_ = $(this).form("enableValidation").form("validate");
            param.aaaaa = bb;

            return isValidate_;
        },
        success : function(result) {
            if (result == "success") {
                $.messager.alert("消息提示", "新增信息成功！", "info", function() {

                    $("#formDialog").dialog("close");
                    if (dg == "dgr") {
                        $("#dgr").datagrid("reload");
                        getdgr(CODE);
                    } else {
                        CODE = "";
                        $("#dgl").datagrid("reload");
                        $("#dgl").datagrid("clearSelections");
                        $("#dgr").datagrid("loadData", []);
                    }
                });
            } else {
                $.messager.alert("消息提示", "新增信息失败！", "error");
            }
        }
    });
}

/**
 * 验证重名
 *
 */
function existCode(bb) {
    var count_ = 0;
    $.ajax({
        url : _controllerId + "/getByCode",
        type : "post",
        async : false,
        dataType : "json",
        data : {
            "orderNum" : $("#orderNum").textbox("getValue"),
            "code" : $("#codeaaa").textbox("getValue"),
            "codetype" : bb
        },
        success : function(result) {
            count_ = result;
        },
        error : function(error) {
        }
    });

    return count_;
}