/*var Request = new Object();
Request = GetRequest();
var storeId=Request["storeId"];
$("#storeId").val(storeId);
*/
function getJobList(){
	cVe.startReqByMsgHandle(cVeName,'','','reqGetJobList','resGetJobList','ECP.handle.JobInfoHandle.handleMsg');
}

var org_id;
$(document).ready(function(){
	org_id=cVeUti.Cookie.getCookie("org_id");
	/*getJobList();*/
	getJobInfo();
});

function reqGetJobList(){
/*	var userId*/
	var req = {};
	req["op"] = "listWork";
	req["storeId"] = org_id;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resGetJobList(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId].data;
	alert(result.length);
	for(var i=0;i<result.length;i++){
		$("#jobListRow").append('<div class="col-md-12">'
                +'<div class="panel panel-default">'
                +'<div class="panel-heading">'
                    +'<h3>'+result[i].job_name+'</h3></div>'
                +'<div class="panel-body">'
                    +'<div class="row col-md-4">'
                    	+'<div class="row col-sm-12">'
                			+'<h4><b>基本信息</b></h4>'
                    		/*+'<h4>岗位代码:'+result[i].job_code+'</h4>'*/
                    		+'<h4>关键字:'+result[i].job_keyword+'</h4>'
                    		+'<h4>岗位时间类型:'+result[i].attendacne_type+'</h4>'
                    		+'<h4>岗位专业（按照国家标准专业分类）</h4>'
                    		+'<h4>岗位级别：初级/中级/高级</h4>'
                    		+'<h4>人数:'+result[i].number+'</h4>'
                    		+'<h4>城市:'+result[i].job_city+'</h4>'
                    		+'<h4>工作地点:'+result[i].job_location+'</h4>'
                    		+'<h4>发布时间:'+result[i].publish_time+'</h4>'
                    		+'<h4>结束时间:'+result[i].end_time+'</h4>'
                    		+'<h4>岗位描述:'+result[i].job_duty+'</h4></div></div>'
                	+'<div class="row col-md-4">'
                		+'<div class="row col-sm-12">'
                			+'<h4><b>基本待遇</b></h4>'
                			+'<h4>工资类型：基本+绩效 /基本+补贴 /绩效/基本</h4>'
                			+'<h4>工资:'+result[i].payment+'</h4>'
                			+'<h4>住宿提供：有偿提供/无偿提供/不提供</h4>'
                			+'<h4>其他待遇</h4></div></div>'
                    +'<div class="row col-md-4">'
                    	+'<div class="row col-sm-12">'
                    		+'<h4><b>基本要求</b></h4>'
                    		+'<h4>学历：无要求/高中/大专/本科/研究生/博士研究生</h4>'
                    		+'<h4>职称：无要求/初级/中级/高级/教授级</h4>'
                    		+'<h4>专业范围</h4>'
                    		+'<h4>身高范围</h4>'
                    		+'<h4>体重范围</h4>'
                    		+'<h4>其他要求:'+result[i].job_require+'</h4></div></div>'
                	+'<div class="row col-md-12">'
                    	+'<button type="button" class="btn btn-info" onclick="updateJobPage('+result[i].job_info_id+','+result[i].org_id+')">岗位修改</button>'
                    	+'<button type="button" class="btn btn-danger" onclick="del('+result[i].job_info_id+')">删除岗位</button></div></div></div></div>');
	}
}
/************************删除岗位******************************/
var jobNo;
function deleteJob(jobId){
	jobNo=jobId;
	cVe.startReqByMsgHandle(cVeName,'','','reqDeleteJob','resDeleteJob','ECP.handle.JobInfoHandle.handleMsg');
}

function reqDeleteJob(){
	var req = {};
	req["op"] = "deleteWork";
	req["jobId"] = jobNo;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resDeleteJob(){
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var resultTip=retDataObj[cVe.cEioVeDataId].resultTip;
	alert(resultTip);
	location.replace(location.href);
}


/************************岗位列表******************************/
function jobInfoList(){
	grid= $('#jobListInfoTable').jqGrid({ 
		  //caption: "网点信息",
		  datatype:'local',  
		  //altRows: true,
		  colNames:['岗位名称','岗位时间类型','岗位专业','工资','工作城市',''],
		  colModel:[  
		    
		   {id:'job_name',name:'job_name', width:'100%',align:'center'},  
		    {id:'attendacne_type',name:'attendacne_type',width:'100%',align:'center'},
		    {id:'job_type_name',name:'job_type_name', width:'100%',align:'center'},  
		    {id:'payment',name:'payment', width:'100%',align:'center'},
		    {id:'job_city',name:'job_city', width:'100%',align:'center'},
		    {id:'job_info_id',name:'job_info_id',key:true, width:'100%',align:'center',formatter:displayButtons}
		  ],  
		  pager:"#pager",  
		  sortname:'0',  
		  viewrecords: true,  
		  height:'100%',  
		  autowidth: true,
		  align:'center',
		  shrinkToFit:true,
		});	
}

function getJobInfo(){
	jobInfoList();
	cVe.startReqByMsgHandle(cVeName,'','','reqGetJobListInfo','resGetJobListInfo','ECP.handle.JobInfoHandle.handleMsg');

}

function reqGetJobListInfo(){
	var req = {};	
	req["op"] = "listWork";
	req["storeId"] = org_id;
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resGetJobListInfo(){
	$('#jobListInfoTable').jqGrid("clearGridData");
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId].data;
//	alert(result.length);
	if(result.length<=0){
		$("#prompt_info").css("display","inline-block");//输入为空时
		$("#table").css("display","none");
	}else{
		$("#prompt_info").css("display","none");
		jobInfoList();
		for(var i=0;i<result.length;i++){
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


function displayButtons(cellValue,options,rowObject){
	job_info_id = options.rowId;//这里的rowId是光见值，不是行号
	var detail = '<button class=" btn btn-primary my-jqgrid-button"  onclick="showJobDetailInfo('+job_info_id+')">查看</button>';
	var del = '<button class=" btn btn-danger my-jqgrid-button"  onclick="del('+job_info_id+')">删除</button>';
	return detail+" "+del;
}

function showJobDetailInfo(job_info_id){
	window.location.href="jobDetailPage.html?jobId="+job_info_id;
}
