package wechat.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/*
 *从连接池中获取数据库连接 
 */
public class DBConnection {
	static ComboPooledDataSource cpds=null;
	static{
		cpds = new ComboPooledDataSource("mysql");
	}
	
	/*
	 * 获取连接
	 * */
	public static Connection getConnect(){
		try{
			return cpds.getConnection();
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 数据库关闭操作
	 * @param conn     
	 * @param pst
	 * @param rs
	 */
	public static void close(ResultSet rs ,PreparedStatement pst , Connection conn){
		if(rs != null){
			try{
				rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		if(pst != null){
			try{
				pst.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		if(conn != null){
			try{
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
	}
}
