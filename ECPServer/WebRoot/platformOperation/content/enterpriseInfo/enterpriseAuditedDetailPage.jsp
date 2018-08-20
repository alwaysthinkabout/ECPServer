<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>企业已审核信息详情</title>
</head>
<body>
    <div id="wrapper">
        <div class="row">
         	<div class="col-md-12">
             	<div class="panel panel-default">
             		<!-- 注册级已审核信息列表 --> 
	                 <div class="panel-heading">
	                     <h3><b>注册级信息</b></h3>
	                 </div>
	                 
	                 <div class="panel-body">
	                 	 <div id="noRegisterDIV">
							<label style="color:red;"><h4>没有已审核的注册级信息！</h4></label>
				         </div>
	                 	 <div id="registerTableDIV">
							<table class="table table-hover" id="registerInfoAuditListTable" > </table>
				                <div id= "pager_register"></div>
				         </div>
                 	 </div>

                 
	                <!-- 初级已审核信息列表 --> 
	                 <div class="panel-heading">
	                      <h3><b>初级信息</b></h3>
	                 </div>
	                 <div class="panel-body">
	                 	 <div id="noPrimaryDIV">
							<label style="color:red;"><h4>没有已审核的初级信息！</h4></label>
				         </div>
	                 	 <div id="primaryTableDIV">
							<table class="table table-hover" id="primaryInfoAuditListTable" > </table>
				                <div id= "pager_primary"></div>
				         </div>
	                 </div>
	                 
	                 <!-- 高级已审核信息列表 --> 
	                 <div class="panel-heading">
	                      <h3><b>高级信息</b></h3>
	                 </div>
	                 <div class="panel-body">
	                 	<div id="noAdvanceDIV">
							<label style="color:red;"><h4>没有已审核的高级信息！</h4></label>
				         </div>
	              		<div id="advanceTableDIV">
							<table class="table table-hover" id="advanceInfoAuditListTable" ></table>
				                <div id= "pager_advance"></div>
				         </div>
	                 </div>
	             </div>

	             <div class="row">
				     <label style="width:45%"> </label>
			  		 <button type="button" class="btn btn-danger btn-sm" onclick="goBack()">返回</button>
				 </div>
        	 </div>
     	</div>
    </div>
    <script src="../../assets/js/ve1.js"></script>
	<script src="../../assets/js/veHelper.js"></script>
    <script src="../../assets/js/grid.locale-cn.js"></script>
    <script src="../../assets/js/jquery.jqGrid.min.js"></script>
    <script src="../../assets/js/simpleUtil.js"></script>
	<script src="../../assets/js/myjs/enterpriseInfo/enterpriseAuditedDetailPage.js"></script>
</body>
</html>