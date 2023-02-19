package com.example.famback.fam.member.controller;

import com.example.famback.fam.member.domain.MemberDomain;
import com.example.famback.fam.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService memberService;

	@PostMapping("")
	public List<MemberDomain> memberList(@RequestBody MemberDomain memberDomain) {
		return memberService.memberList(memberDomain);
	}

	@PostMapping("/signUp")
	public void memberInsert(@RequestBody MemberDomain memberDomain) {
		memberService.memberInsert(memberDomain);
	}
}
