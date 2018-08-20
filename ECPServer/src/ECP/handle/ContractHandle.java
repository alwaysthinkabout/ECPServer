package ECP.handle;

import org.json.JSONObject;
import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;
import ECP.service.ContractService;


public class ContractHandle extends CMsgBaseHandle{
	
	private ContractService contractService;
	
	public ContractHandle(){
		contractService = new ContractService();
	}
	
	@Override
	public int handleMsg(CParam param) throws Exception {
		JSONObject data=getReqMessage(param);
		String op=data.getString("op");
		System.out.println("请求的参数为："+data.toString());
		JSONObject result=null;
		
		switch(op){
		
		case "addContractTemplate":         //添加合同模板
			result=contractService.contractTemplateAdd(data);
			break;
		case "contractTemplateList":        //合同模板列表
			result=contractService.contractTamplateList(data);
			break;
		case "contractTemplateDetail":      //合同模板详情
			result=contractService.contractTamplateDetail(data);
			break;
		case "contractTemplateDelete":      //合同模板删除
			result=contractService.contractTamplateDelete(data);
			break;
		case "contractTemplateUpdate":      //合同模板编辑
			result=contractService.contractTemplateUpdate(data);
			break;			
		case "contractAdd":                 //添加合同
			result=contractService.contractAdd(data);
			break;
		case "contractDelete":              //删除合同
			result=contractService.contractDelete(data);
			break;
		case "contractDetail":              //合同详情
			result=contractService.contractDetail(data);
			break;
		case "contractUpdate":              //合同编辑
			result=contractService.contractUpdate(data);
			break;
		case "contractList":              //合同列表 add by lgp
			result=contractService.contractList(data);
			break;
		case "getContractList":              //求职端获取合同列表 add by lgp
			result=contractService.getContractList(data);
			break;
		case "contractUpdate1":              //求职端更新合同 add by lgp
			result=contractService.contractUpdate1(data);
			break;
		case "setContract_status":              //更新合同状态 by lgp
			result=contractService.setContract_status(data);
			break;
		default:
			String str = "{\"result\":\"error\",\"resultTip\":\"请求参数出错\"}";
			result = new JSONObject(str);
		}
		param.respData.append(result.toString());
		return super.handleMsg(param);
	}

}
