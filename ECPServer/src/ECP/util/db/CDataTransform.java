package ECP.util.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author durenshi
 * 数据库返回数据转换
 */
public class CDataTransform {
	/**
	 * resultset转为stringbuffer
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static StringBuffer RsToArrayStr(ResultSet rs) throws SQLException {
	    ResultSetMetaData rsmd=rs.getMetaData();
	    int k = rsmd.getColumnCount(); 
	    if (k<1) return null;	  
	    StringBuffer tRecsBuffer=new StringBuffer("[");
	    StringBuffer tRowBuffer=new StringBuffer();
	    while (rs.next()){
	    	tRowBuffer.append("\""+rs.getString(1)+"\"") ;
	    	for (int i = 2; i <= k; i++) {
	    		tRowBuffer.append( ",\""+rs.getString(i)+"\"");
	    	}
	    	tRecsBuffer.append("{"+tRowBuffer+"},");
	    	tRowBuffer.setLength(0);
	    }
		if(tRecsBuffer.length()>1){
			tRecsBuffer.setCharAt(tRecsBuffer.length()-1, ']');
		}
	    System.out.println("RsToArrayStr===="+tRecsBuffer);
	    return tRecsBuffer;
	 }
	
	/**
	 * ResultSet转化为jsonarray
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static JSONArray resultToArray(ResultSet rs,int index) throws SQLException {
		if(rs==null||!rs.next())
			return null;
		rs.last();
		int rowCount = rs.getRow();
		ResultSetMetaData rsmd = rs.getMetaData();
		int rol = rsmd.getColumnCount(); // 列数
		rs.first();
		String []colname =new String[rol];

		for(int i =0;i<rsmd.getColumnCount();i++){
			 colname[i] = rsmd.getColumnName(i+1).toLowerCase();
		}

		String[][] data = new String[rowCount][rol];
		for(int i=0;i<rowCount;i++ ){
		     for(int j=0;j<rol;j++){
		    	 if(rs.getString(j+1) != null)
		    		 data[i][j]=rs.getString(j+1);
		    	 else data[i][j] ="";
		     }
		     rs.next();
		}
		for(int i=0;i<rowCount;i++ ){
			for(int j=i+1;j<rowCount;j++){
				if(data[i][0].equals(data[j][0])){
					data[i][index]+=",";
					data[i][index]+=data[j][index];
					data[j][0] = "-1";
				}
			}
		}
		return ArrayToJson(data,colname);
	}
	/**
	 * 数组转为stringbuffer
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private static StringBuffer ArraytoStr(String[][] data){
		  if (data.length<1) return null;	  
		  StringBuffer tRecsBuffer=new StringBuffer("[");
		  StringBuffer tRowBuffer=new StringBuffer();
		  for(int i=0;i<data.length;i++)
		  {
			  if(!data[i][0].equals("-1")){
				  tRowBuffer.append("\""+data[i][0]+"\"") ;
				  for(int j=1;j<data[i].length;j++){
					  tRowBuffer.append( ",\""+data[i][j]+"\"");	  
				  }
				  tRecsBuffer.append("{"+tRowBuffer+"},");
				  }
			  tRowBuffer.setLength(0);
		  }
		  if(tRecsBuffer.length()>1){
				tRecsBuffer.setCharAt(tRecsBuffer.length()-1, ']');
		  }
		  System.out.println("RsToArrayStr===="+tRecsBuffer);
		  return tRecsBuffer;
	}
	
	public static JSONArray ArrayToJson(String[][] data, String []colname){
		JSONArray result =new JSONArray();		
		try {
			for(int i=0;i<data.length;i++){
				if(!data[i][0].equals("-1")){
					JSONObject obj =new JSONObject();
					 for(int j =0;j<data[i].length;j++){
						 //obj.append(colname[i], data[i][j]);
						 obj.put(colname[j],data[i][j]);
					 }
					 result.put(obj);
				}
				 
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return result;
	}
	
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
	/**
	 * ResultSet转换为Json格式,结果使用别名而不使用列名。
	 * @author zhw2
	 * @param rs
	 * @return 
	 * @throws SQLException
	 */
	public static JSONArray rsToJsonLabel(ResultSet rs){
		JSONArray result =new JSONArray();		
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				 JSONObject obj =new JSONObject();
				 for(int i =1;i<=rsmd.getColumnCount();i++){
					 obj.put(rsmd.getColumnLabel(i), toString(rs.getObject(i)).trim());
				 }
				 result.put(obj);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return result;
	}
//gj:输入数据为两列，第一列为标签名称（键），第二列为值。
	public static JSONArray rsToJsonRowLabel(ResultSet rs) {
		// TODO Auto-generated method stub
		JSONArray result =new JSONArray();		
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				 JSONObject obj =new JSONObject();
				 obj.put(toString(rs.getObject(1)).trim(), toString(rs.getObject(2)).trim());				 
				 result.put(obj);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return result;
	}
	public static JSONArray rsToJsonOneColumn(ResultSet rs){
		JSONArray obj =new JSONArray();		
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			
			while (rs.next()) {				 				 
				 obj.put(toString(rs.getObject(1)).trim());				 
	        }
					
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return obj;
	}
}
	
