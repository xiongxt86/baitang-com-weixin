package com.hzboy.weixin.auth;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AuthInterceptor implements HandlerInterceptor {
	private int openingTime; // openingTime 属性指定上班时间  
    private int closingTime; // closingTime属性指定下班时间  
    private String outsideOfficeHoursPage;// outsideOfficeHoursPage属性指定错误提示页面的URL  
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance(); 
		int hour = cal.get(Calendar.HOUR_OF_DAY); // 获取当前时间  
        if (openingTime <= hour && hour < closingTime) { // 判断当前是否处于工作时间段内  
            return true;  
        } else {  
            response.sendRedirect(outsideOfficeHoursPage); // 返回提示页面  
            return false;  
        }  
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
