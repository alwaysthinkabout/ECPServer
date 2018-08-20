var page_id;//页面ID,0:未受理页面,1:已受理页面,2：已批准页面,3:历史页面

function orderInfoList(){
	
	grid= $('#dataGrid').jqGrid({ 
		  //caption: "网点信息",
		  datatype:'local',  
		  altRows: false,
		  colNames:['序号','求职人姓名','求职人岗位','求职日期','操作','求职ID',],
		  colModel:[  
		    
		   {id:'number',name:'number', width:'100%',align:'center'},  
		    {id:'user_name',name:'user_name',width:'100%',align:'center'},
		    {id:'job_name',name:'job_name', width:'100%',align:'center'},  
		    {id:'apply_time',name:'apply_time', width:'100%',align:'center'},
		   /* {id:'apply_status_code',name:'apply_status_code', width:'100%',align:'center',hidden:'true'},*/
		    {id:'operate',name:'operate', width:'100%',align:'center',formatter:displayButtons},
		    
		    {id:'job_apply_id',name:'job_apply_id', key:true, width:'100%',align:'center',hidden:'true'}
		  ],  
		  pager:"#pager",  
		  rowNum: 16,  
		  sortname:'0',  
		  viewrecords: true,  
		  height:'100%',  
		  autowidth: true,
		  align:'center',
		 //multiselect: true,
		  shrinkToFit:true,
		  //styleUI:'Bootstrap',		
		});	
}

function unprocessedOrderInfoList(){
	
	grid= $('#dataGrid').jqGrid({ 
		  //caption: "网点信息",
		  datatype:'local',  
		  altRows: false,
		  colNames:['序号','求职人姓名','求职人岗位','求职日期','是否已读','操作','求职ID',],
		  colModel:[  
		    
		   {id:'number',name:'number', width:'100%',align:'center'},  
		    {id:'user_name',name:'user_name',width:'100%',align:'center'},
		    {id:'job_name',name:'job_name', width:'100%',align:'center'},  
		    {id:'apply_time',name:'apply_time', width:'100%',align:'center'},
		   /* {id:'apply_status_code',name:'apply_status_code', width:'100%',align:'center',hidden:'true'},*/
		    {id:'is_read',name:'is_read', width:'100%',align:'center'},
		    {id:'operate',name:'operate', width:'100%',align:'center',formatter:displayButtons},		    
		    {id:'job_apply_id',name:'job_apply_id', key:true, width:'100%',align:'center',hidden:'true'}
		  ],  
		  pager:"#pager",  
		  rowNum: 16,  
		  sortname:'0',  
		  viewrecords: true,  
		  height:'100%',  
		  autowidth: true,
		  align:'center',
		 //multiselect: true,
		  shrinkToFit:true,
		  //styleUI:'Bootstrap',		
		});	
}

function getOrderInfo(){
	var page_id = cVeUti.Cookie.getCookie("page_id");
	if(page_id==0){
		document.getElementById("title").innerHTML = "未受理求职申请";
		$('#dataGrid').jqGrid("clearGridData");
		jQuery('#dataGrid').GridUnload(); 
		//unprocessedOrderInfoList();//只有未受理求职信息才可能是未读状态
		/*$("#hasIs_read").css("display","inline-block");//输入为空时
		$("#all").css("display","none");
		$("#unRead").css("display","none");*/
	}else if(page_id==1){
		document.getElementById("title").innerHTML = "已受理求职申请";
		$('#dataGrid').jqGrid("clearGridData");
		jQuery('#dataGrid').GridUnload(); 
		//orderInfoList();
	}else if(page_id==2){
		document.getElementById("title").innerHTML = "已批准求职申请";
		$('#dataGrid').jqGrid("clearGridData");
		jQuery('#dataGrid').GridUnload(); 
		//orderInfoList();
	}else{
		document.getElementById("title").innerHTML = "历史求职申请";
		$('#dataGrid').jqGrid("clearGridData");
		jQuery('#dataGrid').GridUnload(); 
		//orderInfoList();
	}
	//orderInfoList();
	cVe.startReqByMsgHandle(cVeName,'','','reqGetOrderInfo','resGetOrderInfo','ECP.handle.JobApplyHandle.handleMsg');

}

/*$(document).ready(
		getOrderInfo()
);*/

function reqGetOrderInfo(){
	var req = {};	
	req["op"] = "showApplys";
	req["org_id"] = cVeUti.Cookie.getCookie("org_id");
	var page_id = cVeUti.Cookie.getCookie("page_id");
	if(page_id==0){
		req["apply_status"]= "未受理";
	}else if(page_id==1){
		req["apply_status"]= "已受理";
	}else if(page_id==2){
		req["apply_status"]= "已批准";
	}else{
		req["apply_status"]= "历史";
	}
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resGetOrderInfo(){
	$('#dataGrid').jqGrid("clearGridData");
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId].rows;
	if(result.length<=0){
		$("#prompt_info").css("display","inline-block");//输入为空时
		$("#tableArea").css("display","none");
		
	}else{
		if(cVeUti.Cookie.getCookie("page_id")==0)
		{
			unprocessedOrderInfoList();
		}else
			{
				orderInfoList();
			}
		$("#prompt_info").css("display","none");
		$("#tableArea").css("display","block");
		var myData = [];
		var j = 0;
		for(var i=0;i<result.length;i++){
			result[i].apply_time = result[i].apply_time.split(".")[0];//去掉时间后面的那个.0
			//alert(result.apply_time);
			if(result[i].is_read=="未读"){
				myData[j] = result[i];
				//alert(result[i].is_read);
				j++;
			}
			
			
		}
		if(j>0){
		msgbox("提示",1,"您有"+j+"条未读申请。");
		}
		for(var i = 0;i<result.length;i++){
			if(result[i].is_read=="已读"){
				myData[j++] = result[i];
			}
			//alert(data1[i].number);
			//alert(result[i].user_name);
		}
		for(var i=0;i<myData.length;i++){
			myData[i].number=i+1; 
		}
		var reader = {
		  page: 1 ,  
		  total: (myData.length-1)/pageSize +1,  
		  records: myData.length
		}; 				
		grid.setGridParam({data: myData, localReader: reader}).trigger('reloadGrid');
	}
}


function displayButtons(cellValue,options,rowObject){
	job_apply_id = options.rowId;//这里的rowId是光见值，不是行号
    
	var edit = '<button class=" btn btn-primary my-jqgrid-button"  onclick="showApplyInfo('+job_apply_id+')">查看</button>';
	return edit;
}

function showApplyInfo(job_apply_id){
	cVeUti.Cookie.setCookie("job_apply_id",job_apply_id);
	window.location.href="applyInfo.html";	
	//cVe.startReqByMsgHandle(cVeName,'','','reqGetApplyInfo','resGetApplyInfo','ECP.handle.JobApplyHandle.handleMsg');
}

function getApplyInfo(){
	var Request = new Object();
	Request = GetRequest();
	var flag =Request["flag"];
	if(flag=="fromSend")
	{
		$("#enterProcess").css("display","none");
		$("#accept").css("display","none");
		$("#generateContract").css("display","none");
		$("#return").css("display","none");
		$("#invite").css("display","inline-block");
	}else{
		var page_id = cVeUti.Cookie.getCookie("page_id");
		if(page_id==0){
			$("#enterProcess").css("display","inline-block");
		}else if(page_id==1){
			$("#accept").css("display","inline-block");
		}else if(page_id==2){
			$("#generateContract").css("display","inline-block");
		}else{
			$("#return").css("display","none");
		}
	}
	cVe.startReqByMsgHandle(cVeName,'','','reqGetApplyInfo','resGetApplyInfo','ECP.handle.JobApplyHandle.handleMsg');
}

function reqGetApplyInfo(){
	var req = {};
	req["op"] = "showApply";
	//req["userId"] = "test";
	req["job_apply_id"]= cVeUti.Cookie.getCookie("job_apply_id");	
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}
var myContractId ="";
var myContractTemplateId = "";
function resGetApplyInfo(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var data=retDataObj[cVe.cEioVeDataId].rows;
	//var data = result[0];
	//inText("job_Name",data.job_name);
	//alert(data.fileList.length);
	if(data.is_read=="未读")
	{
		var Request = new Object();
		Request = GetRequest();
		var flag =Request["flag"];
		var applysCount1 = cVeUti.Cookie.getCookie("applysCount");
		if((flag!="fromSend")&&(applysCount1!="")&&(applysCount1>1))
		{
		    $("#applyCounter").css("display","inline-block");
			document.getElementById("applyCounter").innerHTML = applysCount1-1;
			cVeUti.Cookie.setCookie("applysCount",applysCount1-1);
		}else{
			 $("#applyCounter").css("display","none");
			 cVeUti.Cookie.setCookie("applysCount",0);
		}
	}
	var myApplyStatus = data.apply_status_code;
	myContractId = data.contract_id;
	myContractTemplateId = data.contract_modelno;
	if(myApplyStatus=="已批准"&&myContractId!="")
	{
		$("#generateContract").css("display","none");
		$("#return").css("display","none");
		$("#showContract").css("display","inline-block");
	}else
		{
			$("#showContract").css("display","none");
		}
	document.getElementById("job_Name").innerHTML = data.job_name;
	document.getElementById("name").innerHTML = data.name;
	document.getElementById("apply_time").innerHTML = data.apply_time.split(".")[0];
	if(data.apply_demand=="")
	{
		document.getElementById("apply_demand").innerHTML = "无";
	}else
		{
		    document.getElementById("apply_demand").innerHTML = data.apply_demand;
		}	
	document.getElementById("job_apply_id").value = data.job_apply_id;
	document.getElementById("user_id").value = data.user_id;
	document.getElementById("emailAddress").innerHTML = data.email;
	document.getElementById("telephoneNum").innerHTML = data.phone;
	document.getElementById("id_cardno").innerHTML = data.ID_cardNo;
	document.getElementById("sex").innerHTML = data.sex;	
	document.getElementById("apply_status_code").innerHTML = data.apply_status_code;
	if(data.fileList.length<=0)
	{
		$("#attach_download").append('<p  id="fileName" class="my-lable1" ></p>');
		document.getElementById("fileName").innerHTML = "无";
	}else
		{
			var fileNum = 0;
			var fileList = data.fileList;
			for(var i=0;i<=fileList.length-1;i++){
				fileNum++;
				$("#attach_download").append('<a  id="fileName'+fileNum+'" class="my-lable1" style="line-height:40px;margin-right:10px;" target="_Blank" title="点击下载"' 
						+'href = "http://'+fileDownloadIp+':'+fileDownloadPort+'/ECPServer/download?name='+fileList[i].cert_path+'&type='+fileList[i].cert_name+'"></a>');
				//alert('fileName'+fileNum+'');
				//alert(document.getElementById('fileName'+fileNum+'').innerHTML );
				document.getElementById("fileName"+fileNum+"").innerHTML = fileList[i].filename;	
				//alert(fileList[i].cert_name);
			}
			if(fileList.length>=2)
			{
				$("#attach_download").append('<a  id="allFile" class="my-lable1" style="line-height:40px;margin-right:10px;" target="_Blank" title="点击下载全部文件"' 
					+'href = "http://'+fileDownloadIp+':'+fileDownloadPort+''+data.filesUrl+'">全部下载</a>');
			}
		}
	
}
//若该求职申请已经有了合同，则查看合同
function showMyContract()
{
	window.location.href = "contract.html?contract_id="+myContractId+"&template_no="+myContractTemplateId;
}
function enterProcss(){
	//$("#returnButton").css("display","none");
	//$("#acceptButton").css("display","none");
	$("#returnButton").remove();
	$("#acceptButton").remove();
	$("#confirmButton2").remove();
	$("#interviewInfo").css("display","none");
	$("#prompt_content").css("display","block");
	document.getElementById("prompt_header").innerHTML = "确定受理以下人员的求职申请？受理后我们将发出面试通知。";
	document.getElementById("prompt_content").innerHTML = document.getElementById("name").innerHTML;
	if(typeof $("#confirmButton1").html()=="undefined"){
		$("#modal-footer").append('<button type="button" class="btn btn-danger" id="confirmButton1"onclick="confirmButton1()">确  定</button>');
	}
	$("#confirmModal").modal('show');
	//cVe.startReqByMsgHandle(cVeName,'','','reqEnterProcess','resEnterProcess','ECP.handle.JobApplyHandle.handleMsg');
}

function confirmButton1()
{
	document.getElementById("prompt_header").innerHTML = "请填写面试时间与地点：";
	$("#interviewInfo").css("display","block");
	$("#confirmButton1").remove();
	//$("#confirmButton1").css("display","none");
	$("#prompt_content").css("display","none");
	if(typeof $("#confirmButton2").html()=="undefined"){
		$("#modal-footer").append('<button type="button" class="btn btn-danger" id="confirmButton2"onclick="confirmButton2()">确  定</button>');
	}
}
function confirmButton2()
{
	var date = document.getElementById("interviewDate").value;
	//var time = document.getElementById("interviewTime").value;
	var location = document.getElementById("interviewLocation").value;
	if(date=="")
	{
		alert("请选择面试时间！");
	}else if(location=="")
		{
			alert("请填写面试地点！");
		}else{
			cVe.startReqByMsgHandle(cVeName,'','','reqEnterProcess','resEnterProcess','ECP.handle.JobApplyHandle.handleMsg');
		}
	
}
function reqEnterProcess(){
	var req = {};
	req["op"] = "dealApply";
	req["job_apply_id"]= cVeUti.Cookie.getCookie("job_apply_id");
	req["apply_status2"] = "已受理";
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resEnterProcess(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId];
	//var data = result[0];
	if(result.result == "success"){
		$("#confirmModal").modal('hide');
		alert("成功");
		window.location.href="unprocessed.html";
		sendInterviewNotice();		
    }
}

//推送面试通知
function sendInterviewNotice(){
	cVe.startReqByMsgHandle(cVeName,'','','reqSendInterviewNotice','resSendInterviewNotice','ECP.handle.common.CCommonHandle.handleMsg');				
}

function reqSendInterviewNotice(){
	var req = {};
	var org_name =  cVeUti.Cookie.getCookie("org_name");
	var date = document.getElementById("interviewDate").value;
	var location = document.getElementById("interviewLocation").value;
	var job = document.getElementById("job_Name").innerHTML;
	var applyierName = document.getElementById("name").innerHTML;
	var sex = document.getElementById("sex").innerHTML;
	var call = "";
	if(sex=="男")
	{
	    call="先生";
	}else if(sex=="女")
		{
			call = "女士";
		}
	req["systemInfo"] = ""+applyierName+call+"，您好，这里是"+org_name+"，您对我司的"+job+"岗位申请现已被受理，现诚您来参加面试，面试时间： "+date+" ，面试地点："+location+"。谢谢！";
	//alert(req["systemInfo"]);
	req["toAccount"] = document.getElementById("user_id").value;
	req["fromAccount"] = cVeUti.Cookie.getCookie("org_id");
	req["chat_content"] = ""+applyierName+call+"，您好，这里是"+org_name+"，您对我司的"+job+"岗位申请现已被受理，现诚邀您来参加面试，面试时间： "+date+" ，面试地点："+location+"。谢谢！";
	req["user_id"] = document.getElementById("user_id").value;
	req["org_id"] = cVeUti.Cookie.getCookie("org_id");
	req["msg_type"] = "to";//表示从招聘端发出
	req["is_read"] = "已读";
	req["op"] = "webPush";
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resSendInterviewNotice(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var retData=retDataObj[cVe.cEioVeDataId];
	if(retData.result == "success"){

	}else{
		alert("失败");
	}
}
function returnConfirm()
{
	$("#returnButton").remove();
	$("#confirmButton2").remove();
	$("#confirmButton1").remove();
	$("#interviewInfo").css("display","none");
	$("#acceptButton").remove();
	$("#prompt_content").css("display","block");
	/*$("#confirmButton1").css("display","none");
	$("#confirmButton2").css("display","none");
	$("#interviewInfo").css("display","none");
	$("#acceptButton").css("display","none");
	$("#prompt_content").css("display","block");*/
	document.getElementById("prompt_header").innerHTML = "确定退回以下人员的求职申请？";
	document.getElementById("prompt_content").innerHTML = document.getElementById("name").innerHTML;
	if(typeof $("#returnButton").html()=="undefined"){
		$("#modal-footer").append('<button type="button" class="btn btn-danger" id="returnButton"onclick="returnApply()">确  定</button>');
	}
	$("#confirmModal").modal('show');	
}
function returnApply(){
	cVe.startReqByMsgHandle(cVeName,'','','reqReturnApply','resReturnApply','ECP.handle.JobApplyHandle.handleMsg');
}

function reqReturnApply(){
	var req = {};
	req["op"] = "dealApply";
	req["job_apply_id"]= cVeUti.Cookie.getCookie("job_apply_id");
	req["apply_status2"] = "已退回";
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resReturnApply(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId];
	//var data = result[0];
	if(result.result == "success"){
		$("#confirmModal").modal('hide');
		alert("成功退回");
		//cVeUti.Cookie.setCookie("page_id","0");
		sendReturnNotice();//退回通知
		window.location.href="unprocessed.html";
    }else
    	{
	    	$("#confirmModal").modal('hide');
			alert(result.resultTip+"，请先删除相应的合同");
    	}
}
//推送退回通知
function sendReturnNotice(){
	cVe.startReqByMsgHandle(cVeName,'','','reqSendReturnNotice','resSendReturnNotice','ECP.handle.common.CCommonHandle.handleMsg');				
}

function reqSendReturnNotice(){
	var req = {};
	var org_name =  cVeUti.Cookie.getCookie("org_name");
	var job = document.getElementById("job_Name").innerHTML;
	var applyierName = document.getElementById("name").innerHTML;
	var sex = document.getElementById("sex").innerHTML;
	var call = "";
	if(sex=="男")
	{
	    call="先生";
	}else if(sex=="女")
		{
			call = "女士";
		}
	req["systemInfo"] = ""+applyierName+call+"，您好，这里是"+org_name+"，您对我们公司的"+job+"的申请现已被退回，祝您生活愉快，谢谢！";
	//alert(req["systemInfo"]);
	req["toAccount"] = document.getElementById("user_id").value;;
	req["fromAccount"] = cVeUti.Cookie.getCookie("org_id");
	req["chat_content"] =""+applyierName+call+"，您好，这里是"+org_name+"，您对我们公司的"+job+"的申请现已被退回，祝您生活愉快，谢谢！";
	req["user_id"] = document.getElementById("user_id").value;
	req["org_id"] = cVeUti.Cookie.getCookie("org_id");
	req["msg_type"] = "to";//表示从招聘端发出
	req["is_read"] = "已读";
	req["op"] = "webPush";
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resSendReturnNotice(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var retData=retDataObj[cVe.cEioVeDataId];
	if(retData.result == "success"){

	}else{
		alert("失败");
	}
}
function acceptConfirm()
{
	$("#confirmButton1").remove();
	$("#confirmButton2").remove();
	$("#returnButton").remove();
	$("#interviewInfo").css("display","none");
	$("#acceptButton").remove();
	$("#prompt_content").css("display","block");
	document.getElementById("prompt_header").innerHTML = "确定批准以下人员的求职申请？";
	document.getElementById("prompt_content").innerHTML = document.getElementById("name").innerHTML;
	if(typeof $("#acceptButton").html()=="undefined"){
		$("#modal-footer").append('<button type="button" class="btn btn-danger" id="acceptButton"onclick="acceptApply()">确  定</button>');
	}
	$("#confirmModal").modal('show');
}
function acceptApply(){
	cVe.startReqByMsgHandle(cVeName,'','','reqAcceptApply','resAcceptApply','ECP.handle.JobApplyHandle.handleMsg');
}
function reqAcceptApply(){
	var req = {};
	req["op"] = "dealApply";
	req["job_apply_id"]= cVeUti.Cookie.getCookie("job_apply_id");
	req["apply_status2"] = "已批准";
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resAcceptApply(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId];
	//var data = result[0];
	if(result.result == "success"){ 	
		$("#confirmModal").modal('hide');
		sendAcceptNotice();
		alert("批准成功");
		//cVeUti.Cookie.setCookie("page_id","0");
		window.location.href="unprocessed.html";
    }
}
//推送批准通知
function sendAcceptNotice(){
	cVe.startReqByMsgHandle(cVeName,'','','reqSendAcceptNotice','resSendAcceptNotice','ECP.handle.common.CCommonHandle.handleMsg');				
}

function reqSendAcceptNotice(){
	var req = {};
	var org_name =  cVeUti.Cookie.getCookie("org_name");
	var job = document.getElementById("job_Name").innerHTML;
	var applyierName = document.getElementById("name").innerHTML;
	var sex = document.getElementById("sex").innerHTML;
	var call = "";
	if(sex=="男")
	{
	    call="先生";
	}else if(sex=="女")
		{
			call = "女士";
		}
	req["systemInfo"] =""+applyierName+call+"，您好，这里是"+org_name+"，您对我们公司的"+job+"的申请现已被批准，接下来请留意后续通知，谢谢！";
	req["toAccount"] = document.getElementById("user_id").value;;
	req["fromAccount"] = cVeUti.Cookie.getCookie("org_id");
	req["chat_content"] =""+applyierName+call+"，您好，这里是"+org_name+"，您对我们公司的"+job+"的申请现已被批准，接下来请留意后续通知，谢谢！";
	req["user_id"] = document.getElementById("user_id").value;
	req["org_id"] = cVeUti.Cookie.getCookie("org_id");
	req["msg_type"] = "to";//表示从招聘端发出
	req["is_read"] = "已读";
	req["op"] = "webPush";
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resSendAcceptNotice(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var retData=retDataObj[cVe.cEioVeDataId];
	if(retData.result == "success"){

	}else{
		alert("失败");
	}
}
function toPage(id){
	window.location.href="unprocessed.html";
	cVeUti.Cookie.setCookie("page_id",id);
	
}

function searchApply(){
	//orderInfoList();
	cVe.startReqByMsgHandle(cVeName,'','','reqSearchApply','resSearchApply','ECP.handle.JobApplyHandle.handleMsg');

}

/*$(document).ready(
		getOrderInfo()
);*/
function searchInfoList(){
	
	grid= $('#dataGrid').jqGrid({ 
		  //caption: "网点信息",
		  datatype:'local',  
		  altRows: false,
		  colNames:['序号','求职人姓名','求职人岗位','求职日期','求职状态','操作','求职ID',],
		  colModel:[  		    
		    {id:'number',name:'number', width:'100%',align:'center'},  
		    {id:'user_name',name:'user_name',width:'100%',align:'center'},
		    {id:'job_name',name:'job_name', width:'100%',align:'center'},  
		    {id:'apply_time',name:'apply_time', width:'100%',align:'center'},
		    {id:'apply_status_code',name:'apply_status_code', width:'100%',align:'center'},
		    {id:'operate',name:'operate', width:'100%',align:'center',formatter:displayButtons},		   
		    {id:'job_apply_id',name:'job_apply_id', key:true, width:'100%',align:'center',hidden:'true'}
		  ],  
		  pager:"#pager",  
		  rowNum: 16,  
		  sortname:'0',  
		  viewrecords: true,  
		  height:'100%',  
		  autowidth: true,
		  align:'center',
		 //multiselect: true,
		  shrinkToFit:true,
		 // height:200,
		 /* loadComplete:function(){
              var grid = $("#dataGrid");
              var ids = grid.getDataIDs();
              for (var i = 0; i < ids.length; i++) {
                  grid.setRowData ( ids[i], false, {height: 30 } );
              }},*/
		});	
	//grid.setGridParam({data: result, localReader: reader}).trigger('reloadGrid'); 
}
function reqSearchApply(){
	var req = {};	
	req["op"] = "showApplys";
	req["org_id"] = cVeUti.Cookie.getCookie("org_id");
	req["searchInfo"]= document.getElementById("searchApply").value;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resSearchApply(){
	//$('#dataGrid').jqGrid("clearGridData");
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId].rows;
	if(result.length<=0){
		document.getElementById("title").innerHTML = "查找结果";
		jQuery('#dataGrid').GridUnload(); 
		$("#tableArea").css("display","none");
		$("#prompt_info").css("display","block");
		alert("没有符合条件的查找结果");
		
		
	}else{
		$("#prompt_info").css("display","none");
		$("#tableArea").css("display","block");
		document.getElementById("title").innerHTML = "查找结果";
		$('#dataGrid').jqGrid("clearGridData");
		jQuery('#dataGrid').GridUnload(); 
		searchInfoList();
		
		//$("#dataGrid").jqGrid('showCol',["apply_status_code"]);
		for(var i=0;i<result.length;i++){
			result[i].apply_time = result[i].apply_time.split(".")[0];
			result[i].number=i+1; 
		}
		var reader = {
		  page: 1 ,  
		  total: (result.length-1)/pageSize +1,  
		  records: result.length
		}; 				
		grid.setGridParam({data: result, localReader: reader}).trigger('reloadGrid');
	}
}

//合同模板选择
function getContract_nameList(){
	cVe.startReqByMsgHandle(cVeName,'','','reqGetContract_nameList','resGetContract_nameList','ECP.handle.ContractHandle.handleMsg');
	
}

function reqGetContract_nameList(){
	var req = {};
	req["op"] = "contractTemplateList";
	req["org_id"] = cVeUti.Cookie.getCookie("org_id");
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resGetContract_nameList(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId].rows;
	if(result.length<=0){
		//msgbox("提示",2,"当前没有可用合同模板，请新建。");
		alert("当前没有可用合同模板，请前往“合同管理”模块新建合同模板。");
	}else{	
		var contactModelNameList = document.getElementById("contactModelName");
		contactModelNameList.options.length=0;
		contactModelNameList.options.add(new Option("请选择",""));
		for(var i = 0; i<result.length; i++){
			var retData = result[i];
				contactModelNameList.options.add(new Option(retData.contract_name,retData.template_no));
		}
		$("#contractGenerate").modal('show');
	}
}


