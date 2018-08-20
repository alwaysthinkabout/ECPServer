package ECP.handle;

import org.json.JSONObject;

import ECP.service.MarketService;
import ECP.service.StoreService;
import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;

public class MarketHandle extends CMsgBaseHandle {
	private MarketService marketService;
	
	public MarketHandle(){
		marketService = new MarketService();
	}	
	@Override
	public int handleMsg(CParam param) throws Exception {
		System.out.println("进入MarketHandle的handleMsg");
//		System.out.println("***param***:"+param.getMsgReq().toString());
		JSONObject data=getReqMessage(param);
//		System.out.println("****:"+data.toString());
		//这里初步设想是根据传入参数的长度来转发各种操作。或者像以前一样增加一个op的字段。
		String op=data.getString("op");
		System.out.println("收到的请求信息为："+data.toString());
		JSONObject result=new JSONObject();
		switch(op){
		case "getDeliveryAddress":
			result=marketService.getDeliveryAddress(data);
			break;
		case "getUserData":
			result=marketService.getUserData(data);
			break;
		case "updateUserData":
			result=marketService.updateUserData(data);
			break;
		case "updateDefAddress":
			result=marketService.updateDefAddress(data);
			break;
		case "delDeliveryAddress":
			result=marketService.delDeliveryAddress(data);
			break;
		case "addDeliveryAddress":
			result=marketService.addDeliveryAddress(data);
			break;
		case "updateDeliveryAddress":
			result=marketService.updateDeliveryAddress(data);
			break;
		case "getGoodData":
			result=marketService.getGoodData(data);
			break;
		case "getGoodComment":
			result=marketService.getGoodComment(data);
			break;
		case "getDefaultAddress":
			result=marketService.getDefaultAddress(data);
			break;
		case "getClassifyTree":
			result=marketService.getClassifyTree(data);
			break;
		case "addAttentionStore":
			result=marketService.addAttentionStore(data);
			break;
		case "cannelAttentionStore":
			result=marketService.cannelAttentionStore(data);
			break;
		case "getAttentionStoreList":
			result=marketService.getAttentionStoreList(data);
			break;
		case "getIsAttentionStore":
			result=marketService.getIsAttentionStore(data);
			break;
		case "addFavoriteGood":
			result=marketService.addFavoriteGood(data);
			break;
		case "cannelFavoriteGood":
			result=marketService.cannelFavoriteGood(data);
			break;
		case "getFavoriteGoodList":
			result=marketService.getFavoriteGoodList(data);
			break;
		case "getIsFavoriteGood":
			result=marketService.getIsFavoriteGood(data);
			break;
		case "addGoodToCart":
			result=marketService.addGoodToCart(data);
			break;
		case "cannelGoodInCart":
			result=marketService.cannelGoodInCart(data);
			break;
		case "getCartGoodList":
			result=marketService.getCartGoodList(data);
			break;
		case "getHotGoodList":
			result=marketService.getHotGoodList(data);
			break;
		case "getGoodInfo":
			result=marketService.getGoodInfo(data);
			break;
		case "getStoreInfo":
			result=marketService.getStoreInfo(data);
			break;
		case "getStoreGoodListAll":
			result=marketService.getStoreGoodListAll(data);
			break;
		case "getStoreGoodListNew":
			result=marketService.getStoreGoodListNew(data);
			break;
		case "getKeywordSearchList":
			result=marketService.getKeywordSearchList(data);
			break;
		case "getThemeSearch":
			result=marketService.getThemeSearch(data);
			break;
		case "submitOrders":
			result=marketService.submitOrders(data);
			break;
		case "getOrdersList":
			result=marketService.getOrdersList(data);
			break;
		case "getOrderInfo":
			result=marketService.getOrderInfo(data);
			break;
		case "setDelOrder":
			result=marketService.setDelOrder(data);
			break;
		case "addGoodComment":
			result=marketService.addGoodComment(data);
			break;
		case "setOrderPay":
			result=marketService.setOrderPay(data);
			break;
		case "setOrderReceipt":
			result=marketService.setOrderReceipt(data);
			break;
		case "requestDrawback":
			result=marketService.requestDrawback(data);
			break;
		case "getDrawbackList":
			result=marketService.getDrawbackList(data);
			break;
		case "delDrawbackRecord":
			result=marketService.delDrawbackRecord(data);
			break;	
		case "getUserWallet":
			result=marketService.getUserWallet(data);
			break;
		case "walletCharge":
			result=marketService.walletCharge(data);
			break;
		case "getCardData":
			result=marketService.getCardData(data);
			break;
		case "setAddCard":
			result=marketService.setAddCard(data);
			break;
		case "setUpdateCard":
			result=marketService.setUpdateCard(data);
			break;
		case "setDelCard":
			result=marketService.setDelCard(data);
			break;
		case "getAdData":
			result=marketService.getAdData(data);
			break;
		case "getHeadline":
			result=marketService.getHeadline(data);
			break;
		case "getStoreSearch":
			result=marketService.getStoreSearch(data);
			break;
		case "getStoreSearchList":
			result=marketService.getStoreSearchList(data);
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
