package com.example.famback.error.custom;

import com.example.famback.error.custom.defaultException.CustomDefaultCodeType;
import lombok.Getter;
import org.json.JSONException;
import org.json.JSONObject;

//코드정의
//코드는 일반적인 전체적인 코드값을 나타냄.
//00000 성공 99999 실패 50000 httpStatus 상태값 사용

// 상세코드정의
// 10000자리는 코드의 정상 유무 0은 성공 9는 실패를 나타냄.
// 100자리는 각 서비스를 나타냄 총 99개의 서비스를 나타 낼수 있음. 100~ 9900
// 1자리는 서비스의 각 에러를 나타냄 0~99 총 약 200개의 예외 정의가능 성공 100개 실패 100개
// 00000 99999는 사용불가능한 예약 코드

//일반 서비스 9900
//토큰서비스는 0000
//멤버서비스는 0100
//클래스서비스는 0200
//게시판서비스는 0300
//달력서비스는 0400

//http 상태값
//예외처리에대한 출력 메세지

//2022-09-29
//코드 그룹화 시킬 예정
//2022-10-04
//에러코드 그룹화 완료
public enum CustomCodeType {

     //jwt
    DEFAULT(CustomDefaultCodeType.getTypes());

    @Getter
    private final CustomCodeGroup[] customCodeGroup;

    CustomCodeType(CustomCodeGroup[] customCodeGroup) {
        this.customCodeGroup = customCodeGroup;
    }

    public static boolean is(String message){
        if(message == null) return false;
        for(CustomCodeType customCodeType : values()){
            for(CustomCodeGroup customCodeGroup : customCodeType.customCodeGroup) {
                if(message.equals(customCodeGroup.getName())){
                    return true;
                }
            }
        }
        return false;
    }
    public static CustomCodeGroup getCustomException(String message){
        if(message == null) return null;
        for(CustomCodeType customCodeType : values()){
            for(CustomCodeGroup customCodeGroup : customCodeType.customCodeGroup) {
                if(message.equals(customCodeGroup.getName())){
                    return customCodeGroup;
                }
            }
        }
        return null;
    }
}