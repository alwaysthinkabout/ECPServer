package ECP.handle;

import org.json.JSONObject;

import ECP.service.StoreService;
import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;

public class StoreHandle extends CMsgBaseHandle {
	private StoreService storeService;
	
	public StoreHandle(){
		storeService = new StoreService();
	}
	
	@Override
	public int handleMsg(CParam param) throws Exception {
		System.out.println("进入StoreHandle的handleMsg");
		JSONObject data=getReqMessage(param);
		//这里初步设想是根据传入参数的长度来转发各种操作。或者像以前一样增加一个op的字段。
		System.out.println("收到的请求信息为："+data.toString());
		String op=data.getString("op");
		JSONObject result=null;
		switch(op){
		case "setStoreInfo":
			result=storeService.setStorInfo(data);
			break;
		case "setQualification":
			result=storeService.setQualification(data);
			break;
		case "setDelivery":
			result=storeService.setDelivery(data);
			break;
		case "setProductInfo":
			result=storeService.setProductInfo(data);
			break;
		case "setBankInfo":
			result=storeService.setBankInfo(data);
			break;
		case "setSubmitGoodsIformation":
			result=storeService.setSubmitGoodsIformation(data);
			break;
		case "getGoodsList":
			result=storeService.getGoodsList(data);
			break;
		case "getGoodsIformation":
			result=storeService.getGoodsIformation(data);
			break;
		case "setEditGoodsIformation":
			result=storeService.setEditGoodsIformation(data);
			break;
		case "setDeleteGoodsIformation":
			result=storeService.setDeleteGoodsIformation(data);
			break;
		case "getOrdersList":
			result=storeService.getOrdersList(data);
			break;
		case "getOrderInfo":
			result=storeService.getOrderInfo(data);
			break;
		case "setDelOrder":
			result=storeService.setDelOrder(data);
			break;
		case "setDeliveryInfo":
			result=storeService.setDeliveryInfo(data);
			break;
		case "getStoreList":
			result=storeService.getStoreList(data);
			break;
		case "getStoreInfo":
			result=storeService.getStoreInfo(data);
			break;
		case "getQualificationInfo":
			result=storeService.getQualificationInfo(data);
			break;
		case "getDistributionInfo":
			result=storeService.getDistributionInfo(data);
			break;
		case "getCollectionInfo":
			result=storeService.getCollectionInfo(data);
			break;
		case "setOrderState":
			result=storeService.setOrderState(data);
			break;		
		case "getQualification":
			result=storeService.getQualification(data);
			break;
		case "getDelivery":
			result=storeService.getDelivery(data);
			break;
		case "getBankInfo":
			result=storeService.getBankInfo(data);
			break;
		default:
			String str = "{\"result\":\"0\",\"resultTip\":\"请求参数出错\"}";
			result = new JSONObject(str);
		}
		param.respData.append(result.toString());
		System.out.println("输出数据："+param.respData);
		return super.handleMsg(param);
	}
}
