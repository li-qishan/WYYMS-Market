/**
 * Created by SERENA on 2018/8/16.
 */
var indId;
var editor;
var curPage;
$(function () {
    indId = jsHelp.getQueryString("indId");
    curPage = jsHelp.getQueryString("pageNumber");
    InitKindEditor();
    InitCombo();
    InitBtnEvent();

    if (indId.length > 0) {
        InitForm(function (data) {
            $('#formData').form('load', data);
            // $('#period').combobox('select', data.period);
            // $('#indDimCode').combobox('select', data.indDimCode);
            // $('#indClassCode').combobox('select', data.indClassCode);
            //$('#indDept').combobox('select', data.indDept);
            editor.html(data.indDesc);
            $('#indName').attr("readonly", true);
        });
    }
});

function InitKindEditor() {
    KindEditor.ready(function (K) {
        editor = K.create('#indDesc', {
            resizeType: 0,
            items: ['undo', 'redo', '|',
                'justifyleft', 'justifycenter', 'justifyright', 'justifyfull', '|',
                'insertorderedlist', 'insertunorderedlist', '|',
                'indent', 'outdent', '/',
                'bold', 'italic', 'underline', '|',
                'fontname', 'fontsize', 'fontcolor'
            ],
            newlineTag: "br"
        });

        prettyPrint();
    });
}

function InitCombo() {
    $('#reportType').combobox({
        editable: false,
        panelHeight: 'auto',
        onLoadSuccess: function (data) {
            if (data) {
                $('#reportType').combobox('setValue', data[0].value);
            }
        }
    });

    // 指标分类
    $('#indClassCode').combobox({
        editable: false,
        url: '/ind/classList',
        panelHeight: 'auto',
        valueField: 'code',
        textField: 'code_NAME',
        onLoadSuccess: function (data) {
            if (data) {
                $('#indClassCode').combobox('setValue', data[0].code);
            }
        }
    });


    // 所属地市
    // $("#indDept").combobox("reload", "/ind/getinddept");
    // 所属地市
    // $('#indDept').combobox({
    //     editable: false,
    //     panelHeight: 'auto',
    //     panelMaxHeight: 280,
    //     url: '/ind/getinddept',
    //     onLoadSuccess: function (data) {
    //         if (data) {
    //             $('#indDept').combobox('setValue', data[0].ID);
    //         }
    //     }
    // });

    //级别
    $('#indDimCode').combobox({
        editable: false,
        url: '/ind/dimList',
        panelHeight: 'auto',
        valueField: 'code',
        textField: 'code_NAME',
        onLoadSuccess: function (data) {
            if (data) {
                $('#indDimCode').combobox('setValue', data[0].code);
            }
        },
        onSelect: function (item) {
            $("#indDept").combobox("reload", "/ind/getdept/" + item.code);
        }
        // onChange: function () {
        //     $("#indDept").combobox("unselect", $("#indDept").combobox("getValue"));
        // }
    });

    //频度
    $('#period').combobox({
        editable: false,  //是否可编辑
        panelHeight: 'auto'
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
        editor.sync();
        $('#formData').form('submit', {
            url: '/ind/saveIndList',
            onSubmit: function () {
                var isValidate_ = $(this).form("enableValidation").form("validate");
                return isValidate_;
            },
            success: function (result) {
                if (result == "success") {
                    $.messager.alert('提示信息', '操作成功', 'info',
                        function () {
                            window.location.href = "right1.html?pageNumber="+curPage;
                        }
                    );
                }
                if (result == "exists") {
                    $.messager.alert('提示信息', '指标名称已存在！', 'error');
                }
                if (result == "failed") {
                    $.messager.alert('提示信息', '程序出现异常：请联系管理员！', 'error');
                }
            }
        });
    });
}

function InitForm(callback) {
    $.ajax({
        type: "POST",
        url: "/ind/queryIndById",
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
