package com.example.famback.fam.member.mapper;

import com.example.famback.fam.member.domain.MemberDomain;
import com.example.famback.fam.member.domain.MemberTempDataDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
	MemberDomain memberOneFindByKey(MemberDomain memberDomain);
	List<MemberDomain>memberList(MemberDomain memberDomain);
	int memberCount(MemberDomain memberDomain);
	String findByMember(MemberDomain memberDomain);
	int existsByEmail(String email);
	int memberCreate(MemberDomain memberDomain);
	int memberActive(String memberKey);
	int memberDelete(MemberDomain memberDomain);
	String findByMemberKey(MemberDomain memberDomain);
	int memberLastNumber();
	int insertMemberTempData(MemberTempDataDomain memberTempDataDomain);
	String findByKeyToContentType(MemberTempDataDomain memberTempDataDomain);
	MemberTempDataDomain findByKeyToContentToContentTypeToExpirationTime(MemberTempDataDomain memberTempDataDomain);
	List<MemberTempDataDomain> findByKeyToContentTypeToExpirationTime(MemberTempDataDomain memberTempDataDomain);
	int updateMemberTempDataExpirationTime(MemberTempDataDomain memberTempDataDomain);

}
