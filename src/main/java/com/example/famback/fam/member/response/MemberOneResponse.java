package com.example.famback.fam.member.response;

import com.example.famback.fam.member.domain.MemberDomain;
import com.example.famback.util.paging.PagingDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberOneResponse{
	private String numPk;
	private String createDate;
	private String email;

	public static MemberOneResponse mapping(MemberDomain memberDomain){
		return MemberOneResponse.builder()
				.createDate(memberDomain.getCreateDate())
				.email(memberDomain.getEmail())
				.numPk(memberDomain.getEmail())
				.build();
	}
}
