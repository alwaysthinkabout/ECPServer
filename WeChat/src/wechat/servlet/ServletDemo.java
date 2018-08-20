package wechat.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class ServletDemo extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println(request.getParameterNames());
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String reqData = readJSONString(request);//获取前端传过来的数据
		JSONObject data;
		try {
			data = new JSONObject(reqData);
			System.out.println(data.getJSONObject("reqData").getString("op"));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.flush();
		out.close();
	}
    private String readJSONString(HttpServletRequest request){
	   StringBuffer json = new StringBuffer();
	   String line = null;
	   try {
	   BufferedReader reader = request.getReader();
	   while((line = reader.readLine()) != null) {
	   json.append(line);
	   }
	   }
	   catch(Exception e) {
	   System.out.println(e.toString());
	   }
	   return json.toString();
    }
}
