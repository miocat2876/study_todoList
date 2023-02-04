package com.example.famback.fam.memberClass.exception;

import com.example.famback.error.custom.CustomException;

public class InviteDuplicationEmailException extends CustomException {
    public InviteDuplicationEmailException() {
		super(CustomMemberClassCodeType.INVITE_DUPLICATION_EMAIL);
	}
}
