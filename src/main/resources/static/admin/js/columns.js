//设置全局controller
var _controllerId = "/columnsController";

//设置全局编辑行号标志位
var editIndex = -1;
$(function () {
    initDataGrid();
});

function initDataGrid() {

    $("#dg").datagrid({
        fit: true,
        width: "100%",
        fitColumns: true,// 列是否占满表格区域
        autoRowHeight: false,// 行高自动
        striped: true,// 单双号的行颜色不同
        method: "post",// 加载数据方式
        nowrap: true,// 一行数据太多时是否截取多出部分，false时会全部展示
        idField: "COL_ID",// 设置主键
        toolbar: "#toolbar",
        checkOnSelect: true,
        selectOnCheck: true,
        columns: [[{
            field: "COL_ID",
            title: "参数标识",
            width: $(this).width() * 0.11,
            align: "center",
            halign: "center",
            editor: {
                type: "textbox",
                options: {
                    required: true,
                    validType: "length[1,49]",
                    // onChange : function(newValue, oldValue) {
                    //     if(newValue != oldValue && oldValue != ""){
                    //         var rows_ = $("#dg").datagrid("getRows");
                    //         for(var i = 0; i < rows_.length; i++){
                    //             if(rows_[i].COL_ID == newValue ){
                    //                 var aa =  $(this).textbox('setText',oldValue);
                    //                 $.messager.alert("消息提示", "参数标识重复请从新填写！", "info");
                    //             }
                    //
                    //         }
                    //     }
                    //
                    //
                    // }
                }
            }
        }, {
            field: "COL_NAME",
            title: "参数名字",
            width: $(this).width() * 0.11,
            align: "center",
            halign: "center",
            editor: {
                type: "textbox",
                options: {
                    required: true,
                    validType: "maxLength[49]"
                }
            }
        }]],// 设置列名
        pagination: false,// 是否显示分页控件
        rownumbers: true,// 是否显示行号
        singleSelect: true,// 是否只能选择一行
        showFooter: true,// 是否显示表格底部
        showHeader: true,// 是否显示表格头部
        url: _controllerId + "/getrow",
        queryParams: {},
        onClickCell: function (index, field, value) {
            onClickCell(index, field);
        }
    });
}
/**
 * 添加行
 */
function appendRow() {
    if (endEditing()) {
        // $("#dg").datagrid("appendRow", {"checkbox": false});
        // editIndex = $("#dg").datagrid("getRows").length - 1;
        // $("#dg").datagrid("beginEdit", editIndex);


        $("#dg").datagrid("insertRow", {index: 0, row: {}});
        $('#dg').datagrid('beginEdit', 0);
        editIndex = 0;

    }
}
/**
 * 删除行
 */
function removeRow() {

    if ($("#dg").datagrid("getRows").length == 0) {
        return;
    }
    $("#dg").datagrid("endEdit", editIndex);
    var row_ = $("#dg").datagrid("getChecked");
    if (row_.length == 0) {
        $.messager.alert("消息提示", "删除前请选择一条信息！", "warning");
        return;
    } else {
        var length_ = row_.length;
        for (var i = 0; i < length_; i++) {
            var index_ = $("#dg").datagrid("getRowIndex", row_[0]);
            $("#dg").datagrid("deleteRow", index_);
        }

        if ($("#dg").datagrid("getRows").length == 0) {
            //如果表格所有数据都删除了，重置编辑标志位
            editIndex = -1;
        }
    }
}

/**
 * 点击单元格事件处理
 * @param index
 * @param field
 */
function onClickCell(index, field) {
    if (editIndex != index) {
        if (endEditing()) {
            editIndex = index;
            $("#dg").datagrid("beginEdit", index);
            var ed = $("#dg").datagrid("getEditor", {index: index, field: field});
            if (ed) {
                ($(ed.target).data("textbox") ? $(ed.target).textbox("textbox") : $(ed.target)).focus();
            }
        } else {
            setTimeout(function () {
                $("#dg").datagrid("selectRow", editIndex);
                $("#dg").datagrid("unselectRow", index);
            }, 0);
        }
    }
}
/**
 * 结束编辑操作
 * @returns {Boolean}
 */

function endEditing() {

    if (editIndex == -1) {
        return true;
    }
    if ($("#dg").datagrid("validateRow", editIndex)) {
        $("#dg").datagrid("endEdit", editIndex);
        return true;
    } else {
        return false;
    }
}

/**
 * 保存
 */
function save() {
    $.messager.confirm("保存确认", "确定修改表格中的信息吗？", function (r) {
        if (r) {
            $("#form").form("submit", {
                url: _controllerId + "/add",
                onSubmit: function (param) {
                    var isValidate_ = $(this).form("enableValidation").form("validate");
                    if (isValidate_) {
                        $("#dg").datagrid("endEdit", editIndex);
                        editIndex = -1;
                        var rows_ = $("#dg").datagrid("getRows");
                        var ids_ = [];
                        for (var i = 0; i < rows_.length - 1; i++) { //循环开始元素
                            for (var j = i + 1; j < rows_.length; j++) { //循环后续所有元素
                                //如果相等，则重复
                                if (rows_[i].COL_ID == rows_[j].COL_ID) {
                                    $.messager.alert("消息提示", "不可以添加重复的参数标识，标识值为: " + rows_[i].COL_ID + " 请从新填写后保存", "error");
                                    return false; //结束循环
                                }
                            }
                        }
                        param.params = JSON.stringify({"list": rows_});
                    }
                    return isValidate_;
                },
                success: function (result) {
                    if (result.rows == "err") {
                        $.messager.alert("消息提示", "更新不成功！", "error");
                    } else {
                        $("#dg").datagrid("reload");
                        $.messager.alert("消息提示", "更新成功！", "info");
                    }

                }
            });
        }
    })
}
