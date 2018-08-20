var startDate="";
var endDate="";
$(document).ready(function(){
	getEnterpriseInfoAuditedList();
	$("#daterange-default").daterangepicker({
		format: 'YYYY/MM/DD',
		showDropdowns: !0,
		ranges: {
			"本周": [moment().startOf("week"), moment().endOf("week")],
			"上周": [moment().subtract(1,"week").startOf("week"), moment().subtract(1,"week").endOf("week")],
			"本月": [moment().startOf("month"), moment().endOf("month")],
			"过去30天": [moment().subtract(29,"days"), moment()],
			"上月":[moment().subtract(1,"month").startOf("month"), moment().subtract(1,"month").endOf("month")],
			"过去半年":[moment().subtract(6,"month").startOf("month"), moment().subtract(1,"month").endOf("month")],
			"过去一年": [moment().subtract(1,"year").startOf("year"), moment().subtract(1,"year").endOf("year")],
        },
        separator: " 至 ",
        locale: {
            applyLabel: "确认",
            cancelLabel: "清除",
            fromLabel: "起始",
            toLabel: "截止",
            customRangeLabel: "自定义",
            daysOfWeek: ["日", "一", "二", "三", "四", "五", "六"],
            monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            firstDay: 1
        }
	});
	$('#daterange-default').on('cancel.daterangepicker', function(ev, picker) {
	  $('#daterange-default').val('');
	  dateRangeSQL = "";
	});
	$('#daterange-default').on('apply.daterangepicker', function(ev, picker) {
	  console.log("start:"+picker.startDate.format('YYYY-MM-DD')+"\nend:"+picker.endDate.format('YYYY-MM-DD'));
	  startDate=new Date(picker.startDate.format('YYYY-MM-DD'));
	  endDate=new Date(picker.endDate.format('YYYY-MM-DD'));
	});
});

/************************公司已审核信息列表******************************/
function enterpriseInfoAuditedList(){
	grid= $('#enterpriseInfoAuditListTable').jqGrid({ 
		  //caption: "网点信息",
		  datatype:'local',  
		  //altRows: true,
		  colNames:['营业执照号','公司名称','审核时间','已审核条数',''],
		  shrinkToFit:false,
		  height:400,
		  rowNum:10,
	      rowList: [10, 20, 30],
		  colModel:[  
		    {id:'license_no',name:'license_no',width:'100%',align:'center'},
		    {id:'org_name',name:'org_name', width:'100%',align:'center'},  
		    {id:'time',name:'time', width:'100%',align:'center'},
		    {id:'amount',name:'amount', width:'100%',align:'center'},
		    {id:'org_id',name:'org_id',key:true, width:'100%',align:'center',formatter:displayButtons}
		  ],  
		  pager:"#pager",  
		  sortname:'time',
		  sortorder:'desc',//asc or desc
		  viewrecords: true,  
		  height:'100%',  
		  autowidth: true,
		  align:'center',
		  shrinkToFit:true,
		});	
}

function getEnterpriseInfoAuditedList(){
	enterpriseInfoAuditedList();
	cVe.startReqByMsgHandle(cVeName,'','','reqGetEnterpriseInfoAuditedList','resGetEnterpriseInfoAuditedList','ECP.handle.platform.OrgAuditHandle.handleMsg');

}

function reqGetEnterpriseInfoAuditedList(){
	var req = {};	
	req["op"] = "getEnterpriseInfoAuditedList";
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}

function resGetEnterpriseInfoAuditedList(){
	$('#enterpriseInfoAuditListTable').jqGrid("clearGridData");
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	var result=retDataObj[cVe.cEioVeDataId].data;
//	alert(result.length);
	if(result.length<=0){
		$("#prompt_info").css("display","inline-block");//输入为空时
		$("#table").css("display","none");
	}else{
		$("#prompt_info").css("display","none");
		enterpriseInfoAuditedList();
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
	org_id = options.rowId;//这里的rowId是光见值，不是行号
	var audit = '<button class=" btn btn-primary btn-sm"  onclick="search('+org_id+')">查看</button>';
	return audit;
}

function search(org_id){
	window.location.href="enterpriseAuditedDetailPage.jsp?org_id="+org_id;
}

/***********************返回首页*******************/
function goBack(){
	window.location.href="../../content/common/homePage.jsp;";
}

/*************************表格筛选*******************/
function onSearch(){//js函数开始  
    setTimeout(function(){//因为是即时查询，需要用setTimeout进行延迟，让值写入到input内，再读取  
        var storeId = document.getElementById('enterpriseInfoAuditListTable');//获取table的id标识  
        var rowsLength = storeId.rows.length;//表格总共有多少行  
        var enterpriseName=$("#enterpriseName").val();
        var license_no=$("#license_no").val();
        var searchlLicenseNo = 0;//要搜索的哪一列，这里是第一列，从0开始数起  
        var searchDate = 2;
        var searchEnterpriseName = 1;
        var condition=true;
    	for(var i=1;i<rowsLength;i++){//按表的行数进行循环，本例第一行是标题，所以i=1，从第二行开始筛选（从0数起）  
    		condition=true;
            var searchEnterpriseNameText = storeId.rows[i].cells[searchEnterpriseName].innerHTML;//取得table行，列的值 
            var searchDateValue = storeId.rows[i].cells[searchDate].innerHTML;//取得table行，列的值 
            var searchDateText=new Date(searchDateValue.split(" ")[0]);
            var searchlLicenseNoText = storeId.rows[i].cells[searchlLicenseNo].innerHTML;//取得table行，列的值 
          /*  alert(searchDateText);*/
            if(startDate!=""&&endDate!=""){
            	condition=condition&&(searchDateText>=startDate&&searchDateText<=endDate);
            }
            if(enterpriseName!=""){
            	condition=condition&&searchEnterpriseNameText.match(enterpriseName);
            }
            if(license_no!=""){
            	condition=condition&&searchlLicenseNoText.match(license_no);//用match函数进行筛选，如果input的值，即变量 key的值为空，返回的是ture， 
            }
            if(condition){ 
                storeId.rows[i].style.display='';//显示行操作，  
            }else{  
                storeId.rows[i].style.display='none';//隐藏行操作  
            }  
        }
    },200);//200为延时时间  
}  

/*********************清空选项*********************/
function clearOption(){
	$("#daterange-default").val("");
	$("#enterpriseName").val("");
	$("#license_no").val("");
	startDate="";
	endDate="";
	getEnterpriseInfoAuditedList();
}
