package com.ssafy.mvc.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AdminInterceptor2 implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession();
		System.out.println(2);
		System.out.println(session.getAttribute("userId"));
		if (session.getAttribute("userId") != null && !session.getAttribute("userId").toString().isEmpty()) {
			System.out.println("막을게");
			return false;
		}
		return true;
	}
}
