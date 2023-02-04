package com.example.famback.fam.jwt.exception;

import com.example.famback.error.custom.CustomException;

public class CreateAccessTokenFail extends CustomException {
	public CreateAccessTokenFail() {
		super(CustomJwtCodeType.CREATE_ACCESS_TOKEN_FAIL_EXCEPTION);
	}
}
