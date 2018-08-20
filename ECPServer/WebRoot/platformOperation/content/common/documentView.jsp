<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%-- <%  
    String swfFilePath=session.getAttribute("swfpath").toString();  
%>   --%>
<!DOCTYPE html>  
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<script type="text/javascript" src="../../assets/js/myjs/fileView/jquery.js"></script>  
<script type="text/javascript" src="../../assets/js/myjs/fileView/flexpaper_flash.js"></script>  
<script type="text/javascript" src="../../assets/js/myjs/fileView/flexpaper_flash_debug.js"></script>  
<style type="text/css" media="screen">   
            html, body  { height:100%; }  
            body { margin:0; padding:0; overflow:auto; }     
            #flashContent { display:none; }  
        </style>   
  
<title>文档在线预览系统</title>  
</head>  
<body>   
        <div id="fileView" style="position:absolute;left:50px;top:10px;">  
            <a id="viewerPlaceHolder" style="width:1000px;height:1000px; display:block" target="_blank"></a>  
              
            <script type="text/javascript">  
            	var swfFilePath=cVeUti.Cookie.getCookie("swfpath");
                var fp = new FlexPaperViewer(     
                         '../../../FlexPaperViewer',  
                         'viewerPlaceHolder', { config : {  
                         SwfFile : decodeURI('../../../../'+swfFilePath),  
                         Scale : 1,   
                         ZoomTransition : 'easeOut',  
                         ZoomTime : 1,  
                         ZoomInterval : 0.2,  
                         FitPageOnLoad : true,  
                         FitWidthOnLoad : false,  
                         FullScreenAsMaxWindow : false,  
                         ProgressiveLoading : false,  
                         MinZoomSize : 0.2,  
                         MaxZoomSize : 5,  
                         SearchMatchAll : false,  
                         InitViewMode : 'SinglePage',  
                           
                         ViewModeToolsVisible : true,  
                         ZoomToolsVisible : true,  
                         NavToolsVisible : true,  
                         CursorToolsVisible : true,  
                         SearchToolsVisible : false,  
                          
                         localeChain: 'zh_CN',  
                         }});  
            </script>              
        </div>  
</body>  
</html>  
