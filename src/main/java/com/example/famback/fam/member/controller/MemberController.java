package com.example.famback.fam.member.controller;

import com.example.famback.error.custom.defaultException.CustomDefaultCodeType;
import com.example.famback.error.custom.CustomException;
import com.example.famback.error.custom.CustomCodeGroup;
import com.example.famback.fam.jwt.JwtTokenProvider;
import com.example.famback.fam.member.exception.CustomMemberCodeType;
import com.example.famback.fam.member.request.*;
import com.example.famback.fam.member.response.MemberOneResponse;
import com.example.famback.fam.member.service.MemberService;
import com.example.famback.fam.jwt.response.Token;
import com.example.famback.response.ResponseDto;
import com.example.famback.util.NetUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@RestController
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/memberLogin")
	public ResponseEntity<ResponseDto<String>> memberLogin(@RequestBody LoginRequest loginRequest, @RequestHeader("User-Agent") String userAgent, HttpServletRequest request) throws CustomException {
		//회원 검증 후 토큰 생성
		log.debug("memberLogin =>" + loginRequest.toString());
		loginRequest.setIp(NetUtil.getIp(request));
		loginRequest.setUserAgent(userAgent);
		Token token = memberService.memberLogin(loginRequest);
		//적재
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		HttpHeaders httpHeaders = new HttpHeaders();
		if (token != null) {
			httpHeaders.add("Authorization", token.getAccessToken());
			//리프레쉬 토큰은 해쉬값으로 키값을 만든후 저장
			NetUtil.addRefreshTokenKeyToHeader(httpHeaders, token.getRefreshKey());
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<String>builder()
				.customCodeGroup(customCodeGroup)
				.body("")
				.build(),
				httpHeaders,
				HttpStatus.OK.value());
	}

	@PutMapping("/memberLogout")
	public ResponseEntity<ResponseDto<String>> logout(@RequestBody LogoutRequest logoutRequest, @RequestHeader("User-Agent") String userAgent, @RequestAttribute("memberKey") String memberKey, HttpServletRequest request) throws Exception {
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		logoutRequest.setIp(NetUtil.getIp(request));
		logoutRequest.setUserAgent(userAgent);
		logoutRequest.setMemberKey(memberKey);
		HttpHeaders httpHeaders = new HttpHeaders();
		if (memberService.memberLogout(logoutRequest)) {
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
			httpHeaders.add("Authorization", "");
			//리프레쉬 토큰은 해쉬값으로 키값을 만든후 저장
			NetUtil.deleteRefreshTokenKeyToHeader(httpHeaders);
		}
		return new ResponseEntity<>(ResponseDto.<String>builder()
				.body("")
				.customCodeGroup(customCodeGroup)
				.build(),
				httpHeaders, HttpStatus.OK.value());
	}

	//이메일 검증 확인
	@PutMapping("/member-validation-confirm")
	public ResponseEntity<ResponseDto<String>> memberActive(@RequestBody MemberValidationRequest memberValidationRequest) throws CustomException {
		CustomCodeGroup customCodeGroup = CustomMemberCodeType.MEMBER_EMAIL_AUTH_FAIL;
		if (memberService.MemberActive(memberValidationRequest)) {
			customCodeGroup = CustomMemberCodeType.MEMBER_EMAIL_AUTH_SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<String>builder()
				.customCodeGroup(customCodeGroup)
				.body("")
				.build(),
				new HttpHeaders(),
				HttpStatus.OK.value());
	}

	//이메일 호출
	@PostMapping(value = "/member-validation", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto<String>> memberValidation(@RequestBody MemberValidationRequest memberValidationRequest) throws Exception {
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		if (memberService.memberValidationKeySender(memberValidationRequest)) {
			customCodeGroup = CustomMemberCodeType.MEMBER_AUTH_CODE_SENDER;
		}
		return new ResponseEntity<>(ResponseDto.<String>builder()
				.body("")
				.customCodeGroup(customCodeGroup)
				.build(),
				new HttpHeaders(), HttpStatus.OK.value());
	}

	@PostMapping(value = "/member", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto<String>> memberCreate(@RequestBody CreateMemberRequest createMemberRequest) throws Exception {
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		if (memberService.memberCreate(createMemberRequest)) {
			customCodeGroup = CustomMemberCodeType.MEMBER_AUTH_CODE_SENDER;
		}
		return new ResponseEntity<>(ResponseDto.<String>builder()
				.body("")
				.customCodeGroup(customCodeGroup)
				.build(),
				new HttpHeaders(), HttpStatus.OK.value());
	}

	@DeleteMapping("/members")
	public ResponseEntity<ResponseDto<String>> memberDelete(MemberDeleteRequest memberDeleteRequest, HttpServletRequest request) throws CustomException {
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		memberDeleteRequest.setMemberKey(jwtTokenProvider.getMemberKey(jwtTokenProvider.resolveAccessToken(request)));
		if (memberService.memberDelete(memberDeleteRequest)) {
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<String>builder()
				.body("")
				.customCodeGroup(customCodeGroup)
				.build(),
				new HttpHeaders(), HttpStatus.OK.value());
	}

	@GetMapping("/member")
	public ResponseEntity<ResponseDto<MemberOneResponse>> member(MemberOneRequest memberOneRequest, HttpServletRequest request) throws CustomException {
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		memberOneRequest.setMemberKey(jwtTokenProvider.getMemberKey(jwtTokenProvider.resolveAccessToken(request)));
		MemberOneResponse memberOneResponse = memberService.memberOne(memberOneRequest);
		if (memberOneResponse != null) {
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<MemberOneResponse>builder()
				.body(memberOneResponse)
				.customCodeGroup(customCodeGroup)
				.build(),
				new HttpHeaders(), HttpStatus.OK.value());
	}
}


