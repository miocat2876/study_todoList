package com.example.famback.error.custom;

import lombok.Getter;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

public interface CustomCodeGroup {
    String getName();
    String getCode();
    String getDetailCode();
    String getExceptionMessage();
    HttpStatus getHttpStatus();

    default JSONObject createJsonObject() throws JSONException {
        JSONObject responseJson = new JSONObject();
        responseJson.put("code",this.getCode());
        responseJson.put("detailCode",this.getDetailCode());
        responseJson.put("name",this.getName());
        responseJson.put("httpStatus", this.getHttpStatus());
        responseJson.put("message",this.getExceptionMessage());
        responseJson.put("body","");
        return responseJson;
    }
    @Getter
    enum Code{
        SUCCESS("00000"),
        HTTP_STATUS("50000"),
        ERROR("99999");
        private final String code;
        Code(String code) {
            this.code = code;
        }
    }
}
