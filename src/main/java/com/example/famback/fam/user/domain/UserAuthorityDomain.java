package com.example.famback.fam.user.domain;

import com.example.famback.util.paging.PagingDto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthorityDomain{
	private String numPk;
	private String userFk;
	private String authorityCode;
}