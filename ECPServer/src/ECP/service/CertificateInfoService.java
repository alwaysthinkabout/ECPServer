package ECP.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ECP.dao.CertificateInfoDao;
import ECP.util.helper.FileOperate;

/**
 * @Description 
 * Created by durenshi on 2017-05-05
 */
public class CertificateInfoService {
	
	private CertificateInfoDao certificateInfoDao;
	
	public CertificateInfoService(){
		certificateInfoDao = new CertificateInfoDao();
	}
	
	public JSONObject deletePersonalFile(JSONObject data){
		JSONObject result = new JSONObject();
		JSONArray fileObj = new JSONArray();
		int ret = 0;
		fileObj = certificateInfoDao.query(data);
		ret = certificateInfoDao.delete(data);
		try {
			if (ret>0) {
				int flag = 0;
				for (int i = 0; i < fileObj.length(); i++) {
					String filePath = "d:/ECPServerUpload/"
					+fileObj.getJSONObject(i).getString("cert_name")+"/"+fileObj.getJSONObject(i).getString("cert_path");
					
					System.out.println(filePath);
					if(FileOperate.deleteFile(filePath)){
						flag = 1;
					}
					else {
						break;
					}
				}
				if (flag == 1) {
					result.put("result", "0");
					result.put("resultTip", "删除文件成功");
				}
			}
			else {
				result.put("result", "1");
				result.put("resultTip", "删除文件失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	} 
	
	public JSONObject personalFileState(JSONObject data) {
		JSONObject result = new JSONObject();	
		JSONArray tempArray = certificateInfoDao.findByUserId(data);
		JSONObject tempObject = new JSONObject();
		try {
			for (int i = 0; i < tempArray.length(); i++) {
				tempObject.put(tempArray.getJSONObject(i).getString("cert_name"), tempArray.getJSONObject(i).getString("cert_path"));
			}
			if (!tempObject.has("resumeFile")) {
				tempObject.put("resumeFile", "");
			}
			if (!tempObject.has("pictureFile")) {
				tempObject.put("pictureFile", "");
			}
			if (!tempObject.has("educationFile")) {
				tempObject.put("educationFile", "");
			}
			if (!tempObject.has("degreesFile")) {
				tempObject.put("degreesFile", "");
			}
			if (!tempObject.has("titleFile")) {
				tempObject.put("titleFile", "");
			}
			if (!tempObject.has("qualificationFile")) {
				tempObject.put("qualificationFile", "");
			}
			if (!tempObject.has("otherFile")) {
				tempObject.put("otherFile", "");
			}
			if (!tempObject.has("recommendFile")) {
				tempObject.put("recommendFile", "");
			}
			result.put("result", "0");
			result.put("resultTip", "获取成功");
			result.put("data", tempObject);
		} catch (JSONException e) {
			e.printStackTrace();

		}
		return result;
	}
	
}
