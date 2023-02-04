package com.example.famback.fam.user.controller;

import com.example.famback.error.custom.defaultException.CustomDefaultCodeType;
import com.example.famback.error.custom.CustomException;
import com.example.famback.error.custom.CustomCodeGroup;
import com.example.famback.fam.user.request.UserDeleteRequest;
import com.example.famback.fam.user.request.UserListRequest;
import com.example.famback.fam.user.request.UserModifyRequest;
import com.example.famback.fam.user.request.UserOneRequest;
import com.example.famback.fam.user.response.UserListResponse;
import com.example.famback.fam.user.response.UserOneResponse;
import com.example.famback.fam.user.service.UserService;
import com.example.famback.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/class/{memberClassKey}")
public class UserController {
	private final UserService userService;

	//현재 접속중인 계정의 유저정보
	@GetMapping("/user")
	public ResponseEntity<ResponseDto<UserOneResponse>> user(@RequestAttribute("userKey") String userKey, UserOneRequest userOneRequest) throws CustomException {
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		userOneRequest.setUserKey(userKey);
		UserOneResponse userOneResponse = userService.userOne(userOneRequest);
		if(userOneResponse != null){
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<UserOneResponse>builder()
				.customCodeGroup(customCodeGroup)
				.body(userOneResponse)
				.build(),
				new HttpHeaders(),
				HttpStatus.OK.value());
	}
	@PutMapping("/users")
	public ResponseEntity<ResponseDto<String>> modifyUser(@RequestAttribute("userKey") String userKey, @RequestBody UserModifyRequest userModifyRequest) throws Exception {
		CustomDefaultCodeType customDefaultCodeType = CustomDefaultCodeType.FAIL;
		userModifyRequest.setUserKey(userKey);
		if(userService.modifyMemberInfoClass(userModifyRequest)){
			customDefaultCodeType = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<String>builder()
				.customCodeGroup(customDefaultCodeType)
				.body("")
				.build(),
				new HttpHeaders(),
				HttpStatus.OK.value());
	}
	@GetMapping("/users/{userKey}")
	public ResponseEntity<ResponseDto<UserOneResponse>> userOne(@PathVariable String userKey, UserOneRequest userOneRequest) throws CustomException {
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		userOneRequest.setUserKey(userKey);
		UserOneResponse userOneResponse = userService.userOne(userOneRequest);
		if(userOneResponse != null){
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<UserOneResponse>builder()
				.customCodeGroup(customCodeGroup)
				.body(userOneResponse)
				.build(),
				new HttpHeaders(),
				HttpStatus.OK.value());
	}

	@GetMapping("/users")
	public ResponseEntity<ResponseDto<List<UserListResponse>>> userList(@PathVariable String memberClassKey, UserListRequest userListRequest) throws CustomException {
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		userListRequest.setMemberClassKey(memberClassKey);
		List<UserListResponse> userListResponses = userService.userList(userListRequest);
		if(userListResponses != null){
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<List<UserListResponse>>builder()
				.customCodeGroup(customCodeGroup)
				.body(userListResponses)
				.build(),
				new HttpHeaders(),
				HttpStatus.OK.value());
	}
	@DeleteMapping("/users/{deleteTargetUserKey}")
	public ResponseEntity<ResponseDto<String>> deleteUser(@RequestAttribute("userKey") String userKey, @PathVariable String deleteTargetUserKey, UserDeleteRequest userDeleteRequest) throws Exception {
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		userDeleteRequest.setUserKey(userKey);
		userDeleteRequest.setDeleteTargetUserKey(deleteTargetUserKey);
		if(userService.deleteUser(userDeleteRequest)){
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
