package ECP.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.fabric.xmlrpc.base.Data;

import csAsc.EIO.MsgEngine.CEIOMsgRouter;
import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;

import ECP.dao.JobApplyDao;
import ECP.dao.StudentDao;
import ECP.service.JobInfoService;

public class TestMain {

//	public static void main(String[] args) throws SQLException, JSONException {
//		// TODO Auto-generated method stub
//		JobApplyDao st = new JobApplyDao();
//		JSONObject data = new JSONObject();
//		data.put("userId", "test");
//		data.put("apply_status", "1");
//		//System.out.println(st.query(data));
//		
//		JobInfoService jiService = new JobInfoService();
//		System.out.println(jiService.jobStationList(data));
//		
//	}
	 public static Class[] getParamTypes(Class cls, String mName) 
	 {//获取参数类型，返回值保存在Class[]中
		  Class[] cs = null;
		  Method[] mtd = cls.getDeclaredMethods();// 通过反射机制调用非public方法,使用了getDeclaredMethods()方法    
		  for (int i = 0; i < mtd.length; i++)
		  {
		   if (!mtd[i].getName().equals(mName))
		       continue; // 不是所需要的参数，则进入下一次循环
		   cs = mtd[i].getParameterTypes();
		  }
		  return cs;
	 }
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		Object retObject=null;
		String cName="ECP.handle.MarketHandle";
		String mName="handleMsg";
		CParam params=(new CEIOMsgRouter()).new CParam();	
		Class cls = Class.forName(cName); // 获取Class类的对象的方法
	    // 利用newInstance()方法，获取构造方法的实例
	    // Class的newInstance方法只提供默认无参构造实例
	    // Constructor的newInstance方法提供带参的构造实例
	    Constructor ct = cls.getConstructor(null);
	    Object obj = ct.newInstance(null);   
	    //Object obj = cls.newInstance();
	  
	    //调用本类的方法， 根据方法名获取指定方法的参数类型列表
	    Class paramTypes[] = getParamTypes(cls, mName);
	 
	    // 获取指定方法
	    Method meth = cls.getMethod(mName, paramTypes);
	    meth.setAccessible(true);
	    //System.out.println(params[0]);
	    // 调用类实例obj的指定的方法并获取返回值为Object类型
	    retObject = meth.invoke(obj, params);

	}
}
