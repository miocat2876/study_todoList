package com.example.famback.fam.memberClass.domain;

import com.example.famback.util.paging.PagingDto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberClassDomain extends PagingDto {
	private String numPk;
	private String createDate;
	private String updateDate;
	private String deleteDate;
	private String deleteYn;
	private String className;
	private String nickname;
	private String[] inviteMember;
	private String memberKey;
	private String userFk;
}