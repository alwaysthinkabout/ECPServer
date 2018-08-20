package ECP.service;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

import ECP.dao.MarketDao;

public class MarketService {
	private MarketDao marketDao;	
	
	public MarketService(){
		marketDao = new MarketDao();
		
	}
	public JSONObject getDeliveryAddress(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getDeliveryAddress(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject getUserData(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getUserData(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject updateUserData(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.updateUserData(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", "");
			}else {
				result.put("result", "1");
				result.put("resultTip",retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject updateDefAddress(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.updateDefAddress(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", "");
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject delDeliveryAddress(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.delDeliveryAddress(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", "");
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject addDeliveryAddress(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.addDeliveryAddress(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", "");
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}

	public JSONObject updateDeliveryAddress(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.updateDeliveryAddress(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", "");
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}
	public JSONObject getGoodData(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getGoodData(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}
	public JSONObject getGoodComment(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getGoodComment(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}
	public JSONObject getDefaultAddress(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getDefaultAddress(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static void main(String[] args) throws Exception {
		System.out.println("测试MarketDao中的小函数");
		MarketService marketSer = new MarketService();
		JSONObject data=new JSONObject();
		JSONObject result=new JSONObject();
//		data.put("op", "getUserData");
		data.put("userId", "1");
//		data.put("userId",1);
		String orderString="[{'addressName':'1','addressPhone':'1','addressDetail':'1','storeId':'1'," +
				"'storeName':'','goodId':'1','goodDesc':'','price':'1','originPrice':'1','goodCount':'1','totalMoney':'1'," +
				"'deliveryType':'1','deliveryPrice':'1','extraMessage':'','orderState':'0'}," +
				"{'addressName':'1','addressPhone':'1','addressDetail':'1','storeId':'1'," +
				"'storeName':'','goodId':'1','goodDesc':'','price':'1','originPrice':'1','goodCount':'1','totalMoney':'1'," +
				"'deliveryType':'1','deliveryPrice':'1','extraMessage':'','orderState':'0'}]";
//		String orderString="['34','35']";
		JSONArray oArray=new JSONArray(orderString);
		data.put("orderData", oArray);
//		data.put("goodId", "1");
//		data.put("storeId", "1");
//		result=marketSer.getUserData(data);
//		result=marketSer.getGoodData(data);
//		result=marketSer.getGoodComment(data);
//		result=marketSer.getDefaultAddress(data);
		result=marketSer.submitOrders(data);
		System.out.println("用户数据"+result.toString());
	}
	public JSONObject getClassifyTree(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getClassifyTree(data);
			
			result.put("result", "0");
			result.put("resultTip", "null");
			result.put("data", retObject);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject addAttentionStore(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.addAttentionStore(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject cannelAttentionStore(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.cannelAttentionStore(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject getAttentionStoreList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getAttentionStoreList(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject getIsAttentionStore(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getIsAttentionStore(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject addFavoriteGood(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.addFavoriteGood(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject cannelFavoriteGood(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.cannelFavoriteGood(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject getFavoriteGoodList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getFavoriteGoodList(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject getIsFavoriteGood(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getIsFavoriteGood(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject addGoodToCart(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.addGoodToCart(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject cannelGoodInCart(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.cannelGoodInCart(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject getCartGoodList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getCartGoodList(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject getHotGoodList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getHotGoodList(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject getGoodInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getGoodInfo(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject getStoreInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getStoreInfo(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject getStoreGoodListAll(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getStoreGoodListAll(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject getStoreGoodListNew(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getStoreGoodListNew(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject getKeywordSearchList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getKeywordSearchList(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject getThemeSearch(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getThemeSearch(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject submitOrders(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.submitOrders(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getString("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject getOrdersList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getOrdersList(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject getOrderInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getOrderInfo(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject setDelOrder(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.setDelOrder(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject addGoodComment(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.addGoodComment(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject setOrderPay(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.setOrderPay(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject setOrderReceipt(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.setOrderReceipt(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject requestDrawback(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.requestDrawback(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject getDrawbackList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getDrawbackList(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject delDrawbackRecord(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.delDrawbackRecord(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject getUserWallet(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getUserWallet(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject walletCharge(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.walletCharge(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject getCardData(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getCardData(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject setAddCard(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.setAddCard(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject setUpdateCard(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.setUpdateCard(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject setDelCard(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.setDelCard(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject getAdData(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getAdData(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject getHeadline(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getHeadline(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject getStoreSearch(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getStoreSearch(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject getStoreSearchList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = marketDao.getStoreSearchList(data);
			
			if(retObject.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.getJSONArray("data"));
			}else {
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data","");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	
	

}
