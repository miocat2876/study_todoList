package com.example.famback.fam.member.exception;

import com.example.famback.error.custom.CustomCodeGroup;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum CustomMemberCodeType implements CustomCodeGroup {

    NOT_MEMBER_EXCEPTION(Code.ERROR.getCode(), "90200",HttpStatus.FORBIDDEN,"해당하는 사용자 정보가 없습니다."),
    DUPLICATE_MEMBER_EXCEPTION(Code.ERROR.getCode(),"90201",HttpStatus.FORBIDDEN,"가입된 이메일이 존재 합니다."),
    CREATE_MEMBER_FAIL(Code.ERROR.getCode(), "90202",HttpStatus.FORBIDDEN, "회원 등록에 실패하였습니다."),
    MEMBER_EMAIL_AUTH_FAIL(Code.ERROR.getCode(), "90203",HttpStatus.FORBIDDEN , "이메일 인증에 실패하였습니다."),
    MEMBER_DELETE_EXCEPTION(Code.ERROR.getCode(), "90204",HttpStatus.FORBIDDEN , "회원 탈퇴중 문제가 발생하였습니다."),

    MEMBER_CREATE_SUCCESS(Code.SUCCESS.getCode(), "00200",HttpStatus.OK, "회원 등록에 성공하였습니다."),
    MEMBER_AUTH_CODE_SENDER(Code.SUCCESS.getCode(), "00201",HttpStatus.OK, "가입을 위한 이메일 인증키를 발송 하였습니다."),
    MEMBER_AUTH_CODE_RE_SENDER(Code.SUCCESS.getCode(), "00202",HttpStatus.OK, "가입을 위한 이메일 인증키를 재 발송 하였습니다."),
    MEMBER_EMAIL_AUTH_SUCCESS(Code.SUCCESS.getCode(), "00203",HttpStatus.OK, "이메일 인증에 성공하였습니다."),
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

    public static CustomMemberCodeType[] getTypes() {
        return values();
    }


    CustomMemberCodeType(String code, String detailCode, HttpStatus httpStatus, String exceptionMessage) {
        this.code = code;
        this.exceptionMessage = exceptionMessage;
        this.detailCode = detailCode;
        this.httpStatus = httpStatus;
    }
}
