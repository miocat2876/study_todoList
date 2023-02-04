package com.example.famback.fam.user.domain;

import com.example.famback.util.paging.PagingDto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDomain extends PagingDto {
	private String numPk;
	private String createDate;
	private String updateDate;
	private String deleteDate;
	private String deleteYn;
	private String memberClassFk;
	private String nickname;
	private String memberFk;
	private String description;
	private String className;
	private String email;
	private String authorityCode;
}