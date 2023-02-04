package com.example.famback.fam.jwt.exception;

import com.example.famback.error.custom.CustomException;

public class NotJwtUserInfo extends CustomException {

	public NotJwtUserInfo() {
		super(CustomJwtCodeType.NOT_JWT_USER_INFO_EXCEPTION);
	}
}
