package com.example.famback.fam.member.controller;

import com.example.famback.error.custom.CustomCodeGroup;
import com.example.famback.error.custom.CustomException;
import com.example.famback.error.custom.defaultException.CustomDefaultCodeType;
import com.example.famback.error.response.ResponseDto;
import com.example.famback.fam.exemple.request.*;
import com.example.famback.fam.exemple.response.AccountListResponse;
import com.example.famback.fam.exemple.response.AccountOneResponse;
import com.example.famback.fam.exemple.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	private final AccountService accountService;

	@GetMapping("/{memberKey}")
	public ResponseEntity<ResponseDto<List<AccountListResponse>>> accountList(@PathVariable String memberKey) throws CustomException {



		return new ResponseEntity<>(ResponseDto.<List<AccountListResponse>>builder()
					.build(),
					new HttpHeaders(),
					HttpStatus.OK.value());
	}
}
