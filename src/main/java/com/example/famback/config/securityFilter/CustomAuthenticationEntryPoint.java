package com.example.famback.config.securityFilter;

import com.example.famback.error.custom.CustomCodeType;
import lombok.extern.log4j.Log4j2;
import org.json.JSONException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String exception = (String) request.getAttribute("exception");
        response.setContentType("application/json;charset=UTF-8");
        try {
            if(CustomCodeType.is(exception)) {
                log.error("CustomAuthenticationEntryPoint => CustomException");
                response.getWriter().print(CustomCodeType.getCustomException(exception).createJsonObject());
            }else if(authException != null){
                log.error("CustomAuthenticationEntryPoint => authException");
                log.error(authException.getMessage());
                response.getWriter().print(CustomCodeType.authJsonObject(authException.getMessage()));
            }else{
                throw new RuntimeException(exception);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}