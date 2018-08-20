<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ page import="java.io.*"%>  
<%@ page import="java.util.Enumeration"%>  
<%@ page import="com.oreilly.servlet.MultipartRequest"%>  
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>  
<%@ page import="ECP.servlet.fileView.DocConverter"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
	<script type="text/javascript" src="../../assets/js/myjs/fileView/jquery.js"></script>  
	<script type="text/javascript" src="../../assets/js/myjs/fileView/flexpaper_flash.js"></script>  
	<script type="text/javascript" src="../../assets/js/myjs/fileView/flexpaper_flash_debug.js"></script> 
    <title>注册级已审核信息详情</title>
</head>
<body>
    <div id="wrapper">
        <div class="row">
         	<div class="col-md-12">
	         	<div class="panel panel-default">
	             	<div class="panel-body" id="registerPanelBody">
		                 	<div id="enterpriseRegisterDIV">
			                     <div class="form-group row">
				                   <div class="col-md-2">
				                       <label style="font-weight:bold;">营业执照号：</label>
				                   </div>
				                   <div class="col-md-4">
				                       <label id="license_no"></label>
				                   </div>
				                   <div class="col-md-2">
				                       <label style="font-weight:bold;">单位名称：</label>
				                   </div>
				                   <div class="col-md-4">
				                       <label id="org_name"></label>
				                   </div>
				                 </div>
			               
				               <div class="form-group row">
				                   <div class="col-md-2">
				                       <label style="font-weight:bold;">公司性质：</label>
				                   </div>
				                   <div class="col-md-4">
				                       <label id="org_type"></label>
				                   </div>
				                   <div class="col-md-2">
				                       <label style="font-weight:bold;">住所：</label>
				                   </div>
				                   <div class="col-md-4">
				                       <label id="reg_address"></label>
				                   </div>
				               </div>
			               
				               <div class="form-group row">
				                   <div class="col-md-2">
				                       <label style="font-weight:bold;">法定代表人：</label>
				                   </div>
				                   <div class="col-md-4">
				                      <label id="legal_rep"></label>
				                   </div>
				                   <div class="col-md-2">
				                       <label style="font-weight:bold;">注册资本：</label>
				                   </div>
				                   <div class="col-md-4">
				                       <label id="reg_capital"></label>
				                   </div>
				               </div>
			               
				               <div class="form-group row">
				                   <div class="col-md-2">
				                       <label style="font-weight:bold;">注册时间：</label>
				                   </div>
				                   <div class="col-md-4">
				                       <label id="reg_time" ></label>
				                   </div>
				                   <div class="col-md-2">
				                       <label style="font-weight:bold;">经营范围：</label>
				                   </div>
				                   <div class="col-md-4">
				                       <label id="business_scope" ></label>
				                   </div>
				               </div>
			               
				               <div class="form-group row">
				                   <div class="col-md-2">
				                       <label style="font-weight:bold;">公司在职人数：</label>
				                   </div>
				                   <div class="col-md-4">
				                       <label id="staffcount"></label>
				                   </div>
				                   <div class="col-md-2">
				                       <label style="font-weight:bold;">登记机关：</label>
				                   </div>
				                   <div class="col-md-4">
				                   	<label id="reg_auth" ></label>
				                   </div>
				               </div>
			               </div>
			               
			               <div id="departmentRegisterDIV">
			                     <div class="form-group row">
				                   <div class="col-md-2">
				                       <label style="font-weight:bold;">营业执照号：</label>
				                   </div>
				                   <div class="col-md-4">
				                       <label id="license_noD"></label>
				                   </div>
				                   
				                   <div class="col-md-2">
				                       <label style="font-weight:bold;">单位名称：</label>
				                   </div>
				                   <div class="col-md-4">
				                       <label id="org_nameD"></label>
				                   </div>
				                 </div>
			               
				               <div class="form-group row">
				                   <div class="col-md-2">
				                       <label style="font-weight:bold;">宗旨：</label>
				                   </div>
				                   <div class="col-md-4">
				                       <label id="aimD"></label>
				                   </div>
				                   <div class="col-md-2">
				                       <label style="font-weight:bold;">业务范围：</label>
				                   </div>
				                   <div class="col-md-4">
				                       <label id="coreBusinessD"></label>
				                   </div>
				               </div>
			               
				               <div class="form-group row">
				                   <div class="col-md-2">
				                       <label style="font-weight:bold;">住所：</label>
				                   </div>
				                   <div class="col-md-4">
				                      <label id="registerRegionD"></label>
				                   </div>
				                   <div class="col-md-2">
				                       <label style="font-weight:bold;">法定代表人：</label>
				                   </div>
				                   <div class="col-md-4">
				                       <label id="registerPersonD"></label>
				                   </div>
				               </div>
			               
				               <div class="form-group row">
				                   <div class="col-md-2">
				                       <label style="font-weight:bold;">经费来源：</label>
				                   </div>
				                   <div class="col-md-4">
				                       <label id="fundSrcD" ></label>
				                   </div>
				                   <div class="col-md-2">
				                       <label style="font-weight:bold;">开办资金：</label>
				                   </div>
				                   <div class="col-md-4">
				                       <label id="registerFundD" ></label>
				                   </div>
				               </div>
			               
				               <div class="form-group row">
				                   <div class="col-md-2">
				                       <label style="font-weight:bold;">举办单位：</label>
				                   </div>
				                   <div class="col-md-4">
				                       <label id="holderD"></label>
				                   </div>
				                   <div class="col-md-2">
				                       <label style="font-weight:bold;">登记机关：</label>
				                   </div>
				                   <div class="col-md-4">
				                   	<label id="regAuthD" ></label>
				                   </div>
				               </div>
			               </div>
			               
			               <div class="form-group row">
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">招聘联系人：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="contact"></label>
			                   </div>
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">电话：</label>
			                   </div>
			                   <div class="col-md-4">
			                   	<label id="contact_phone" ></label>
		                  	   </div>
			          	    </div>
				               
			               <div class="form-group row" id="lastDiv">
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">Email：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="contact_email"></label>
			                   </div>
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">公司官网：</label>
			                   </div>
			                   <div class="col-md-4">
			                   	<a id="website" target="_blank"></a>
			                   </div>
			               </div>
			               
			               <!-- <div class="form-group">
			               		<a onclick="preview()"><label>证明材料预览  >></label></a>
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
							<p class="modal-title" ><h3><b>初级证明材料预览</b></h3>
						</div>
					</div> -->
					<div class="modal_form_content" id="previewContent">
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
    <script src="../../assets/js/jquery.jqGrid.min.js"></script>
	<script src="../../assets/js/myjs/enterpriseInfo/registerAuditedDetailPage.js"></script>
</body>
</html>