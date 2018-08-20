package ECP.util.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * 数据库工具类
 * @author durenshi
 *
 */
public class DBConnect {
	static ComboPooledDataSource cpds=null;
	static{
		cpds = new ComboPooledDataSource("mysql");
	}
	/**
	 * 获得数据库连接
	 * @return   Connection
	 */
	public static Connection getConn(){
		try {
			return cpds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 数据库关闭操作
	 * @param conn  
	 * @param st    
	 * @param pst
	 * @param rs
	 */
	public static void close(Connection conn,PreparedStatement pst,ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pst!=null){
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
