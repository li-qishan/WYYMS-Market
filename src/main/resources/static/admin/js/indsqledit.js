/**
 * Created by SERENA on 2018/8/16.
 */
var indId;
var indName;
var curPage;
$(function () {
    indId = jsHelp.getQueryString("indId");
    indName = jsHelp.getQueryString("indName");
    curPage = jsHelp.getQueryString("pageNumber");
    $('#indId').textbox("setValue", indId);
    $('#indName').textbox("setValue", indName);

    InitCombo();
    InitBtnEvent();

    if (indId.length > 0) {
        InitForm(function (r) {
            if (r.indListSql.length > 0) {
                for (var i = 0; i < r.indListSql.length; i++) {
                    if (r.indListSql[i].sqlType == "TOTAL") {
                        $('#totalDataSource').combobox('select', r.indListSql[i].dataSourceCode);
                        $('#TOTAL').textbox("setValue", r.indListSql[i].sqlString);
                        $('#TOTALDESC').textbox("setValue", r.indListSql[i].sqlDesc);
                    }
                    if (r.indListSql[i].sqlType == "VALUE") {
                        $('#valueDataSource').combobox('select', r.indListSql[i].dataSourceCode);
                        $('#VALUE').textbox("setValue", r.indListSql[i].sqlString);
                        $('#VALUEDESC').textbox("setValue", r.indListSql[i].sqlDesc);
                    }
                    if (r.indListSql[i].sqlType == "TOTAL_LIST") {
                        $('#totalListDataSource').combobox('select', r.indListSql[i].dataSourceCode);
                        var TOTAL_LIST = r.indListSql[i].sqlString;
                        $('#TOTAL_LIST').textbox("setValue", TOTAL_LIST);
                        //$('#TOTAL_LIST').textbox("setValue",r.indListSql[i].sqlString);
                        $('#TOTAL_LISTDESC').textbox("setValue", r.indListSql[i].sqlDesc);
                    }
                    if (r.indListSql[i].sqlType == "VALUE_LIST") {
                        $('#valueListDataSource').combobox('select', r.indListSql[i].dataSourceCode);
                        var VALUE_LIST = r.indListSql[i].sqlString;
                        if (VALUE_LIST.indexOf(">") > -1) {
                            $('#VALUE_LIST').textbox("setValue", VALUE_LIST.replace(new RegExp(">", 'g'), "大于"));
                        }
                        $('#VALUE_LIST').textbox("setValue", r.indListSql[i].sqlString);
                        $('#VALUE_LISTDESC').textbox("setValue", r.indListSql[i].sqlDesc);
                    }
                }
            }
        });
    }
})

function InitCombo() {
    $('#totalDataSource').combobox({
        editable: false,  //是否可编辑
        url: '/ind/dataSourceList',
        panelHeight: 'auto',
        valueField: 'code',
        textField: 'code_NAME',
        onLoadSuccess: function (data) {
            if (data) {
                $('#totalDataSource').combobox('setValue', data[0].code);
            }
        }
    });

    $('#valueDataSource').combobox({
        editable: false,  //是否可编辑
        url: '/ind/dataSourceList',
        panelHeight: 'auto',
        valueField: 'code',
        textField: 'code_NAME',
        onLoadSuccess: function (data) {
            if (data) {
                $('#valueDataSource').combobox('setValue', data[0].code);
            }
        }
    });

    $('#totalListDataSource').combobox({
        editable: false,  //是否可编辑
        url: '/ind/dataSourceList',
        panelHeight: 'auto',
        valueField: 'code',
        textField: 'code_NAME',
        onLoadSuccess: function (data) {
            if (data) {
                $('#totalListDataSource').combobox('setValue', data[0].code);
            }
        }
    });

    $('#valueListDataSource').combobox({
        editable: false,  //是否可编辑
        url: '/ind/dataSourceList',
        panelHeight: 'auto',
        valueField: 'code',
        textField: 'code_NAME',
        onLoadSuccess: function (data) {
            if (data) {
                $('#valueListDataSource').combobox('setValue', data[0].code);
            }
        }
    });
}

function InitBtnEvent() {
    $('#returnBtn').click(function () {
        $.messager.confirm('系统提示', '是否返回指标目录页面，如点击是，所做修改将无效？', function (r) {
            if (r) {
                window.location.href = "right1.html?pageNumber="+curPage;
            }
        });
    });

    $('#saveBtn').click(function () {
        $('#formData').form('submit', {
            url: '/ind/saveIndSql',
            onSubmit: function () {
                // >,<的转换
                var TOTAL = $('#TOTAL').val();
                var VALUE = $('#VALUE').val();
                var TOTAL_LIST = $('#TOTAL_LIST').val();
                var VALUE_LIST = $('#VALUE_LIST').val();

                if (TOTAL.indexOf(">") > -1) {
                    TOTAL = TOTAL.replace(new RegExp(">", 'g'), "大于");
                }
                if (TOTAL.indexOf("<") > -1) {
                    TOTAL = TOTAL.replace(new RegExp("<", 'g'), "小于");
                }

                if (VALUE.indexOf(">") > -1) {
                    VALUE = VALUE.replace(new RegExp(">", 'g'), "大于");
                }
                if (VALUE.indexOf("<") > -1) {
                    VALUE = VALUE.replace(new RegExp("<", 'g'), "小于");
                }

                if (TOTAL_LIST.indexOf(">") > -1) {
                    TOTAL_LIST = TOTAL_LIST.replace(new RegExp(">", 'g'), "大于");
                }
                if (TOTAL_LIST.indexOf("<") > -1) {
                    TOTAL_LIST = TOTAL_LIST.replace(new RegExp("<", 'g'), "小于");
                }

                if (VALUE_LIST.indexOf(">") > -1) {
                    VALUE_LIST = VALUE_LIST.replace(new RegExp(">", 'g'), "大于");
                }
                if (VALUE_LIST.indexOf("<") > -1) {
                    VALUE_LIST = VALUE_LIST.replace(new RegExp("<", 'g'), "小于");
                }

                $('#TOTAL').textbox("setValue", TOTAL);
                $('#VALUE').textbox("setValue", VALUE);
                $('#TOTAL_LIST').textbox("setValue", TOTAL_LIST);
                $('#VALUE_LIST').textbox("setValue", VALUE_LIST);
            },
            success: function (result) {
                if (result == "success") {
                    $.messager.alert('提示信息', '操作成功！', 'info',
                        function () {
                            window.location.href = "right1.html?pageNumber="+curPage;
                        }
                    );
                }else if (result == "failed") {
                    $.messager.alert('提示信息', '程序出现异常：请联系管理员！', 'error');
                }
            }
        });
    });
}

function InitForm(callback) {
    $.ajax({
        type: "POST",
        url: "/ind/querySqlById",
        dataType: "json",
        data: {indId: indId},
        success: function (data) {
            if (data != null) {
                if (callback)
                    callback(data);
            }
        }
    });
}