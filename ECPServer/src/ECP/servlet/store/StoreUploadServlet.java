package ECP.servlet.store;

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
import ECP.dao.StoreDao;


/**
 * Servlet implementation class UploadServlet
 * @author durenshi 
 * @since 2017-09-04
 */

@WebServlet("/StoreUploadServlet")
public class StoreUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
     
    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 5;  // 5MB，不能超5MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
    
    private CertificateInfoDao certificateInfoDao;
    private StoreDao storeDao;
 
    public StoreUploadServlet() {
        super();
        certificateInfoDao = new CertificateInfoDao();
        storeDao=new StoreDao();
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
        	JSONObject tempData=new JSONObject();
        	String[] tempStrings = new String[2];;
        	String downLoadPath= null;//文件下载路径，最多3项。
        	tempStrings[0]="store";
        	String opflag=null;//用来判断操作类型
        	//用来判断新增还是修改操作。通过判断storeId是否为空。
            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
            	for (FileItem item : formItems) {
                	if (item.isFormField()) {
                    	// 处理普通表单项
                		tempData.put(item.getFieldName(), item.getString("UTF-8"));
                		System.out.println(item.getFieldName()+":"+item.getString("UTF-8"));
                		if(item.getFieldName().equals("user_id")){
                			tempStrings[1]=item.getString("UTF-8");
                		}else if(item.getFieldName().equals("op")){
                			opflag=item.getString("UTF-8");
                		}
                    }
                }
            	uploadPath += "/storeFile";
            	//判断是否已经上传相关文件，若有则在客户端删除再上传
            	JSONObject fileData = new JSONObject();
            	//个人经历文件可多次上传,其余的不行
            	/*if (!tempStrings[0].equals("proveFile") && !tempStrings[0].equals("recommendFile")) {
            		fileData.put("userId", tempStrings[1]);
                	fileData.put("file", tempStrings[0]);
                	JSONArray certArray = certificateInfoDao.query(fileData);
                	if (certArray.length() > 0) {
                		writer.println("已上传过文件，请删除原文件再进行上传操作");
                		return ;
    				}
				}*/
//            	if(findShopPhoto()){
//            		writer.println("已上传过文件，请删除原文件再进行上传操作");
//            		return ;
//            	}
            	
            	// 如果目录不存在则创建
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                int i=0;
                for (FileItem item : formItems) {
                    if (!item.isFormField()) { //处理非表单项。
                    	
                    	Date date = new Date();
                    	String str = "";
                    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                    	str = df.format(date);
                    	String tempName = new File(item.getName()).getName();
                    	fileName = (str+"_"+tempName);
                    	//判断文件类型是否符合要求
                    	String tip = isCorrect(tempStrings[0], fileName);
                    	/*if (!tip.equals("0")) {
                        	retObject.put("resultTip", tip);
                        	retObject.put("result", "1");
                        	writer.println(retObject.toString());
                        	System.out.println(retObject.toString());
                    		return ;
						}*/
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        // 在控制台输出文件的上传路径
                        System.out.println(filePath);
                        // 保存文件到硬盘
                        item.write(storeFile);
                        // 文件数据保存到数据库。这里的路径写死了，以后改。
                        downLoadPath = "http://222.201.145.241:80/ECPServer/download?name="+fileName+"&type=storeFile";
                        System.out.println(downLoadPath);
                        System.out.println("非表单项："+item.getFieldName());
                        /*if(opflag.equals("setStoreInfo")){
                        	tempData.put("shop_logo", downLoadPath);
//                        	result=storeDao.setStoreInfo(tempData).getInt("store_id");  
                        }else if(opflag.equals("setQualification")){
                        	//不知道多个文件能不能同时上传，上传过程中会不会保持顺序。
*/                      
                        switch(item.getFieldName()){
                    	case "store_Logo":
                    		tempData.put("store_Logo", downLoadPath);
                    		System.out.println(tempData.toString());
                    		break;
                    	case "identity_Photo":
                    		tempData.put("identity_Photo", downLoadPath);
                    		break;
                    	case "license_Photo":
                    		tempData.put("license_Photo", downLoadPath);
                    		break;
                    	case "permit_Photo":
                    		tempData.put("permit_Photo", downLoadPath);
                    		break;
                    	case "goodPic":
                    		JSONArray goodDArray=new JSONArray(tempData.getString("goodData"));
                    		goodDArray.getJSONObject(0).put("goodPic", downLoadPath);                    		
                    		tempData.put("goodData", goodDArray);
                    		break;
                    	default:
                    		System.out.println("没有匹配项");
                    		break;
                    	}                       
                    }                   
                }
                if(opflag.equals("setStoreInfo")){                    	
                	result=storeDao.setStoreInfo(tempData).getInt("data");  
                }else if(opflag.equals("setQualification")){  
                	System.out.println("***:"+tempData.toString());
                	result=storeDao.setQualification(tempData);
                }else if(opflag.equals("setSubmitGoodsIformation")){   
                	System.out.println("***:"+tempData.toString());
                	result=storeDao.setSubmitGoodsIformation(tempData).getInt("data");
                }else if(opflag.equals("setEditGoodsIformation")){
                	JSONObject tempDataObject=new JSONObject();
                	tempDataObject=tempData.getJSONArray("goodData").getJSONObject(0);
                	tempDataObject.put("storeId", tempData.get("storeId"));                	
                	result=storeDao.setEditGoodsIformation(tempDataObject).getInt("data");
                }
            }
            if (result>0) {
//            	retObject.put("data", String.valueOf(result));
            	retObject.put("data", String.valueOf(result));
            	retObject.put("fileName", fileName);
            	retObject.put("resultTip", "文件上传成功");
            	retObject.put("result", "0");
            	writer.println(retObject.toString());
			}
            else if(result==-1){
            	retObject.put("data", "");
            	retObject.put("fileName", fileName);
            	retObject.put("resultTip", "更新文件成功");
            	retObject.put("result", "0");
            	writer.println(retObject.toString());
            }else{
            	retObject.put("data", "");
            	retObject.put("fileName", "");
            	retObject.put("resultTip", "文件上传失败");
            	retObject.put("result", "1");
            	writer.println(retObject.toString());
            }
        } catch (Exception ex) {
        	System.out.println("上传文件异常");
        	ex.printStackTrace();
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
    
    private boolean findShopPhoto() {
		// TODO Auto-generated method stub
		return false;
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