package com.example.famback.fam.member.mapper;

import com.example.famback.fam.member.domain.MemberDomain;
import com.example.famback.fam.member.request.MemberInsertRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper //dao
public interface MemberMapper {

    List<MemberDomain> memberList(MemberDomain memberDomain);
    void memberInsert(MemberDomain memberDomain);
}
