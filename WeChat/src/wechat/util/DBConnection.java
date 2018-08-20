package wechat.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/*
 *�����ӳ��л�ȡ���ݿ����� 
 */
public class DBConnection {
	static ComboPooledDataSource cpds=null;
	static{
		cpds = new ComboPooledDataSource("mysql");
	}
	
	/*
	 * ��ȡ����
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
	 * ���ݿ�رղ���
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
