$(document).ready(function(){
	var yearA= document.getElementById('yearA');
	var year=document.getElementById('year');
	getYearOptions(yearA); 
	getYearOptions(year);  
});
var thisYear=(new Date()).getFullYear();
function getYearOptions(obj){
	var length=obj.options.length;
	if(length==0){
		for(var i=0;i<20;i++){
			var year = document.createElement('option');
			year.value = year.text = thisYear-i;
			obj.options.add(year);
		}
	}
}


















/***********************************
* 简单时间控件： version 1.0
* 作者：Joe
* 时间：2015-08-03
*
* 使用说明：
* 首先把本控件包含到页面
* <script src="XXX/setTime.js" type="text/javascript"></script>
* 控件调用函数：_SetTime(field)
* 例如 <onclick="_SetTime(this)"/>
************************************/
/*
var MyDate = new Date();
var CurrentYear = MyDate.getFullYear();
var StartYear = CurrentYear - 7;
var EndYear = CurrentYear + 7;
var Count = 0;

function _SetTime(obj){
	var str = "";
	document.writeln("<div id = \"_top\" style=\"text-align:center;vertical-align:middle;background-color:#CC0000;padding:6px;border: 2px solid #CC0000; position:absolute; z-index:1;width:247px;height:20px;visibility:hidden\">");
	str += "<img id=\"img_left\" alt=\"\" src=\"../images/left.ico\" width=\"16px\"  onclick=\"Left_getTdValue()\" onmouseover=\"mousemove(this)\"/> &nbsp;";
	str += "<label  id=\"title\" style=\"color:White;font-family: 微软雅黑; font-size: small;\">当前年份: </label>";
	str += "<label  id=\"title2\" style=\"color:White;font-family: 微软雅黑; font-size: small;\" onclick=\"c_year()\" onmouseover=\"mousemove(this)\">" + CurrentYear + "</label> &nbsp;";
	str += "<img id=\"img_right\" alt=\"\" src=\"../images/right.ico\" width=\"16px\"  onclick=\"Right_getTdValue()\" onmouseover=\"mousemove(this)\" />";
	str += "</div>";
	str += "<div id=\"_contents\" style=\"background-color:#E4E4E4;padding:6px;border: 2px solid #CC0000; position:absolute; z-index:1;width:247px;visibility:hidden\">";
	str += "<table id=\"tab\">";
	for (var h = StartYear; h <= EndYear; h++)
	{
	    if (Count == 0) {
	        str += "<tr class=\"TR-main\">";
	    }
	    Count = Count + 1;

	    str += "<td style=\"text-align:center;vertical-align:middle;font-family: 微软雅黑; font-size: small;\" class=\"TD1\" onmouseover=\"mouseover(this)\" onmouseout=\"mouseout(this)\" onclick=\"tr_click(this.id)\"  id=" + h.toString() + ">";
	    str +=h.toString();
	    str +="</td>";

	    if (Count == 3 || h == EndYear)
	    {
	        str +="</tr>";
	        Count = 0;
	    }
	}
	str += "</table>";
	str += "</div>";
	document.writeln(str);
}

var _fieldname;
function onclick_SetYear(tt) {
    _fieldname = tt;
    var ttop = tt.offsetTop; //TT控件的定位点高
    var thei = tt.clientHeight; //TT控件本身的高
    var tleft = tt.offsetLeft; //TT控件的定位点宽
    while (tt = tt.offsetParent)
    {
        ttop += tt.offsetTop;
        tleft += tt.offsetLeft;
    }
 
    document.getElementById("_top").style.top = (ttop + thei + 4) + "px";
    document.getElementById("_top").style.left = tleft + "px";
    document.getElementById("_top").style.visibility = "visible";

    document.getElementById("_contents").style.top = (ttop + thei + 32) + "px";
    document.getElementById("_contents").style.left = tleft + "px";
    document.getElementById("_contents").style.visibility = "visible";
}

function tr_click(value) {
    _fieldname.value = value;
    document.getElementById("_top").style.visibility = "hidden";
    document.getElementById("_contents").style.visibility = "hidden";
}

function c_year() {
    var newid = StartYear;
    var tab = document.getElementById('tab');
    for (var i = 0; i < tab.rows.length; i++) {
        for (var j = 0; j < tab.rows[i].cells.length; j++) {
            tab.rows[i].cells[j].innerHTML = newid;
            tab.rows[i].cells[j].id = newid;
            newid = parseInt(newid) + 1;
        }
    }
}

function Left_getTdValue() {
    var tab = document.getElementById('tab');
    for (var i = 0; i < tab.rows.length; i++) {
        for (var j = 0; j < tab.rows[i].cells.length; j++) {
            var newid = tab.rows[i].cells[j].id;
            newid = parseInt(newid) - 15;
            tab.rows[i].cells[j].innerHTML = newid;
            tab.rows[i].cells[j].id = newid;
        }
    }
}

function Right_getTdValue() {
    var tab = document.getElementById('tab');
    for (var i = 0; i < tab.rows.length; i++) {
        for (var j = 0; j < tab.rows[i].cells.length; j++) {
            var newid = tab.rows[i].cells[j].id;
            newid = parseInt(newid) + 15;
            tab.rows[i].cells[j].innerHTML = newid;
            tab.rows[i].cells[j].id = newid;
        }
    }
}




var curColor;  
var fontColor;  
function mouseover(obj) {  
    curColor = obj.style.backgroundColor;  
    fontColor = obj.style.color;  
    obj.style.backgroundColor = "#CC0000";  
    obj.style.color = "White";  
    obj.style.cursor = 'pointer'; obj.style.cursor = 'hand';  
}  

function mousemove(obj) {  
    obj.style.cursor = 'pointer'; obj.style.cursor = 'hand';  
}  

function mouseout(obj) {  
    obj.style.backgroundColor = curColor;  
    obj.style.color = fontColor;  
}  

function _SetTime2(tt) {  
    onclick_SetYear(tt);  
}  

function addListener(element, e, fn) {  
    if (element.addEventListener) {  
        element.addEventListener(e, fn, false);  
    } else {  
        element.attachEvent("on" + e, fn);  
    }  
}  

addListener(document, "click",  
function (evt) {  
    var evt = window.event ? window.event : evt,  
   target = evt.srcElement || evt.target;  
    if (target.id == "txtYear" || target.id == "img_left" || target.id == "img_right" || target.id == "title") {  
        document.getElementById("_top").style.display = "";  
        document.getElementById("_contents").style.display = "";  
    } else {  
        while (target.nodeName.toLowerCase() != "div" && target.nodeName.toLowerCase() != "html") {  
            target = target.parentNode;  
        }  
        if (target.id == "_top" || target.id == "_contents") {  
            document.getElementById("_top").style.display = "";  
            document.getElementById("_contents").style.display = "";  
        }  
        else {  
            document.getElementById("_top").style.display = "none";  
            document.getElementById("_contents").style.display = "none";  
        }  
    }  
})  */