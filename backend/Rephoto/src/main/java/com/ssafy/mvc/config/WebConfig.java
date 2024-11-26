package com.ssafy.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.mvc.interceptor.AdminInterceptor;
import com.ssafy.mvc.interceptor.AdminInterceptor2;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Autowired
	AdminInterceptor adminInterceptor;			// 로그인이 안되어 있으면 요청 차단
	
	@Autowired
	AdminInterceptor2 adminInterceptor2;		// 로그인이 되어 있으면 요청 차단
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(adminInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns("/user/login","/user/regist/**","/user/check/**");
		
		registry.addInterceptor(adminInterceptor2)
				.addPathPatterns("/user/login","/user/regist/**");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedOrigins("http://localhost:5173/")
		.allowedMethods("GET","POST","PUT","DELETE")
		.allowedHeaders("*")
		.allowCredentials(true);
	}
}
