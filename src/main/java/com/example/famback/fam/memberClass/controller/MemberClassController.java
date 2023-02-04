package com.example.famback.fam.memberClass.controller;

import com.example.famback.error.custom.defaultException.CustomDefaultCodeType;
import com.example.famback.error.custom.CustomException;
import com.example.famback.error.custom.CustomCodeGroup;
import com.example.famback.fam.memberClass.exception.CustomMemberClassCodeType;
import com.example.famback.fam.memberClass.request.CreateMemberClassRequest;
import com.example.famback.fam.memberClass.request.MemberClassInfoRequest;
import com.example.famback.fam.memberClass.request.MemberClassListRequest;
import com.example.famback.fam.memberClass.response.MemberClassListResponse;
import com.example.famback.fam.memberClass.response.CreateMemberClassResponse;
import com.example.famback.fam.memberClass.response.MemberClassInfoResponse;
import com.example.famback.fam.memberClass.service.MemberClassService;
import com.example.famback.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//회원단건조회      members/id
//그룹회원리스트조회 members
//그룹명 중복체크   existsGroup/group
//이메일 중복체크   existsEmail/email
//회원가입         memberCreate
//회원수정         memberUpdate
//회원활성화       memberActive
//회원탈퇴         memberDelete


@Log4j2
@RestController
@RequiredArgsConstructor
public class MemberClassController {
	private final MemberClassService memberClassService;

	@PostMapping("/class")
	public ResponseEntity<ResponseDto<CreateMemberClassResponse>> createMemberClass(@RequestAttribute("memberKey") String memberKey ,@RequestBody CreateMemberClassRequest createMemberClassRequest) throws CustomException {
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		createMemberClassRequest.setMemberKey(memberKey);
		CreateMemberClassResponse createMemberClassResponse = memberClassService.createMemberClass(createMemberClassRequest);
		if(createMemberClassResponse != null){
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<CreateMemberClassResponse>builder()
				.customCodeGroup(customCodeGroup)
				.body(createMemberClassResponse)
				.build(),
				new HttpHeaders(),
				HttpStatus.OK.value());
	}
	@GetMapping("/class")
	public ResponseEntity<ResponseDto<List<MemberClassListResponse>>> classes(@RequestAttribute("memberKey") String memberKey, MemberClassListRequest memberClassListRequest) throws CustomException {
		CustomCodeGroup customCodeGroup = CustomMemberClassCodeType.NOT_MEMBER_CLASS_EXCEPTION;
		memberClassListRequest.setMemberKey(memberKey);
		List<MemberClassListResponse> memberClassDomains = memberClassService.classList(memberClassListRequest);
		if(memberClassDomains != null){
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}

		return new ResponseEntity<>(ResponseDto.<List<MemberClassListResponse>>builder()
				.customCodeGroup(customCodeGroup)
				.body(memberClassDomains)
				.build(),
				new HttpHeaders(),
				HttpStatus.OK.value());
	}

	@GetMapping("/class/{memberClassKey}")
	public ResponseEntity<ResponseDto<MemberClassInfoResponse>> memberClassInfo(@PathVariable String memberClassKey, MemberClassInfoRequest memberClassInfoRequest) throws Exception {
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		memberClassInfoRequest.setMemberClassKey(memberClassKey);
		MemberClassInfoResponse memberClassInfoResponse = memberClassService.memberClassInfo(memberClassInfoRequest);
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
}
