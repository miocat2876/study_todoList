package com.example.famback.fam.jwt.domain;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDomain {
	private String numPk;
	private String crateDate;
	private String deleteDate;
	private String deleteYn;
	private String refreshToken;
	private String userAgent;
	private String memberFk;
	private String accessToken;
	private Date refreshExpirationTime;
	private Date accessExpirationTime;
	private String ip;
	private List<String> roles;
}
