package com.example.famback.fam.exemple.exception;

import com.example.famback.error.custom.CustomCodeGroup;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum CustomAccountCodeType implements CustomCodeGroup {

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

    public static CustomAccountCodeType[] getTypes() {
        return values();
    }


    CustomAccountCodeType(String code, String detailCode, HttpStatus httpStatus, String exceptionMessage) {
        this.code = code;
        this.exceptionMessage = exceptionMessage;
        this.detailCode = detailCode;
        this.httpStatus = httpStatus;
    }
}
