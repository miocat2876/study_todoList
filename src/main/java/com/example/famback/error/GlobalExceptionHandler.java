package com.example.famback.error;

import com.example.famback.error.custom.CustomException;
import com.example.famback.error.custom.CustomCodeGroup;
import com.example.famback.error.custom.CustomCodeType;
import com.example.famback.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e){
        log.error("MethodArgumentNotValidException");
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDto<String>> customException(CustomException exception) throws JSONException {
        log.error("CustomException");
        log.error(exception.getMessage());
        CustomCodeGroup customCodeGroup = CustomCodeType.getCustomException(exception.getMessage());
        //JSONObject jsonObject =.customCodeGroup.createJsonObject();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json;charset=UTF-8");
        return new ResponseEntity<>(ResponseDto.<String>builder()
                .customCodeGroup(customCodeGroup)
				.body("")
				.build(),
				httpHeaders,
		        customCodeGroup.getHttpStatus().value());
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDto<String>> runtimeException(RuntimeException exception) throws JSONException {
        log.error("runtimeException");
        log.error(exception.getMessage());
        CustomCodeGroup customCodeGroup = CustomCodeType.getCustomException(exception.getMessage());
        //JSONObject jsonObject =.customCodeGroup.createJsonObject();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json;charset=UTF-8");
        return new ResponseEntity<>(ResponseDto.<String>builder()
                .customCodeGroup(customCodeGroup)
				.body("")
				.build(),
				httpHeaders,
		        customCodeGroup.getHttpStatus().value());
    }
}
