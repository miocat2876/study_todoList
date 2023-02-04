package com.example.famback.fam.memberClass.exception;

import com.example.famback.error.custom.CustomException;

public class NotMemberClassException extends CustomException {
	public NotMemberClassException() {
		super(CustomMemberClassCodeType.NOT_MEMBER_CLASS_EXCEPTION);
	}
}
