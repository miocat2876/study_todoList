package com.example.famback.fam.member.exception;

import com.example.famback.error.custom.CustomException;

public class CreateMemberFailException extends CustomException {

    public CreateMemberFailException() {
        super(CustomMemberCodeType.CREATE_MEMBER_FAIL);
    }
}
