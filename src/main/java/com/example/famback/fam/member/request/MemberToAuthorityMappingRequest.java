package com.example.famback.fam.member.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberToAuthorityMappingRequest {
	private int mappingNumPk;
	private int mappingMemberFk;
	private String authorityCode;
}
