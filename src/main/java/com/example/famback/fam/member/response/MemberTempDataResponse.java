package com.example.famback.fam.member.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberTempDataResponse extends MemberResponse {
	private int numPk;
	private String createDate;
	private String updateDate;
	private String contentType;
	private String content;
	private String memberFk;
}
