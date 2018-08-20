<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>角色管理待审核详情</title>
</head>
<body>
    <div id="wrapper">
        <div class="row">
         	<div class="col-md-12">
             	<form id="roleInfoForm">
                     <input class="form-control" id="audit_id" name="audit_id" type="hidden" />
                     <input class="form-control" id="audit_user_id" name="user_id" type="hidden" />
                     <input class="form-control" id="audit_status" name="audit_status" type="hidden" value="已审核" />
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
               
               <div class="form-group">
		            <label style="font-weight:bold;">审核内容:</label>
		            <textarea class="form-control input-sm" id="operation_reason" rows="5" disabled="disabled"></textarea>
		     		<!-- <label id="operation_reason"></label> -->
		       </div>
		       
               <div class="form-group">
		            <label style="font-weight:bold;">审核理由:</label>
		     		<textarea class="form-control input-sm" name="audit_reason" rows="5" ></textarea>
		       </div>
                            
                     <div class="form-group form-inline ">
                       	<table id="shenhejieguo" style="display: inline-table;">
                         		<tr>
				      		<td><label style="font-weight:bold;" for="shenhejieguo">审核结果:</label></td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td><div class="radio"><input id="pass" type="radio" name="audit_result" value="1" checked>
               				<label for="pass">通过</label></div></td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td><div class="radio"><input id="unpass" type="radio" name="audit_result" value="2">
               				<label for="unpass">不通过</label></div></td>
						</tr>
                          </table>
		      </div>
		      <div class="row">
			      <label style="width:45%"> </label>
			     	 <button type="button" class="btn btn-info btn-sm" onclick="auditRoleInfo()">提交</button>
			  		 <button type="button" class="btn btn-danger btn-sm" onclick="goBack()">取消</button>
			  </div>
             </form>
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
	<script src="../../assets/js/myjs/roleInfo/roleInfoAuditDetailPage.js"></script>
</body>
</html>