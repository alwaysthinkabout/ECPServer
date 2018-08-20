<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>用户信息详情</title>
</head>
<body>
    <div id="wrapper">
        <div class="row">
         	<div class="col-md-12">
          		<!-- <input class="form-control" id="uid" name="uid" type="hidden" /> -->
          		<table width="90%" border="2" align="center"  bgcolor="#000000">
          			<tr height="20"> 
	          			<td rowspan="5" width="18%" id="user_image"></td>
	          			<td width="10%" ><b>账号</b></td>
	          			<td width="30%" ><label id="uid"/></label></td>
	          			<td width="10%" ><b>昵称</b></td>
	          			<td width="30%"><label id="nick_name"/></label></td>
          			</tr>
          			<tr>
	          			<td><b>性别</b></td>
	          			<td><label id="user_sex"></label></td>
	          			<td><b>密码</b></td>
	          			<td><label id="pass"/></label></td>
          			</tr>
          			<tr>
	          			<td><b>用户类型</b></td>
	          			<td><label id="user_type"/></label></td>
	          			<td><b>用户状态</b></td>
	          			<td><label id="user_state"/></label></td>
          			</tr>
          			<tr>
	          			<td><b>邮箱</b></td>
	          			<td><label id="mail"/></label></td>
	          			<td><b>手机</b></td>
	          			<td><label id="user_phone"/></label></td>
          			</tr>
          			<tr >
	          			<td><b>用户介绍</b></td>
	          			<td colspan="3" ><label id="user_type"/></label></td>
          			</tr>
          		</table>
          		
		      <div class="row" style="margin-top:30px">
			      <label style="width:45%"> </label>
			  		 <button type="button" class="btn btn-danger btn-sm" onclick="goBack()">返回</button>
			  </div>
         </div>
     </div>
     
	<div class="modal fade" id="permitPhotoPreviewModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" >
			<div class="modal-content"> 					
				<div class="modal_form_content" id="permitPhotoPreviewContent">
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
	<script src="../../assets/js/myjs/userInfo/userInfoDetailPage.js"></script>
</body>
</html>