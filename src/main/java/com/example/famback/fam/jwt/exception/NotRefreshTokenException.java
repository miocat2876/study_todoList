package com.example.famback.fam.jwt.exception;

import com.example.famback.error.custom.CustomException;

public class NotRefreshTokenException extends CustomException {
	public NotRefreshTokenException() {
		super(CustomJwtCodeType.NOT_REFRESH_TOKEN_EXCEPTION);
	}
}
