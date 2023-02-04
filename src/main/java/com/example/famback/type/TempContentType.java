package com.example.famback.type;

import lombok.Getter;

@Getter
public enum TempContentType {
	MEMBER_AUTH_CODE("00001"),
	MEMBER_CLASS_INVITE_CODE("00002");
	private final String contentType;
	TempContentType(String contentType) {
		this.contentType = contentType;
	}
}
