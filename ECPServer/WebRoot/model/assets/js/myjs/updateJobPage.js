function getJobDetail(){
	cVe.startReqByMsgHandle(cVeName,'','','reqGetJobDetail','resGetJobDetail','ECP.handle.JobInfoHandle.handleMsg');
}

var Request = new Object();
Request = GetRequest();
var storeId=Request["storeId"];
var jobId=Request["jobId"];
//var typeId=new Array();
//var typeName=new Array();


$(document).ready(function(){
	getJobDetail();
});

function reqGetJobDetail(){
/*	var userId*/
	var req = {};
	req["op"] = "loadWorkInfo";
	req["jobId"] = jobId;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

var keywords = "";
var jobLocation ="";
var payment ="";
var publishTime = "";
var endTime = "";
var city1 = "";
var number = "";
var requirements = "";
var jobName = "";
var timeType = "";
var description = "";
var other_payment = "";
var accommodation = "";
var degree = "";
var title = "";
var payment_type = "";
var job_type_scode = "";
function resGetJobDetail(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId].job[0];
	var jobTypes=retDataObj[cVe.cEioVeDataId].jobTypes;
	var jobSecondTypes = retDataObj[cVe.cEioVeDataId].jobSecondTypes;
	for(var i=0;i<jobTypes.length;i++){
		$("#job_type_pcode").append("<option  value='"+jobTypes[i].type_id+"'>"+jobTypes[i].job_type_name+"</option>");
	}
	for(var i=0;i<jobSecondTypes.length;i++){
		$("#job_type_scode").append("<option  value='"+jobSecondTypes[i].type_id+"'>"+jobSecondTypes[i].job_type_name+"</option>");
	}
	$("#storeId").val(result.storeId);
	$("#keywords").val(result.keywords);
	$("#jobLocation").val(result.jobLocation);
	$("#payment").val(result.payment);
	$("#jobId").val(result.jobId);
	$("#publishTime").val(result.publishTime);
	$("#province").val(result.jobProvince);
	showChild(province, city, cityArr);
	$("#city").val(result.jobCity);
	$("#number").val(result.number);
	$("#endTime").val(result.endTime);
	$("#requirements").val(result.requirements);
	$("#jobName").val(result.jobName);
	$("#timeType").val(result.timeType);
	$("#description").val(result.description);
	$("#job_type_pcode").val(result.typeId);
	$("#job_type_scode").val(result.job_type_scode);
	$("#other_payment").val(result.other_payment);
	$("#accommodation").val(result.accommodation);
	$("#degree").val(result.degree);
	$("#title").val(result.title);
	$("#payment_type").val(result.payment_type);
	$("#storeId").val(result.storeId);
	//用于判断是否修改
	keywords = $("#keywords").val();
	jobLocation = $("#jobLocation").val();
	payment = $("#payment").val();
	publishTime = $("#publishTime").val();
	city1 = $("#city").val();
	number = $("#number").val();
	endTime = $("#endTime").val();
	requirements = $("#requirements").val();
	jobName = $("#jobName").val();
	timeType = $("#timeType").val();
	description = $("#description").val();
	other_payment = $("#other_payment").val();
	accommodation = $("#accommodation").val();
	degree = $("#degree").val();
	title = $("#title").val();
	payment_type = $("#payment_type").val();
	job_type_scode = $("#job_type_scode").val();
}

function confirmUpdate() { 
	var msg = "您没有修改任何内容，是否确定重新发布？"; 
	if (confirm(msg)==true){
		var days = DateMinus(publishTime,endTime);
		var newEndTime = dateAddDays(getNowFormat_Date(),days);
		$("#publishTime").val(getNowFormat_Date());
		$("#endTime").val(newEndTime);
		cVe.startReqByMsgHandle(cVeName,'','','reqJobUpdate','resJobUpdate','ECP.handle.JobInfoHandle.handleMsg');		
		return true; 
	}else{ 
		location.replace("jobListPage.html");
		return false; 
	} 
}
function jobUpdate(){
	if(keywords == $("#keywords").val()&&
	jobLocation ==$("#jobLocation").val()&&
	payment == $("#payment").val()&&
	publishTime ==$("#publishTime").val()&&
	city1 == $("#city").val()&&
	number == $("#number").val()&&
	endTime == $("#endTime").val()&&
	requirements == $("#requirements").val()&&
	jobName == $("#jobName").val()&&
	timeType == $("#timeType").val()&&
	description == $("#description").val()&&
	other_payment == $("#other_payment").val()&&
	accommodation == $("#accommodation").val()&&
	degree == $("#degree").val()&&
	title == $("#title").val()&&
	payment_type == $("#payment_type").val()&&
	job_type_scode== $("#job_type_scode").val())
	{
		confirmUpdate();
	}else{
		cVe.startReqByMsgHandle(cVeName,'','','reqJobUpdate','resJobUpdate','ECP.handle.JobInfoHandle.handleMsg');
	}
}

function GetJsonObject() {
	var job=$("#updateJobForm").serializeArray();
	var theRequest = new Object();
	for(var i=0;i<job.length;i++){
		theRequest[job[i].name]=unescape(job[i].value);
	}
	return theRequest;
}


function reqJobUpdate(){
	var job = new Object();
	job = GetJsonObject();
	var req = {};
	req["op"] = "updateWork";
	req["job"] = job;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resJobUpdate(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var resultTip=retDataObj[cVe.cEioVeDataId].resultTip;
	alert(resultTip);
	location.replace("jobListPage.html");
}
//获取二级岗位专业
function getSecondType(){
	cVe.startReqByMsgHandle(cVeName,'','','reqGetSecondType','resGetSecondType','ECP.handle.JobInfoHandle.handleMsg');
	
}
function reqGetSecondType(){
	var req = {};
	req["op"] = "jobTypeSecondList";
	req["root_id"] = document.getElementById("job_type_pcode").value;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resGetSecondType(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var data=retDataObj[cVe.cEioVeDataId].data;
    var type_idList = document.getElementById("job_type_scode");
    type_idList.options.length=0;
	//contactModelNameList.options.add(new Option("请选择",""));
	for(var i = 0; i<data.length; i++){
		type_idList.options.add(new Option(data[i].job_type_name,data[i].type_id));
	}
}
