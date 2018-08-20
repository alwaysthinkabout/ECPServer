package wechat.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class BaseDao {
	protected Connection conn;
	protected PreparedStatement pst;
	protected ResultSet rs;
	
	public BaseDao(){
		conn = null;
		pst = null;
		rs = null;
	}
}
