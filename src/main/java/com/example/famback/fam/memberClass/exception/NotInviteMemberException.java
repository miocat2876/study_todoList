package com.example.famback.fam.memberClass.exception;

import com.example.famback.error.custom.CustomException;

public class NotInviteMemberException extends CustomException {
	public NotInviteMemberException() {
super(CustomMemberClassCodeType.NOT_INVITE_MEMBER);
}
}
