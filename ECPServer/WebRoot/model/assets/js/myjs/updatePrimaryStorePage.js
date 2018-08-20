var stateIdforFileUpload="";
var stateYearForFileUpload;
function getPrimaryStoreDetailAnnual(id){
	stateIdforFileUpload=id;
	cVe.startReqByMsgHandle(cVeName,'','','reqGetPrimaryStoreAnnual','resGetPrimaryStoreAnnual','ECP.handle.OrganizationInfoHandle.handleMsg');
}

function reqGetPrimaryStoreAnnual(){
	var req = {};
	req["op"] = "orgStateDetailAnnual";
	req["id"] = stateIdforFileUpload;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resGetPrimaryStoreAnnual(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var year=retDataObj[cVe.cEioVeDataId].data.year;
	document.getElementById("stateFileUpload_cert_name").value = year+"年度企业认证材料"; 
}

/*function getPrimaryStoreDetail(){
	cVe.startReqByMsgHandle(cVeName,'','','reqGetPrimaryStoreDetail','resGetPrimaryStoreDetail','ECP.handle.OrganizationInfoHandle.handleMsg');
}

var Request = new Object();
Request = GetRequest();
var storeId=Request["storeId"];

$(document).ready(function(){
	$("#org_id").val(storeId);
	getPrimaryStoreDetail();
});

function reqGetPrimaryStoreDetail(){
	var userId
	var req = {};
	req["op"] = "orgStateDetail";
	req["org_id"] = storeId;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resGetPrimaryStoreDetail(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId].data;
	$("#current_indebt").val(result.current_indebt);
	$("#profit").val(result.profit);
	$("#techstaff_num").val(result.techstaff_num);
	$("#taxation").val(result.taxation);
	$("#license_no").val(result.license_no);
	$("#loan_balance").val(result.loan_balance);
	$("#total_assets").val(result.total_assets);
	$("#net_sales").val(result.net_sales);
	$("#rd_budget").val(result.rd_budget);
	$("#operate_expense").val(result.operate_expense);
	$("#employee_num").val(result.employee_num);
	$("#current_assets").val(result.current_assets);
	$("#year").val(result.year);
	$("#total_indebt").val(result.total_indebt);
	$("#revenue").val(result.revenue);
//	alert(result.org_name);
}



function primaryStoreUpdate(){
	var license_no=$("#license_no").val();
	var year=$("#year").val();
	var employee_num=$("#employee_num").val();
	var techStaff_num=$("#techStaff_num").val();
	if(license_no==""){
		alert("营业执照号不能为空");
	}else if(year==""){
		alert("年度不能为空");
	}else if(employee_num==""){
		alert("员工数不能为空");
	}else if(techStaff_num==""){
		alert("技术人员数不能为空");
	}else{
		cVe.startReqByMsgHandle(cVeName,'','','reqPrimaryStoreUpdate','resPrimaryStoreUpdate','ECP.handle.OrganizationInfoHandle.handleMsg');
	}
}

function GetJsonObject() {
	var state=$("#primaryStoreUpdateForm").serializeArray();
	var theRequest = new Object();
	for(var i=0;i<state.length;i++){
		theRequest[state[i].name]=unescape(state[i].value);
	}
	return theRequest;
}


function reqPrimaryStoreUpdate(){
	var state = new Object();
	state = GetJsonObject();
	var req = {};
	req["op"] = "orgStateUpdate";
	req["state"] = state;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resPrimaryStoreUpdate(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var resultTip=retDataObj[cVe.cEioVeDataId].resultTip;
	alert(resultTip);
	location.replace("informationSettingPage.jsp?storeId="+storeId);
//	location.replace(location.href);
}*/