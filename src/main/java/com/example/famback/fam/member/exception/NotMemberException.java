package com.example.famback.fam.member.exception;

import com.example.famback.error.custom.CustomException;

public class NotMemberException extends CustomException {

    public NotMemberException() {
        super(CustomMemberCodeType.NOT_MEMBER_EXCEPTION);
    }
}
