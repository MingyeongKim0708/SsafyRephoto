package com.ssafy.mvc.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AdminInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession();

		System.out.println(1);
		if (session.getAttribute("loginUser") == null || session.getAttribute("loginUser").toString().isEmpty()) {
			System.out.println(session.getAttribute("loginUser"));
			response.sendRedirect("/user/login");
			return false;
		}

		return true;
	}
}
