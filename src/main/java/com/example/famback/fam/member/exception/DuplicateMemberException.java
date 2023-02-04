package com.example.famback.fam.member.exception;

import com.example.famback.error.custom.CustomException;

public class DuplicateMemberException extends CustomException {

    public DuplicateMemberException() {
        super(CustomMemberCodeType.DUPLICATE_MEMBER_EXCEPTION);
    }
}
