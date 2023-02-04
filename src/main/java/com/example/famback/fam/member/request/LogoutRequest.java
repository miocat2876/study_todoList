package com.example.famback.fam.member.request;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogoutRequest {
	private String memberKey;
	private String userAgent;
	private String accessToken;
	private String ip;
}
