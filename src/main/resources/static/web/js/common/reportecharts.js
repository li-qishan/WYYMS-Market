/**
 * Created by L on 2018/5/31.
 */
var EchartsInit = function(){
    var oEchartsInit = new Object();
    oEchartsInit.children = [];
    oEchartsInit.xData = [];
    oEchartsInit.yData = [];
    oEchartsInit.echartsData = [];
    return oEchartsInit;
}
var EchartsDataInit = function(){
    var oEchartsDataInit = new Object();
    oEchartsDataInit.data = [];
    oEchartsDataInit.name = "";
    oEchartsDataInit.type = "";
    oEchartsDataInit.ymax = 100;
    return oEchartsDataInit;
}