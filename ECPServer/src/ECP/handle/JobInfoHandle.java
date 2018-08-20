package ECP.handle;

import org.json.JSONObject;

import ECP.service.JobInfoService;
import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;

/**
 * @Description 
 * Created by durenshi on 2017-4-26
 */
public class JobInfoHandle extends CMsgBaseHandle {
	private JobInfoService jobInfoService;
	
	public JobInfoHandle(){
		jobInfoService = new JobInfoService();
	}
	
	@Override
	public int handleMsg(CParam param) throws Exception {
		JSONObject data=getReqMessage(param);
		System.out.println("收到的请求信息为："+data.toString());
		String op=data.getString("op");
		JSONObject result=null;
		
		switch(op){
		//安卓端接口
		case "getJobStationList":           //招聘站列表
			result=jobInfoService.jobStationList(data);
			break;
		case "getJobStationDetail":         //招聘站详情
			result=jobInfoService.jobStationDetail(data);
			break;
		case "getWorkDetatil":              //岗位详情
			result=jobInfoService.jobDetatil(data);
			break;
		case "searchJob":                   //岗位搜索
			result=jobInfoService.jobSearch(data);
			break;
		case "getHotWorkList":              //热门岗位列表
			result=jobInfoService.hotJobSearch(data);
			break;
		case "getSortWorkList":             //岗位分类查看
			result=jobInfoService.sortWorkList(data);
			break;
		
		//浏览器端接口
		case "listWork":        //岗位列表
			result=jobInfoService.jobList(data);
			break;
		case "addWork":         //添加岗位
			result=jobInfoService.jobAdd(data);
			break;
		case "deleteWork":      //删除岗位
			result=jobInfoService.jobDelete(data);
			break;
		case "updateWork":      //更新岗位
			result=jobInfoService.jobUpdate(data);
			break;
		case "loadWorkInfo":    //岗位详情
			result=jobInfoService.jobInformation(data);
			break;
		case "jobTypeList":     //岗位分类信息
			result=jobInfoService.jobTypeList(data);
			break;
		case "jobTypeSecondList":     //岗位二级分类信息
			result=jobInfoService.jobTypeSecondList(data);
			break;
		default:
			String str = "{\"result\":\"error\",\"resultTip\":\"请求参数出错\"}";
			result = new JSONObject(str);
		}
		param.respData.append(result.toString());
		return super.handleMsg(param);
	}
}
