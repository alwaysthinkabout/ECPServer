package wechat.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MsgMethodCaller {
	public Object loadExec(String cName, String mName, Object[] params){
		//������ʱ����ָ�����࣬������ָ���ķ���,���ط�����Object��ֵ����Ҫ������ǿ��ת��Ϊʵ�����ͣ�
	    //cName: Ҫ���õķ���������Java������
	    // mName��Ҫ���õķ�����
	    // params�������Ĳ���ֵ     	 
		System.out.println("����loadExec ����="+cName + " ������="+mName);
		Object retObject = null;
		try
		  {// ����ָ������
		   Class cls = Class.forName(cName); // ��ȡClass��Ķ���ķ���
		   // ����newInstance()��������ȡ���췽����ʵ��
		   // Class��newInstance����ֻ�ṩĬ���޲ι���ʵ��
		   // Constructor��newInstance�����ṩ���εĹ���ʵ��
		   //Constructor ct = cls.getConstructor(null);
		   //Object obj = ct.newInstance(null);    
		   Object obj = cls.newInstance();
		   

		   
		   //���ñ���ķ����� ���ݷ�������ȡָ�������Ĳ��������б�
		   //Class paramTypes[] = this.getParamTypes(cls, mName);
		  
		   // ��ȡָ������
		   //Method meth = cls.getMethod(mName, paramTypes);
		   Class[] argsClass = new Class[params.length];    		     
		   for (int i = 0, j = params.length; i < j; i++) {    
		       argsClass[i] = params[i].getClass();    
		   }   
		   Method meth = cls.getMethod(mName, argsClass);
		   meth.setAccessible(true);
		   //System.out.println(params[0]);
		   // ������ʵ��obj��ָ���ķ�������ȡ����ֵΪObject����
		   retObject = meth.invoke(obj, params);		  
		  } catch (InvocationTargetException e){  
			  System.err.println(e.getTargetException()); System.err.println("  ���ó�������÷����Ƿ���ڣ��Ƿ��������: "+cName+"."+mName);   
			}
		  catch (Exception e){  
			  System.err.println(e); System.err.println("  ���ó�������÷����Ƿ���ڣ��Ƿ��������: "+cName+"."+mName);   
			}
		  
		return retObject;
	}
	//�÷����Ǹ���Class��ͷ���������ȡ�÷����Ĳ����б�����û��������ʹ�ã���Ϊ�����б�����֪��
	public Class[] getParamTypes(Class cls, String mName) {
		//��ȡ�������ͣ�����ֵ������Class[]��
	    Class[] cs = null;
	    Method[] mtd = cls.getDeclaredMethods();// ͨ��������Ƶ��÷�public����,ʹ����getDeclaredMethods()����    
		for (int i = 0; i < mtd.length; i++){
		if (!mtd[i].getName().equals(mName))
	       continue; // ��������Ҫ�Ĳ������������һ��ѭ��
	   	   cs = mtd[i].getParameterTypes();
		}
		return cs;
	}

}

