/**
 * Created by Liangzhu on 2018/7/8.
 */
$(document).ready(function () {
    $(window).resize(function () {
//                $("body #popup_pane").css({left:$("body #companyInput").offset().left});
    });

    var vm = new Vue({
        el: '#indexBody',
        data: {
            //记录子窗体
            lw: null,
            //登录用户名密码
            userInfo: {username: "", userpwd: "", rusername: ""},
            //记录登录状态
            login: false,
            //搜索项
            selectCompany: {companyName: '', companyId: '', deptId: ''},
            companyData: {
                //供电分公司pop是否显示
                popup_company: false,
                //设置popup是否显示true
                popup_paneShow: false,
                //获取焦点边框样色库
                company_FocusBorderColor: ['#fabe00', '#e9ebee'],
                company_Width: null,
                //下三角号是否显示
                isCitySD: true,
                //单位基本样式库
                baseCity: 'search-term required',
                Result: {
                    ResultList: []
                }
            },
            zjgzData: {},
            zdzbData: {},
            bmflzbData: {},
            deptData: {},
            myReportData: {},
            click1:false,
            click2:false,
            click3:false,
        },

        mounted: function () {
            var _ = this;

            _.companyData.company_Width = $("#companyInput").width();

//                    $("body #popup_pane").css({left:$("body #companyInput").offset().left});
//
//                    $("body .popupdivh3").width($("body #companyInput").outerWidth(true)-3);


            // laydate.render({
            //
            //     elem: '#timeInputStart', theme: _.companyData.company_FocusBorderColor[0]
            // });
            // laydate.render({
            //     elem: '#timeInputEnd', theme: _.companyData.company_FocusBorderColor[0]
            // });
            PageAttentionInit.zjgzAjaxFun(function (data) {
                _.zjgzData = data;
            });
            PageAttentionInit.zdzbAjaxFun(function (data) {
                _.zdzbData = data;
            });
            PageAttentionInit.bmflzbAjaxFun(function (data) {
                _.bmflzbData = data;

                //alert(JSON.stringify(_.bmflzbData))
            });
            PageAttentionInit.myReportAjaxFun(function (data) {
                _.myReportData = data;

            });
            PageAttentionInit.setDepartDataAjaxFun(function (data) {
                //alert(data.data.ResultList[0].id);
                _.companyData.Result = data;

                _.deptData = data;
            });
        },
        methods: {
            appClick: function () {
                var _ = this;
                this.hideCompanyFun(this);
            },
            searchCLick: function () {
                var _ = this;

                window.open('indlist?cn=' + encodeURI(_.selectCompany.companyName) + '&ci=' + _.selectCompany.companyId + "&di=" + encodeURI(_.selectCompany.deptId), "_self");
            },
            companyResultClick: function (list, item) {
                var _ = this;
                for (var i = 0; i < list.length; i++) {
                    list[i].class = null;
                }
                item.class = "cur";
                //判断如果是按照英文字母菜单
                if (item.id >= 3) {
                    _.companyData.popup_company = true;
                }
                else {
                    _.companyData.popup_company = false;
                }

                if (item.id == 2) {
                    if (_.companyData.Result.ResultList[0].clickIds == "" && _.companyData.Result.ResultList[0].childRen.length != 0) {
                        for (var i = 0; i < _.companyData.Result.ResultList[0].childRen.length; i++) {
                            _.companyData.Result.ResultList[0].clickIds += _.companyData.Result.ResultList[0].childRen[i].id + ",";
                        }
                        _.companyData.Result.ResultList[0].clickIds = _.companyData.Result.ResultList[0].clickIds.substring(0, _.companyData.Result.ResultList[0].clickIds.length - 1);
                    }
                }
                else if (item.id >= 3) {
                    if (_.companyData.Result.ResultList[1].clickIds == "" && _.companyData.Result.ResultList[1].childRen.length != 0) {
                        for (var i = 0; i < _.companyData.Result.ResultList[1].childRen.length; i++) {
                            _.companyData.Result.ResultList[1].clickIds += _.companyData.Result.ResultList[1].childRen[i].id + ",";
                        }
                        _.companyData.Result.ResultList[1].clickIds = _.companyData.Result.ResultList[1].clickIds.substring(0, _.companyData.Result.ResultList[1].clickIds.length - 1);
                    }
                }
            },
            companyInputClick: function () {
                var _ = this;
                if ($("body #layui-laydate1").length == 1) {
                    $("body #layui-laydate1").remove();
                }
                else if ($("body #layui-laydate2").length == 1) {
                    $("body #layui-laydate2").remove();
                }
                if (!_.companyData.popup_paneShow) {
                    _.companyData.popup_paneShow = true;
                    $("body #companyInput").css("borderColor", _.companyData.company_FocusBorderColor[0]);
                    _.companyData.isCitySD = false;
                    _.companyData.company_Width = _.companyData.company_Width - 2;
                }
            },

            hideCompanyFun: function (_) {
                if (_.companyData.popup_paneShow) {
                    _.companyData.popup_paneShow = false;
                    $("body #companyInput").css("borderColor", _.companyData.company_FocusBorderColor[1]);
                    _.companyData.isCitySD = true;
                    _.companyData.company_Width = _.companyData.company_Width + 2;
                }
            },


            companyCClick: function (list, item, parentItem) {

                var _ = this;

                if (item.class == null) {
                    for (var i = 0; i < list.length; i++) {
                        list[i].class = null;
                    }

                    item.class = "city-cur";
                    _.selectCompany.companyName = item.title;

                    _.selectCompany.companyId = item.id;
                }
                else {
                    item.class = null;
                    _.selectCompany.companyName = "";

                    _.selectCompany.companyId = "";
                }


                parentItem.clickIds = "";

                for (var i = 0; i < list.length; i++) {
                    if (list[i].class != null) {
                        parentItem.clickIds += list[i].id + ",";
                    }
                }
                if (parentItem.clickIds.length != 0) {
                    parentItem.clickIds = parentItem.clickIds.substring(0, parentItem.clickIds.length - 1);
                }

                if (_.companyData.Result.ResultList[1].childRen.length != 0) {
                    for (var i = 0; i < _.companyData.Result.ResultList[1].childRen.length; i++) {
                        _.companyData.Result.ResultList[1].childRen[i].class = null;
                    }
                }


            },

            companyAClick: function (list, item, parentItem) {
                var _ = this;

                if (item.class == null) {
                    item.class = "city-cur";
                }
                else {
                    item.class = null;
                }
                parentItem.clickIds = "";
                for (var i = 0; i < list.length; i++) {
                    if (list[i].class != null) {
                        parentItem.clickIds += list[i].id + ",";
                    }
                }
                if (parentItem.clickIds.length != 0) {
                    parentItem.clickIds = parentItem.clickIds.substring(0, parentItem.clickIds.length - 1);
                }
            },
            companyDClick: function (list, item) {
                var _ = this;


                if (item.class == null) {
                    for (var i = 2; i < list.length; i++) {
                        for (var j = 0; j < list[i].childRen.length; j++) {
                            for (var k = 0; k < list[i].childRen[j].childRen.length; k++) {
                                list[i].childRen[j].childRen[k].class = null;
                            }

                        }

                    }
                    item.class = "city-cur";
                    //_.companyName=item.text;
                }
                else {
                    //_.companyName="";
                    item.class = null;
                }


            },
            ulClick1:function () {
                this.click1=!this.click1;
            },
            ulClick2:function () {
                this.click2=!this.click2;
            },
            ulClick3:function () {
                this.click3=!this.click3;
            },
            // loginC: function () {
            //     var username = $("#username").val();//取框中的用户名
            //     var password = $("#password").val();//取框中的密码
            //     $.ajax({
            //         type: "POST",
            //         url: "login",
            //         data: {username: username, password: password},
            //         dataType: "json",
            //         success: function (result) {
            //             if (result.code == "0") {
            //                 document.getElementById("refresh").click();
            //             } else {
            //                 alert(result.msg);
            //             }
            //
            //         },
            //         error: function (result) {
            //             document.getElementById("refresh").click();
            //         }
            //     });
            // },
        }
    });
});



// Login Form
// $(function () {
//     var button = $('#loginButton');
//     var box = $('#loginBox');
//     var form = $('#loginForm');
//     button.removeAttr('href');
//     button.mouseup(function (login) {
//         box.toggle();
//         button.toggleClass('active');
//     });
//     form.mouseup(function () {
//         return false;
//     });
//     $(this).mouseup(function (login) {
//         if (!($(login.target).parent('#loginButton').length > 0)) {
//             button.removeClass('active');
//             box.hide();
//         }
//     });
// });