package com.example.famback.fam.jwt.exception;

import com.example.famback.error.custom.CustomCodeGroup;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum CustomJwtCodeType implements CustomCodeGroup {

    NOT_ACCESS_TOKEN_EXCEPTION(Code.ERROR.getCode(),"90100", HttpStatus.FORBIDDEN,"토큰이 유효하지 않습니다."),
    NOT_REFRESH_TOKEN_EXCEPTION(Code.ERROR.getCode(),"90101",HttpStatus.FORBIDDEN,"토큰이 유효하지 않습니다."),
    NOT_TOKEN_INFO_EXCEPTION(Code.ERROR.getCode(),"90102",HttpStatus.FORBIDDEN,"토큰정보가 존재하지 않습니다."),
    NOT_JWT_USER_INFO_EXCEPTION(Code.ERROR.getCode(),"90103",HttpStatus.FORBIDDEN,"토큰 유저정보가 존재하지 않습니다."),
    CREATE_TOKEN_FAIL_EXCEPTION(Code.ERROR.getCode(),"90104",HttpStatus.FORBIDDEN,"토큰생성 실패."),
    CREATE_ACCESS_TOKEN_FAIL_EXCEPTION(Code.ERROR.getCode(),"90105",HttpStatus.FORBIDDEN,"엑세스 토큰생성 실패."),
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

    public static CustomJwtCodeType[] getTypes() {
        return values();
    }


    CustomJwtCodeType(String code, String detailCode, HttpStatus httpStatus, String exceptionMessage) {
        this.code = code;
        this.exceptionMessage = exceptionMessage;
        this.detailCode = detailCode;
        this.httpStatus = httpStatus;
    }
}
