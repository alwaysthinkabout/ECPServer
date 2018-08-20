package wechat.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MsgMethodCaller {
	public Object loadExec(String cName, String mName, Object[] params){
		//在运行时加载指定的类，并调用指定的方法,返回泛类型Object型值（需要调用者强制转换为实际类型）
	    //cName: 要调用的方法所属的Java的类名
	    // mName：要调用的方法名
	    // params：方法的参数值     	 
		System.out.println("进入loadExec 类名="+cName + " 方法名="+mName);
		Object retObject = null;
		try
		  {// 加载指定的类
		   Class cls = Class.forName(cName); // 获取Class类的对象的方法
		   // 利用newInstance()方法，获取构造方法的实例
		   // Class的newInstance方法只提供默认无参构造实例
		   // Constructor的newInstance方法提供带参的构造实例
		   //Constructor ct = cls.getConstructor(null);
		   //Object obj = ct.newInstance(null);    
		   Object obj = cls.newInstance();
		   

		   
		   //调用本类的方法， 根据方法名获取指定方法的参数类型列表
		   //Class paramTypes[] = this.getParamTypes(cls, mName);
		  
		   // 获取指定方法
		   //Method meth = cls.getMethod(mName, paramTypes);
		   Class[] argsClass = new Class[params.length];    		     
		   for (int i = 0, j = params.length; i < j; i++) {    
		       argsClass[i] = params[i].getClass();    
		   }   
		   Method meth = cls.getMethod(mName, argsClass);
		   meth.setAccessible(true);
		   //System.out.println(params[0]);
		   // 调用类实例obj的指定的方法并获取返回值为Object类型
		   retObject = meth.invoke(obj, params);		  
		  } catch (InvocationTargetException e){  
			  System.err.println(e.getTargetException()); System.err.println("  调用出错，请检查该方法是否存在，是否可以运行: "+cName+"."+mName);   
			}
		  catch (Exception e){  
			  System.err.println(e); System.err.println("  调用出错，请检查该方法是否存在，是否可以运行: "+cName+"."+mName);   
			}
		  
		return retObject;
	}
	//该方法是根据Class类和方法名来获取该方法的参数列表，但并没有在上面使用，因为参数列表是已知的
	public Class[] getParamTypes(Class cls, String mName) {
		//获取参数类型，返回值保存在Class[]中
	    Class[] cs = null;
	    Method[] mtd = cls.getDeclaredMethods();// 通过反射机制调用非public方法,使用了getDeclaredMethods()方法    
		for (int i = 0; i < mtd.length; i++){
		if (!mtd[i].getName().equals(mName))
	       continue; // 不是所需要的参数，则进入下一次循环
	   	   cs = mtd[i].getParameterTypes();
		}
		return cs;
	}

}

