package com.example.famback.fam.common.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonDomain {
	private int commonNumPk;
	private String codeGroup;
	private String commonCode;
	private String commonDescription;
	private String commonCreateDate;
	private String commonUseYn;
	private String[] codeKeys;
}
