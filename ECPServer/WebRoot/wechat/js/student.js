var org_id = "";
var user_id = "";
function studentAddClick(){
	studentAdd();
}

function studentAdd(){
	//姓名
	var real_name=$("#realName").val().length;
	//身份证
	var IDCardNo = $("#ID");
	//email
	var email = $("#contactEmail");
	//phone
	var phone = $("#contactPhone");
	//学历
	var educationHis=$("#education").val().length;
	//最高职称
	var tHighestTitle=$("#highestTitle").val().length;

	
	var regID = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;
	var teleNumber = /^((13[0-9]{1})|159|153)+\d{8}$/;
	var fixedNumber = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;
	var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/; 
	//验证数据是否完整、合格
	if(real_name==""){
		alert("姓名不能为空");
		return false;
	} 
	if(IDCardNo.val()==""){
		alert("身份证号码不能为空");
		return false;
	}
	if(educationHis==""){
		alert("学历不能为空");
		return false;
	}
	//身份证号码判定		
	if(regID.test(IDCardNo.val())){
	    alert("请输入正确的身份证号码!");
		IDCardNo.focus();
	    return false;
	}
	//对电话验证--storeNumber.val() 还是 storeNumber.value
	if(phone.val()!=""&&!teleNumber.test(phone.val())&&!fixedNumber.test(phone.val()))
	 {
       alert("请输入有效的电话号码！");
       phone.focus();
       return false;
	 }
	 //对电子邮件的验证	
	 if(email.val()!=""&&!myreg.test(email.val()))
	 {
       alert("请输入有效的Email！");
       email.focus();
       return false;
   }	  
	var maxWorsNumbers = 30;//最大输入长度
	if(real_name>maxWorsNumbers||
	   (IDCardNo.val().length).length>maxWorsNumbers||
		educationHis>maxWorsNumbers||
		tHighestTitle>maxWorsNumbers||
	   (email.val().length)>maxWorsNumbers||
	   (phone.val().length).length>maxWorsNumbers)
	{
		alert("每一个输入项的长度不能超过 " +maxWorsNumbers+ " !");
	}
	//eio方法:把填的数据发送到后台的函数.前面3个参数固定,第四个是请求函数名，第五个是响应函数名，最后一个是后台处理函数名
	//handleMsg后台处理函数:eio发送到后台之后，后台进行处理的那个函数
	//ECP.handle.OrganizationInfoHandle.handleMsg 是后台的处理函数
	cVe.startReqByMsgHandle(cVeName,'','','reqStudentAdd','resStudentAdd','ECP.handle.OrganizationInfoHandle.handleMsg');
}
/**
 * 获取补登的json对象
 * return:解码后的student信息
 */
function GetJsonObjectForRegisterAdd() {
	var student;
	student=$("#addStudentForm").serializeArray();
	var theRequest = new Object();
	for(var i=0;i<student.length;i++){
		theRequest[student[i].name]=unescape(student[i].value);
	}
	return theRequest;
}
/**
 * reqStudentAdd:请求函数，把前端的数据发送给后台
 * 请求函数:eio发给后台之前会调用的函数
 * 后台处理函数，保存到数据库.完成后返回到前面响应函数
 */
function reqStudentAdd(){
	var store = new Object();
	store = GetJsonObjectForRegisterAdd();
	var req = {};
	//将数据封装到req中，op字段指明ECP.handle.OrganizationInfoHandle.handleMsg的具体操作
	req["op"] = "studentAdd";
	req["real_name"]=$("#realName").val();
//	req["gender"]=genderJudging();
	req["gender"]=$('input:radio:checked').val();
	req["born_time"]=$("#bornTime").val();
	req["IDCardNo"]=$("#ID").val();
	req["email"]=$("#contactEmail").val();
	req["phone"]=$("#contactPhone").val();
	req["educationHis"]=$("#education").val();
	req["tHighestTitle"]=$("#highestTitle").val();
	//user_id记录登录账号
	req["user_id"] = cVeUti.Cookie.getCookie("user_id");
	//发送封装的req信息到后台
	cVe.setConn(cServerUri,"POST", true, JSON.stringify(req));
}
////学生性别判断，男单选框为真即为男，否则为女
//function genderJudging(){
//	if($("#male").checked){
//		return "male";
//	}else{
//		return "female";
//	}
//}
/**
 * resStudentAdd:响应函数，根据后台返回的数据，显示成功或者失败
 * 响应函数:后台返回消息后前端会执行的函数
 */
function resStudentAdd(){
	//固定写法
	var retData =cVe.XHR.responseText;
	var retDataObj=JSON.parse(retData);
	
	var resultTip=retDataObj[cVe.cEioVeDataId].resultTip;
	var result=retDataObj[cVe.cEioVeDataId].result;
	//获取data字段，该字段由前段后台定
//	var data=retDataObj[cVe.cEioVeDataId].data;

//	org_id=data;
	alert(result);
}