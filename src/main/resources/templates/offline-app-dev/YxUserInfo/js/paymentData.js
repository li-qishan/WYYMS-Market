function getPaymentData(dataArrays) {
	// alert("getPaymentData");
//	$("#payment").empty();
	document.getElementById('payment').innerHTML = "";
	if (dataArrays != null) {
		var paymentDiv = document.getElementById("payment");

		var divbgx = document.createElement("div");
		divbgx.className = "gui-bgx";
		var divtop = document.createElement("div");
		divtop.className = "gui-list-top";
		var divwrapper = document.createElement("div");
		divwrapper.className = "gui-wrapper";
		if(dataArrays.length<=0){
			divwrapper.className = "gui-wrapper tx-c";
            divwrapper.innerHTML = "无业务数据！"
		}
		for (var i = 0; i < dataArrays.length; i++) {
			// 获取单条数据
			var obj = dataArrays[i];
			// 创建外层div
			var div = document.createElement("div");
			div.className = "ub c-wh uinn-a1 ubb bc-border";
			
			var div1 = document.createElement("div");
			div1.className = "gui-tariff-date fs9";

			var p = document.createElement("p");
			// 未入账
			if(obj.paymentTime ==  "AAAAAA"){
				p.innerHTML = "AAAAAA";
				
			}else {
				p.innerHTML = obj.time;
				
				var span = document.createElement("span");
				span.innerHTML = obj.paymentTime;
				
				div1.appendChild(span);
			}
			
			// 增加子元素
			div1.appendChild(p);
			// 增加子元素
			div.appendChild(div1);

			var div2 = document.createElement("div");
			div2.className = "gui-tariff-d t-blu fs9 ufm1 ut-s";
			div2.innerHTML = obj.PAYMENTTYPE;
			// 增加子元素
			div.appendChild(div2);
			
			var div3 = document.createElement("div");
			div3.className = "gui-tariff-d t-blu fs9 ufm1 ut-s";
			div3.innerHTML = obj.RCV_AMT +  '元';
			// 增加子元素
			div.appendChild(div3);

			divwrapper.appendChild(div);
		}
		
        //
        // //创建input标签
        // var div5 = document.createElement("button");
        // div5.className = "btn-side-more";
        // div5.innerHTML = "PAY加载更多...";
        // //div4.type = "button";
        // //div4.value = "加载更多..";
        // div5.id = "btn_more_pay";
        // //div4.name = "加载更多...";
        //
        //
        // // 增加子元素 获取上一层父级
        // var tabContainer = document.getElementById("tabs-container");
        // tabContainer.appendChild(div5);

        paymentDiv.appendChild(divbgx);
		paymentDiv.appendChild(divtop);
		paymentDiv.appendChild(divwrapper);
		
		// 解决页面高度问题
		$("#tabs-container").height($("#payment").outerHeight(true));
	}
}