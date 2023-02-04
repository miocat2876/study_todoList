package com.example.famback.util;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class NetUtil {
	public static String getIp(HttpServletRequest request) {
		String ip = null;
		ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-RealIP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("REMOTE_ADDR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static Cookie resolveCookie(ServletRequest request, String cookieName){
		Cookie[] cookies= ((HttpServletRequest) request).getCookies();
		if (cookies == null) {
			return null;
		}
		return Arrays.stream(cookies).filter(item -> Objects.equals(item.getName(), cookieName)).findFirst().get();
	}

	public static String getUserAgent(HttpServletRequest request){
		return request.getHeader("User-Agent");
	}

	public static String getAgentbrowser(String userAgent){
		String[] browsers = {"Trident","Chrome","Opera"};
		return Arrays.stream(browsers).filter(userAgent::contains).findFirst().get();
	}

	public static boolean isMobile(String userAgent){
		return userAgent.contains("Mobile");
	}
}
