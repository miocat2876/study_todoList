package com.example.famback.fam.member.service;

import com.example.famback.error.custom.defaultException.NotRequiredDataException;
import com.example.famback.fam.member.domain.MemberDomain;
import com.example.famback.fam.member.mapper.MemberMapper;
import com.example.famback.fam.member.request.MemberInsertRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    // 회원 가입
    public boolean memberInsert(MemberInsertRequest memberInsertRequest) {
        if(memberInsertRequest == null
        || memberInsertRequest.getMember_code() == null
        || memberInsertRequest.getMember_mail() == null
        || memberInsertRequest.getMember_nickname() == null
        || memberInsertRequest.getMember_password() == null
        || memberInsertRequest.getReq_date() == null
        || memberInsertRequest.getUpdate_date() == null) throw new NotRequiredDataException();

        MemberDomain memberDomain = MemberDomain.builder()
                .member_code(memberInsertRequest.getMember_code())
                .member_mail(memberInsertRequest.getMember_mail())
                .member_nickname(memberInsertRequest.getMember_nickname())
                .member_password(memberInsertRequest.getMember_password())
                .req_date(memberInsertRequest.getReq_date())
                .update_date(memberInsertRequest.getUpdate_date())
                .build();

        return memberMapper.insertMember(memberDomain) == 1;
    }
}
