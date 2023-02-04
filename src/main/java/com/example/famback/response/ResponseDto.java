package com.example.famback.response;

import com.example.famback.error.custom.CustomCodeGroup;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class ResponseDto<T> {
    private String code;
    private String detailCode;
    private String name;
    private String status;
    private String message;
    private final T body;
    @Builder
    public ResponseDto(CustomCodeGroup customCodeGroup, T body){
        this.body = body;
        if(customCodeGroup != null){
            this.code = customCodeGroup.getCode();
            this.detailCode = customCodeGroup.getDetailCode();
            this.name = customCodeGroup.getName();
            this.status = customCodeGroup.getCode().equals("50000") ?  customCodeGroup.getHttpStatus().name() : HttpStatus.OK.name();
            this.message = customCodeGroup.getExceptionMessage();
        }
    }
}
