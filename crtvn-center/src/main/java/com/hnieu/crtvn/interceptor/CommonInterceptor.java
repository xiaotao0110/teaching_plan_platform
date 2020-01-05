package com.hnieu.crtvn.interceptor;

import com.hnieu.crtvn.entity.Academician;
import com.hnieu.crtvn.entity.Admin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String url = request.getServletPath();
		
		//考务人员模块拦截
		if(url.contains("admin") || url.equals("college") ){ 
			Admin admin =  (Admin)request.getSession().getAttribute("admin"); 
			if(admin == null){
				response.sendRedirect(request.getContextPath() + "/sys/login");
					return false;
			}
			return true;
		}else{
			//考务信息模块拦截			
			Academician academicianBean =  (Academician)request.getSession().getAttribute("academician");
			if(academicianBean == null){
				response.sendRedirect(request.getContextPath() + "/sys/login");
					return false;
			}
			request.setAttribute("adminName", academicianBean.getName());
			return true;
			
		}		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}
	
	
}
