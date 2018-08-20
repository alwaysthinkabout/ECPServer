package ECP.util.helper;

public class JobHelper {
	
	public static String orgInfoEnToCN(String column){
		switch(column) { 
		case "org_type": 
			return "公司性质";
		case "org_name":
			return "企业名称";
		case "legal_rep":
			return "法定代表人";
		case "reg_address":
			return "单位地址";
		case "business_scope":
			return "经营范围";
		case "reg_capital":
			return "注册资本";
		case "reg_time":
			return "注册时间";
		case "staffcount":
			return "公司在职人数";
		case "contact":
			return "招聘联系人";
		case "contact_email":
			return "招聘联系人邮箱";
		case "reg_auth":
			return "登记机关";
		case "contact_phone":
			return "招聘联系人电话";
		case "license_no":
			return "营业执照号";
		case "website":
			return "企业官网";
		default: 
			return ""; 
		} 
	}
	
	public static String stateInfoEnToCN(String column){
		switch(column) { 
		case "year": 
			return "年度";
		case "employee_num":
			return "员工数";
		case "techstaff_num":
			return "技术人员数量";
		case "revenue":
			return "营业额";
		case "total_assets":
			return "资产总额";
		case "total_indebt":
			return "负债总额";
		case "net_sales":
			return "净销售额";
		case "current_assets":
			return "流动资产";
		case "current_indebt":
			return "流动负债";
		case "operate_expense":
			return "营业费用";
		case "profit":
			return "利润";
		case "loan_balance":
			return "贷款余额";
		case "taxation":
			return "纳税";
		case "rd_budget":
			return "研发经费金额";
		default: 
			return ""; 
		} 
	}
	
	public static String personInfoEnToCN(String column){
		switch(column) { 
		case "name": 
			return "真实姓名";
		case "phone":
			return "手机号码";
		case "sex":
			return "性别";
		case "idCard":
			return "身份证号";
		case "email":
			return "电子邮箱";
		case "title":
			return "最高职称";
		case "birth":
			return "出生日期";
		case "education":
			return "最高学历";
		default: 
			return ""; 
		} 
	}
}
