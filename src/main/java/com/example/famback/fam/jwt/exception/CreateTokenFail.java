package com.example.famback.fam.jwt.exception;

import com.example.famback.error.custom.CustomException;

public class CreateTokenFail extends CustomException {
	public CreateTokenFail() {
		super(CustomJwtCodeType.CREATE_TOKEN_FAIL_EXCEPTION);
	}
}
