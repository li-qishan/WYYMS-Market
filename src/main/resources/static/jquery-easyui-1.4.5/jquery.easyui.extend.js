/**
* linkbutton方法扩展
* @param {Object} jq
*/
$.extend($.fn.linkbutton.methods, {
    /**
     * 激活选项（覆盖重写）
     * @param {Object} jq
     */
    enable: function (jq) {
        return jq.each(function () {
            var state = $.data(this, 'linkbutton');
            if ($(this).hasClass('l-btn-disabled')) {
                var itemData = state._eventsStore;
                //恢复超链接
                if (itemData.href) {
                    $(this).attr("href", itemData.href);
                }
                //回复点击事件
                if (itemData.onclicks) {
                    for (var j = 0; j < itemData.onclicks.length; j++) {
                        $(this).bind('click', itemData.onclicks[j]);
                    }
                }
                //设置target为null，清空存储的事件处理程序
                itemData.target = null;
                itemData.onclicks = [];
                $(this).removeClass('l-btn-disabled');
            }
        });
    },
    /**
     * 禁用选项（覆盖重写）
     * @param {Object} jq
     */
    disable: function (jq) {
        return jq.each(function () {
            var state = $.data(this, 'linkbutton');
            if (!state._eventsStore)
                state._eventsStore = {};
            if (!$(this).hasClass('l-btn-disabled')) {
                var eventsStore = {};
                eventsStore.target = this;
                eventsStore.onclicks = [];
                //处理超链接
                var strHref = $(this).attr("href");
                if (strHref) {
                    eventsStore.href = strHref;
                    $(this).attr("href", "javascript:void(0)");
                }
                //处理直接耦合绑定到onclick属性上的事件
                var onclickStr = $(this).attr("onclick");
                if (onclickStr && onclickStr != "") {
                    eventsStore.onclicks[eventsStore.onclicks.length] = new Function(onclickStr);
                    $(this).attr("onclick", "");
                }
                //处理使用jquery绑定的事件
                var eventDatas = $(this).data("events") || $._data(this, 'events');
                if (eventDatas["click"]) {
                    var eventData = eventDatas["click"];
                    for (var i = 0; i < eventData.length; i++) {
                        if (eventData[i].namespace != "menu") {
                            eventsStore.onclicks[eventsStore.onclicks.length] = eventData[i]["handler"];
                            $(this).unbind('click', eventData[i]["handler"]);
                            i--;
                        }
                    }
                }
                state._eventsStore = eventsStore;
                $(this).addClass('l-btn-disabled');
            }
        });
    }
});

$.extend($.fn.validatebox.defaults.rules, {
    CHS: {
        validator: function (value, param) {
            return /^[\u0391-\uFFE5]+$/.test(value);
        },
        message: '请输入汉字'
    },
    ZIP: {
        validator: function (value, param) {
            return /^[1-9]\d{5}$/.test(value);
        },
        message: '邮政编码不存在'
    },
    QQ: {
        validator: function (value, param) {
            return /^[1-9]\d{4,10}$/.test(value);
        },
        message: 'QQ号码不正确'
    },
    mobile: {
        validator: function (value, param) {
            return /^13[0-9]{9}$|15[012356789][0-9]{8}$|18[0256789][0-9]{8}$|147[0-9]{8}$/.test(value);
        },
        message: '手机号码不正确'
    },
    loginName: {
        validator: function (value, param) {
            return /^[\u0391-\uFFE5\w]+$/.test(value);
        },
        message: '登录名称只允许汉字、英文字母、数字及下划线。'
    },
    safepass: {
        validator: function (value, param) {
            return safePassword(value);
        },
        message: '密码由字母和数字组成，至少6位'
    },
    equalTo: {
        validator: function (value, param) {
            return value == $(param[0]).val();
        },
        message: '两次输入的字符不一至'
    },
    number: {
        validator: function (value, param) {
            return /^\d+$/.test(value);
        },
        message: '请输入数字'
    },
    idcard: {
        validator: function (value, param) {
            return idCard(value);
        },
        message: '请输入正确的身份证号码'
    },
    length: { // 长度  
        validator: function (value, param) {
            var len = $.trim(value).length;
            return len >= param[0] && len <= param[1];
        },
        message: "输入非空长度必须介于{0}和{1}之间"
    },
    lengthNum: { // 长度  
        validator: function (value, param) {
            var len = $.trim(value).length;
            var flag = len >= param[0] && len <= param[1];
            if (!flag)
            {
                value = value.substring(0, param[1]);
                $("#" + $($(this).next()[0]).attr("name")).numberbox("setValue",value);                
            }
            var len = $.trim(value).length;
            var flag = len >= param[0] && len <= param[1];
            return flag;
        },
        message: "输入非空长度必须介于{0}和{1}之间"
    }
});


$.extend($.fn.validatebox.methods, {
    remove: function (jq, newposition) {
        return jq.each(function () {
            $(this).removeClass("validatebox-text validatebox-invalid").unbind('focus').unbind('blur');
        });
    },
    reduce: function (jq, newposition) {
        return jq.each(function () {
            var opt = $(this).data().validatebox.options;
            $(this).addClass("validatebox-text").validatebox(opt);
        });
    }
});
//动态验证提示信息的清除：
$.extend($.fn.datagrid.methods, {
    setDColumnTitle: function (jq, option) {
        if (option.field) {
            return jq.each(function () {
                var $panel = $(this).datagrid("getPanel");
                var $field = $('td[field=' + option.field + ']', $panel);
                if ($field.length) {
                    var $span = $("span", $field).eq(0);
                    var $span1 = $("span", $field).eq(1);
                    $span.html(option.title);
                    $span1.html(option.title);
                }
            });
        }
        return jq;
    },

    ///设置Datagrid中Editor中验证的清除：
    removeRequired: function (jq, field) {
        if (field) {
            return jq.each(function () {
                var $panel = $(this).datagrid("getPanel");
                var $field = $('td[field=' + field + ']', $panel);
                if ($field.length) {
                    var $input = $("input", $field);
                    $input.removeClass("validatebox-text validatebox-invalid").unbind('focus').unbind('blur');
                }
            });
        }
        return jq;
    },
    addRequired: function (jq, field) {
        if (field) {
            return jq.each(function () {
                var $panel = $(this).datagrid("getPanel");
                var $field = $('td[field=' + field + ']', $panel);
                if ($field.length) {
                    var $input = $("input", $field);
                    $input.addClass("validatebox-text validatebox-invalid").unbind('focus').unbind('blur');
                }
            });
        }
    }
});




$.extend($.fn.datagrid.methods, {
    fixRownumber: function (jq) {
        return jq.each(function () {
            var panel = $(this).datagrid("getPanel");
            //获取最后一行的number容器,并拷贝一份
            var clone = $(".datagrid-cell-rownumber", panel).last().clone();
            //由于在某些浏览器里面,是不支持获取隐藏元素的宽度,所以取巧一下
            clone.css({
                "position": "absolute",
                left: -1000
            }).appendTo("body");
            var width = clone.width("auto").width();
            //默认宽度是25,所以只有大于25的时候才进行fix
            if (width > 25) {
                //多加5个像素,保持一点边距
                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);
                //修改了宽度之后,需要对容器进行重新计算,所以调用resize
                $(this).datagrid("resize");
                //一些清理工作
                clone.remove();
                clone = null;
            } else {
                //还原成默认状态
                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");
            }
        });
    }
});

//combobox 清除
(function ($) {

    //初始化清除按钮
    function initClear(target) {
        var jq = $(target);
        var opts = jq.data('combo').options;
        var combo = jq.data('combo').combo;
        var arrow = combo.find('span.combo-arrow');

        var clear = arrow.siblings("span.combo-clear");
        if (clear.size() == 0) {
            //创建清除按钮。
            clear = $('<span class="combo-clear" style="height: 20px;"></span>');

            //清除按钮添加悬停效果。
            clear.unbind("mouseenter.combo mouseleave.combo").bind("mouseenter.combo mouseleave.combo",
                      function (event) {
                          var isEnter = event.type == "mouseenter";
                          clear[isEnter ? 'addClass' : 'removeClass']("combo-clear-hover");
                      }
            );
            //清除按钮添加点击事件，清除当前选中值及隐藏选择面板。
            clear.unbind("click.combo").bind("click.combo", function () {
                jq.combo("setValue", "").combo("setText", "");
                jq.combo('hidePanel');
            });
            arrow.before(clear);
        };
        var input = combo.find("input.combo-text");
        input.outerWidth(input.outerWidth() - clear.outerWidth());

        opts.initClear = true;//已进行清除按钮初始化。
    }

    //扩展easyui combo添加清除当前值。
    var oldResize = $.fn.combo.methods.resize;
    $.extend($.fn.combo.methods, {
        initClear: function (jq) {
            return jq.each(function () {
                initClear(this);
            });
        },
        resize: function (jq) {
            //调用默认combo resize方法。
            var returnValue = oldResize.apply(this, arguments);
            var opts = jq.data("combo").options;
            if (opts.initClear) {
                jq.combo("initClear", jq);
            }
            return returnValue;
        }
    });
}(jQuery));

