/**
 * 
 */
package csAsc.ECOSS.register;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author wxyyuppie
 *
 */
public class IPLocator {
	public static String getHostIP(){
		InetAddress ia=null;
		try {
			ia=ia.getLocalHost();
			
			String localname=ia.getHostName();
			String localip=ia.getHostAddress();
			//System.out.println("本机名称是："+ localname);
			//System.out.println("本机的ip是 ："+localip);
			return localip;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	 public static String getRealIp() throws SocketException {
	        String localip = null;// 本地IP，如果没有配置外网IP则返回它
	        String netip = null;// 外网IP
	 
	        Enumeration<NetworkInterface> netInterfaces = 
	            NetworkInterface.getNetworkInterfaces();
	        InetAddress ip = null;
	        boolean finded = false;// 是否找到外网IP
	        while (netInterfaces.hasMoreElements() && !finded) {
	            NetworkInterface ni = netInterfaces.nextElement();
	            Enumeration<InetAddress> address = ni.getInetAddresses();
	            while (address.hasMoreElements()) {
	                ip = address.nextElement();
	                if (!ip.isSiteLocalAddress() 
	                        && !ip.isLoopbackAddress() 
	                        && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
	                    netip = ip.getHostAddress();
	                    finded = true;
	                    break;
	                } else if (ip.isSiteLocalAddress() 
	                        && !ip.isLoopbackAddress() 
	                        && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
	                    localip = ip.getHostAddress();
	                }
	            }
	        }
	     
	        if (netip != null && !"".equals(netip)) {
	            return netip;
	        } else {
	            return localip;
	        }
	    }
	
}
