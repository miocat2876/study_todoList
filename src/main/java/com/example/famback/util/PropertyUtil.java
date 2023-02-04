package com.example.famback.util;

import com.example.famback.context.ApplicationContextServe;
import org.springframework.context.ApplicationContext;

public class PropertyUtil {

	public static String getProperty(String propertyName) {
		ApplicationContext applicationContext = ApplicationContextServe.getApplicationContext();
		if (applicationContext.getEnvironment().getProperty(propertyName) != null) {
			return applicationContext.getEnvironment().getProperty(propertyName);
		}
		return "";
	}
}