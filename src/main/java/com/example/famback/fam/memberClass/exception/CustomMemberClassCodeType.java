package com.example.famback.fam.memberClass.exception;

import com.example.famback.error.custom.CustomCodeGroup;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum CustomMemberClassCodeType implements CustomCodeGroup {

    NOT_MEMBER_CLASS_EXCEPTION(Code.ERROR.getCode(),"90202",HttpStatus.BAD_REQUEST,"존재하지 않은 방입니다."),
    NOT_INVITE_EMAIL_AUTH(Code.ERROR.getCode(),"90203",HttpStatus.BAD_REQUEST,"잘못된 권한입니다."),
    INVITE_DUPLICATION_EMAIL(Code.ERROR.getCode(),"90204",HttpStatus.BAD_REQUEST,"초대요청자와 초대대상자의 이메일이 같습니다."),
    NOT_INVITE_MEMBER(Code.ERROR.getCode(),"90205",HttpStatus.BAD_REQUEST,"초대되지 않은 사용자 입니다."),
    ;

    @Getter
    private final String code;
    @Getter
    private final String detailCode;
    @Getter
    private final String exceptionMessage;
    private final HttpStatus httpStatus;
    public HttpStatus getHttpStatus(){
        return getCode().equals("50000") ?  getHttpStatus() : HttpStatus.OK;
    }

    public String getName(){
        return name();
    }

    public static CustomMemberClassCodeType[] getTypes() {
        return values();
    }


    CustomMemberClassCodeType(String code, String detailCode, HttpStatus httpStatus, String exceptionMessage) {
        this.code = code;
        this.exceptionMessage = exceptionMessage;
        this.detailCode = detailCode;
        this.httpStatus = httpStatus;
    }
}
