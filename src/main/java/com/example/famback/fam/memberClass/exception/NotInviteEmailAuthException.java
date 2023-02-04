package com.example.famback.fam.memberClass.exception;

import com.example.famback.error.custom.CustomException;

public class NotInviteEmailAuthException extends CustomException {
    public NotInviteEmailAuthException() {
		super(CustomMemberClassCodeType.NOT_INVITE_EMAIL_AUTH);
	}
}
