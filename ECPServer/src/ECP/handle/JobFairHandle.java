package ECP.handle;

import org.json.JSONObject;

import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;
import ECP.service.JobFairService;

/**
 * @Description 
 * Created by durenshi on 2017-5-2
 */
public class JobFairHandle extends CMsgBaseHandle {

	private JobFairService jobFairService;
	
	public JobFairHandle(){
		jobFairService = new JobFairService();
	}
	
	@Override
	public int handleMsg(CParam param) throws Exception {
		JSONObject data=getReqMessage(param);
		String op=data.getString("op");
		JSONObject result=null;
		
		switch(op){
        //安卓端接口
		case "getJobFairList":            //招聘会列表
			result=jobFairService.jobFairList(data);
			break;
		case "getJobFairDetail":          //招聘会详情
			result=jobFairService.JobFairDetail(data);
			break;
		default:
			String str = "{\"result\":\"error\",\"resultTip\":\"请求参数出错\"}";
			result = new JSONObject(str);
		}
		param.respData.append(result.toString());
		return super.handleMsg(param);
	}
}
