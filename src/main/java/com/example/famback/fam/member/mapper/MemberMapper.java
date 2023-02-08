package com.example.famback.fam.member.mapper;

import com.example.famback.fam.member.domain.MemberDomain;
import org.apache.ibatis.annotations.Mapper;

@Mapper //dao
public interface MemberMapper {

    int insertMember(MemberDomain memberDomain);
}
