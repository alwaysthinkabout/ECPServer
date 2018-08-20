<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="../../assets/css/daterangepicker-bs3.css">
    <title>求职者已审核信息列表</title>
</head>
<body>
	<fieldset>
		<legend>查询条件</legend>
		<form class="form-horizontal" role="form" id="exactForm">
		<div class="col-sm-3">
			<div class="input-group input-group-sm" >
				<div class="input-group-addon">审核时间</div>
				<input type="text" id="daterange-default" class="form-control">
				<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
			</div>
		</div>
		
		<div class="col-sm-3">
			<div class="input-group input-group-sm">
				<div class="input-group-addon">求职者姓名</div>		 
 				    <input  name="name" id="name" class="form-control ">
			</div>
		</div>
		
		<div class="col-sm-3">
			<div class="input-group input-group-sm">
				<div class="input-group-addon">审核结果</div>		 
 				    <select  name="audit_result" id="audit_result" class="form-control selectpicker" data-live-search="true">
	 				<option value="">所有结果</option>	
	 				<option value="通过">通过</option>	
	 				<option value="不通过">不通过</option>	
				</select>   	
			</div>
		</div>
	</form>				
	<div class="col-sm-2 pull-right">
		<button class="btn btn-primary btn-sm" onclick="onSearch()">查询</button>
		<button class="btn btn-danger btn-sm" onclick="clearOption()">清除</button>
	</div>
	</fieldset>
	
   <div id="jqgrid-wrapper">
   		<div id="table">
			<table class="table table-hover" id="jobHunterInfoAuditListTable" > </table>
                <div id= "pager"></div>
         </div>
   </div>
   <div class="row" style="padding-top:20px;">
	   <label style="width:45%"> </label>
	   <button type="button" class="btn btn-danger btn-sm" onclick="goBack()">返回</button>
   </div>
    
	<script src="../../assets/js/ve1.js"></script>
	<script src="../../assets/js/veHelper.js"></script>
    <script src="../../assets/js/grid.locale-cn.js"></script>
    <script src="../../assets/js/jquery.jqGrid.min.js"></script>
    <script src="../../assets/js/simpleUtil.js"></script>
	<script src="../../assets/js/myjs/jobHunterInfo/jobHunterInfoAuditedList.js"></script>
	<script src="../../assets/js/moment.min.js"></script>
	<script src="../../assets/js/daterangepicker.js"></script>
	
</body>
</html>