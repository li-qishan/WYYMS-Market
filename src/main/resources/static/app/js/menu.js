var _menus = "";


//左侧导航加载
function InitMenu() {
    ////清空菜单
    $(".easyui-accordion").empty();
    ////初始化菜单
    $.each(_menus, function (i, sm) {
        var menulist = "";
        menulist = GetMenuList(sm, menulist);

        menulist = "<ul id='tt1' class='easyui-tree' animate='true' dnd='false'>" + menulist.substring(4);

        $('.easyui-accordion').accordion('add', {
            title: sm.menuName,
            content: menulist,
            iconCls: sm.iconCls
        });
    });

    ////绑定菜单事件(单击、悬停、离开)
    $('.easyui-accordion .tree-node').bind('click', function () {
        var aobj = $(this).find("a");
        var tabTitle = aobj.children('.nav').text();
        var url = aobj.attr("rel");
        var menuid = aobj.attr("ref");
        var icon = aobj.attr("refIcon");
        addTab(tabTitle, url, icon);
        $('.easyui-accordion .tree-node').removeClass("selected");
        //Record();
        //aobj.parent().addClass("selected");
    }).hover(function () {
        $(this).parent().addClass("hover");
    }
    , function () {
        $(this).parent().removeClass("hover");
    }
    );

    ////导航菜单绑定初始化
    $(".easyui-accordion").accordion();

    ////默认展开第一个panel
    var pp = $('.easyui-accordion').accordion('panels');
    var t = pp[0].panel('options').title;
    $('.easyui-accordion').accordion('select', t);
}

////形成树形结构（适应多级菜单）
function GetMenuList(data, menulist) {
    if (data.children == null)
        return menulist;
    else {
        menulist += '<ul>';
        $.each(data.children, function (i, sm) {
            var menuUrl = sm.menuURL;
            if (menuUrl != null) {
                if (menuUrl.indexOf("?") > 0) {
                    menuUrl = menuUrl + "&" + Math.random();
                } else {
                    menuUrl = menuUrl + "?" + Math.random();
                }
            }
            
            if (sm.children.length == 0) {
                menulist += '<li iconCls="' + sm.iconCls + '"><a ref="' + sm.menuCode + '" href="javascript:void(0)" rel="'
                    + menuUrl + '" refIcon="' + sm.iconCls + '" ><span class="nav" >' + sm.menuName
                    + '</span></a></li>';
            } else {
                menulist += '<li state="closed" iconCls="' + sm.iconCls + '"><span class="nav"><a ref="' + sm.menuCode + '" href="javascript:void(0)" rel="'
                    + menuUrl + '" refIcon="' + sm.iconCls + '" ><span class="nav" >' + sm.menuName
                    + '</span></a></span>';
            }
            menulist = GetMenuList(sm, menulist);
        });
        menulist += '</ul>';
    }
    //alert(menulist);
    return menulist;
}
// 获取左侧导航的图标Tab
function getIcon(menuid) {
    var icon = '';
    $.each(_menus, function (i, n) {
        $.each(n.children, function (k, m) {
            //alert(m.FunctionCode);
            if (m.FunctionCode == menuid) {
                icon = m.iconCls;
                return false;
            }
        });
    });
    return icon;
}
function fleshExistsTab(subtitle, url, icon) {
    if ($('#maintabs').tabs('exists', subtitle)) {
        var myTab = $('#maintabs').tabs('getTab', subtitle);
        $('#maintabs').tabs('update', {
            tab: myTab,
            options: {
                content: createFrame(url),
            }
        });
        $('#maintabs').tabs('select', subtitle)
    }
    else {
        $('#maintabs').tabs('add', {
            title: subtitle,
            content: createFrame(url),
            closable: true,
            icon: icon
        });
    }
}
function addTab(subtitle, url, icon) {
    if ($('#maintabs').tabs('exists', subtitle)) {
        $('#maintabs').tabs("close", subtitle);
    }
    if (url == null || url == "null") {
        url = "/SysManage/IsBuilding";
    }
    if (url == "///") {
        return ;
    }
    if (!$('#maintabs').tabs('exists', subtitle)) {
        $('#maintabs').tabs('add', {
            title: subtitle,
            content: createFrame(url),
            closable: true,
            icon: icon,
            style: { overflow: "hidden" }
        });
    } else {
        $('#maintabs').tabs('select', subtitle);
        $('#mm-tabupdate').click();
    }
    tabEvent();

    $(".panel-body.panel-body-noheader.panel-body-noborder").css("overflow", "hidden");
}

function addTab2(subtitle, url, icon, id) {
    if (url == null || url == "null") {
        url = "/SysManage/IsBuilding";
    }
    if (!$('#maintabs').tabs('exists', subtitle)) {
        $('#maintabs').tabs('add', {
            id: id,
            title: subtitle,
            content: createFrame(url),
            closable: true,
            icon: icon
        });
    } else {
        $('#maintabs').tabs('select', subtitle);
        $('#mm-tabupdate').click();
    }
    tabEvent();
}

function createFrame(url) {
    if (url.indexOf("?") > 0) {
        url += "&t=" + new Date().getTime();
    } else {
        url += "?t=" + new Date().getTime();
    }
    var s = '<iframe scrolling="auto" frameborder="0"  src="' + url+'" style="width:100%;height:100%;"></iframe>';
    return s;
}
function closeTabById(title, id) {
    var myTab = $('#maintabs').tabs('getTab', title);
    if (myTab.attr("id") == id) {
        $('#maintabs').tabs('close', title);
    }
}
function closeTabByTitle(title) {
    var myTab = $('#maintabs').tabs('getTab', title);
    $('#maintabs').tabs('close', title);
}
function tabEvent() {
    ////双击关闭TAB选项卡
    $(".tabs-inner").dblclick(function () {
        var subtitle = $(this).children(".tabs-closable").text();
        if (subtitle != "系统首页") {
            $('#maintabs').tabs('close', subtitle);
        }
    });
    ////为选项卡绑定右键
    $(".tabs-inner").bind('contextmenu', function (e) {
        $('#mm').menu('show', {
            left: e.pageX,
            top: e.pageY
        });
        var subtitle = $(this).children(".tabs-closable").text();
        $('#mm').data("currtab", subtitle);
        $('#maintabs').tabs('select', subtitle);
        return false;
    });
}
function getSelectedTabTitle() {
    return $('#maintabs').tabs('getSelected').panel('options').title;
}
function closeTabByTitle(title) {
    $('#maintabs').tabs('close', title);
}

// 绑定右键菜单事件
function tabRightEvent() {
    // 刷新
    $('#mm-tabupdate').click(function () {
        var currTab = $('#maintabs').tabs('getSelected');
        var url = $(currTab.panel('options').content).attr('src');
        $('#maintabs').tabs('update', {
            tab: currTab,
            options: {
                content: createFrame(url)
            }
        });
    });
    // 关闭当前
    $('#mm-tabclose').click(function () {
        var currtab_title = $('#mm').data("currtab");
        $('#maintabs').tabs('close', currtab_title);
    });
    // 全部关闭
    $('#mm-tabcloseall').click(function () {
        $('.tabs-inner span').each(function (i, n) {
            var t = $(n).text();
            if (t != "工程进度") {
                $('#maintabs').tabs('close', t);
            }
        });
    });
    // 关闭除当前之外的TAB
    $('#mm-tabcloseother').click(function () {
        $('#mm-tabcloseright').click();
        $('#mm-tabcloseleft').click();
    });
    // 关闭当前右侧的TAB
    $('#mm-tabcloseright').click(function () {
        var nextall = $('.tabs-selected').nextAll();
        if (nextall.length == 0) {
            return false;
        }
        nextall.each(function (i, n) {
            var t = $('a:eq(0) span', $(n)).text();
            if (t == "工程进度") {
                return;
            }
            $('#maintabs').tabs('close', t);
        });
        return false;
    });
    // 关闭当前左侧的TAB
    $('#mm-tabcloseleft').click(function () {
        var prevall = $('.tabs-selected').prevAll();
        if (prevall.length == 0) {
            return false;
        }
        prevall.each(function (i, n) {
            var t = $('a:eq(0) span', $(n)).text();
            if (t == "工程进度") {
                return;
            }
            $('#maintabs').tabs('close', t);
        });
        return false;
    });
    // 退出
    $("#mm-exit").click(function () {
        $('#mm').menu('hide');
    });
}

//刷新
function refreshTab() {
    var currTab = $('#maintabs').tabs('getSelected');
    var url = $(currTab.panel('options').content).attr('src');
    $('#maintabs').tabs('update', {
        tab: currTab,
        options: {
            content: createFrame(url)
        }
    });
}
