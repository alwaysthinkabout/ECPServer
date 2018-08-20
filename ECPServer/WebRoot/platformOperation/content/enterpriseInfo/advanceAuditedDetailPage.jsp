<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>高级已审核信息详情</title>
</head>
<body>
    <div id="wrapper">
        <div class="row">
         	<div class="col-md-12">
         		<div class="panel panel-default">
	         		<div class="panel-body" id="advancePanelBody">
	             		 <div class="form-group row">
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">证件种类：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="cert_type"></label>
			                   </div>
			                   
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">证件名称：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="cert_name"></label>
			                   </div>
			               </div>
		               
			               <div class="form-group row">
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">证件编号：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="cert_id"></label>
			                   </div>
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">认证机构编号：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="auth_org"></label>
			                   </div>
			               </div>
		               
			               <div class="form-group row">
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">发证日期：</label>
			                   </div>
			                   <div class="col-md-4">
			                      <label id="cert_date"></label>
			                   </div>
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">证件有效期：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="cert_expire"></label>
			                   </div>
			               </div>
		               
			               <div class="form-group row" id="lastDiv">
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">证件状态：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="cert_status" ></label>
			                   </div>
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">证件描述：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="cert_desc" ></label>
			                   </div>
			               </div>
			               
			               <!-- <div class="form-group">
			               		<a onclick="preview()"><label style="font-weight:bold;">证明材料预览  >></label></a>
					       </div> -->
				   </div>
             	</div>
         	</div>
     	 </div>
	     <div class="form-group row">
     	 	 <div class="col-md-6" >
            	<div class="col-md-3"><label  style="font-weight:bold;">操作人:</label></div>
            	<div class="col-md-9"><label id="oper_user_name" style=" text-align:left;"></label></div>
             </div>
             <div class="col-md-6" >
            	<div class="col-md-3"><label style="font-weight:bold;">操作时间:</label></div>
            	<div class="col-md-9"><label id="oper_time" style=" text-align:left;"></label></div>
             </div>
	     </div>
     	 
     	 <div class="form-group row">
             <div class="col-md-6" >
            	<div class="col-md-3"><label  style="font-weight:bold;">审核人:</label></div>
            	<div class="col-md-9"><label id="audit_user_name" style=" text-align:left;"></label></div>
             </div>
             <div class="col-md-6" >
            	<div class="col-md-3"><label style="font-weight:bold;">审核时间:</label></div>
            	<div class="col-md-9"><label id="audit_time" style=" text-align:left;"></label></div>
            </div>
	     </div>
	       
	     <div class="form-group row">
            <div class="col-md-6" >
            	<div class="col-md-3"><label style="font-weight:bold;">审核结果:</label></div>
            	<div class="col-md-9"><label id="audit_result" style="text-align:left;color:red;"></label></div>
            </div>
	     </div>
	             
         <div class="form-group row">
        	<div class="col-md-6" >
	        	<div class="col-md-3"><label style="font-weight:bold;">审核内容:</label></div>
	        	<div class="col-md-9"><label id="content" style="text-align:left;"></label></div>
        	</div>
         	<div class="col-md-6" >
	        	<div class="col-md-3"><label style="font-weight:bold;">审核理由:</label></div>
	        	<div class="col-md-9"><label id="audit_reason" style=" text-align:left;"></label></div>
       		</div>
       </div>
     	 
	  <div class="row">
	      <label style="width:45%"> </label>
	  		 <button type="button" class="btn btn-info btn-sm" onclick="goBack()">返回</button>
	  </div>
		 
		<div class="modal fade" id="previewModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" >
				<div class="modal-content"> 					
					<!-- <div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<div>
							<p class="modal-title" ><h3><b>高级证明材料预览</b></h3>
						</div>
					</div> -->
					<div class="modal_form_content" id="previewContent">
						<!-- <img id="previewImg" src="http://222.201.145.241:8888/ECPServer/download?type=OrgFile&name=20170724214813_斗地主冠军.png"> -->
					</div>
				</div>
			</div>	
		</div>
		
		<div class="modal fade" id="filePreviewModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" >
				<div class="modal-content"> 					
					<!-- <div class="modal_form_content" id="filePreviewContent"> -->
					<div style="position:absolute;left:50px;top:10px;">  
            			<a id="viewerPlaceHolder" style="width:1000px;height:1000px;display:block"></a>  
            		</div>
				</div>
			</div>	
		</div>
		
		 
    </div>
    <!-- /. WRAPPER  -->
    <!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
    <!-- JQUERY SCRIPTS -->
    <script src="../../assets/js/jquery-1.11.1.min.js"></script>
    <script src="../../assets/js/bootstrap.js"></script>
    <script src="../../assets/js/simpleUtil.js"></script>
    <script src="../../assets/js/ve1.js"></script>
	<script src="../../assets/js/veHelper.js"></script>
    <script src="../../assets/js/grid.locale-cn.js"></script>
    <!-- <script src="../../assets/js/jquery.jqGrid.min.js"></script> -->
	<script src="../../assets/js/myjs/enterpriseInfo/advanceAuditedDetailPage.js"></script>
</body>
</html>