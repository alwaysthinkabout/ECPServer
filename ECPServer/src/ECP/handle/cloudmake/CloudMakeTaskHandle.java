package ECP.handle.cloudmake;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import csAsc.EIO.MsgEngine.CEIOMsgRouter.CParam;
import ECP.handle.CMsgBaseHandle;
import ECP.service.cloudmake.CloudMakeBidService;
import ECP.service.cloudmake.CloudMakeTaskService;


public class CloudMakeTaskHandle extends CMsgBaseHandle{
	
	private CloudMakeTaskService cloudMakeTaskService;
	private CloudMakeBidService cloudMakeBidService;
	
	public CloudMakeTaskHandle(){
		cloudMakeTaskService = new CloudMakeTaskService();
		cloudMakeBidService = new CloudMakeBidService();
	}
	
	@Override
	public int handleMsg(CParam param) throws Exception {
		JSONObject data=getReqMessage(param);
		String op=data.getString("op");
		System.out.println("收到的请求信息为："+data.toString());
		JSONObject result=null;
		
		HttpServletRequest msgReq;
		msgReq = param.getMsgReq();
		msgReq.setCharacterEncoding("UTF-8");
		
		switch(op){
		
		case "getReleasableSubtasks":         //获取可发布的子任务集合
			result=cloudMakeTaskService.releasableSubtasks(data);
			break;
		case "getTenderingTasks":             //获取招标中的任务集合
			result=cloudMakeTaskService.tenderingTasks(data);
			break;
		case "getWinBidTasks":                //获取已中标的任务集合
			result=cloudMakeTaskService.winBidTasks(data);
			break;
		case "getComposedTasks":              //获取已合成的任务集合
			result=cloudMakeTaskService.composedTasks(data);
			break;
		case "getMyTasks":                    //获取我接受的任务集合
			result=cloudMakeTaskService.myTasks(data);
			break;
		case "getAllTenderingTasks":          //获取所有招标中的任务集合
			result=cloudMakeTaskService.tenderingTasks(data);
			break;
		case "getSubTasks":                   //获取某任务的子任务集合
			result=cloudMakeTaskService.subTasks(data);
			break;
		case "getOneTask":                    //获取任务详情
			result=cloudMakeTaskService.taskDetail(data);
			break;
		case "getGradeInfo":                  //获取用户评分信息
			result=cloudMakeTaskService.gradeInfo(data);
			break;
		case "newTask":                       //发布新任务
			result=cloudMakeTaskService.taskAdd(data);
			break;
		case "newSubTask":                    //发布子任务
			result=cloudMakeTaskService.taskUpdate(data);
			break;
		case "confirmTask":                   //确认完成一个任务
			result=cloudMakeTaskService.confirmTask(data);
			break;
		case "deleteTask":                    //删除任务数据
			result=cloudMakeTaskService.taskDelete(data);
			break;
		case "addSubTask":                    //添加一个任务分解
			result=cloudMakeTaskService.subTaskAdd(data);
			break;
		case "modSubTask":                    //修改一个任务分解
			result=cloudMakeTaskService.subTaskUpdate(data);
			break;
		case "submitTask":                    //提交任务
			result=cloudMakeTaskService.submitTask(data);
			break;
		case "acceptTask":                    //接受任务
			result=cloudMakeTaskService.acceptTask(data);
			break;
		case "composeTask":                   //合成任务
			result=cloudMakeTaskService.composeTask(data);
			break;
		case "getComposableTasks":            //获取可合成的任务
			result=cloudMakeTaskService.composableTasks(data);
			break;
			
			
		case "getBidToMeInfo":                //获取向我投标的信息
			result=cloudMakeBidService.bidToMeInfo(data);
			break;
		case "getMyBidInfo":                  //获取我的投标信息
			result=cloudMakeBidService.myBidInfo(data);
			break;
		case "submitBid":                     //提交投标申请
			result=cloudMakeBidService.bidAdd(data);
			break;
		case "deleteBid":                     //删除投标数据
			result=cloudMakeBidService.bidDelete(data);
			break;
		case "confirmBid":                    //批准投标申请
			result=cloudMakeBidService.confirmBid(data);
			break;
		case "undoTask":                      //撤销一个招标中的任务
			result=cloudMakeBidService.undoTask(data);
			break;
		default:
			String str = "{\"result\":\"error\",\"resultTip\":\"请求参数出错\"}";
			result = new JSONObject(str);
		}
		param.respData.append(result.toString());
		return super.handleMsg(param);
	}

}
