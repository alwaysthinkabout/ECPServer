<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<s:i18n name="decoratorsMessage.top">
<!-- TOP BAR -->
        
<div id="wrapper">
    <div class="row">
	    <div class="row">
		    <nav class="navbar navbar-cls-top " role="navigation" style="margin-bottom: 0;background:white">
		        <div style="width:100%;">
			        <div style="height:130px;width:35%;float:left">
			        	<a href="homePage.html"><img alt="picture" src="assets/img/titlePic2.png" height="130"></a>
		        	</div>
			             <div style="width:60%;height:90px;float:left;"></div> 
	  					 <div style="width:60%;float:left;padding-left:40%"><span style="font-size:20px;">欢迎您：</span><span id="navUser_call"style="display:inline-block;font-size:20px;"></span></div>
  				</div>
		     </nav>
	    </div>
     <div class="row">
	     <nav class="navbar navbar-default navbar-cls-top " role="navigation" style="margin-bottom: 0">
	     	<div class="col-md-12" >
		        <ul id="nav">
		        	<li><a href="homePage.html">首页</a></li>
		        	<li style="padding-left: 65%;"><a id="applyJsp" href="unprocessed.html" onclick="toPage(0)">
		       				<i class="fa fa-newspaper-o  fa-5x" style="margin-top: 6.4px;font-size: 1.4em;" title="求职申请提醒"></i>
		       				<span class="nav-counter"id="applyCounter" style="background:red;display:none"></span>
                		</a>
                	</li>
		        	
		       		<li style="padding-left: 1.8%;"><a id="chatJsp" href="chat.html" onclick="setCss()">
		       				<i class="fa fa-envelope fa-lg" title="消息提醒"></i>
		       				<span class="nav-counter"id="messageCounter" style="display:none;"></span>
                		</a>
                	</li >
                	<li style="padding-left: 3%;"><a href="login.jsp" onclick="logout()">退出</a></li>
		        </ul>
		     </div>
	     </nav>
     <!-- /. NAV TOP  -->
      </div>
    </div>
</div>
</s:i18n>
