var _controllerId = "/apkDown";

$(window).resize(function () {
    $('#tabData').datagrid('resize');
});

$(function () {
    //表格
    InitGrid();
    //按钮事件
    InitBtnEvent();
    // 初始化弹出框
    initDialog();
});

function InitGrid() {
    var w = 0;
    var l = 100;
    $('#tabData').datagrid({
        title: 'apk文件列表',
        iconCls: 'icon-menu',
        nowrap: false,
        autoRowHeight: false,
        striped: true,
        collapsible: false,
        singleSelect: true,
        fit: true,
        loadMsg: "数据加载中，请稍后...",
        url: _controllerId+'/list',
        remoteSort: false,
        idField: 'userId',
        columns: [[
            {field: 'versionControId', width: 0, hidden: true, title: '主键', align: 'center'},
            {field: 'apkName', width: $(this).width() * w + 2*l, title: 'apk名', align: 'left'},
            {field: 'apkId', width:  $(this).width() * w + l, title: 'apkId', align: 'center'},
            {field: 'versionCode', width: $(this).width() * w + l, title: '版本号', align: 'left'},
            {field: 'apkUrl', width: $(this).width() * w * 1.5 + 4 * l, title: '路径', align: 'left'},
            {field: 'uploadBy', width: $(this).width() * w * 1.5 + 1 * l, title: '文件上传人', align: 'left'},
            {
                field: 'uploadTime',
                width: $(this).width() * w * 1.5 + 1.5 * l,
                title: '文件上传时间',
                align: 'left',
                formatter: DateTimeFormatter
            },
        ]],
        pagination: false,
        rownumbers: true,
        toolbar: "#tb"
    });

    var p = $('#tabData').datagrid('getPager');
    $(p).pagination({
        pageSize: 10, //每页显示的记录条数，默认为10
        pageList: [10, 20, 30], //可以设置每页记录条数的列表
        beforePageText: '第', //页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        onBeforeRefresh: function (pageNumber, pageSize) {
        }
    });
}
// 时间格式化
function DateTimeFormatter(value) {
    var time = new Date(value);
    var y = time.getFullYear();
    var m = getTime(time.getMonth() + 1);
    var d = getTime(time.getDate());
    var h = getTime(time.getHours());
    var mm = getTime(time.getMinutes());
    var s = getTime(time.getSeconds());
    return y + '-' + m + '-' + d + ' ' + h + ':' + mm + ':' + s;
}

//因获取到时间是存在1位的 故当值小于10的时候改为0开头，例如01
function getTime(i) {
    if (i < 10) {
        i = "0" + i
    }
    return i
}

/**
 * 打开对话框操作
 * @param _dialogTitle_
 */
function openDialog(_dialogTitle_) {

    _dialogTitle = _dialogTitle_
    // 编辑
    if ("分类编辑" == _dialogTitle_) {
        // 获取选中行
        var rows_ = $("#type_list").treegrid("getChecked");

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
    descDiv_.style.height = "400px";
    descDiv_.style.maxHeight = "500px";
    descDiv_.style.padding = "5px";
    $("#formDialog").dialog({
        border: true,
        modal: true,
        autoOpen: false,
        collapsible: false,
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
        href: "form/appVersion_form.html",// 弹出框包含页面
        onLoad: function () {
            // var row_ = $("#type_list").treegrid("getChecked");
            // 修改时获取参数
            // if (row_ && ("分类编辑" == _dialogTitle )) {
            //
            //     $("#formData").form("load", row_[0]);
            // }

        },
        onClose: function () {
            // $("#type_list").treegrid("clearSelections");
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

function InitBtnEvent() {
    //删除
    $('#btnDel').click(function () {
        var rows = $('#tabData').datagrid('getSelections');
        if (rows.length == 0) {
            $.messager.alert('提示信息', '请选择一条要删除的数据！', 'info');
            return false;
        }
        $.messager.confirm('系统提示', '您确定删除数据吗?', function (r) {
            if (r) {
                $.get(_controllerId+"/deleteById?apkId=" + rows[0].apkId,
                    function (data) {
                        if (data == "0") {
                            $.messager.alert('提示信息', '程序出现异常：请联系管理员！', 'error');
                        } else {
                            $.messager.alert('提示信息', data, 'info',
                                function () {
                                    $('#tabData').datagrid('clearSelections');
                                    $('#tabData').datagrid('reload');
                                });
                        }
                    });
            }
        });
    });
}

/**
 * 表单保存操作
 */
function save() {
    //保存前清空脏数据，自定义设置所提交数据
    // var row_ = $("#type_list").treegrid("getSelected");row_ &&
    var url_;
    if ("分类编辑" == _dialogTitle) {// 未用到
        url_ = _controllerId + "/update";
        debugger
        update(url_, row_);
    } else {
        add(_controllerId + "/uploadFiles");
    }
}

/**
 * 表单取消操作
 */
function cancel() {
    $("#formDialog").dialog("close");
};
/**
 * 新增
 * @param _url_
 */
function add(_url_) {
    $("#formData").form("submit", {
        url: _url_,
        onSubmit: function (param) {

        },
        success: function (result) {
            if (result == "success") {
                $.messager.alert("消息提示", "新增信息成功！", "info", function () {
                    $('#tabData').datagrid('reload');
                    $("#formDialog").dialog("close");
                });
            } else if (result == "sizeFalse"){
                $.messager.alert("消息提示", "新增信息失败,apkId重复", "error");
            } else {
                $.messager.alert("消息提示", "新增信息失败！", "error");
                // $("#type_list").treegrid("reload");
                // $("#formDialog").dialog("close");
            }
        }
    });
}