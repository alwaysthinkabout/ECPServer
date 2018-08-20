package ECP.service;

import org.json.JSONObject;

import ECP.dao.StoreDao;

public class StoreService {
	private StoreDao storeDao;	
	
	public StoreService(){
		storeDao = new StoreDao();
		
	}
	public JSONObject setStorInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = storeDao.setStoreInfo(data);
			int flag=retObject.getInt("flag");
			if(flag == 1)
			{
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
				result.put("data", retObject.get("data"));
			}else if(flag ==2){
				result.put("result", "0");
				result.put("resultTip", retObject.getString("tip"));
			}else{
				result.put("result", "1");
				result.put("resultTip", retObject.getString("tip"));
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}
	public JSONObject setQualification(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			int flag = storeDao.setQualification(data);
			if(flag > 0)
			{
				result.put("result", "0");
				result.put("resultTip", "保存成功");
			}else if(flag == 0){
				result.put("result", "1");
				result.put("resultTip", "证件已存在");
			}else{
				result.put("result", "1");
				result.put("resultTip", "数据库写入失败");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}
	public JSONObject setDelivery(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			int flag = storeDao.setDelivery(data);
			if(flag > 0)
			{
				result.put("result", "0");
				result.put("resultTip", "保存成功");
			}else if(flag == -1){
				result.put("result", "1");
				result.put("resultTip", "物流方式已存在");
			}else{
				result.put("result", "1");
				result.put("resultTip", "数据库写入失败");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;			
	}
	public JSONObject setProductInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retData = storeDao.setProductInfo(data);
			if(retData.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", "保存成功");
			}else {
				result.put("result", "1");
				result.put("resultTip", retData.getString("resultTip"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}
	public JSONObject setBankInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retData = storeDao.setBankInfo(data);
			if(retData.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", "保存成功");
			}else {
				result.put("result", "1");
				result.put("resultTip", retData.getString("tip"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}
	public JSONObject setSubmitGoodsIformation(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retData = storeDao.setSubmitGoodsIformation(data);
			if(retData.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", "保存成功");
			}else {
				result.put("result", "1");
				result.put("resultTip", retData.getString("tip"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject getGoodsList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = storeDao.getGoodsList(data);
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
	public JSONObject getGoodsIformation(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = storeDao.getGoodsIformation(data);			
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
	public JSONObject setEditGoodsIformation(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retData = storeDao.setEditGoodsIformation(data);
			if(retData.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", "保存成功");
			}else {
				result.put("result", "1");
				result.put("resultTip", retData.getString("resultTip"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public JSONObject setDeleteGoodsIformation(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retData = storeDao.setDeleteGoodsIformation(data);
			if(retData.getInt("flag") == 0)
			{
				result.put("result", "0");
				result.put("resultTip", "保存成功");
			}else {
				result.put("result", "1");
				result.put("resultTip", retData.getString("resultTip"));
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
			JSONObject retObject = storeDao.getOrdersList(data);			
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
			JSONObject retObject = storeDao.getOrderInfo(data);			
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
			JSONObject retObject = storeDao.setDelOrder(data);			
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
	public JSONObject setDeliveryInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = storeDao.setDeliveryInfo(data);			
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
			JSONObject retObject = storeDao.getStoreInfo(data);			
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
	public JSONObject getStoreList(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = storeDao.getStoreList(data);			
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
	public JSONObject getQualificationInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = storeDao.getQualificationInfo(data);			
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
	public JSONObject getDistributionInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = storeDao.getDistributionInfo(data);			
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
	public JSONObject getCollectionInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = storeDao.getCollectionInfo(data);			
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
	public JSONObject setOrderState(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = storeDao.setOrderState(data);			
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
	public JSONObject getQualification(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = storeDao.getQualification(data);			
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
	public JSONObject getDelivery(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = storeDao.getDelivery(data);			
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
	public JSONObject getBankInfo(JSONObject data) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			JSONObject retObject = storeDao.getBankInfo(data);			
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
