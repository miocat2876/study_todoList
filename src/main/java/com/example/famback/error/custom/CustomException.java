package com.example.famback.error.custom;

public class CustomException extends RuntimeException {

    public CustomException(CustomCodeGroup customCodeGroup) {
        super(customCodeGroup.getName());
    }
    public CustomException(Throwable throwable) {
        super(throwable);
    }
    public CustomException(CustomCodeGroup customCodeGroup, Throwable throwable) {
        super(customCodeGroup.getName(),throwable);
    }
}
