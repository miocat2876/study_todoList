package com.example.famback.fam.memberClass.controller;

import com.example.famback.error.custom.defaultException.CustomDefaultCodeType;
import com.example.famback.error.custom.CustomCodeGroup;
import com.example.famback.fam.memberClass.request.InviteMemberClassesRequest;
import com.example.famback.fam.memberClass.request.MemberClassInfoRequest;
import com.example.famback.fam.memberClass.request.MemberClassInviteRequest;
import com.example.famback.fam.memberClass.response.InviteMemberClassesResponse;
import com.example.famback.fam.memberClass.response.MemberClassInfoResponse;
import com.example.famback.fam.memberClass.response.MemberClassInviteResponse;
import com.example.famback.fam.memberClass.service.MemberClassService;
import com.example.famback.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/invite")
public class InviteMemberClassController {
	private final MemberClassService memberClassService;

	//방참가
	@PostMapping
	public ResponseEntity<ResponseDto<MemberClassInviteResponse>> attendMemberClass(@RequestAttribute(value = "memberKey",required = false) String memberKey, @RequestBody MemberClassInviteRequest memberClassInviteRequest) throws Exception {
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		memberClassInviteRequest.setMemberKey(memberKey);
		log.debug("attendMemberClass => " + memberClassInviteRequest.toString());
		MemberClassInviteResponse memberClassInviteResponse = memberClassService.attendMemberClass(memberClassInviteRequest);
		if(memberClassInviteResponse != null){
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<MemberClassInviteResponse>builder()
				.customCodeGroup(customCodeGroup)
				.body(memberClassInviteResponse)
				.build(),
				new HttpHeaders(),
				HttpStatus.OK.value());
	}

	//초대방정보 조회
	@GetMapping("/{memberClassKey}")
	public ResponseEntity<ResponseDto<MemberClassInfoResponse>> memberClassInfo(@PathVariable String memberClassKey, MemberClassInfoRequest memberClassInfoRequest) throws Exception {
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		memberClassInfoRequest.setMemberClassKey(memberClassKey);
		MemberClassInfoResponse memberClassInfoResponse = memberClassService.inViteMemberClassInfo(memberClassInfoRequest);
		if(memberClassInfoResponse != null){
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<MemberClassInfoResponse>builder()
				.customCodeGroup(customCodeGroup)
				.body(memberClassInfoResponse)
				.build(),
				new HttpHeaders(),
				HttpStatus.OK.value());
	}

	//초대방정보 조회
	@GetMapping
	public ResponseEntity<ResponseDto<InviteMemberClassesResponse>> inviteMemberClasses(@RequestAttribute("memberKey") String memberKey, InviteMemberClassesRequest inviteMemberClassesRequest) throws Exception {
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		inviteMemberClassesRequest.setMemberKey(memberKey);
		InviteMemberClassesResponse inviteMemberClassesResponse = memberClassService.inviteAscOneMemberClass(inviteMemberClassesRequest);
		if(inviteMemberClassesResponse != null){
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<InviteMemberClassesResponse>builder()
				.customCodeGroup(customCodeGroup)
				.body(inviteMemberClassesResponse)
				.build(),
				new HttpHeaders(),
				HttpStatus.OK.value());
	}

	//방초대 키 생성 및 발송
	@PostMapping("/{memberClassKey}")
	public ResponseEntity<ResponseDto<String>> inviteMemberClass(@PathVariable String memberClassKey, @RequestAttribute(value = "memberKey") String memberKey, @RequestAttribute(value = "userKey") String userKey, @RequestBody MemberClassInviteRequest memberClassInviteRequest ) throws Exception {
		//발송한 멤버가 관리자가 맞는지 확인
		//유저키,방번호,이메일 리스트
		//키 생성
		//발송할 이메일 대상 적재
		//이메일 발송
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		memberClassInviteRequest.setUserKey(userKey);
		memberClassInviteRequest.setMemberClassKey(memberClassKey);
		memberClassInviteRequest.setMemberKey(memberKey);
		log.debug("inviteMemberClass =>" + memberClassInviteRequest.toString());
		if(memberClassService.inviteMemberClass(memberClassInviteRequest)){
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<String>builder()
				.customCodeGroup(customCodeGroup)
				.body("")
				.build(),
				new HttpHeaders(),
				HttpStatus.OK.value());
	}
}
