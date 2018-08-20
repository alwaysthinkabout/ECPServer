package ECP.service;

import java.util.HashSet;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import ECP.dao.JobFairCompanyDao;
import ECP.dao.JobFairDao;

public class JobFairService {
	private JobFairDao jobFairDao;
	private JobFairCompanyDao jobFairCompanyDao;
	
	public JobFairService(){
		jobFairDao = new JobFairDao();
		jobFairCompanyDao = new JobFairCompanyDao();
	}
	
	public JSONObject jobFairList(JSONObject data){
		JSONObject result = new JSONObject();
		JSONArray jobFairArray = jobFairDao.query(data);
		try {
			result.put("result", "0");
			result.put("resultTip", "招聘会列表数据获取成功");
			result.put("rows", jobFairArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject JobFairDetail(JSONObject data){
		JSONObject result = new JSONObject();
		JSONArray jobFairArray = new JSONArray();
		JSONArray workListArray = new JSONArray();
		JSONArray stationArray = new JSONArray();
		JSONObject redata = new JSONObject();
		try {
			if (data.has("fairId")) {
				jobFairArray = jobFairDao.findById(Integer.parseInt(data.getString("fairId")));
				result.put("result", "0");
				result.put("resultTip", "招聘会详细数据获取成功");
				redata = jobFairArray.getJSONObject(0);
				result.put("data", redata);
				String jobFairCode = jobFairDao.findJobFairCode(Integer.parseInt(data.getString("fairId")));
				workListArray = jobFairCompanyDao.findById(jobFairCode);
				HashSet <Station>sh = new HashSet <Station>();  
				for (int i = 0; i < workListArray.length(); i++) {
					sh.add(new Station(workListArray.getJSONObject(i).getString("stationId"),
							workListArray.getJSONObject(i).getString("stationName"),
							workListArray.getJSONObject(i).getString("exhibitionNum"))); 
				}
				int hsIndex = 0;
				for(Iterator<Station> it = sh.iterator();it.hasNext();)  { 
					JSONArray temp = new JSONArray();
		            Station p  = (Station)it.next();  
		            int index = 0;
		            for (int i = 0; i < workListArray.length(); i++) {
						if (workListArray.getJSONObject(i).getString("stationId").equals(p.getStationId())) {
							temp.put(index,workListArray.getJSONObject(i));
							index++;
							
						}
					}
		            JSONObject tempObject = new JSONObject();
					tempObject.put("workList", temp);
					tempObject.put("stationId", p.getStationId());
					tempObject.put("stationName", p.getStationName());
					tempObject.put("exhibitionNum", p.getExhibitionNum());
					stationArray.put(hsIndex,tempObject);
					hsIndex++;
		        }  
				redata.put("stationList", stationArray);
			}
			else {
				result.put("result", "1");
				result.put("resultTip", "获取招聘会详情失败，可能缺失fairId或者fairId类型错误，请确认..");
				result.put("rows", "");
			}
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		return result;
	}
}

class Station  {  
    private String stationId;  
    private String stationName;  
    private String exhibitionNum;
    Station(String stationId,String stationName,String exhibitionNum)  {  
        this.stationId = stationId;  
        this.stationName = stationName;  
        this.exhibitionNum = exhibitionNum;
    }  
    
    public int hashCode()  {  
        return stationId.hashCode();  
    }  
    
    public boolean equals(Object obj){  
        if(!(obj instanceof Station))  
            return false;  
        Station p = (Station)obj;  
        return this.stationId.equals(p.stationId)&&this.stationName.equals(p.stationName)
        		&&this.exhibitionNum.equals(p.exhibitionNum);  
    }  
    
    public String getStationId(){
    	return stationId;
    }
    
    public String getStationName(){
    	return stationName;
    }
    
    public String getExhibitionNum(){
    	return exhibitionNum;
    }
}  
