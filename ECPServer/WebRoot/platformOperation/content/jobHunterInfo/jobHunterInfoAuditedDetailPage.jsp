<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>求职者信息已审核详情</title>
</head>
<body>
    <div id="wrapper">
        <div class="row">
         	<div class="col-md-12">
             	<form id="jobHunterInfoForm">
            		 <input class="form-control" id="audit_user_id" name="user_id" type="hidden" />
                     <input class="form-control" id="audit_id" name="audit_id" type="hidden" />
                     <input class="form-control" id="audit_status" name="audit_status" type="hidden" value="已审核" />
	             	<div class="panel panel-default">
		                <!--  <div class="panel-heading">
		                     <h3><b>求职者信息</b></h3>
		                 </div> -->
		                 <div class="panel-body ">
		                     <div class="form-group row">
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">真实姓名：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="name"></label>
			                   </div>
			                   
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">性别：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="sex"></label>
			                   </div>
			                 </div>
		               
			               <div class="form-group row">
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">出身日期：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="birth"></label>
			                   </div>
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">身份证号码：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="id_cardno"></label>
			                   </div>
			               </div>
		               
			               <div class="form-group row">
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">手机号码：</label>
			                   </div>
			                   <div class="col-md-4">
			                      <label id="phone"></label>
			                   </div>
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">常用邮箱：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="email"></label>
			                   </div>
			               </div>
		               
			               <div class="form-group row">
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">最高学历：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="degree" ></label>
			                   </div>
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">最高职称：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="prof_title" ></label>
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
			     		<!-- <label id="operation_reason"></label> -->
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
			     		<!-- <label id="operation_reason"></label> -->
			       </div>
			       
	               <!-- <div class="form-group">
			            <label class="control-label" style=" text-align:left;">审核理由:</label>
			            <div class="col-md-9"><label id="audit_reason" style=" text-align:left;"></label></div>
			       </div> -->
                
			      <div class="row">
				      <label style="width:45%"> </label>
				  		 <button type="button" class="btn btn-info btn-sm" onclick="goBack()">返回</button>
				  </div>
             
             </form>
         </div>
     </div>
     
     <div class="modal fade" id="previewModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" >
				<div class="modal-content"> 					
					<!-- <div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<div>
							<p class="modal-title" ><h3><b>注册级证明材料预览</b></h3>
						</div>
					</div> -->
					<div class="modal_form_content" id="previewContent">
						<!-- <img id="previewImg" src="http://222.201.145.241:8888/ECPServer/download?type=OrgFile&name=20170724214813_斗地主冠军.png"> -->
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
	<script src="../../assets/js/myjs/jobHunterInfo/jobHunterInfoAuditedDetailPage.js"></script>
</body>
</html>