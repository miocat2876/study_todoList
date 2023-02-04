package com.example.famback.fam.member.domain;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberTempDataDomain {
	private int numPk;
	private String createDate;
	private String updateDate;
	private String contentType;
	private String content;
	private String key;
	private Date expirationTime;
}
