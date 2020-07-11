
String.prototype.stringReplaceAll = function (s1, s2) {
    return this.split(s1).join(s2);
    //return this.replace(new RegExp(s1, "gm"), s2);
}

var jsHelp = {

        setCookie:function(key,val,time){//设置cookie方法
            var date=new Date(); //获取当前时间
            var expiresDays=time;  //将date设置为n天以后的时间
            date.setTime(date.getTime()+expiresDays*24*3600*1000); //格式化为cookie识别的时间
            document.cookie=key + "=" + val +";expires="+date.toGMTString();  //设置cookie
        },
        getCookie:function(key){//获取cookie方法
            /*获取cookie参数*/
            var getCookie = document.cookie.replace(/[ ]/g,"");  //获取cookie，并且将获得的cookie格式化，去掉空格字符
            var arrCookie = getCookie.split(";")  //将获得的cookie以"分号"为标识 将cookie保存到arrCookie的数组中
            var tips;  //声明变量tips
            for(var i=0;i<arrCookie.length;i++){   //使用for循环查找cookie中的tips变量
                var arr=arrCookie[i].split("=");   //将单条cookie用"等号"为标识，将单条cookie保存为arr数组
                if(key==arr[0]){  //匹配变量名称，其中arr[0]是指的cookie名称，如果该条变量为tips则执行判断语句中的赋值操作
                    tips=arr[1];   //将cookie的值赋给变量tips
                    break;   //终止for循环遍历
                }
            }
            return tips;
        },
        deleteCookie:function(key){ //删除cookie方法
            var date = new Date(); //获取当前时间
            date.setTime(date.getTime()-10000); //将date设置为过去的时间
            document.cookie = key + "=v; expires =" +date.toGMTString();//设置cookie
        },

    jsNewId: function () {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
            var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
            return v.toString(16);
        });
    }
    ,
    getDateString: function () {
        var curTime = new Date();
     
        try {
            var year = curTime.getFullYear();
            var month =curTime.getMonth();
            var day = curTime.getDate();
            var hour = curTime.getHours();
            var minute = curTime.getMinutes();
            var second = curTime.getSeconds();

            //当前时间  
            curTime = year + "" + month + "" + day + "" + hour + "" + minute + "" + second + "";
                  
        } catch (e) {

        }
        return curTime;
    }
    ,
    getFormatDate: function (value) {
        var curTime = value.toString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '');
        try {
            var date = new Date(curTime);
            var month = this.zeroFill(date.getMonth() + 1);//月  
            var day = this.zeroFill(date.getDate());//日  
            var hour = this.zeroFill(date.getHours());//时  
            var minute = this.zeroFill(date.getMinutes());//分  
            var second = this.zeroFill(date.getSeconds());//秒  

            //当前时间  
            var curTime = date.getFullYear() + "/" + month + "/" + day
                    + " " + hour + ":" + minute + ":" + second;
        } catch (e) {

        }
        return curTime;

    },
    getFormatDay: function (value) {
        var curTime = value.toString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '');
        try {
            var date = new Date(value);
            var month = this.zeroFill(date.getMonth() + 1);//月  
            var day = this.zeroFill(date.getDate());//日  


            //当前时间  
            curTime = date.getFullYear() + "/" + month + "/" + day;


        } catch (e) {

        }

        return curTime;

    },
    add0:function(m){return m<10?'0'+m:m },
    getLocalTime:function(nS) {
        var time = new Date(nS);
        var y = time.getFullYear();
        var m = time.getMonth()+1;
        var d = time.getDate();
        var h = time.getHours();
        var mm = time.getMinutes();
        var s = time.getSeconds();
        return y+'-'+this.add0(m)+'-'+this.add0(d)+' '+this.add0(h)+':'+this.add0(mm)+':'+this.add0(s);
    },
    zeroFill: function (i) {
        if (i >= 0 && i <= 9) {
            return "0" + i;
        } else {
            return i;
        }
    },

    getQueryString: function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(decodeURI(r[2])); return '';
    }


}
