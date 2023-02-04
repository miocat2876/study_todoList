package com.example.famback.fam.member.request;

import com.example.famback.util.paging.PagingDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequest extends PagingDto {
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
