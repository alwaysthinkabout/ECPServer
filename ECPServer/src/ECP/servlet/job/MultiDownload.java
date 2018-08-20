package ECP.servlet.job;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ECP.dao.CertificateInfoDao;

/** 
 *  文件打包下载接口
 *  @durenshi 2017-6-17
 */ 

@WebServlet("/job/downloads")
public class MultiDownload extends HttpServlet {  
	/** 
     *  
     */  
    private static final long serialVersionUID = -1379339763454618902L;  
    
    private CertificateInfoDao certificateInfoDao;
  
    public MultiDownload() {  
        super();  
        certificateInfoDao = new CertificateInfoDao();
    }  
      
    /** 
     * doPost提交方式 
     */  
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        this.doGet(request, response);  
    }  
      
    /** 
     * doGet提交方式 
     */  
    //http://localhost:8080/ECPServer/job/downloads?userId=26
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {  
        response.setContentType("APPLICATION/OCTET-STREAM");  
        response.setHeader("Content-Disposition", "attachment; filename="+ this.getZipFilename());  
        System.out.println("文件打包下载................");  
        ZipOutputStream zos = new ZipOutputStream(response.getOutputStream()); 
        String uid = request.getParameter("userId");
        JSONArray certsArray = new JSONArray();
        JSONObject data = new JSONObject();
        try {
			data.put("userId", uid);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        certsArray = certificateInfoDao.findByUserId(data);
        
        File[] files = new File[certsArray.length()]; 
        String filepath = "D:/ECPServerUpload/";
        for (int i = 0; i < files.length; i++) {
        	String fileString = "";
			try {
				fileString += filepath+certsArray.getJSONObject(i).getString("cert_name")
						+"/"+certsArray.getJSONObject(i).getString("cert_path");
				System.out.println(fileString);
				files[i] = new File(fileString);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        for(File f:files){  
            zipFile(f, "", zos);  
        }  
        zos.flush();  
        zos.close();  
    }  
      
    /** 
     * 使用递归进行文件夹,文件的扫描 
     */  
    private void zipFile(File subs, String baseName, ZipOutputStream zos)throws IOException {  
        if(subs.exists()){  
            if(subs.isFile()){  
                zos.putNextEntry(new ZipEntry(baseName + subs.getName()));  
                FileInputStream fis = new FileInputStream(subs);  
                byte[] buffer = new byte[1024];  
                int r = 0;  
                while ((r = fis.read(buffer)) != -1) {  
                    zos.write(buffer, 0, r);  
                }  
                fis.close();  
            }else{  
                //如果是目录。递归查找里面的文件  
                String dirName = baseName + subs.getName() + "/";  
                zos.putNextEntry(new ZipEntry(dirName));  
                File[] sub = subs.listFiles();  
                for (File f : sub) {  
                    zipFile(f, dirName, zos);  
                }  
            }  
        }  
      
    }  
  
    /** 
     * 获取zip文件名 
     * @return 
     */  
    private String getZipFilename() {  
        Date date = new Date();  
        String s = date.getTime() + ".zip";  
        return s;  
    }  
  
}