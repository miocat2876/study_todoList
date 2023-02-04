package com.example.famback.fam.memberClass.mapper;

import com.example.famback.fam.memberClass.domain.MemberClassDomain;
import com.example.famback.fam.user.domain.UserDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberClassMapper {
//	com.example.famback.fam.MemberClassResponse memberOneFindByNum(String memberKey);
    int createMemberClass(MemberClassDomain memberClassDomain);
    int memberClassLastNumber();
    int existsMemberClass(MemberClassDomain memberClassDomain);
    List<MemberClassDomain> findClassByMemberKey(MemberClassDomain memberClassDomain);
    MemberClassDomain findMemberClassOneByMemberClassKey(MemberClassDomain memberClassDomain);
}
