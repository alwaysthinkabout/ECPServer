package ECP.servlet.job;

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
import org.json.JSONObject;

import ECP.dao.CertificateInfoDao;
import ECP.dao.OrgAuditDao;
import ECP.dao.OrgStateDao;
import ECP.dao.OrganizationInfoDao;
import ECP.model.OrgStateAudit;
import ECP.servlet.fileView.DocConverter;


/**
 * 上传企业资料文件操作类（企业高级信息）
 * @author durenshi 
 * @since 2017-05-22
 */

@WebServlet("/job/OrgFileUpload")
public class OrgFileUpload extends HttpServlet {
    private static final long serialVersionUID = 1L;
     
    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
    
    private CertificateInfoDao certificateInfoDao;
    private OrganizationInfoDao organizationInfoDao;
    private OrgStateDao orgStateDao;
    private OrgAuditDao orgAuditDao;
 
    public OrgFileUpload() {
        super();
        certificateInfoDao = new CertificateInfoDao();
        organizationInfoDao = new OrganizationInfoDao();
        orgStateDao = new OrgStateDao();
        orgAuditDao = new OrgAuditDao();
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
                    	System.out.println("表单内容：---------------------------");
                    	System.out.println(item.getFieldName()+":"+item.getString("UTF-8"));
                    }
                }
            	uploadPath += "/OrgFile";
            	
            	
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
                                   	
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        // 在控制台输出文件的上传路径
                        System.out.println(filePath);
                        // 保存文件到硬盘
                        item.write(storeFile);
                        System.out.println("fileName.indexOf(.docx)=="+(fileName.indexOf(".docx")!=-1));
                        if((fileName.indexOf(".pdf")!=-1)||(fileName.indexOf(".doc")!=-1)||(fileName.indexOf(".docx")!=-1)||(fileName.indexOf(".xls")!=-1)||(fileName.indexOf(".xlsx")!=-1)||(fileName.indexOf(".txt")!=-1)){
                            DocConverter dc=new DocConverter(filePath);
                            dc.convert();
                        }
                        // 文件数据保存到数据库
						data.put("cert_path", fileName);
						result = certificateInfoDao.insert(data);
						if (data.has("org_id")||data.has("state_id")) {
							data.put("cid", result);
							String url = "/ECPServer/download?type=OrgFile&name="+fileName;
							data.put("url", url);
							if (data.getString("uploadType").equals("orgInfo")) {
								result = organizationInfoDao.insertIntoOrgCert(data);
//								orgAuditDao.insertOrgCertAudit(data);
								JSONArray temp = orgAuditDao.findByOrgIdRecent(data);
								if(temp.length()>0){
									JSONObject tObject = temp.getJSONObject(0);
									if(tObject.getString("audit_status").equals("未审核")){
										data.put("audit_id", tObject.getString("audit_id"));
										orgAuditDao.updateOrgAuditCertInfo(data);
									}
									else{
										orgAuditDao.insertOrgInfoAudit(data);
									}
								}
							}
							else if (data.getString("uploadType").equals("stateInfo")) {
								result = orgStateDao.insertIntoOrgStateCert(data);
								data.put("stateIds", data.getString("state_id"));
								JSONArray temp = orgAuditDao.findByStateIdRecent(data);
								if(temp.length()>0){
									JSONObject tObject = temp.getJSONObject(0);
									if(tObject.getString("audit_status").equals("未审核")){
										data.put("audit_id", tObject.getString("audit_id"));
										orgAuditDao.updateStateAuditCertInfo(data);
									}
									else{
										OrgStateAudit org = new OrgStateAudit();
										org.setOperationReason("新上传证明材料");
										org.setOrgStateId(data.getString("state_id"));
										orgAuditDao.insertOrgStateAudit(org);
									}
								}
							}
						}
                        break;
                    }
                }
            }
            if (result>0) {
            	writer.println("文件上传成功");
			}
            else{
            	writer.println("上传文件失败");
            }
        } catch (Exception ex) {
        	System.out.println("上传文件异常");
        	writer.println("上传文件异常，请选择要上传的文件");
        }
	    writer.flush();
	    writer.close();
    }
}