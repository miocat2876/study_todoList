package com.example.famback.fam.member.exception;

import com.example.famback.error.custom.CustomException;

public class DeleteMemberException extends CustomException {

    public DeleteMemberException() {
        super(CustomMemberCodeType.MEMBER_DELETE_EXCEPTION);
    }
}
