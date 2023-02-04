package com.example.famback.fam.account.controller;

import com.example.famback.error.custom.defaultException.CustomDefaultCodeType;
import com.example.famback.error.custom.CustomException;
import com.example.famback.error.custom.CustomCodeGroup;
import com.example.famback.fam.account.request.*;
import com.example.famback.fam.account.response.AccountGroupAccountTypeResponse;
import com.example.famback.fam.account.response.AccountGroupNicknameResponse;
import com.example.famback.fam.account.service.AccountStatsService;
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
@RequestMapping("/class/{memberClassKey}")
@RequiredArgsConstructor
public class AccountStatsController {
	private final AccountStatsService accountStatsService;

	@GetMapping("/accountStats/nickname")
	public ResponseEntity<ResponseDto<List<AccountGroupNicknameResponse>>> groupNickname(AccountGroupNicknameRequest accountGroupNicknameRequest, @PathVariable String memberClassKey) throws CustomException {
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		accountGroupNicknameRequest.setMemberClassKey(memberClassKey);
		List<AccountGroupNicknameResponse> accountGroupNicknameResponses = accountStatsService.groupNickName(accountGroupNicknameRequest);
		if(accountGroupNicknameResponses != null){
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<List<AccountGroupNicknameResponse>>builder()
					.customCodeGroup(customCodeGroup)
					.body(accountGroupNicknameResponses)
					.build(),
					new HttpHeaders(),
					HttpStatus.OK.value());
	}

	@GetMapping("/accountStats/accountType")
	public ResponseEntity<ResponseDto<List<AccountGroupAccountTypeResponse>>> groupAccountType(AccountGroupAccountTypeRequest accountGroupAccountTypeRequest, @PathVariable String memberClassKey) throws CustomException {
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		accountGroupAccountTypeRequest.setMemberClassKey(memberClassKey);
		log.debug("groupAccountType => " + accountGroupAccountTypeRequest.toString());
		List<AccountGroupAccountTypeResponse> accountGroupAccountTypeResponses = accountStatsService.groupAccountType(accountGroupAccountTypeRequest);
		if(accountGroupAccountTypeResponses != null){
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<List<AccountGroupAccountTypeResponse>>builder()
					.customCodeGroup(customCodeGroup)
					.body(accountGroupAccountTypeResponses)
					.build(),
					new HttpHeaders(),
					HttpStatus.OK.value());
	}
}
