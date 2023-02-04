package com.example.famback.error.custom.defaultException;

import com.example.famback.error.custom.CustomCodeGroup;
import lombok.Getter;
import org.springframework.http.HttpStatus;

//코드 번호 0000
public enum CustomDefaultCodeType implements CustomCodeGroup {
    NOT_REQUIRED_DATA_EXCEPTION(Code.ERROR.getCode(),"90001",HttpStatus.OK , "해당 요청에 필요한 데이터가 없습니다."),
    ABNORMAL_REQUEST_EXCEPTION(Code.ERROR.getCode(),"90002",HttpStatus.OK , "잘못된 요청입니다"),
    SUCCESS(Code.SUCCESS.getCode(),"00000",HttpStatus.OK , "성공"),
    FAIL(Code.ERROR.getCode(), "99999",HttpStatus.INTERNAL_SERVER_ERROR, "실패");

    @Getter
    private final String code;
    @Getter
    private final String detailCode;
    @Getter
    private String exceptionMessage;

    public CustomDefaultCodeType setExceptionMessage(String exceptionMessage){
        this.exceptionMessage = exceptionMessage;
        return this;
    }
    public static CustomDefaultCodeType[] getTypes() {
        return values();
    }
    public HttpStatus getHttpStatus(){
        return getCode().equals("50000") ?  getHttpStatus() : HttpStatus.OK;
    }

    public String getName(){
        return name();
    }

    CustomDefaultCodeType(String code, String detailCode, HttpStatus httpStatus, String exceptionMessage) {
        this.code = code;
        this.exceptionMessage = exceptionMessage;
        this.detailCode = detailCode;
    }
}
