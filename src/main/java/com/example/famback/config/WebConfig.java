package com.example.famback.config;

import com.example.famback.config.interceptor.InviteMemberClassUserKeyAddInterceptor;
import com.example.famback.config.interceptor.MemberClassUserKeyAddInterceptor;
import com.example.famback.fam.jwt.JwtTokenProvider;
import com.example.famback.fam.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
	private final JwtTokenProvider jwtTokenProvider;
	private final UserMapper userMapper;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new MemberClassUserKeyAddInterceptor(jwtTokenProvider, userMapper))
				.addPathPatterns("/class")
				.addPathPatterns("/class/*/**")
				.addPathPatterns("/memberLogout")
				.excludePathPatterns("/css/**", "/fonts/**", "/plugin/**", "/scripts/**");
		registry.addInterceptor(new InviteMemberClassUserKeyAddInterceptor(jwtTokenProvider, userMapper))
				.addPathPatterns("/invite")
				.addPathPatterns("/invite/*")
				.excludePathPatterns("/css/**", "/fonts/**", "/plugin/**", "/scripts/**");
	}
}
