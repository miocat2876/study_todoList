package com.example.famback.fam.user.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDeleteRequest {
	private String userKey;
	private String deleteTargetUserKey;
}