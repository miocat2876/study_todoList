package com.example.famback.error.custom;

import lombok.Getter;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

//코드정의
//코드는 일반적인 전체적인 코드값을 나타냄.
//00000 성공 99999 실패 50000 httpStatus 상태값 사용

// 상세코드정의
// 10000자리는 코드의 정상 유무 0은 성공 9는 실패를 나타냄.
// 100자리는 각 서비스를 나타냄 총 99개의 서비스를 나타 낼수 있음. 100~ 9900
// 1자리는 서비스의 각 에러를 나타냄 0~99 총 약 200개의 예외 정의가능 성공 100개 실패 100개
// 00000 99999는 사용불가능한 예약 코드

//토큰서비스는 0000
//멤버서비스는 1000
//클래스서비스는 2000
//게시판서비스는 3000
//달력서비스는 4000

//http 상태값
//예외처리에대한 출력 메세지

//2022-09-29
//코드 그룹화 시킬 예정
public enum CustomExceptionType2 {

     //jwt
    NOT_ACCESS_TOKEN_EXCEPTION(Code.ERROR.getCode(),"90100",HttpStatus.FORBIDDEN,"토큰이 유효하지 않습니다."),
    NOT_REFRESH_TOKEN_EXCEPTION(Code.ERROR.getCode(),"90101",HttpStatus.FORBIDDEN,"토큰이 유효하지 않습니다."),
    NOT_TOKEN_INFO_EXCEPTION(Code.ERROR.getCode(),"90102",HttpStatus.FORBIDDEN,"토큰정보가 존재하지 않습니다."),
    NOT_JWT_USER_INFO_EXCEPTION(Code.ERROR.getCode(),"90103",HttpStatus.FORBIDDEN,"토큰 유저정보가 존재하지 않습니다."),
    CREATE_TOKEN_FAIL_EXCEPTION(Code.ERROR.getCode(),"90104",HttpStatus.FORBIDDEN,"토큰생성 실패."),
    CREATE_ACCESS_TOKEN_FAIL_EXCEPTION(Code.ERROR.getCode(),"90105",HttpStatus.FORBIDDEN,"엑세스 토큰생성 실패."),

    //member
    NOT_MEMBER_EXCEPTION(Code.ERROR.getCode(), "90200",HttpStatus.FORBIDDEN,"해당하는 사용자 정보가 없습니다."),
    DUPLICATE_MEMBER_EXCEPTION(Code.ERROR.getCode(),"90201",HttpStatus.FORBIDDEN,"중복된 이메일이 존재 합니다."),
    MEMBER_CREATE_FAIL(Code.ERROR.getCode(), "90202",HttpStatus.FORBIDDEN, "회원 등록에 실패하였습니다."),
    MEMBER_EMAIL_AUTH_FAIL(Code.ERROR.getCode(), "90203",HttpStatus.FORBIDDEN , "이메일 인증에 실패하였습니다."),
    MEMBER_EMAIL_DUPLICATION(Code.ERROR.getCode(), "90204",HttpStatus.FORBIDDEN, "중복된 이메일 정보가 존재합니다."),

    MEMBER_CREATE_SUCCESS(Code.SUCCESS.getCode(), "00200",HttpStatus.OK, "회원 등록에 성공하였습니다."),
    MEMBER_AUTH_CODE_SENDER(Code.SUCCESS.getCode(), "00201",HttpStatus.OK, "가입을 위한 이메일 인증키를 발송 하였습니다."),
    MEMBER_AUTH_CODE_RE_SENDER(Code.SUCCESS.getCode(), "00202",HttpStatus.OK, "가입을 위한 이메일 인증키를 재 발송 하였습니다."),
    MEMBER_EMAIL_AUTH_SUCCESS(Code.SUCCESS.getCode(), "00203",HttpStatus.OK, "이메일 인증에 성공하였습니다."),

    //memberClass
    NOT_MEMBER_CLASS_EXCEPTION(Code.ERROR.getCode(),"90202",HttpStatus.BAD_REQUEST,"존재하지 않은 방입니다."),


    //성공 실패 코드
    SUCCESS(Code.SUCCESS.getCode(),"00000",HttpStatus.OK , "성공"),
    FAIL(Code.ERROR.getCode(), "99999",HttpStatus.INTERNAL_SERVER_ERROR, "실패");


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

    CustomExceptionType2(String code, String detailCode, HttpStatus httpStatus, String exceptionMessage) {
        this.code = code;
        this.exceptionMessage = exceptionMessage;
        this.detailCode = detailCode;
        this.httpStatus = httpStatus;
    }

    public static boolean is(String name){
        if(name == null) return false;
        for(CustomExceptionType2 customExceptionType : values()){
            if(name.equals(customExceptionType.name())){
                return true;
            }
        }
        return false;
    }
    public JSONObject createJsonObject() throws JSONException {
        JSONObject responseJson = new JSONObject();
        responseJson.put("code",this.code);
        responseJson.put("detailCode",this.detailCode);
        responseJson.put("name",this.name());
        responseJson.put("httpStatus", this.getHttpStatus());
        responseJson.put("message",this.exceptionMessage);
        responseJson.put("body","");
        return responseJson;
    }
    public static JSONObject authJsonObject(String message) throws JSONException {
        JSONObject responseJson = new JSONObject();
        CustomExceptionType2 customExceptionType = CustomExceptionType2.NOT_ACCESS_TOKEN_EXCEPTION;
        responseJson.put("code",customExceptionType.code);
        responseJson.put("detailCode",customExceptionType.detailCode);
        responseJson.put("name",customExceptionType.name());
        responseJson.put("httpStatus", customExceptionType.getHttpStatus());
        responseJson.put("message",customExceptionType.exceptionMessage);
        responseJson.put("body","");
        return responseJson;
    }

    @Getter
    private enum Code{
        SUCCESS("00000"),
        HTTP_STATUS("50000"),
        ERROR("99999");
        private final String code;
        Code(String code) {
            this.code = code;
        }
    }
}