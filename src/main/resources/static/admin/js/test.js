//设置全局controller
var _controllerId = "/testController";
// 设置全局页码
var _pageNumber = 1;
// 设置全局每页展示条数
var _pageSize = 10;
//设置全局对话框标题
var _dialogTitle = "新增"
$(function() {
    initDataGrid();
    initPagination();
    initCombobox();
    initDialog();
});
function initDataGrid() {

    $("#dg").datagrid({
        fit : true,
        width : "100%",
        fitColumns : true,// 列是否占满表格区域
        autoRowHeight : false,// 行高自动
        striped : true,// 单双号的行颜色不同
        method : "post",// 加载数据方式
        nowrap : true,// 一行数据太多时是否截取多出部分，false时会全部展示
        idField : "ID",// 设置主键
        toolbar : "#toolbar",
        checkOnSelect : true,
        selectOnCheck : true,
        columns : [ [ {
            field : "checkbox",
            title : "checkbox",
            checkbox : true
        },{
            field : "ID",
            title : "ID",
            hidden : true
        }, {
            field : "DEPT_NAME",
            title : "单位名称",
            width : $(this).width() * 0.11,
            align : "center",
            halign : "center"
        }, {
            field : "IND_TOTAL",
            title : "指标数据",
            width : $(this).width() * 0.11,
            align : "center",
            halign : "center"
        }, {
            field : "IND_VALUE",
            title : "指标数据,比率",
            width : $(this).width() * 0.11,
            align : "center",
            halign : "center"
        }, {
            field : "STAT_DATE",
            title : "统计时间",
            width : $(this).width() * 0.11,
            align : "center",
            halign : "center",
        }, {
            field : "STAT_USER",
            title : "统计人",
            width : $(this).width() * 0.11,
            align : "center",
            halign : "center",
        }] ],// 设置列名
        pagination : true,// 是否显示分页控件
        rownumbers : false,// 是否显示行号
        singleSelect : false,// 是否只能选择一行
        pageNumber : _pageNumber,
        pageSize : _pageSize,
        showFooter : true,// 是否显示表格底部
        showHeader : true,// 是否显示表格头部
        url :  _controllerId + "/test",
        queryParams : {
            params : setLoadParams(_pageNumber, _pageSize)
        },// 设置查询参数
        onLoadSuccess : function(data) {
            //重新设置分页控件的属性值
            $("#dg").datagrid("getPager").pagination("refresh", {
                total : data.total,
                pageNumber : data.pNumber
            });

            $("#dg").datagrid("clearSelections");
        },
        onBeforeLoad : function() {
        }
    });
}
function setLoadParams(_pNumber_, _pSize_) {
    return JSON.stringify({
        pNumber : _pNumber_,
        pSize : _pSize_,
        DEPT_NO : $("#DEPT_NO").combobox("getValue")
    });
}
/**
 * 初始化分页栏
 */
function initPagination() {
    $("#dg").datagrid("getPager").pagination("options").onSelectPage = function(
        pageNumber, pageSize) {
        var params_ = setLoadParams(pageNumber, pageSize);
        $("#dg").datagrid("reload", {
            params : params_
        });
    }
}
/**
 * 初始化下拉框样式
 */
function initCombobox() {
    $("input.easyui-combobox")
        .each(
            function() {
                // 下拉列表添加清空按钮
                $(this).combobox().textbox("addIcon", "icon-clear");
                // 初始化隐藏清空按钮
                $(this).combobox("getIcon", 0).css("visibility",
                    "hidden");
                // 设置下拉列表选中事件处理
                $(this).combobox("options").onSelect = function() {
                    $(this).combobox("getIcon", 0).css("visibility",
                        "visible");
                };
                $(this).combobox("options").onShowPanel = function() {
                    if ($(this)[0].id == "DEPT_NO") {
                        $(this).combobox("reload",
                            _controllerId + "/getdept");
                    }

                    if ($(this)[0].id == "STAT_USER") {
                        $(this).combobox("reload",
                            _controllerId + "/getuser");
                    }
                }
            });
    // $("input.easyui-datebox")
    //     .each(
    //         function() {
    //             // 下拉列表添加清空按钮
    //             $(this).datebox().textbox("addIcon", "icon-clear");
    //             // 初始化隐藏清空按钮
    //             $(this).datebox("getIcon", 0).css("visibility",
    //                 "hidden");
    //             // 设置下拉列表选中事件处理
    //             $(this).datebox("options").onSelect = function() {
    //                 $(this).datebox("getIcon", 0).css("visibility",
    //                     "visible");
    //             };
    //         });
}
/**
 * 数据加载后重置滚动条样式
 */
function resizescrollbar() {
    if ($("#dg").datagrid("getRows").length > 20) {
        $("#dg").datagrid("resize", {
            scrollbarSize : 18
        });
    } else {
        $("#dg").datagrid("resize", {
            scrollbarSize : 0
        });
    }
}
/**
 * 查询操作
 */
function query() {
    var params_ = setLoadParams(_pageNumber, _pageSize);
    $("#dg").datagrid("reload", {
        params : params_
    });
}

/**
 * 打开对话框操作
 * @param _dialogTitle_
 */
function openDialog(_dialogTitle_) {

    _dialogTitle = _dialogTitle_
    var rows_ = $("#dg").datagrid("getChecked");
    if("变更" == _dialogTitle_ || "查看" == _dialogTitle_) {
        if(rows_.length == 0) {
            $.messager.alert("消息提示", _dialogTitle_ + "前请选择一条数据！", "warning");
            return;
        }
        if(rows_.length > 1) {
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
    descDiv_.style.width = "1000px";
    descDiv_.style.height = "auto";
    descDiv_.style.maxHeight = "500px";
    descDiv_.style.padding = "0px";
    $("#formDialog").dialog({
        border : true,
        top : 10,
        minimizable : false,
        resizable : false,
        closable : true,
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
        iconCls : "icon-search",
        href : "testform.html",
        onLoad : function() {
            $("#deptNo").combobox("reload",_controllerId + "/getdept");

            if("查看" == _dialogTitle) {
                //查看设置各个控件为只读
                $("#testform").find("input.textbox-f").each(function() {
                    $(this).textbox("readonly",true);
                    if($(this)[0].id == "deptNo" ) {
                        $(this).combobox("getIcon",0).css("display","none");
                    }
                });
            }


            //设置表单各个下拉选项的下拉列表
            $("#powerConsumerForm").find("input.easyui-combobox").each(function() {
                if("变更" == _dialogTitle || "新增" == _dialogTitle) {
                    //下拉列表添加清空按钮
                    $(this).combobox().textbox("addIcon","icon-clear");
                    //初始化隐藏清空按钮
                    $(this).combobox("getIcon",0).css("visibility","hidden");
                    //设置下拉列表选中事件处理
                    $(this).combobox("options").onSelect = function() {
                        $(this).combobox("getIcon",0).css("visibility","visible");
                    };

                    //设置下拉列表展开事件处理
                    $(this).combobox("options").onShowPanel = function() {
                        if($(this)[0].id == "DEPT_NO"){
                            debugger
                            $(this).combobox("reload",_controllerId + "/getdept");
                        }
                    }
                }
            });


            var row_ = $("#dg").datagrid("getChecked");
            if(row_ && ("变更" == _dialogTitle || "查看" == _dialogTitle)){
                $("#testform").form("load", row_[0]);
            }
        },
        onClose : function() {
            $("#dg").datagrid("clearSelections");
            //销毁对话框
            $("#formDialog").dialog("destroy");
            //每次销毁后需要重新初始化对话框
            initDialog();
        },
        onBeforeOpen : function() {
            $("#formDialog").dialog("setTitle","<span class='dialogTitle'>" + _dialogTitle + "</span>");
        }
    });
}

/**
 * 表单取消操作
 */
function cancel() {
    $("#formDialog").dialog("close");
};

/**
 * 表单保存操作
 */
function save() {
    //保存前清空脏数据，自定义设置所提交数据
    var row_ = $("#dg").datagrid("getSelected");
    var url_;
    if(row_ && "变更" == _dialogTitle) {
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
    $("#testform").form("submit", {
        url : _url_,
        onSubmit : function(param) {

            var isValidate_ = $(this).form("enableValidation").form("validate");

            return isValidate_;
        },
        success : function(result) {
            if (result == "success") {
                $.messager.alert("消息提示", "新增信息成功！", "info", function() {
                    $("#dg").datagrid("reload");
                    $("#formDialog").dialog("close");
                });
            } else {
                $.messager.alert("消息提示", "新增信息失败！", "error");
            }
        }
    });
}
/**
 * 更新
 * @param _url_
 * @param _row_
 */
function update(_url_, _row_) {
    $("#testform").form("submit", {
        url : _url_,
        onSubmit : function(param) {
            debugger
            if($("#testform").form("options").dirtyFields.length == 0) {
                $.messager.alert("消息提示", "信息没有发生改变，无需更新！", "warning");
                return false;
            }
            param.ID = _row_.ID;

            var isValidate_ = $(this).form("enableValidation").form("validate");

            return isValidate_;
        },
        success : function(result) {
            if (result == "success") {
                $.messager.alert("消息提示", "更新信息成功！", "info", function() {
                    $("#dg").datagrid("reload");
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
    var row_ = $("#dg").datagrid("getChecked");
    if (row_.length == 0) {
        $.messager.alert("消息提示", "删除前请选择一条信息！", "info");
    } else {
        $.messager.confirm("删除确认", "确定删除所选择的信息吗？", function(r){
            if(r) {
                var ids_ = [];
                for(var i = 0; i < row_.length; i++){
                    ids_.push(row_[i].ID);
                }

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
                                $("#dg").datagrid("reload");
                                //$("#dg").datagrid("clearSelections");
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
