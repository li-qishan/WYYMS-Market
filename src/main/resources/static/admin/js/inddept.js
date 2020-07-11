//设置全局controller
var _controllerId = "/aDeptController";

$(function () {
    initDataGrid();
    initDialog();
});

function initDataGrid() {
    $("#dg").treegrid({
        fit: true,
        width: "100%",
        fitColumns: true,// 列是否占满表格区域
        autoRowHeight: true,// 行高自动
        striped: true,// 单双号的行颜色不同
        method: "post",// 加载数据方式
        nowrap: true,// 一行数据太多时是否截取多出部分，false时会全部展示
        idField: "dept_NO",
        treeField: "dept_NAME",// 设置主键
        toolbar: "#toolbar",
        checkOnSelect: true,
        selectOnCheck: true,
        columns: [[{
            field: "dept_NO",
            title: "部门编码",
            width: $(this).width() * 0.11,
            align: "center",
            halign: "center"
        }, {
            field: "dept_NAME",
            title: "部门名称",
            width: $(this).width() * 0.11
            // align: "life",
            // halign: "center"
        }, {
            field: "short_NAME",
            title: "单位名简称",
            width: $(this).width() * 0.11,
            align: "center",
            halign: "center"
        }]],// 设置列名
        pagination: false,// 是否显示分页控件
        rownumbers: true,// 是否显示行号
        singleSelect: true,// 是否只能选择一行
        showFooter: true,// 是否显示表格底部
        showHeader: true,// 是否显示表格头部
        url: _controllerId + "/getrow"
    });
}

/**
 * 打开对话框操作
 * @param _dialogTitle_
 */
function openDialog(_dialogTitle_) {

    _dialogTitle = _dialogTitle_
    var rows_ = $("#dg").treegrid("getChecked");
    if ("变更" == _dialogTitle_) {
        if (rows_.length == 0) {
            $.messager.alert("消息提示", _dialogTitle_ + "前请选择一条数据！", "warning");
            return;
        }
        if (rows_.length > 1) {
            $.messager.alert("消息提示", "只能选择一条数据进行" + _dialogTitle_ + "！", "warning");
            return;
        }
    }
    $("#formDialog").dialog("open");
}

/**
 * 初始化弹窗
 */
function initDialog() {
    var descDiv_ = document.createElement("div");
    document.body.appendChild(descDiv_);
    descDiv_.id = "formDialog";
    descDiv_.className = "easyui-dialog";
    descDiv_.style.width = "600px";
    descDiv_.style.height = "325px";
    descDiv_.style.maxHeight = "500px";
    descDiv_.style.padding = "5px";
    $("#formDialog").dialog({
        border: true,
        modal: true,
        autoOpen: false,
        collapsible : false,
        minimizable: false,
        resizable: false,
        closed: true,
        buttons: [{
            id: "btnSave",
            text: "保存",
            iconCls: "icon-save",
            plain: true,
            handler: function () {
                save();
            }
        }, {
            id: "btnCancel",
            text: "取消",
            iconCls: "icon-cancel",
            plain: true,
            handler: function () {
                cancel();
            }
        }],
        iconCls: "icon-search",
        href: "inddeptform.html",
        onLoad: function () {
            var row_ = $("#dg").treegrid("getChecked");
            if (row_ && ("变更" == _dialogTitle )) {
                $("#formData").form("load", row_[0]);
                $("#parent_NO").textbox("readonly",true);
                $("#parent_NO").textbox("textbox").css("background-color","#F0F0F0");
                $("#dept_NO").textbox("readonly",true);
                $("#dept_NO").textbox("textbox").css("background-color","#F0F0F0");
            }

        },
        onClose: function () {
            $("#dg").treegrid("clearSelections");
            //销毁对话框
            $("#formDialog").dialog("destroy");
            //每次销毁后需要重新初始化对话框
            initDialog();
        },
        onBeforeOpen: function () {
            $("#formDialog").dialog("setTitle", "<span class='dialogTitle'>" + _dialogTitle + "</span>");
        }
    });
}

/**
 * 表单保存操作
 */
function save() {
    //保存前清空脏数据，自定义设置所提交数据
    var row_ = $("#dg").treegrid("getSelected");
    var url_;
    if (row_ && "变更" == _dialogTitle) {
        url_ = _controllerId + "/update";
        update(url_, row_);
    } else {
        url_ = _controllerId + "/add";
        add(url_);
    }
}

/**
 * 新增
 * @param _url_
 */
function add(_url_) {
    $("#formData").form("submit", {
        url: _url_,
        onSubmit: function (param) {
            var dept_NO = $("#dept_NO").textbox("getValue");
            var dept_NAME = $("#dept_NAME").textbox("getValue");
            var n = $("#parent_NO").combotree('tree').tree('getSelected').ind_DIM_ID;
            n = Number(n) + 1;
            var count_ = exist(dept_NO, dept_NAME);
            if (count_.rows > 0) {
                $.messager.alert("消息提示", "已存在相同的部门编号或部门名称，请重新输入！", "warning");
                return false;
            }
            param.ind_DIM_ID = n;
            var isValidate_ = $(this).form("enableValidation").form("validate");

            return isValidate_;
        },
        success: function (result) {
            if (result == "success") {
                $.messager.alert("消息提示", "新增信息成功！", "info", function () {
                    $("#dg").treegrid("reload");
                    $("#formDialog").dialog("close");
                });
            } else {
                $.messager.alert("消息提示", "新增信息失败！", "error");
            }
        }
    });
}

function exist(dept_NO, dept_NAME) {
    var count_ = 0;
    $.ajax({
        url: _controllerId + "/getByNames",
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "no": dept_NO,
            "name": dept_NAME
        },
        success: function (result) {
            count_ = result
        },
        error: function (error) {
        }
    });

    return count_;
}

function update(_url_, _row_) {
    $("#formData").form("submit", {
        url : _url_,
        onSubmit : function(param) {
            // if($("#formData").form("options").dirtyFields.length == 0) {
            //     $.messager.alert("消息提示", "信息没有发生改变，无需更新！", "warning");
            //     return false;
            // }
            var dept_NO = "";
            var dept_NAME = $("#dept_NAME").textbox("getValue");
            var count_ = exist(dept_NO, dept_NAME);
            if (count_.rows > 0) {
                $.messager.alert("消息提示", "已存在相同的部门名称，请重新输入！", "warning");
                return false;
            }
            // param.ID = _row_.ID;
            var isValidate_ = $(this).form("enableValidation").form("validate");

            return isValidate_;
        },
        success : function(result) {
            if (result == "success") {
                $.messager.alert("消息提示", "更新信息成功！", "info", function() {
                    $("#dg").treegrid("reload");
                    $("#formDialog").dialog("close");
                });
            } else {
                $.messager.alert("消息提示", "更新信息失败！", "error");
            }
        }
    });
}
/**
 * 删除数据
 */
function remove() {
    var row_ = $("#dg").treegrid("getChecked");
    if (row_.length == 0) {
        $.messager.alert("消息提示", "删除前请选择一条信息！", "info");
    } else {
        $.messager.confirm("删除确认", "确定删除所选择的信息吗？", function(r){
            if(r) {
                var ids_ = [];
                ids_.push( row_[0].dept_NO);
                $.each($("#dg").treegrid("getChildren", row_[0].dept_NO), function (i, n) {
                    ids_.push(n.dept_NO);
                });

                $.ajax({
                    url : _controllerId + "/remove",
                    type : "post",
                    async : false,
                    dataType : "json",
                    data : {
                        "params" : JSON.stringify({"ids" : ids_.join("\',\'")})
                    },
                    success : function(result) {
                        if(result) {
                            $.messager.alert("消息提示", "删除信息成功！", "info", function() {
                                $("#dg").treegrid("reload");
                                //$("#dg").treegrid("clearSelections");
                            });
                        } else {
                            $.messager.alert("消息提示", "删除信息失败！", "error");
                        }
                    },
                    error : function(error) {
                        $.messager.alert("消息提示", "删除信息失败！原因：[" + error + "]", "error");
                    }
                });
            }
        });
    }
}


/**
 * 表单取消操作
 */
function cancel() {
    $("#formDialog").dialog("close");
};
