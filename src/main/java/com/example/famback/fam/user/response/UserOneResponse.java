package com.example.famback.fam.user.response;

import com.example.famback.fam.memberClass.domain.MemberClassDomain;
import com.example.famback.fam.memberClass.response.MemberClassInfoResponse;
import com.example.famback.fam.user.domain.UserDomain;
import com.example.famback.util.paging.PagingDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserOneResponse{
	private String numPk;
	private String createDate;
	private String updateDate;
	private String deleteDate;
	private String deleteYn;
	private String memberClassFk;
	private String nickname;
	private String memberFk;
	private String description;
	private String email;
	private String authorityCode;

	public static UserOneResponse mapping(UserDomain userDomain){
		return UserOneResponse.builder()
				.numPk(userDomain.getNumPk())
				.createDate(userDomain.getCreateDate())
				.memberClassFk(userDomain.getMemberClassFk())
				.nickname(userDomain.getNickname())
				.description(userDomain.getDescription())
				.email(userDomain.getEmail())
				.authorityCode(userDomain.getAuthorityCode())
				.build();
	}
}