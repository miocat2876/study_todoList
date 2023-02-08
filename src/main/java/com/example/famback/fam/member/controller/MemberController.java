package com.example.famback.fam.member.controller;

import com.example.famback.fam.member.request.MemberInsertRequest;
import com.example.famback.fam.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/signUp")
	public boolean memberInsert(@RequestBody MemberInsertRequest memberInsertRequest) {

		return memberService.memberInsert(memberInsertRequest);
	}
}
