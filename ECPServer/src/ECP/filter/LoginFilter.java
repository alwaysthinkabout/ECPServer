package ECP.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		 // 获得在下面代码中要用的request,response,session对象
		 HttpServletRequest servletRequest = (HttpServletRequest) request;
		 HttpServletResponse servletResponse = (HttpServletResponse) response;
		 Cookie[] cookies=servletRequest.getCookies();
		// 获得用户请求的URI
		 String path = servletRequest.getRequestURI();
		 System.out.println(path);
		 
		 String userId=getCookie(cookies,"userId");
		 
		// 登陆页面无需过滤
		 if(path.indexOf("/login.jsp")>-1||path.indexOf("/userRegisterPage.jsp")>-1||path.indexOf("/findPasswordPage.jsp")>-1){
			 chain.doFilter(servletRequest, servletResponse);
			 return;
		 }
		 
		// 判断如果没有取到员工信息,就跳转到登陆页面
		 if(userId==null||"".equals(userId)){
			 //跳转到登录页面
			 servletResponse.sendRedirect("../../login.jsp");
		 }else{
			 //已经登录
			 chain.doFilter(request, response);
		 }
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	public String getCookie(Cookie[] cookies,String key){
		if(cookies!=null){
			for(Cookie cookie:cookies){
				if(cookie.getName().equals(key)){
					return cookie.getValue();
				}
			}
		}
		return null;
	}

}
