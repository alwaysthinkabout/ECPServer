package ECP.servlet.cloudmake;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONException;
import org.json.JSONObject;

import ECP.dao.CloudMakeBidDao;
import ECP.dao.CloudMakeTaskDao;


/**
 * 上传云制造文件操作类
 * @author durenshi 
 * @since 2017-05-22
 */

@WebServlet("/cloudmake/CloudMakeFileUpload")   //使用webservlet注解，无需在web.xml上配置
public class CloudMakeFileUpload extends HttpServlet {
    private static final long serialVersionUID = 1L;
     
    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
    
    private CloudMakeTaskDao cloudMakeTaskDao;
    private CloudMakeBidDao cloudMakeBidDao;
 
    public CloudMakeFileUpload() {
        super();
        cloudMakeTaskDao = new CloudMakeTaskDao();
        cloudMakeBidDao = new CloudMakeBidDao();
    }
    /**
     * 上传数据及保存文件
     */
    protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {

    	response.setContentType("text/html;charset=UTF-8");
    	
    	JSONObject retObj = new JSONObject();
		// 检测是否为多媒体上传
		if (!ServletFileUpload.isMultipartContent(request)) {
		    // 如果不是则停止
		    PrintWriter writer = response.getWriter();
		    writer.println("Error: 表单必须包含 enctype=multipart/form-data");
		    writer.flush();
		    return;
		}
 
		
        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
        ServletFileUpload upload = new ServletFileUpload(factory);
        
        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);
         
        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // 中文处理
        upload.setHeaderEncoding("UTF-8"); 

        // 构造临时路径来存储上传的文件
        // 这个路径相对当前应用的目录
        //String uploadPath = request.getServletContext().getRealPath("./") + File.separator + UPLOAD_DIRECTORY;
        String uploadPath = "d:/ECPServerUpload";
 
        PrintWriter writer = response.getWriter();
	    int result = 0;
        try {
            // 解析请求的内容提取文件数据
        	@SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
        	String fileName = "";
        	JSONObject data = new JSONObject();
            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
            	for (FileItem item : formItems) {
                	if (item.isFormField()) {
                    	// 处理普通表单项
                    	data.put(item.getFieldName(), item.getString("UTF-8"));
                    }
                }
            	uploadPath += "/CloudMakeFile";
            	
            	// 如果目录不存在则创建
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                System.out.println("上传文件请求的参数："+data.toString());
                for (FileItem item : formItems) {
                    if (!item.isFormField()) {
                    	Date date = new Date();
                    	String str = "";
                    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                    	str = df.format(date);
                    	String tempName = new File(item.getName()).getName();
                    	fileName += (str+"_"+tempName);
                                   	
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        // 在控制台输出文件的上传路径
                        System.out.println(filePath);
                        // 保存文件到硬盘
                        item.write(storeFile);
                        // 文件数据保存到数据库
                        String downLoadPath = "http://125.216.242.9:8080/ECPServer/download?name="+fileName+"&type=CloudMakeFile";;
                        if (data.getString("op").equals("uploadRequirementDoc")) {
    						data.put("requirement_doc", downLoadPath);
    						result = cloudMakeTaskDao.setRequirementDoc(data);
						}
                        else if (data.getString("op").equals("uploadComposeTemplet")) {
    						data.put("compose_templet", downLoadPath);
    						result = cloudMakeTaskDao.setComposeTemplet(data);
						}
                        else if (data.getString("op").equals("uploadCompleteBody")) {
    						data.put("complete_body", downLoadPath);
    						result = cloudMakeTaskDao.setCompleteBody(data);
						}
                        else if (data.getString("op").equals("uploadQualification")) {
    						data.put("qualification_proof", downLoadPath);
    						result = cloudMakeBidDao.setQualificationProof(data);
						}
                        break;
                    }
                }
            }
            if (result>0) {
            	retObj.put("opResult", "0");
            	retObj.put("resultTip", "上传任务需求文档成功");
			}
            else{
            	retObj.put("opResult", "1");
            	retObj.put("errorMessage", "上传任务需求文档失败");
            }
        } catch (Exception ex) {
        	System.out.println("上传文件异常");
        	try {
				retObj.put("opResult", "1");
				retObj.put("errorMessage", "上传任务需求文档异常");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
	    writer.flush();
	    writer.close();
    }
}