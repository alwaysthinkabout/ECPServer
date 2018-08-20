<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>个人中心</title>
</head>
<body>
	<div id="wrapper">
        <div class="row">
         	<div class="col-md-12">
             	<form id="jobHunterInfoForm">
	             	<div class="panel panel-default">
		                 <div class="panel-body ">
		                     <div class="form-group row">
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">用户ID：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="userId"></label>
			                   </div>
			                   
			                   <div class="col-md-2">
			                       <label style="font-weight:bold;">用户名：</label>
			                   </div>
			                   <div class="col-md-4">
			                       <label id="user_name"></label>
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
			               <div class="form-group row" style="padding-top:20px;">
						       <label style="width:40%"> </label>
						  		  <button type="button" class="btn btn-info btn-sm" onclick="mailModelShow()">修改邮箱</button>
						  		  <button type="button" class="btn btn-info btn-sm" onclick="passwordModelShow()" style="margin-left:8px;">修改密码</button>
						   </div>
		               </div>
	               </div>
               </form>
            </div>
            <div class="row">
		        <label style="width:45%"> </label>
		  		   <button type="button" class="btn btn-danger btn-sm" onclick="goBack()">返回</button>
		    </div>
         </div>
         
         <div class="modal fade" id="passwordModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width:45%">
				<div class="modal-content"> 					
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<div>
							<p class="modal-title" ><h3><b>修改密码</b></h3>
						</div>
					</div>
					<div class="modal_form_content" id="previewContent" style="margin-top:5%">
						<div class="form-group row" id="password1">
	                        <div class="col-md-3" style="display:inline-block;width:20%;text-align:right;">
	                            <label>原密码</label>
	                        </div>
	                        <div class="col-md-8" style="display:inline-block;width:60%;padding-right:5px;position:relative">
	                        	<input class="form-control" id="password" name="password" onfocus="this.type='password'" autoComplete="off"/>
	                        	<div id="pwdSpan" style="display:none;">
		                           <div class="topArrow"></div>
		                           <span  class="capslock">大写锁定已打开</span>
	                            </div>
	                        </div>
	                    </div>
	                    <div class="form-group row"id="newPassword1">
	                        <div class="col-md-3" style="display:inline-block;width:20%;text-align:right;">
	                            <label>新密码</label>
	                        </div>
	                        <div class="col-md-8" style="display:inline-block;width:60%;padding-right:5px;position:relative">
	                        	<input class="form-control" id="newPassword" name="newPassword" onfocus="this.type='password'" autoComplete="off"/>
	                        	<div id="newPwdSpan" style="display:none;">
		                           <div class="topArrow"></div>
		                           <span  class="capslock">大写锁定已打开</span>
	                            </div>
	                        </div>
	                    </div>
	                    <div class="form-group row" id="reNewPassword1">
	                        <div class="col-md-3" style="display:inline-block;width:20%;text-align:right;">
	                            <label>密码确认</label>
	                        </div>
	                        <div class="col-md-8" style="display:inline-block;width:60%;padding-right:5px;position:relative">
	                        	<input class="form-control" id="reNewPassword" name="reNewPassword" onfocus="this.type='password'" />
	                        	<div id="newPwdSpan1" style="display:none;">
		                           <div class="topArrow"></div>
		                           <span  class="capslock">大写锁定已打开</span>
	                            </div>
	                        </div>
	                    </div>
	                    
	                    <div class="row" style="padding-bottom:3%;">
					        <label style="width:45%;"> </label>
					  		   <button type="button" class="btn btn-danger btn-sm" onclick="alterPassword()">确定</button>
					    </div>	
					</div>
				</div>
			</div>	
		</div>
		
		<div class="modal fade" id="mailModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width:45%">
				<div class="modal-content"> 					
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<div>
							<p class="modal-title" ><h3><b>修改邮箱</b></h3>
						</div>
					</div>
					<div class="modal_form_content" id="previewContent" style="margin-top:5%">
						<div class="form-group row"id="email1" >
		                        <div id="originalMail1">
			                        <div class="col-md-3" style="display:inline-block;width:20%;text-align:right;">
			                            <label>原邮箱</label>
			                        </div>
			                        <div class="col-md-8 parentCls" style="display:inline-block;width:60%;padding-left:0px;padding-right:5px">
			                        	<input class="form-control inputElem" id="originalEmail" name="email" autocomplete="off"  type="text" />
			                        </div>
			                        <div style="display:inline-block;width:8%;">       
			                        	<button type="button"  class="btn btn-default" onclick="verifyBeforeUpdateFirst()">验证</button>
			                     	</div>
		                        </div>
		                        
		                        <div id="newMail1" style="display:none">
			                        <div class="col-md-3" style="display:inline-block;width:20%;text-align:right;">
			                            <label>新邮箱</label>
			                        </div>
			                        <div class="col-md-8 parentCls" style="display:inline-block;width:60%;padding-left:0px;padding-right:5px">
			                        	<input class="form-control inputElem" id="newEmail" name="newEmail" autocomplete="off" type="text" />
			                        </div>
			                        <div style="display:inline-block;width:8%;">       
				                        <button type="button"  class="btn btn-default" onclick="verifyBeforeUpdateSecond()">验证</button>
				                     </div>
				                 </div>
		                    </div>
		                    <div class="form-group row" id="verify_code"style="display:none;">
		                        <div class="col-md-3" style="display:inline-block;width:20%;text-align:right;">
		                            <label>验证码</label>
		                        </div>
		                        <div class="col-md-8" style="display:inline-block;width:60%;padding-left:0px;padding-right:5px">
		                        	<input class="form-control" style="display:inline-block" id="code" name="code" type="text" />
		                        	<!-- <span class="flag_wrong" id="code_flag">x</span> -->
		                        </div>		                        
		                    </div>
		                    
	                    	 <div class="form-group row" style="text-align:center">
						        <button type="button" id="nextStep_btn"class="btn btn-primary" onclick="nextStep()">下一步</button>
						        <button type="button" style="display:none;" id="alterEmail" class="btn btn-danger btn-sm" onclick="alterEmail()">确定</button>
						        <!-- <button type="button" class="btn btn-info"  onclick="goBack()">返 回</button> -->
				            </div>
	                    <!-- <div class="row" style="padding-bottom:3%;">
					        <label style="width:45%;"> </label>
					  		   <button type="button" class="btn btn-danger btn-sm" onclick="alterPassword()">确定</button>
					    </div>	 -->
					</div>
				</div>
			</div>	
		</div>
         
     </div>
    <script src="../../assets/js/simpleUtil.js"></script>
    <script src="../../assets/js/ve1.js"></script>
	<script src="../../assets/js/veHelper.js"></script>
    <script src="../../assets/js/myjs/common/userSettingPage.js"></script>
</body>
</html>
