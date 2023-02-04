package com.example.famback.error.custom.defaultException;

import com.example.famback.error.custom.CustomException;

public class NotRequiredDataException extends CustomException {
	public NotRequiredDataException(String message) {
		super(CustomDefaultCodeType.NOT_REQUIRED_DATA_EXCEPTION.setExceptionMessage(message));
	}
	public NotRequiredDataException() {
		super(CustomDefaultCodeType.NOT_REQUIRED_DATA_EXCEPTION);
	}
}
