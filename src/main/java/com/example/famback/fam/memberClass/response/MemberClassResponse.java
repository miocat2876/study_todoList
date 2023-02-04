package com.example.famback.fam.memberClass.response;

import com.example.famback.util.paging.PagingDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberClassResponse extends PagingDto {
	private String numPk;
	private String createDate;
	private String updateDate;
	private String deleteDate;
	private String deleteYn;
	private String className;
	private String nickname;
	private String[] inviteMember;
}