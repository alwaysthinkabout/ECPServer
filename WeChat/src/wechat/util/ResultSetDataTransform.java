package wechat.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

public class ResultSetDataTransform {
	/**
	 * ResultSet转换为Json格式
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static JSONArray rsToJson(ResultSet rs){
		JSONArray result =new JSONArray();		
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				 JSONObject obj =new JSONObject();
				 for(int i =1;i<=rsmd.getColumnCount();i++){
					 obj.put(rsmd.getColumnName(i).toLowerCase(), toString(rs.getObject(i)).trim());
				 }
				 result.put(obj);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return result;
	}
	private static String toString(Object obj){
		if(obj==null) return "";
		else return obj.toString();
	}
}
