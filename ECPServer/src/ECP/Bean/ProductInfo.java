package ECP.Bean;

import java.sql.Timestamp;

import ECP.dao.BaseDao;

import com.sun.beans.editors.FloatEditor;
//gj
public class ProductInfo {
	private int product_id;
	private int store_id;
	private String goods_Photo;
	private String description;
	private float org_price;
	private int amount;
	private String production;
	private String goods_Category;
	private String goods_Name;
	private float goods_Price;
	private float package_Price;
	private int goods_Stock;
	private int supplier_id;
	private int goods_state;
	private java.sql.Timestamp submitTime;
	private BaseDao baseDao=new BaseDao();
	
	public ProductInfo(){
		this.store_id=-1;
		this.goods_Photo=null;
		this.description=null;
		this.org_price=-1;
		this.amount=-1;
		this.production=null;
		this.goods_Category=null;
		this.goods_Name=null;
		this.goods_Price=-1;
		this.package_Price=-1;
		this.goods_Stock=-1;
		this.supplier_id=-1;
		this.goods_state=-1;
		this.submitTime=new Timestamp(System.currentTimeMillis());
	}
	public int getProduct_id(){
		return this.product_id;
	}
	public int getStoreID(){
		return this.store_id;
	}
	public void setStoreID(int storeID){
		this.store_id=storeID;
	}
	public String getGoodsPhoto(){
		return this.goods_Photo;
	}
	public void setGoodsPhoto(String goodsPhoto){
		this.goods_Photo=goodsPhoto;
	}
	public String getDescription(){
		return this.description;
	}
	public void setDescription(String description){
		this.description=description;
	}
	public float getOrgPrice(){
		return this.org_price;
	}
	public void setOrgPrice(float orgPrice){
		this.org_price=orgPrice;
	}
	public int getAmount(){
		return this.amount;
	}
	public void setAmount(int amount){
		this.amount=amount;
	}
	public String getProduction(){
		return this.production;
	}
	public void setProduction(String production){
		this.production=production;
	}
	public String getGoodsCategory(){
		return this.goods_Category;
	}
	public void setGoodsCategory(String goodsCategory){
		this.goods_Category=goodsCategory;
	}
	public String getGoodsName(){
		return goods_Name;
	}
	public void setGoodsName(String goodsName){
		this.goods_Name=goodsName;
	}
	public float getGoodsPrice(){
		return this.goods_Price;
	}
	public void setGoodsPrice(float goodsPrice){
		this.goods_Price=goodsPrice;
	}
	public float getPackagePrice(){
		return this.package_Price;
	}
	public void setPackagePrice(float packagePrice){
		this.package_Price=packagePrice;
	}
	public int getGoodsStock(){
		return this.goods_Stock;
	}
	public void setGoodsStock(int goodsStock){
		this.goods_Stock=goodsStock;
	}
	public int getSupplierID(){
		return this.supplier_id;
	}
	public void setSupplierID(int supplierID){
		this.supplier_id=supplierID;
	}
	public int getGoodsState(){
		return this.goods_state;
	}
	public void setGoodsState(int goodsState){
		this.goods_state=goodsState;
	}
	public java.sql.Timestamp getSubmitTime(){
		return this.submitTime;
	}
	public void setSubmitTime(java.sql.Timestamp submitTime){
		this.submitTime=submitTime;
	}
	
	
	
}
	
	
	
	
	
	
	
