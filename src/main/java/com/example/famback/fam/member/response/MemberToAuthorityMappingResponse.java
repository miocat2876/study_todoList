package com.example.famback.fam.member.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberToAuthorityMappingResponse {
	private int mappingNumPk;
	private int mappingMemberFk;
	private String authorityCode;
}
