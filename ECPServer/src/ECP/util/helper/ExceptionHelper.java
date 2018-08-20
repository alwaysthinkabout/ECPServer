package ECP.util.helper;

import org.json.JSONException;
import org.json.JSONObject;

public class ExceptionHelper {
	private JSONObject errorResult;
	
	public ExceptionHelper(String error){
		this.errorResult = new JSONObject();
		try {
			this.errorResult.put("result", "fail");
			this.errorResult.put("resultTip", error);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			System.out.println("自定义异常类构造错误...");
		}
		
	}

	public JSONObject getErrorResult() {
		return errorResult;
	}

	public void setErrorResult(JSONObject errorResult) {
		this.errorResult = errorResult;
	}

	
	
}
