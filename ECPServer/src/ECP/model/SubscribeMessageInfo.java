package ECP.model;

import java.sql.Timestamp;


public class SubscribeMessageInfo {
	private String userID;//订阅者账号
	private String subsribe_type1;//系统消息、用户消息
	private String subsribe_type2;//消息的应用分类，来自商品分类表，店铺分类表，岗位分类表，服务分类表。
	private String key_word;//关键字
	private String sendID;//发送者ID
	private java.sql.Timestamp send_time;//消息的发送时间
	private Long keep_time;//消息的保留时间。
	private String content;//消息内容
	private String order_condition;//订阅条件，是属性的逻辑表达式。
	private short send_state;//消息是否发送
	private String eventType;//事件类型：如商品新增，商品降价，商品到货
	
	public SubscribeMessageInfo(){
		this.userID= null;
		this.subsribe_type1="用户消息";
		this.subsribe_type2= null;
		this.key_word= null;
		this.sendID=null;
		this.send_time=new Timestamp(System.currentTimeMillis());
		this.keep_time=2592000000L; //30天的毫秒数
		this.order_condition=null;
		this.send_state=0;//0未发送，1已发送
		this.eventType=null;
	}
	public String getUserID(){
		return userID;
	}
	public void setUserID(String userID){
		this.userID=userID;
	}
	public String getSubscribeType1(){
		return subsribe_type1;
	}
	public void setSubscribeType1(String subscribeType1){
		this.subsribe_type1=subscribeType1;
	}
	public String getSubscribeType2(){
		return subsribe_type2;
	}
	public void setSubscribeType2(String subscribeType2){
		this.subsribe_type2=subscribeType2;
	}
	public String getKeyWord(){
		return key_word;
	}
	public void setKeyWord(String keyWord){
		this.key_word=keyWord;
	}
	public String getSendID(){
		return sendID;
	}
	public void setSendID(String sendID){
		this.sendID=sendID;
	}
	public Timestamp getSendTime(){
		return send_time;
	}
	public void setSendTime(Timestamp sendTime){
		this.send_time=sendTime;
	}
	public Long getKeepTime(){
		return keep_time;
	}
	public void setKeepTime(Long keepTime){
		this.keep_time=keepTime;
	}
	public String getContent(){
		return content;
	}
	public void setContent(String content){
		this.content=content;
	}
	public String getOrderCondition(){
		return order_condition;
	}
	public void setOrderCondtion(String orderCondition){
		this.order_condition=orderCondition;
	}
	public short getSendState(){
		return send_state;
	}
	public void setSendState(short sendState){
		this.send_state=sendState;
	}
	public String getEventType() {
		// TODO Auto-generated method stub
		return eventType;
	}
	public void setEventType(String eventType){
		this.eventType=eventType;
	}
}
