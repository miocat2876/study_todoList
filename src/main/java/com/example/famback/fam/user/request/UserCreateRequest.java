package com.example.famback.fam.user.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {
	private String nickname;
	private String description;
	private String authorityCode;
	private String memberClassKey;
	private String memberKey;
}