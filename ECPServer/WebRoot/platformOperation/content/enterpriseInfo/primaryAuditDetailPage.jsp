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
    <title>初级信息审核</title>
</head>
<body>
    <div id="wrapper">
        <div class="row">
         	<div class="col-md-12">
             	<form id="primaryAuditForm">
             		 <div class="form-group row">
             		 	   <input class="form-control" id="audit_id" name="audit_id" type="hidden" />
             		 	   <input class="form-control" id="org_info_id" name="org_info_id" type="hidden" />
             		 	   <input class="form-control" id="org_state_id" name="org_state_id" type="hidden" />
	                       <input class="form-control" id="audit_status" name="audit_status" type="hidden" value="已审核" />
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
		               
		               <div class="form-group row">
		               		<div class="col-md-1"><label style="font-weight:bold;">审核内容:</label></div>
				            <div class="col-md-10"><textarea class="form-control input-sm" id="operation_reason" rows="5" disabled="disabled"></textarea></div>
				       </div>
				       
		               <div class="form-group row">
				            <div class="col-md-1"><label style="font-weight:bold;">审核理由:</label></div>
				     		<div class="col-md-10"><textarea class="form-control input-sm" name="audit_reason" rows="5" ></textarea></div>
				       </div>
	                             
                       <div class="form-group form-inline ">
                         	<table id="shenhejieguo" style="display: inline-table;">
                           		<tr>
						      		<td><label style="font-weight:bold;" for="shenhejieguo">审核结果:</label></td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td><div class="radio"><input id="pass" type="radio" name="audit_result" value="通过" checked>
		               				<label for="pass">通过</label></div></td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td><div class="radio"><input id="unpass" type="radio" name="audit_result" value="未通过">
		               				<label for="unpass">不通过</label></div></td>
								</tr>
                            </table>
				      </div>
             	</form>
         	</div>
     	 </div>
	     <div class="row">
		     <label style="width:45%"> </label>
	     	 <button type="button" class="btn btn-info btn-sm" onclick="auditPrimaryInfo()">提交</button>
	  		 <button type="button" class="btn btn-danger btn-sm" onclick="goBack()">取消</button>
		 </div>
		 
		 <div class="modal fade" id="previewModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" >
				<div class="modal-content"> 					
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
	<script src="../../assets/js/myjs/enterpriseInfo/primaryAuditDetailPage.js"></script>
</body>
</html>