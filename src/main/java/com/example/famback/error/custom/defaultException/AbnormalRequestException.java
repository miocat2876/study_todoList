package com.example.famback.error.custom.defaultException;

import com.example.famback.error.custom.CustomException;

public class AbnormalRequestException extends CustomException {
	public AbnormalRequestException(String message) {
		super(CustomDefaultCodeType.ABNORMAL_REQUEST_EXCEPTION.setExceptionMessage(message));
	}
	public AbnormalRequestException() {
		super(CustomDefaultCodeType.ABNORMAL_REQUEST_EXCEPTION);
	}
}
