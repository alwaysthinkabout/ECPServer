//本文件对应的页面组所需的全局JS变量
var cVe= new EIO.ve();
var cVei = new EIO.vei();
var cVeUti = new EIO.veUti();

var cVeName="VeDemo1";//定义本应用的Ve引擎名称，用户自定义，用作服务按的消息处理调度
var cServerUri="EIOServletMsgEngine" //指定servlet的引用名，该名为web.xml中指定的<url-pattern>内容


var cSelData =['中国','俄罗斯','日本','韩国','加拿大'] ;

var cMsgConfigure;
/*var cMsgConfigure;

     ={ MsgSendFormData:{
                         MsgCheckFormData:{ReqFunc:"reqCheckFormData",ResFunc:"resCheckFormData",MsgHandle:"csAsc.ECOSS.reso.demo1.CMsgCheckFormData.handleMsg"},
                           	                 MsgProcessFormData:{ReqFunc:"sendFormdata", ResFun:"refreshForm",MsgHandle:""}
                       },
        MsgVcInject: {
                       MsgVcInjectSelect:{ReqFunc:"reqInjectSelect",ResFunc:"resInjectSelect",MsgHandle:"csAsc.ECOSS.reso.demo1.CMsgVcInject.handleMsg"},
                       MsgVcInjectInput:{ReqFunc:"sendVcIds",ResFunc:"resInjectVcSelect",MsgHandle:""},
                       MsgInjectTree:{ReqFunc:"reqInjectTree",ResFunc:"resInjectTree",MsgHandle:"csAsc.ECOSS.reso.demo1.CMsgGetTree.handleMsg"}
      	               
                     },               
        MsgGetNewPage:{
        	           MsgOpenNewWindow:{ReqFunc:"reqOpenNewWindow",ResFunc:"resOpenNewWindow",MsgHandle:"csAsc.ECOSS.reso.demo1.CMsgSendNewPage.handleMsg"},
        	           MsgSwitchPage:{ReqFunc:"reqGetNewPageData",ResFunc:"resGetNewPageData",MsgHandle:"csAsc.ECOSS.reso.demo1.CMsgQueryTableByKeys.handleMsg"},
        	           MsgOpenNewPageFile:{ReqFunc:"reqOpenNewPageFile",ResFunc:"resOpenNewPageFile",MsgHandle:"csAsc.ECOSS.reso.demo1.COpenHtmlFile.handleMsg"},
                      },
        MsgQueryDb:{
          	           MsgGetDbRecs:{ReqFunc:"reqGetDbRecs",ResFunc:"resGetDbRecs",MsgHandle:"csAsc.ECOSS.reso.demo1.CMsgGetDbRecs.handleMsg"},
          	           MsgGetDbRecsToVeGrid:{ReqFunc:"reqGetDbRecsToVegrid",ResFunc:"resGetDbRecsToVegrid",MsgHandle:"csAsc.ECOSS.reso.demo1.CMsgGetDbRecs.handleMsg"} 
        	       },
        MsgInjectBuffer:{
          	             MsgInjectBuff1:{ReqFunc:"reqInjectBuffer",ResFunc:"resInjectBuffer",MsgHandle:"csAsc.ECOSS.reso.demo1.CMsgGetBufferData.handleMsg"},
          	             MsgInjectBuff2:{ReqFunc:"reqInjectBuffer",ResFunc:"resInjectBuffer",MsgHandle:""} 
        	           }       	       
      };
*/
////////////////具体的请求消息函数与回复处理函数，在VE消息表中指定============

//消息格式
//请求消息：ve名称标识符=ve名称 & 消息类型标识符=消息类型值 & 消息名称标识符=消息名称值 & 消息处理器标识符=消息处理器值 & eio消息数据标识符=消息数据的JSON串

///////////////////系统初始化消息//////////////////
function reqSysInit()
{//系统初始化消息，被ve自动调用
	//请求消息数据格式：{"MsgInitName1" : Init初始化名称1, "MsgInitName2" : Init初始化名称21, ...}
	  //回送消息数据格式：{Init初始化名称1 : 回送数据1, Init初始化名称2 : 回送数据2, ...}	
  //alert("从消息人口 进入 消息处理函数reqSysInit();	
 var sendData = "{\"MsgInitName1\": \"GetMsgMap\"}";
 //(serverUri,method,isAsync,sendData)
 cVe.setConn(cServerUri,"POST", true, sendData);
}


function resSysInit()
{//一个具体的回复处理函数， 在msgConfigure中描述，被ve自动调用

 var retData =cVe.XHR.responseText;
 //alert("从 消息响应人口 进入 消息回应消息处理函数reqSysInit()， 获得服务器的响应数据为：" +cVe.cEioVeDataId+"=="+retData); 
 var retDataObj=eval("("+retData+")");	
 
 cMsgConfigure = retDataObj[cVe.cEioVeDataId].GetMsgMap;
 cVeUti.Cookie.setCookie("VeMsgMap",retData);//将消息映射保存到Cookies
 
 //alert(cVeUti.Cookie.getCookie('VeMsgMap'));
 //这里调用其他初始化程序
 cVe.startReqByMsgMap(cVeName, 'MsgVcInject','MsgVcInjectSelect',cMsgConfigure);
}

///////////////////reqCheckFormData()//////////////////
function reqCheckFormData()
{//一个具体的请求消息函数， 在msgConfigure中描述，被ve自动调用
  //alert("从消息人口gotoMsgHandle 进入 消息处理函数sendFormData");	
 var sendData =getFormData();//调用用户自己的数据提取函数
 //(serverUri,method,isAsync,sendData)
 cVe.setConn(cServerUri,"POST", true, sendData);
}


function resCheckFormData()
{//一个具体的回复处理函数， 在msgConfigure中描述，被ve自动调用

 var retData =cVe.XHR.responseText;
 alert("从 消息响应人口 进入 消息回应消息处理函数resCkeckFormData， 获得服务器的响应数据为："+retData); 
 var retDataObj=eval("("+retData+")");	 
 var htmlData = "<table border=0>";
 htmlData += "<tr><td><b>姓名：</b></td><td>" + retDataObj[cVe.cEioVeDataId].cName + "</td></tr>";
 htmlData += "<tr><td><b>年龄：</b></td><td>" + retDataObj[cVe.cEioVeDataId].cAge + "</td></tr>";
 htmlData += "<tr><td><b>简介：</b></td><td>" + retDataObj[cVe.cEioVeDataId].cInfo + "</td></tr>";
 htmlData += "</table>";
		  
 document.getElementById("serverReturn").innerHTML = htmlData;	
}


//////////////////消息sendVcIds的处理与相应函数/////////////////////////
function reqInjectSelect()
{//一个具体的请求消息函数， 在msgConfigure中描述，被ve自动调用
 //返回EIO视图组件Vc的ID号。用于从服务器请求相应的Vc数据
  //alert("从消息人口gotoMsgHandle 进入 消息处理函数sendVcIds");	
  var sendData ="{\"VcId\":\"idSelCountry\"}" ;//设置信息内容：Vc的ID值，这里假设，ID好用键值对表示，键为VxID,所需数据的HTML元素的iD值
  //(serverUri,method,isAsync,sendData)
  cVe.setConn(cServerUri,"POST", true, sendData);
}

function resInjectSelect()
{//resInjectSelect()的响应处理人口，需要在消息配置表中描述
 //设服务端返回一个JSON格式的字符串：{VcId:VC的id值, VcItems:[条目1，条目2，...]}
 var retData =cVe.XHR.responseText; //获取服务端回送的数据；
 alert("从 消息响应人口 进入 消息回应消息处理函数resInjectSelect()， 获得服务器的响应数据为："+retData); 
 var retDataObj=eval("("+retData+")");//将消息数据转化为JS的JSON对象，以方便处理	 

 cVei.injectSelect(retDataObj[cVe.cEioVeDataId].VcId,retDataObj[cVe.cEioVeDataId].VcItems);//调用注入函数，将服务端返回的数据注入到指定的视图控件
 
}

////////////消息MsgSendLoinData的处理///////////
function reqOpenNewWindow()
{//一个具体的请求消息函数， 在msgConfigure中描述，被ve自动调用
  alert("从消息人口gotoMsgHandle 进入 消息处理函数reqOpenNewWindow");

  var s=injectText("idName");
  var sendData = "{\"idName\":\"" + s+"\"}" ;//设置信息内容：cName:“张三"为查询条件，该值需要与服务端信息处理器协商确定
  //(serverUri,method,isAsync,sendData)
  cVe.setConn(cServerUri,"POST", true, sendData);
 
}

function resOpenNewWindow()
{//响应处理人口，需要在消息配置表中描述
	//这里收到的数据的格式规定：消息头后接HTML文件内容，以EIO的数据标志（即cEioVeDataId的值）为起点，结束字符必须是"</html>", 大小写无关，要求7个字符
 var retData =cVe.XHR.responseText; //获取服务端回送的数据；
 alert("从 消息响应人口 进入 消息回应消息处理函数resOpenNewWindow， 获得服务器的响应数据为："+retData);
 //var retDataObj=eval("("+retData+")");//将消息数据转化为JS的JSON对象，以方便处理
 k = retData.indexOf(cVe.cEioVeDataId);
 kk =retData.lastIndexOf("<"); //定位到最后的</html>
 retData=retData.substring(k+cVe.cEioVeDataId.length+2,kk+7);

 //mesg=window.open("","后台生成网页并打开","toolbar=no,,menubar=no,location=no,scrollbars=no");
 mesg=window.open("", "newwin", "height=250, width=250,toolbar=no,scrollbars=no,menubar=no"); 
 
 mesg.document.write(retData);//Chrome不支持？
  
}

function reqGetNewPageData()
{//一个具体的请求消息函数， 在msgConfigure中描述，被ve自动调用
 //从服务端请求一个新网页内容
 var tName=injectText("idName");
 var tAge=injectText("idAge");
 var sendData = "{\"idName\":\"" + tName+"\", \"idAge\":\"" + tAge+"\"}" ;
 //(serverUri,method,isAsync,sendData)
 cVe.setConn(cServerUri,"POST", true, sendData);
 
}

function resGetNewPageData()
{//reqGetNewPageData()的响应处理人口，需要在消息配置表中描述
 //设服务端返回一个包含新网页内容的JSON格式串 ：{InitialData:初始化数, HTML:网页串 }
 //初始化数可用于在加载网页时或者其他时间执行JS程序的参数，本例不考虑
 var retData =cVe.XHR.responseText; //获取服务端回送的数据；
 alert("从 消息响应人口 进入 消息回应消息处理函数resGetNewPageData， 获得服务器的响应数据为："+retData);
 var retDataObj=eval("("+retData+")");//将消息数据转化为JS的JSON对象，以方便处理

 retData = "<head> <body><table border=1>";
 retData += "<tr><td><b>第一条：</b></td><td>" + retDataObj[cVe.cEioVeDataId].HTML[0] + "</td></tr>";
 retData += "<tr><td><b>第二条：</b></td><td>" + retDataObj[cVe.cEioVeDataId].HTML[1] + "</td></tr>";
 retData += "<tr><td><b>第三条：</b></td><td>" + retDataObj[cVe.cEioVeDataId].HTML[2] + "</td></tr>";
 retData += "</table> </head> </body>";
 
 //mesg=window.open("","后台生成网页并打开","toolbar=no,,menubar=no,location=no,scrollbars=no");
 switchToNewPage(retData); 
}

//==============MsgOpenNewPageFile==============
function reqOpenNewPageFile()
{//一个具体的请求消息函数， 在msgConfigure中描述，被ve自动调用
 //从服务端请求一个新网页内容
 var tName=injectText("idName");
 var tAge=injectText("idAge");
 var sendData = "{\"idName\":\"" + tName+"\", \"idAge\":\"" + tAge+"\"}" ;

 //(serverUri,method,isAsync,sendData)
 cVe.setConn(cServerUri,"POST", true, sendData);
 
}

function resOpenNewPageFile()
{//reqGetNewPageData()的响应处理人口，需要在消息配置表中描述
 //这里收到的数据的格式规定：消息头后接HTML文件内容，以EIO的数据标志（即cEioVeDataId的值）为起点，结束字符必须是"</html>", 大小写无关，要求7个字符
 var retData =cVe.XHR.responseText; //获取服务端回送的数据；
 alert("从 消息响应人口 进入 消息回应消息处理函数resGetNewPageData， 获得服务器的响应数据为："+retData);
 //var retDataObj=eval("("+retData+")");//将消息数据转化为JS的JSON对象，以方便处理
 k = retData.indexOf(cVe.cEioVeDataId);
 kk =retData.lastIndexOf("<"); //定位到最后的</html>
 retData=retData.substring(k+cVe.cEioVeDataId.length+2,kk+7);
 //document.write('<html><head><script type="text/javascript">document.write("测试")<\/script><head></body></html>');
 
 window.document.write(retData);
 window.document.close();

 //mesg=window.open("","后台生成网页并打开","toolbar=no,,menubar=no,location=no,scrollbars=no");
// switchToNewPage(retData); 
}

//=================MsgGetDbRecs=============
function reqGetDbRecs()
{//一个具体的请求消息函数， 在msgConfigure中描述，被ve自动调用
  //alert("从消息人口gotoMsgHandle 进入 消息处理函数reqQueryDb");	
 
 var sendData =getFormData();//调用用户自己的数据提取函数
 //(serverUri,method,isAsync,sendData)
 cVe.setConn(cServerUri,"POST", true, sendData);
}


function resGetDbRecs()
{//一个具体的回复处理函数， 在msgConfigure中描述，被ve自动调用

 var retData =cVe.XHR.responseText;
 var retDataObj=eval("("+retData+")");	

 document.getElementById("serverReturn").innerHTML="<tr><td><b>";
 for (var i=0;i<retDataObj[cVe.cEioVeDataId].length; i++)
 {
  document.getElementById("serverReturn").innerHTML += retDataObj[cVe.cEioVeDataId][i]+"<br>";
 }
 document.getElementById("serverReturn").innerHTML+="</tr></td></b>";
	
}

//=================MsgInjectBuffer=============
function reqInjectBuffer()
{//一个具体的请求消息函数， 在msgConfigure中描述，被ve自动调用
 //请求服务端数据，以注入Buffer
 //调用方法：在读Buffer中，判断到需要从服务端请求新数据时，执行：
 //cVe.gotoMsgHandle('MsgInjectBuffer','MsgInjectBuff1',cMsgConfigure)
 //这里，MsgInjectBuff1为针对具体Buffer的消息名称（可以有多个Buffer）
  //alert("从消息人口gotoMsgHandle 进入 消息处理函数reqQueryDb");	
 var sendData ="{\"DataSource\":\"tb1\", \"DataStart\":\"1\", \"DataNum\":\"100\"}" ;
   //这里设：请求的数据源名称为tb1,请求的数据起点为1， 数目为100	  
 //(serverUri,method,isAsync,sendData)
 cVe.setConn(cServerUri,"POST", true, sendData);
}


function resInjectBuffer()
{//一个具体的回复处理函数， 在msgConfigure中描述，被ve自动调用
 //处理服务端回送的数据，将其注入到缓冲区
 //回送的数据格式：{EIOVEDATA:{[r1, r2, ...] }
 //r1,r2， ...的格式：[f1, f2, ...]， 每个f是一个具体的数据内容

 var retData =cVe.XHR.responseText;
 var retDataObj=eval("("+retData+")");
 //回送的数据存储在（长度为retDataObj[cEioVeDataId].length）：retDataObj[cEioVeDataId][i]，每个i为一条记录（数组）

 buffer.append(start,retDataObj[cVe.cEioVeDataId]);
}

var buffer = new EIO.veBuffer();
//设置源
//@param {function} 获取源数据总数目
//@param {function} 获取源数据
  /*
   * @param {number} 起始位置
   * @param {number} 需要获取数据的数目 
   */
//@param {function} 将缓冲区修改的数据同步到源
  /*
   * @param {number} 起始位置
   * @param {number} 结束位置
   * @param {string} 需要同步到后台的数据 
   */
buffer.setSource(function(){return 10;},function(s,n){
	start = s;
	cVe.startReqByMsgMap(cVeName,'MsgInjectBuffer','MsgInjectBuff1',cMsgConfigure);
},function(s,e,str){});
//为grid加入一个全局的buffer
window.buffer_grid= new EIO.veBuffer();;
function gridDemo()
{
			//设置buffer
			buffer_grid.setSource(function() {
				return 1001;
			}, function(s, n) {
				start = s;
				cVe.startReqByMsgMap(cVeName, 'MsgQueryDb','MsgGetDbRecsToVeGrid',cMsgConfigure);
			}, null);
			//新建表格
				cVei.initGrid('serverReturn', buffer_grid, {
				'caption': "学生信息",
				'colNames': ['id', 'no', 'name','school','score','degree','diploma'],
				'widths': ['150%', '150%', '150%','150%','150%','150%','150%'],
				shrinkToFit: true,
			});
}
//==============reqGetDbRecsToVegrid============
function reqGetDbRecsToVegrid()
{//一个具体的请求消息函数， 在msgConfigure中描述，被ve自动调用
  //alert("从消息人口gotoMsgHandle 进入 消息处理函数reqQueryDb");	
 
 var sendData =getFormData();//调用用户自己的数据提取函数
 //(serverUri,method,isAsync,sendData)
 cVe.setConn(cServerUri,"POST", true, sendData);
}


function resGetDbRecsToVegrid()
{//一个具体的回复处理函数， 在msgConfigure中描述，被ve自动调用

 var retData =cVe.XHR.responseText;
 var retDataObj=eval("("+retData+")");	
alert(retDataObj[cVe.cEioVeDataId]);
buffer_grid.append(start,retDataObj[cVe.cEioVeDataId]);;
}
	

///////////一些通用函数////////////////////////


function injectText(idText)
{//抽取id为inText的文本框中的内容，并返回
 return document.getElementById(idText).value;
}


function getFormData()
{
 //var formobj = document.getElementById("idForm"); alert(formData);
 //var formdata = new FormData(formobj);   
 var formData;
 formData = "{\"cName\":\""+document.getElementById("idName").value +"\"";
 formData += ",\"cAge\":\""+document.getElementById("idAge").value+"\"}";
 
 //其他元素的值的获取，此略
 return formData;
}

