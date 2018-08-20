function certInfoList(){
	
	grid= $('#dataGrid').jqGrid({ 
		  //caption: "网点信息",
		  datatype:'local',  
		  altRows: true,
		  colNames:['序号','证件名称','证件状态','发证日期','有效期','操作','证件编号',],
		  colModel:[  
		    
		   {id:'number',name:'number', width:'100%',align:'center'},  
		    {id:'cert_name',name:'cert_name',width:'100%',align:'center'},
		    {id:'cert_status',name:'cert_status', width:'100%',align:'center'},  
		    {id:'cert_date',name:'cert_date', width:'100%',align:'center'},
		    {id:'cert_expire',name:'cert_expire', width:'100%',align:'center'},
		   /* {id:'apply_status_code',name:'apply_status_code', width:'100%',align:'center',hidden:'true'},*/
		    {id:'operate',name:'operate', width:'100%',align:'center',formatter:displayButtons},

		    {id:'cert_id',name:'cert_id', key:true, width:'100%',align:'center',hidden:'true'}
		  ],  
		  pager:"#pager",  
		 // rowNum: pageSize,  
		  sortname:'0',  
		  viewrecords: true,  
		  height:'100%',  
		  autowidth: true,
		  align:'center',
		 //multiselect: true,
		  shrinkToFit:true,		
		});	
}

function displayButtons(cellValue,options,rowObject){
	cert_id = options.rowId;//这里的rowId是关键值，不是行号    
	var edit = '<button class=" btn btn-primary my-jqgrid-button"  onclick="deleteCert('+cert_id+')">删除</button>';
	return edit;
}

