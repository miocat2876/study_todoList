package com.example.famback.util;

import lombok.Getter;

@Getter
public enum TempDataType {
	EMAIL("00001");
	private final String dataType;
	TempDataType(String dataType) {
		this.dataType = dataType;
	}
}
