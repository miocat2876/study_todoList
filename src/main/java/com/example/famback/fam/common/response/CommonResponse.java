package com.example.famback.fam.common.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse {
	private int commonNumPk;
	private String codeGroup;
	private String commonCode;
	private String commonDescription;
	private String commonCreateDate;
	private String commonUseYn;
}
