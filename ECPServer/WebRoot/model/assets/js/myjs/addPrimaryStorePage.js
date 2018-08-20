var Request = new Object();
Request = GetRequest();
var storeId=Request["storeId"];

function primaryStoreAdd(){
//	var license_no=$("#license_no").val();
	var year=$("#year").val();
	var employee_num=$("#employee_num").val();
	var techStaff_num=$("#techStaff_num").val();
	/*if(license_no==""){
		alert("营业执照号不能为空");
	}else */
	if(year==""){
		alert("年度不能为空");
	}else if(employee_num==""){
		alert("员工数不能为空");
	}else if(techStaff_num==""){
		alert("技术人员数不能为空");
	}else{
		cVe.startReqByMsgHandle(cVeName,'','','reqPrimaryStoreAdd','resPrimaryStoreAdd','ECP.handle.OrganizationInfoHandle.handleMsg');
	}
}


/*$(document).ready(
	$("#org_id").val(storeId)
);*/


function GetJsonObject() {
	var store=$("#primaryStoreAddForm").serializeArray();
	var theRequest = new Object();
	for(var i=0;i<store.length;i++){
		theRequest[store[i].name]=unescape(store[i].value);
	}
	return theRequest;
}


function reqPrimaryStoreAdd(){
	var store = new Object();
	store = GetJsonObject();
	var req = {};
	req["op"] = "orgStateAdd";
	req["store"] = store;
	req["user_id"] = cVeUti.Cookie.getCookie("user_id");
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resPrimaryStoreAdd(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var resultTip=retDataObj[cVe.cEioVeDataId].resultTip;
	alert(resultTip);
}