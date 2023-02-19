package com.example.famback.fam.member.service;

import com.example.famback.error.custom.defaultException.NotRequiredDataException;
import com.example.famback.fam.member.domain.MemberDomain;
import com.example.famback.fam.member.mapper.MemberMapper;
import com.example.famback.fam.member.request.MemberInsertRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
//@RequiredArgsConstructor
public class MemberService {

//    private final MemberMapper memberMapper;

    @Autowired
    MemberMapper memberMapper;

    public List<MemberDomain> memberList(MemberDomain memberDomain) {
        if (memberDomain == null) {
            memberDomain = new MemberDomain();
        }
        return memberMapper.memberList(memberDomain);
    }

    // 회원 가입
    public void memberInsert(MemberDomain memberDomain) {
        memberMapper.memberInsert(memberDomain);
    }
}
