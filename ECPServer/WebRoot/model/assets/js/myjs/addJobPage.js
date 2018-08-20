function jobAdd(){
	var publishTime=$("#publishTime").val();
	var endTime=$("#endTime").val();
	var jobName=$("#jobName").val();
	var city=$("#city").val();
	var number=$("#number").val();
	//var payment=$("#payment").val();
	 
	var curTime = getNowFormatDate();
	if(jobName=="")
	{
		alert("岗位名称不能为空!");
		return;
	}
	if(publishTime==""||endTime==""){
		alert("发布时间和结束时间不能为空");
		return;
	}
	if(publishTime<curTime)
	{
		alert("发布时间不得早于今天!");
		return;
	}
	if(endTime<=publishTime)
	{
		alert("结束时间必须晚于发布时间!");
		return;
	}
	if(city==null)
	{
		alert("请重新确认城市!");
		return;
	}
	var reg = new RegExp("^[0-9]*$");
    var obj = document.getElementById("payment");
      if(obj.value=="")
	  {
	  	alert("工资不能为空!");
	  	return;
	  }
	  if(!reg.test(obj.value)){
	      alert("工资只能为数字!");
	      return;
	  }
	  if(!reg.test(number)){
		  alert("招聘人数只能为数字！");
		  return;
	  }
	cVe.startReqByMsgHandle(cVeName,'','','reqJobAdd','resJobAdd','ECP.handle.JobInfoHandle.handleMsg');

}

//获取当前日期
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    //var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
            /*+ " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();*/
    return currentdate;
}

$(document).ready(function(){
	org_id=cVeUti.Cookie.getCookie("org_id");
	$("#storeId").val(org_id);
	getMajorList();
});

function GetJsonObject() {
	var job=$("#addJobForm").serializeArray();
	var theRequest = new Object();
	for(var i=0;i<job.length;i++){
		theRequest[job[i].name]=unescape(job[i].value);
	}
	return theRequest;
}


function reqJobAdd(){
	var job = new Object();
	job = GetJsonObject();
	var req = {};
	req["op"] = "addWork";
	req["job"] = job;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resJobAdd(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId].result;
	if(result=="fail"){
		alert("岗位添加失败");
	}else{
		alert("岗位添加成功");
		location.replace("jobListPage.html");
	}
	
}

/********************  获取专业列表  **********************/
function getMajorList(){
	cVe.startReqByMsgHandle(cVeName,'','','reqMajorList','resMajorList','ECP.handle.JobInfoHandle.handleMsg');
	
}
function reqMajorList(){
	var req = {};
	req["op"] = "jobTypeList";
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resMajorList(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var data=retDataObj[cVe.cEioVeDataId].data;
//	alert(data.length);
	
	for(var i=0;i<data.length;i++){
		$("#job_type_pcode").append("<option  value='"+data[i].type_id+"'>"+data[i].job_type_name+"</option>");
	}
	getSecondType();
}

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
