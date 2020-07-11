// 用户数据增加
function getUserData(dataArrays){
//	alert("getUserData:"+JSON.stringify(dataArrays));
	if(JSON.stringify(dataArrays) != "{}"){
		$(".colmn").attr("class", "colmn");
		$(".msg").attr("class", " tx-c msg disNone");
		document.getElementById("ORG_NAME").innerHTML = dataArrays.ORG_NAME == undefined ? "":dataArrays.ORG_NAME;
		document.getElementById("DEPT_NAME").innerHTML = dataArrays.DEPT_NAME == undefined ? "":dataArrays.DEPT_NAME;
		document.getElementById("CB_REAL_NAME").innerHTML = dataArrays.CB_REAL_NAME == undefined ? "":dataArrays.CB_REAL_NAME;
		document.getElementById("CF_REAL_NAME").innerHTML = dataArrays.CF_REAL_NAME == undefined ? "":dataArrays.CF_REAL_NAME;
		document.getElementById("TGNAME").innerHTML = dataArrays.TGNAME == undefined ? "":dataArrays.TGNAME;
		document.getElementById("CONS_NO").innerHTML = dataArrays.CONS_NO == undefined ? "":dataArrays.CONS_NO;
		document.getElementById("CONS_NAME").innerHTML = dataArrays.CONS_NAME == undefined ? "":dataArrays.CONS_NAME;
		document.getElementById("ELEC_ADDR").innerHTML = dataArrays.ELEC_ADDR == undefined ? "":dataArrays.ELEC_ADDR;
		document.getElementById("MR_SECT_NO").innerHTML = dataArrays.MR_SECT_NO == undefined ? "":dataArrays.MR_SECT_NO;
		document.getElementById("STATUS_CODE").innerHTML = dataArrays.STATUS_CODE == undefined ? "":dataArrays.STATUS_CODE;
		document.getElementById("BUILD_DATE").innerHTML = dataArrays.BUILD_DATE == undefined ? "":dataArrays.BUILD_DATE;
		document.getElementById("CUST_QUERY_NO").innerHTML = dataArrays.CUST_QUERY_NO == undefined ? "":dataArrays.CUST_QUERY_NO;
		document.getElementById("DQMOBILE").innerHTML = dataArrays.DQMOBILE == undefined ? "":dataArrays.DQMOBILE;
		document.getElementById("TSDMOBILE").innerHTML = dataArrays.TSDMOBILE == undefined ? "":dataArrays.TSDMOBILE;
		document.getElementById("ZWMOBILE").innerHTML = dataArrays.ZWMOBILE == undefined ? "":dataArrays.ZWMOBILE;
		document.getElementById("TYPE").innerHTML = dataArrays.TYPE == undefined ? "":dataArrays.TYPE;
		document.getElementById("DYDJ").innerHTML = dataArrays.DYDJ == undefined ? "":dataArrays.DYDJ;
		document.getElementById("TRADE").innerHTML = dataArrays.TRADE == undefined ? "":dataArrays.TRADE;
		document.getElementById("JLFS").innerHTML = dataArrays.JLFS == undefined ? "":dataArrays.JLFS;
		document.getElementById("THIS_READ").innerHTML = dataArrays.THIS_READ == undefined ? "":dataArrays.THIS_READ;
		document.getElementById("BAR_CODE").innerHTML = dataArrays.BAR_CODE == undefined ? "":dataArrays.BAR_CODE;
		document.getElementById("T_FACTOR").innerHTML = dataArrays.T_FACTOR == undefined ? "":dataArrays.T_FACTOR;
//		document.getElementById("PAP_R").innerHTML = dataArrays.PAP_R == undefined ? "":dataArrays.PAP_R;
		document.getElementById("CONTRACT_CAP").innerHTML = dataArrays.CONTRACT_CAP == undefined ? "":dataArrays.CONTRACT_CAP;
		document.getElementById("RUN_CAP").innerHTML = dataArrays.RUN_CAP == undefined ? "":dataArrays.RUN_CAP;
		document.getElementById("PREPAY_BAL").innerHTML = dataArrays.PREPAY_BAL == undefined ? "":dataArrays.PREPAY_BAL + "元";
		
		// 解决页面高度问题
		$("#tabs-container").height($("#userInfo").outerHeight(true));
	}else{
		$(".colmn").attr("class", "colmn disNone");
		$(".msg").attr("class", " tx-c msg ");
	}
	
}