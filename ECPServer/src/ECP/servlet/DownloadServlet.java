package ECP.servlet;
 
import java.io.FileInputStream;  
import java.io.IOException;  
import java.io.InputStream;

import javax.servlet.ServletException;  
import javax.servlet.ServletOutputStream;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
  

/**
* durenshi 2017-05-04
*/
public class DownloadServlet extends HttpServlet {  
  

	private static final long serialVersionUID = 1L;

	public DownloadServlet() {  
        super();  
    }  
  
  
    public void destroy() {  
        super.destroy(); // Just puts "destroy" string in log  
        // Put your code here  
    }  
  
  
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
	    //获取文件名
	    String filename=request.getParameter("name");
	    String filetype=null;
	    System.out.println("filename.indexOf(^)=="+filename.indexOf("^"));
	    if(filename.indexOf("^")!=-1){
	    	String []strs=filename.split("\\^");
	    	System.out.println("strs.length=="+strs.length);
	    	filetype=strs[strs.length-1];
	    	filename=strs[0];
	    }else{
	    	filetype=request.getParameter("type");
	    }
	    //防止读取name名乱码
	    filename=new String(filename.getBytes("iso-8859-1"),"utf-8");
	    //在控制台打印文件名
	    System.out.println("文件名："+filename);
	    
	     //设置文件MIME类型  
	    response.setContentType(getServletContext().getMimeType(filename));  
	    //设置Content-Disposition  
	    try {
			response.setHeader("Content-Disposition", "attachment;filename="+getStr(request, filename));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    response.setCharacterEncoding("utf-8"); 
	    //获取要下载的文件绝对路径
	    String fullFileName="d:/ECPServerUpload/"+filetype+"/"+filename;
	    System.out.println(fullFileName);
	    
	    //输入流为项目文件，输出流指向浏览器
	    InputStream is=new FileInputStream(fullFileName);
	    ServletOutputStream os = response.getOutputStream();
	    
	    /*
	     * 设置缓冲区
	     * is.read(b)当文件读完时返回-1
	     */
	    int len=-1;
	    byte[] b=new byte[1024];
	    while((len=is.read(b))!=-1){
	        os.write(b,0,len);
	    }
	    //关闭流
	    is.close();
	    os.close();
}
  
      
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        doGet(request, response);
            
          
    }  
  
    /** 
     * Initialization of the servlet. <br> 
     * 
     * @throws ServletException if an error occurs 
     */  
    public void init() throws ServletException {  
        // Put your code here  
    }  
  
    //设置浏览器文件名编码
    private String getStr(HttpServletRequest request, String realFileName) throws Exception {
    	String browName = null;
    	String clientInfo = request.getHeader("User-agent");
    	System.out.println(clientInfo);
    	if (clientInfo != null && clientInfo.indexOf("MSIE") > 0) {//
    		   // IE采用URLEncoder方式处理
    		if (clientInfo.indexOf("MSIE 6") > 0 || clientInfo.indexOf("MSIE 5") > 0) {
    		  // IE6，用GBK，此处实现由局限性
    			browName = new String(realFileName.getBytes("GBK"),
    		      "ISO-8859-1");
    		} else {// ie7+用URLEncoder方式
    		    browName = java.net.URLEncoder.encode(realFileName, "UTF-8");
    		}
    	} else {//其他浏览器
    		browName = new String(realFileName.getBytes("GBK"), "ISO-8859-1");
    	}
    	return browName;
    } 
}  
