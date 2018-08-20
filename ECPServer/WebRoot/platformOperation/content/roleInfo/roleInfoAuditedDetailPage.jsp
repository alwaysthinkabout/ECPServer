<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>角色管理已审核详情</title>
</head>
<body>
    <div id="wrapper">
        <div class="row">
         	<div class="col-md-12">
             	<div class="panel panel-default">
	                 <div class="panel-heading">
	                     <h3><b>角色信息</b></h3>
	                 </div>
	                 <div class="panel-body">
	                     <div class="form-group row">
		                   <div class="col-md-2">
		                       <label style="font-weight:bold;">用户名：</label>
		                   </div>
		                   <div class="col-md-4">
		                       <label id="user_name"></label>
		                   </div>
		                   
		                   <div class="col-md-2">
		                       <label style="font-weight:bold;">身份证号码：</label>
		                   </div>
		                   <div class="col-md-4">
		                       <label id="identity"></label>
		                   </div>
		                 </div>
	               
		               <div class="form-group row">
		                   <div class="col-md-2">
		                       <label style="font-weight:bold;">角色类型：</label>
		                   </div>
		                   <div class="col-md-4">
		                       <label id="role_type"></label>
		                   </div>
		                   <div class="col-md-2">
		                       <label style="font-weight:bold;">角色权限：</label>
		                   </div>
		                   <div class="col-md-4">
		                       <label id="role_limits"></label>
		                   </div>
		               </div>
	               
		               <div class="form-group row">
		                   <div class="col-md-2">
		                        <a onclick="previewIdentityPhoto()"><label style="font-weight:bold;">身份证照片  >></label></a>
		                   </div>
		                   <div class="col-md-4">
		                       <label id="idCard"></label>
		                   </div>
		               </div>
	               </div>
               </div>
               
               <div class="form-group row">
	            	<div class="col-md-2"><label  style="font-weight:bold;">操作人:</label></div>
	            	<div class="col-md-4"><label id="oper_user_name" style=" text-align:left;"></label></div>
	            	<div class="col-md-2"><label style="font-weight:bold;">操作时间:</label></div>
	            	<div class="col-md-4"><label id="oper_time" style=" text-align:left;"></label></div>
			     </div>
		     	 
		     	 <div class="form-group row">
	            	<div class="col-md-2"><label  style="font-weight:bold;">审核人:</label></div>
	            	<div class="col-md-4"><label id="audit_user_name" style=" text-align:left;"></label></div>
	            	<div class="col-md-2"><label style="font-weight:bold;">审核时间:</label></div>
	            	<div class="col-md-4"><label id="audit_time" style=" text-align:left;"></label></div>
			     </div>
			       
			     <div class="form-group row">
	            	<div class="col-md-2"><label style="font-weight:bold;">审核结果:</label></div>
	            	<div class="col-md-4"><label id="audit_result" style="text-align:left;color:red;"></label></div>
			     </div>
			             
		         <div class="form-group row">
		        	<div class="col-md-2"><label style="font-weight:bold;">审核内容:</label></div>
		        	<div class="col-md-4"><label id="content" style="text-align:left;"></label></div>
		        	<div class="col-md-2"><label style="font-weight:bold;">审核理由:</label></div>
		        	<div class="col-md-4"><label id="audit_reason" style=" text-align:left;"></label></div>
		         </div>
		     	 
				 <div class="row">
				      <label style="width:45%"> </label>
				  		 <button type="button" class="btn btn-info btn-sm" onclick="goBack()">返回</button>
				 </div>
         </div>
     </div>
     
	<div class="modal fade" id="IDCardPreviewModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" >
			<div class="modal-content"> 					
				<div class="modal_form_content" id="IDCardPreviewContent">
				</div>
			</div>
		</div>	
	</div>
	
    </div>
    <script src="../../assets/js/ve1.js"></script>
	<script src="../../assets/js/veHelper.js"></script>
    <script src="../../assets/js/grid.locale-cn.js"></script>
    <script src="../../assets/js/jquery.jqGrid.min.js"></script>
    <script src="../../assets/js/simpleUtil.js"></script>
	<script src="../../assets/js/myjs/roleInfo/roleInfoAuditedDetailPage.js"></script>
</body>
</html>