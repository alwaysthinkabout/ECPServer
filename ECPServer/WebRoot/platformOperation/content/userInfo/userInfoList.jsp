<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="../../assets/css/daterangepicker-bs3.css">
    <!-- <link rel="stylesheet" href="../../assets/css/bootstrap-datetimepicker.css"> -->
    <title>用户信息列表</title>
</head>
<body>
	<fieldset>
		<legend>查询条件</legend>
		<form class="form-horizontal" role="form" id="exactForm">
		<!-- <div class="col-sm-3">
			<div class="input-group input-group-sm" >
				<div class="input-group-addon">创建时间</div>
				<input type="text" id="daterange-default" class="form-control">
				<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
			</div>
		</div> -->
		
		<div class="col-sm-3">
			<div class="input-group input-group-sm">
				<div class="input-group-addon">账号</div>		 
 				    <input  name="uid" id="uid" class="form-control ">
			</div>
		</div>
		
		<div class="col-sm-3">
			<div class="input-group input-group-sm">
				<div class="input-group-addon">昵称</div>		 
 				    <input  name="nick_name" id="nick_name" class="form-control ">
			</div>
		</div>
		
		<div class="col-sm-3">
			<div class="input-group input-group-sm">
				<div class="input-group-addon">用户类型</div>		 
 				    <select  name="user_type" id="user_type" class="form-control selectpicker" data-live-search="true">
	 				<option value="">所有类型</option>	
	 				<option value="机构">机构</option>	
	 				<option value="特殊">特殊</option>	
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
			<table class="table table-hover" id="userInfoListTable" > </table>
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
	<script src="../../assets/js/myjs/userInfo/userInfoList.js"></script>
	<script src="../../assets/js/moment.min.js"></script>
	<script src="../../assets/js/daterangepicker.js"></script>
	
</body>
</html>