package com.example.famback.fam.jwt.controller;

import com.example.famback.error.custom.defaultException.CustomDefaultCodeType;
import com.example.famback.error.custom.CustomCodeGroup;
import com.example.famback.fam.jwt.exception.NotRefreshTokenException;
import com.example.famback.fam.jwt.JwtContext;
import com.example.famback.fam.jwt.request.JwtRequest;
import com.example.famback.fam.jwt.service.JwtService;
import com.example.famback.response.ResponseDto;
import com.example.famback.util.NetUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@RestController
@RequiredArgsConstructor
public class jwtController {

    private final JwtService jwtService;
	@PutMapping("/refresh")
	public ResponseEntity<ResponseDto<String>> refresh(@CookieValue(value = JwtContext.REFRESH_COOKIE_KEY, required = false) String refreshTokenKey, @RequestHeader("User-Agent") String userAgent, HttpServletRequest request) throws Exception {
		if(refreshTokenKey == null) throw new NotRefreshTokenException();
        JwtRequest jwtRequest = JwtRequest.builder()
                .numPk(refreshTokenKey)
                .ip(NetUtil.getIp(request))
                .userAgent(userAgent)
                .build();
        String accessToken = jwtService.generateAccessToken(jwtRequest);

		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		HttpHeaders httpHeaders = new HttpHeaders();
		if (accessToken != null){
			httpHeaders.add("Authorization",accessToken);
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<String>builder()
				.customCodeGroup(customCodeGroup)
				.body("")
				.build(),
				httpHeaders,
				HttpStatus.OK.value());
	}
}
