<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>企业待审核信息详情</title>
</head>
<body>
    <div id="wrapper">
        <div class="row">
         	<div class="col-md-12">
             	<div class="panel panel-default">
	                 <div class="panel-heading">
	                     <h3><b>注册级信息</b></h3>
	                 </div>
	                 <div class="panel-body" id="noRegisterPanelBody">
	                 	<div class="form-group row">
		                   <div class="col-md-8">
		                       <label style="color:red;"><h4>没有待审核的注册级信息！</h4></label>
		                   </div>
	                    </div>
	                 </div>
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
		               
		               
				      <div class="row">
					      <label style="width:45%"> </label>
					     	 <button type="button" class="btn btn-primary btn-sm" onclick="openRegisterAuditModel()">审核</button>
					  </div>
                 </div>
                 
                <!-- 初级待审核信息列表 --> 
                 <div class="panel-heading">
                      <h3><b>初级信息</b></h3>
                 </div>
                 <div class="panel-body">
                 	 <div id="noPrimaryDIV">
						<label style="color:red;"><h4>没有待审核的初级信息！</h4></label>
			         </div>
                 	 <div id="primaryTableDIV">
						<table class="table table-hover" id="primaryInfoAuditListTable" > </table>
			                <div id= "pager_primary"></div>
			         </div>
                 </div>
                 
                 <div class="panel-heading">
                      <h3><b>高级信息</b></h3>
                 </div>
                 <div class="panel-body">
                 	<div id="noAdvanceDIV">
						<label style="color:red;"><h4>没有待审核的高级信息！</h4></label>
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
     
     <div class="modal fade" id="previewModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog">
				<div class="modal-content"> 					
					<div class="modal_form_content" id="previewContent">
						<!-- <img id="previewImg" src="http://222.201.145.241:8888/ECPServer/download?type=OrgFile&name=20170724214813_斗地主冠军.png"> -->
					</div>
				</div>
			</div>	
	</div>
	
	<div class="modal fade" id="registerAuditModel" style="width:1200px;padding-left:20%;" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" >
				<div class="modal-content"> 					
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<div>
							<p class="modal-title" ><h3><b>注册级信息审核</b></h3>
						</div>
					</div>
					<div class="modal_form_content" id="registerAuditContent">
						<form id="registerInfoForm">
							<input class="form-control" id="org_id" name="org_id" type="hidden" />
		                    <input class="form-control" id="audit_id" name="audit_id" type="hidden" />
		                    <input class="form-control" id="org_info_id" name="org_info_id" type="hidden" />
		                    <input class="form-control" id="audit_status" name="audit_status" type="hidden" value="已审核" />
							<div class="col-sm-11" style="padding-top:10px;padding-right:20px;">
								<div class="form-group" >
						            <label class="control-label" style=" text-align:left;">审核内容:</label>
						            <textarea class="form-control col-md-6" id="operation_reason" rows="5" disabled="disabled"></textarea>
						       </div>
						       
				               <div class="form-group">
						            <label class="control-label" style=" text-align:left;">审核理由:</label>
						     		<textarea class="form-control input-sm" name="audit_reason" rows="5" ></textarea>
						       </div>
			                             
		                       <div class="form-group form-inline ">
		                         	<table id="shenhejieguo" style="display: inline-table;">
		                           		<tr>
								      		<td><label class="control-label" for="shenhejieguo">审核结果:</label></td>
											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
											<td><div class="radio"><input id="pass" type="radio" name="audit_result" value="通过" checked>
				               				<label for="pass">通过</label></div></td>
											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
											<td><div class="radio"><input id="unpass" type="radio" name="audit_result" value="不通过">
				               				<label for="unpass">不通过</label></div></td>
										</tr>
		                            </table>
						      </div>
							</div>
						    <div class="row" style="padding-bottom:10px;">
							      <label style="width:45%"> </label>
						  		  <button type="button" class="btn btn-info btn-sm" onclick="auditRegisterInfo()">提交</button>
							</div>
						</form>
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
	<script src="../../assets/js/myjs/enterpriseInfo/enterpriseAuditDetailPage.js"></script>
</body>
</html>