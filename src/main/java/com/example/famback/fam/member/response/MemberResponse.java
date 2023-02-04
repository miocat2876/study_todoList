package com.example.famback.fam.member.response;

import com.example.famback.util.paging.PagingDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponse extends PagingDto {
	private String memberPk;
	private String memberCreateDate;
	private String memberUpdateDate;
	private String memberDeleteDate;
	private String memberDeleteYn;
	private String email;
	private String password;
	private String authorityCode;
	private String authorityName;
	private String state;
}
