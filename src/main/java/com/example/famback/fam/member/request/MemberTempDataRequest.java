package com.example.famback.fam.member.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberTempDataRequest extends MemberRequest {
	private int numPk;
	private String createDate;
	private String updateDate;
	private String contentType;
	private String content;
	private String memberFk;
}
