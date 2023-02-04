package com.example.famback.fam.member.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberToAuthorityMappingDomain {
	private int mappingNumPk;
	private int mappingMemberFk;
	private String authorityCode;
}
