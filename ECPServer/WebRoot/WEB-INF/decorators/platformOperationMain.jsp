<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <!--使部分国产浏览器默认采用高速模式渲染页面-->
        <meta name="renderer" content="webkit">
        <title>平台运营管理系统</title>
        <meta name="viewport" content="width=device-width">

        <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
		<link rel="shortcut icon" href="../../img/favicon.ico">

		<!-- CSS -->
        <link rel="stylesheet" href="../../assets/css/bootstrap.cosmo.css">
        <link rel="stylesheet" href="../../assets/css/font-awesome.css">
        <link rel="stylesheet" href="../../assets/css/main.css">
        <link rel="stylesheet" href="../../assets/css/darkblue.css">
        <link rel="stylesheet" href="../../assets/css/style.css">
        <script src="../../assets/js/vendor/modernizr-2.6.2.min.js"></script>
        <script src="../../assets/js/jquery-1.11.1.min.js"></script>
        <script src="../../assets/js/bootstrap.js"></script>
		<!--[if (lt IE 9) & (!IEMobile)]>
		<script src="js/vendor/html5shiv.min.js"></script>
		<script src="js/vendor/respond.min.js"></script>
		<![endif]-->
		<decorator:head/>
    </head>
    <body>
        <!--[if lte IE 7]>
            <s:text name="browserOutDate" />
        <![endif]-->

		
		
			<!-- WRAPPER -->
	        <div class="wrapper">
	            <jsp:include page="platformOperationTop.jsp" flush="true"></jsp:include>
	            <!-- BOTTOM: LEFT NAV AND RIGHT MAIN CONTENT -->
	            <div class="bottom">
	                <div class="container">
	                    <div class="row">
	                        <!-- left sidebar -->
	                        <div class="col-md-2 left-sidebar" id="leftMenu"  style="visibility: none;">
	                            <jsp:include page="platformOperationLeft.jsp" flush="true"></jsp:include>
	                        </div>
	                        <!-- end left sidebar -->

	                        <!-- content-wrapper -->
	                        <div class="col-md-10 content-wrapper">
	                            <div class="row">
	                            	<h1></h1>
									<%-- <jsp:include page="breadCrumb.jsp" flush="true"></jsp:include> --%>
								</div>
								<!-- main -->
								<div class="content">
									<div class="main-header">
										<h2 style="border:0;"><decorator:title/></h2>
									</div>
									<div class="main-content">
										<decorator:body/>
									</div>
									<!-- /main-content -->
								</div>
								<!-- /main -->
	                        </div><!-- /content-wrapper -->
	                    </div><!-- /row -->
	                </div><!-- /container -->
	            </div>
	            <!-- END BOTTOM: LEFT NAV AND RIGHT MAIN CONTENT -->
	        </div><!-- WRAPPER -->
	        
	<!-- <script src="../../assets/js/king-common.js"></script> -->
    </body>
</html>


