package com.example.famback.fam.jwt.exception;

import com.example.famback.error.custom.CustomException;

public class NotAccessTokenException extends CustomException {
	public NotAccessTokenException() {
		super(CustomJwtCodeType.NOT_ACCESS_TOKEN_EXCEPTION);
	}
}
