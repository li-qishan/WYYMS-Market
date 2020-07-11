function getElectricData(dataArrays) {
	// $("#electric").empty();
	document.getElementById('electric').innerHTML = "";
	// var len = electricTable.rows.length;
	// for (var i = 1; i < len; i++) {
	// electricTable.deleteRow(1);// 也能够写成table.deleteRow(0);
	// }
	if (dataArrays != null) {
		var electricDiv = document.getElementById("electric");

		var divbgx = document.createElement("div");
		divbgx.className = "gui-bgx";
		var divtop = document.createElement("div");
		divtop.className = "gui-list-top";

		var divwrapper = document.createElement("div");
		divwrapper.className = "gui-wrapper";
		if (dataArrays.length <= 0) {
			divwrapper.className = "gui-wrapper tx-c";
			divwrapper.innerHTML = "查询失败，该用户不在辖区内！"
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
			p.innerHTML = obj.year + "年" + obj.month + "月";

//			var span = document.createElement("span");
//			span.innerHTML = obj.mouthDay;
//			div1.appendChild(span);

			// 增加子元素
			div1.appendChild(p);
			// 增加子元素
			div.appendChild(div1);

			var div2 = document.createElement("div");
			div2.className = "gui-tariff-d t-blu fs9 ufm1 ut-s";
			div2.innerHTML = obj.T_SETTLE_PQ + "kW.h";
			// 增加子元素
			div.appendChild(div2);

			var div3 = document.createElement("div");
			div3.className = "gui-tariff-d t-blu fs9 ufm1 ut-s";
			div3.innerHTML = obj.T_AMT + '元';
			// 增加子元素
			div.appendChild(div3);

			divwrapper.appendChild(div);
		}

		electricDiv.appendChild(divbgx);
		electricDiv.appendChild(divtop);
		electricDiv.appendChild(divwrapper);
		
		// 解决页面高度问题
		$("#tabs-container").height($("#electric").outerHeight(true));
	}
}