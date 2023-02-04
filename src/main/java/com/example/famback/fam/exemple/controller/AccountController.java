package com.example.famback.fam.exemple.controller;

import com.example.famback.error.custom.defaultException.CustomDefaultCodeType;
import com.example.famback.error.custom.CustomException;
import com.example.famback.error.custom.CustomCodeGroup;
import com.example.famback.fam.exemple.request.*;
import com.example.famback.fam.exemple.response.AccountListResponse;
import com.example.famback.fam.exemple.response.AccountOneResponse;
import com.example.famback.fam.exemple.service.AccountService;
import com.example.famback.error.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/class/{memberClassKey}")
@RequiredArgsConstructor
public class AccountController {
	private final AccountService accountService;

	@GetMapping("/account")
	public ResponseEntity<ResponseDto<List<AccountListResponse>>> accountList(AccountListRequest accountListRequest, @PathVariable String memberClassKey) throws CustomException {
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		accountListRequest.setMemberClassKey(memberClassKey);
		List<AccountListResponse> accountListResponses = accountService.accountList(accountListRequest);
		if(accountListResponses != null){
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<List<AccountListResponse>>builder()
					.customCodeGroup(customCodeGroup)
					.body(accountListResponses)
					.build(),
					new HttpHeaders(),
					HttpStatus.OK.value());
	}

	@GetMapping("/account/{key}")
	public ResponseEntity<ResponseDto<AccountOneResponse>> accountOne(@RequestAttribute("userKey") String userKey, @PathVariable String key, AccountOneRequest accountOneRequest) throws CustomException {
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		accountOneRequest.setNumPk(key);
		accountOneRequest.setUserKey(userKey);
		AccountOneResponse accountOneResponse =  accountService.accountOne(accountOneRequest);
		if(accountOneResponse != null){
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<AccountOneResponse>builder()
					.customCodeGroup(customCodeGroup)
					.body(accountOneResponse)
					.build(),
					new HttpHeaders(),
					HttpStatus.OK.value());
	}

	@PostMapping("/account")
	public ResponseEntity<ResponseDto<String>> accountInsert(@RequestAttribute("userKey") String userKey, @PathVariable String memberClassKey, @RequestBody AccountInsertRequest accountInsertRequest) throws CustomException {
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		accountInsertRequest.setUserKey(userKey);
		accountInsertRequest.setMemberClassKey(memberClassKey);
		if(accountService.accountInsert(accountInsertRequest)){
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<String>builder()
					.customCodeGroup(customCodeGroup)
					.body(customCodeGroup.getExceptionMessage())
					.build(),
					new HttpHeaders(),
					HttpStatus.OK.value());
	}
	@PutMapping("/account")
	public ResponseEntity<ResponseDto<String>> accountUpdate(@RequestAttribute("userKey") String userKey, @RequestBody AccountUpdateRequest accountUpdateRequest) throws CustomException {
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		accountUpdateRequest.setUserKey(userKey);
		if(accountService.accountUpdate(accountUpdateRequest)){
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<String>builder()
					.customCodeGroup(customCodeGroup)
					.body(customCodeGroup.getExceptionMessage())
					.build(),
					new HttpHeaders(),
					HttpStatus.OK.value());
	}

	@DeleteMapping("/account/{key}")
	public ResponseEntity<ResponseDto<String>> accountDelete(@RequestAttribute("userKey") String userKey, @PathVariable String key, AccountDeleteRequest accountDeleteRequest) throws CustomException {
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		accountDeleteRequest.setNumPk(key);
		accountDeleteRequest.setUserKey(userKey);
		if(accountService.accountDelete(accountDeleteRequest)){
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<String>builder()
					.customCodeGroup(customCodeGroup)
					.body(customCodeGroup.getExceptionMessage())
					.build(),
					new HttpHeaders(),
					HttpStatus.OK.value());
	}
}
