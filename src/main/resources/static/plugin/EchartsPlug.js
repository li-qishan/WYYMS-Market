    //Echarts控件
    var EchartControl=function(containerid){
        
        if(containerid==""||!containerid)
        {
            console.log("容器Id未赋值!");
            return null;
        }

        var that=this;
        
        this.baseDataSource=[];

        //数据转换类
        this.DataTranslate=function(dataSource){
            that.xDataSource=[];
            that.seriesDataSource=[];            
        }
        
        //表头legend转换实体类
        this.legendDataContrast={

        }

        //Echarts对象
        this.myChart=null;

        this.controlId=containerid;
            
        //option组件
        this.option = {
        
        };

        
        //legend项数据
        this.legendData=[];  

        //x轴数据
        this.xDataSource=[];
        
        //y轴数据
        this.yDataSource=[];

        //柱状图数据
        this.seriesDataSource=[];
        
        //series模板
        this.seriesItemTemplate={};

        //表头
        this.title={
             text: '请选择比对条件:',
             textStyle:{
                fontSize:14
             }
        };

        //提示文本
        this.tooltip={
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        };

        //说明图例
        this.legend={
            left:140,
            selected: {
            
            },            
            align:'auto',
            // backgroundColor:'red',
            // data:[{name:'市场竞争指数'},'市场拓展指数','智能用电管理成效','业扩报装服务规范率'],
            selectedMode:'single',
            textStyle:{
                backgroundColor:'red'
            },
        //     formatter: function (name) {
        // return echarts.format.truncateText(name, 100, '14px Microsoft Yahei', '…');
        // // return "";
        // },
        tooltip: {
            show: false
        }

        };
        
        //网格
        this.grid={
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true,
            splitLine: { show: false },
            show:false
        };

        //x轴值
        this.xAxis=[
            {
                name:'单位',
                type : 'category',
                triggerEvent:true,
                nameLocation:'start',
                nameTextStyle:{fontWeight:'bold',fontSize:14},
                formatter: function (name) {
                return echarts.format.truncateText(name, 100, '14px Microsoft Yahei', '…');
                },
                data:that.xDataSource,
                axisLabel:{
                 formatter: function (value, index) {
                // 格式化成月/日，只在第一个刻度显示年份
                console.log(value);
                return that.xDataSource[index].name;
                // return  echarts.format.truncateText(value, 100, '14px Microsoft Yahei red', '…');
                }
            },
           
           }];

        //y轴值
        this.yAxis=[
            {
                type : 'value',
                name:'指标完成值%',
                axisLabel: {
                 formatter:'{value}%'

                 },
                 nameTextStyle:{fontWeight:'bold',fontSize:14},
                 nameLocation:'end'
            }];
            
        //条形数据模块
        this.series=that.seriesDataSource;
        
        //初始化Echarts控件
        this.initEcharts= function(){
        that.myChart = echarts.init(document.getElementById(that.controlId));       
        window.onresize = that.myChart.resize;
        }
      
       this.legendClick=function(selected){

              
        }
        
        //加载echarts
        this.LoadEcharts=function(){
             // that.DataTranslate(that.baseDataSource);
             that.option.series=that.seriesDataSource;
             that.option.title=that.title;
             that.option.yAxis=that.yAxis;
             that.option.xAxis = that.xAxis;
             that.option.tooltip = that.tooltip;
             // that.option.xAxis[0].data= that.xDataSource;
             that.option.grid=that.grid;
             that.option.legend=that.legend;     
             that.myChart.setOption($.extend(true,{},that.option));          
        }

        //事件绑定模块
        this.eventModule=(function() {
        var strategy = {};
        return {            
            addEventBind: function(key,fn) {
                strategy[key] = fn;
                that.myChart.on(key,strategy[key]);
            }
           }
          })();  

    };
    
    var EchartsModuleClass=EchartsModuleClass || {};
    //series数据样式
    EchartsModuleClass.series=[];
    EchartsModuleClass.series.itemTemplatge={name:'市场竞争指数',
            type:'bar',
            symbol:'circle',
            symbolSize :60,
            barWidth:1,
            label:{
                normal:{show:true,position:'top',
                formatter:function(params){
                    console.log(params);
                    return params.data['ranking'];
                }
            }
            },data:[]
        };

    //派发事件
   function DispatchEvent(){

     var currentIndex = -1;

     setInterval(function () {
        var dataLen = option.series[0].data.length;
        // 取消之前高亮的图形
        myChart.dispatchAction({
            type: 'downplay',
            seriesIndex: 0,
            dataIndex: currentIndex
        });
        currentIndex = (currentIndex + 1) % dataLen;
        // 高亮当前图形
        myChart.dispatchAction({
            type: 'highlight',
            seriesIndex: 0,
            dataIndex: currentIndex
        });
        // 显示 tooltip
        myChart.dispatchAction({
            type: 'showTip',
            seriesIndex: 0,
            dataIndex:currentIndex
        });
        }, 1000);   
   }

    var EchartsModuleClass=EchartsModuleClass || {};
    //series数据样式
    EchartsModuleClass.series=[];
    EchartsModuleClass.series.itemTemplatge={name:'市场竞争指数',
            type:'bar',
            symbol:'circle',
            symbolSize :60,
            barWidth:1,
            label:{
                normal:{show:true,position:'top',
                formatter:function(params){
                    console.log(params);
                    return params.data['ranking'];
                }
            }
            },data:[]
        };
