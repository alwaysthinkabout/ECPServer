package ECP.servlet;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ECP.dao.CertificateInfoDao;


/**
 * Servlet implementation class UploadServlet
 * @author durenshi 
 * @since 2017-05-04
 */

@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
     
    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 5;  // 5MB，不能超5MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
    
    private CertificateInfoDao certificateInfoDao;
 
    public UploadServlet() {
        super();
        certificateInfoDao = new CertificateInfoDao();
    }
    /**
     * 上传数据及保存文件
     */
    protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {

    	response.setContentType("text/html;charset=UTF-8");
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
        JSONObject retObject = new JSONObject();
	    int result = 0;
        try {
        	System.out.println("开始上传文件............");
            // 解析请求的内容提取文件数据
        	@SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
        	String fileName = "";
        	JSONObject data = new JSONObject();
        	String[] tempStrings = null;
            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
            	for (FileItem item : formItems) {
                	if (item.isFormField()) {
                    	// 处理普通表单项
                    	/*if (item.getFieldName().equals("data")) {
                    		
                    		System.out.println(item.getString("UTF-8"));
                    		tempStrings = item.getString("utf-8").split(",");
                    		fileName += tempStrings[1]+"_";
                    		uploadPath += File.separator+tempStrings[0];
							break;
						}*/
                		switch(item.getFieldName()){
                		case "data":
                			System.out.println(item.getString("UTF-8"));
                    		tempStrings = item.getString("utf-8").split(",");
                    		fileName += tempStrings[1]+"_";
                    		uploadPath += File.separator+tempStrings[0];
							break;
                		case "data1":
                			System.out.println(item.getFieldName()+":"+item.getString("UTF-8"));
                			break;
                		default:
                			System.out.println("还有一些内容"+item.getFieldName()+":"+item.getString("UTF-8"));
                		}
                			
                    }
                }
            	
            	//判断是否已经上传相关文件，若有则在客户端删除再上传
            	JSONObject fileData = new JSONObject();
            	//个人经历文件可多次上传,其余的不行
            	if (!tempStrings[0].equals("proveFile") && !tempStrings[0].equals("recommendFile")) {
            		fileData.put("userId", tempStrings[1]);
                	fileData.put("file", tempStrings[0]);
                	JSONArray certArray = certificateInfoDao.query(fileData);
                	if (certArray.length() > 0) {
                		writer.println("已上传过文件，请删除原文件再进行上传操作");
                		return ;
    				}
				}
            	
            	// 如果目录不存在则创建
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                
                for (FileItem item : formItems) {
                    if (!item.isFormField()) {
                    	Date date = new Date();
                    	String str = "";
                    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                    	str = df.format(date);
                    	String tempName = new File(item.getName()).getName();
                    	fileName += (str+"_"+tempName);
                    	//判断文件类型是否符合要求
                    	String tip = isCorrect(tempStrings[0], fileName);
                    	if (!tip.equals("0")) {
                        	retObject.put("resultTip", tip);
                        	retObject.put("result", "1");
                        	writer.println(retObject.toString());
                        	System.out.println(retObject.toString());
                    		return ;
						}
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        // 在控制台输出文件的上传路径
                        System.out.println(filePath);
                        // 保存文件到硬盘
                        item.write(storeFile);
                        // 文件数据保存到数据库
                        data.put("cert_name", tempStrings[0]);
						data.put("user_id", tempStrings[1]);
						data.put("cert_path", fileName);
						result = certificateInfoDao.insert(data);
                        break;
                    }
                }
            }
            if (result>0) {
            	retObject.put("data", String.valueOf(result));
            	retObject.put("fileName", fileName);
            	retObject.put("resultTip", "文件上传成功");
            	retObject.put("result", "0");
            	writer.println(retObject.toString());
			}
            else{
            	retObject.put("data", "");
            	retObject.put("fileName", "");
            	retObject.put("resultTip", "文件上传失败");
            	retObject.put("result", "1");
            	writer.println(retObject.toString());
            }
        } catch (Exception ex) {
        	System.out.println("上传文件异常");
        	try {
				retObject.put("resultTip", "上传文件异常，文件大小不要超过5MB");
				retObject.put("result", "1");
	        	writer.println(retObject.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
	    writer.flush();
	    writer.close();
    }
    
    private String isCorrect(String fileType, String fileName){
    	String extName = "";
    	if(fileType.equals("resumeFile") || fileType.equals("proveFile") || fileType.equals("recommendFile")){
    		extName = fileName.substring(fileName.indexOf(".") + 1).toLowerCase().trim();
    		if(!extName.equals("pdf") && !extName.equals("docx") && !extName.equals("doc")){
        		return "文件类型错误，简历文件后缀必须为pdf、docx或doc格式！";
    		}
    	}   
    	
    	else{
    		extName = fileName.substring(fileName.indexOf(".") + 1).toLowerCase().trim();
    		if(!extName.equals("png") && !extName.equals("gif") && !extName.equals("jpg")){
        		return "文件类型错误，文件后缀必须为png、gif或jpg格式！";
    		}
    	}
    	return "0";
    }
}