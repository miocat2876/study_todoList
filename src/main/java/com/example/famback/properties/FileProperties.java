package com.example.famback.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "server")
public final class FileProperties {
	private final Tomcat tomcat;

	@Getter
	@RequiredArgsConstructor
	public static final class Tomcat {
		private final String baseDir;
	}
}
