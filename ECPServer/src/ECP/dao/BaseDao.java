package ECP.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.json.JSONObject;

/*
 * @author durenshi
 * 数据操作层基类
 */
public class BaseDao {
	protected Connection conn;
	protected PreparedStatement ps,ps1;      
	protected ResultSet rs,rs1;   
	
	public BaseDao(){
		conn = null;
		ps = null;
		ps1 = null;
		rs = null;
		rs1 = null;
	}
	
	protected Object findAll() {
		return null;
	}
	
	protected Object findById(Integer id) {
		return null;
	}
	
	protected int insert(JSONObject data) {
		return 1;
	}
	
	protected int delete(JSONObject data){
		return 1;
	}
	
	protected int delete(Integer data){
		return 1;
	}
	
	protected int delete(String data){
		return 1;
	}
	
	protected int update(JSONObject data){
		return 1;
	}
	
	protected Object query(JSONObject data){
		return 1;
	}

	protected Object findById(String id) {
		return null;
	}
	
	protected boolean IsExist(JSONObject data){
		return true;
	}
	
}
