package com.example.famback.fam.member.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	private String email;
	private String password;
	private String ip;
	private String userAgent;
}
