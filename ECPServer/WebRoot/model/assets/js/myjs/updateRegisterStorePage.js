/*function getStoreDetail(){
	cVe.startReqByMsgHandle(cVeName,'','','reqGetStoreDetail','resGetStoreDetail','ECP.handle.OrganizationInfoHandle.handleMsg');
}*/

var Request = new Object();
Request = GetRequest();
var storeId=Request["storeId"];

$(document).ready(function(){
//	getStoreDetail();
	
}
);

/*function reqGetStoreDetail(){
	var userId
	var req = {};
	req["op"] = "storeDetail";
	req["storeId"] = storeId;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resGetStoreDetail(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId].store;
	$("#storeId").val(result.org_id);
	$("#storeName").val(result.org_name);
	$("#license_no").val(result.license_no);
	$("#registerRegion").val(result.reg_address);
	var registerRegion=result.reg_address;
	var registerRegionArray=registerRegion.split("-");
	$("#province").val(registerRegionArray[0]);
//	alert(registerRegionArray[1]);
	$("#registerPerson").val(result.legal_rep);
	$("#registerTime").val(result.reg_time);
//	$("#workPlace").val(result.site);
	var workPlace=result.site;
	var workPlaceArray=workPlace.split("-");
	$("#province1").val(workPlaceArray[0]);
	$("#registerFund").val(result.reg_capital);
	$("#coreBusiness").val(result.business_scope);
	$("#properties").val(result.org_type);
	$("#staffAmount").val(result.staffcount);
	$("#annualSales").val(result.site);//noData
	$("#qualification").val(result.site);//noData 
	$("#officialWebsite").val(result.website);
	$("#contacts").val(result.site);//noData
	$("#phone").val(result.site);//noData
	$("#email").val(result.site);//noData
	
	showChild(province, city, cityArr);
	showChild(province1, city1, cityArr);
    $("#city").val(registerRegionArray[1]);
    $("#city1").val(workPlaceArray[1]);
}

*/

function storeUpdate(){
	cVe.startReqByMsgHandle(cVeName,'','','reqStoreUpdate','resStoreUpdate','ECP.handle.OrganizationInfoHandle.handleMsg');
}

function GetJsonObject() {
	/*var province=$("#province").val();
	var city=$("#city").val();
	var registerRegion=province+"-"+city;
	$("#registerRegion").val(registerRegion);
	var province1=$("#province1").val();
	var city1=$("#city1").val();
	var workPlace=province1+"-"+city1;
	$("#workPlace").val(workPlace);*/
	var store=$("#storeSettingForm").serializeArray();
	var theRequest = new Object();
	for(var i=0;i<store.length;i++){
		theRequest[store[i].name]=unescape(store[i].value);
	}
	return theRequest;
}


function reqStoreUpdate(){
	var store = new Object();
	store = GetJsonObject();
	var req = {};
	req["op"] = "storeUpdate";
	req["store"] = store;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resStoreUpdate(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var resultTip=retDataObj[cVe.cEioVeDataId].resultTip;
	alert(resultTip);
	location.replace("informationSettingPage.html?storeId="+storeId);
}