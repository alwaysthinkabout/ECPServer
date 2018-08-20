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
    <title>初级已审核信息详情</title>
</head>
<body>
    <div id="wrapper">
        <div class="row">
         	<div class="col-md-12">
             	<div class="panel panel-default">
	         		<div class="panel-body" id="advancePanelBody">
	             		 <div class="form-group row">
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">年度：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="year"></label>
			                   </div>
			                   
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">员工数：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="employee_num"></label>
			                   </div>
			                 </div>
		               
			               <div class="form-group row">
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">技术人员数量：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="techstaff_num"></label>
			                   </div>
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">营业额：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="revenue"></label>
			                   </div>
			               </div>
		               
			               <div class="form-group row">
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">资产总额：</label>
			                   </div>
			                   <div class="col-md-4">
			                      <label id="total_assets"></label>
			                   </div>
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">负债总额：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="total_indebt"></label>
			                   </div>
			               </div>
		               
			               <div class="form-group row">
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">净销售额：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="net_sales" ></label>
			                   </div>
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">流动资产：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="current_assets" ></label>
			                   </div>
			               </div>
		               
			               <div class="form-group row">
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">流动负债：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="current_indebt"></label>
			                   </div>
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">营业费用：</label>
			                   </div>
			                   <div class="col-md-4">
			                   	<label id="operate_expense" ></label>
			                   </div>
			               </div>
			               
			               <div class="form-group row">
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">利润：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="profit"></label>
			                   </div>
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">贷款余额：</label>
			                   </div>
			                   <div class="col-md-4">
			                   	<label id="loan_balance" ></label>
			                   </div>
			               </div>
			               
			               <div class="form-group row" id="lastDiv">
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">纳税：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="taxation"></label>
			                   </div>
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">研发经费金额：</label>
			                   </div>
			                   <div class="col-md-4">
			                   	<label id="rd_budget" ></label>
			                   </div>
			               </div>
			               
			               <!-- <div class="form-group">
			               		<a onclick="preview()" ><label>证明材料预览  >></label></a>
					       </div> -->
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
	<script src="../../assets/js/myjs/enterpriseInfo/primaryAuditedDetailPage.js"></script>
</body>
</html>