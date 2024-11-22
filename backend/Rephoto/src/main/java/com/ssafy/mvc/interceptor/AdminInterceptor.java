package com.ssafy.mvc.interceptor;


import java.util.Arrays;
import java.util.Enumeration;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.Cookie;
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
		if (session.getAttribute("userId") == null || session.getAttribute("userId").toString().isEmpty()) {
			System.out.println("막을게");
			return false;
		}

		return true;
	}
}
