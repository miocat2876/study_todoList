package com.example.famback.fam.member.domain;

import com.example.famback.util.paging.PagingDto;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDomain extends PagingDto {
	private String numPk;
	private String createDate;
	private String updateDate;
	private String deleteDate;
	private String deleteYn;
	private String authorityCode;
	private String authorityName;

	//조회조건
	private String email;
	private String password;
	private String userAgent;
	private String ip;
}
