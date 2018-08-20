package csAsc.ECOSS.reso.demo1;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import csAsc.ECOSS.reso.demo1.CMsgLogin;
import csAsc.com.ControlTable;
import csAsc.dbcom.CPermit;
import csAsc.dbcom.CTransform;
import csAsc.dbcom.DBAccess;

public class Test {
	public static void  main(String args[]) throws Exception{
		 DBAccess db = new DBAccess();
	 	  StringBuffer  retData;
	 	  ResultSet result;
		  String sqlStr;
		  List<String> lists=new ArrayList<String>();
		  //System.out.println(lists.contains("commonusermanage.html"));
		  if (db.createConn()) {
			   sqlStr = "select pages, element_id, permission from controltable where right_id=1";
			   result = db.query(sqlStr);
//			   retData=CTransform.RsToArrayStr(result);
//			   System.out.println("返回的数据是："+retData);			
			   try{
					while(result.next()){
						lists.add(result.getString("pages"));
					}
				}catch (SQLException e) {
					//return 0;
					System.out.println("数据库读取异常。");
				}
				
		  }
		  db.closeRs();
		  db.closeStm();
		  db.closeConn();
		ControlTable table=new ControlTable();
		CPermit permit=new CPermit();
		
		System.out.println(permit.getControlTableList("www"));
	}
}
