package com.example.famback.fam.user.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModifyRequest {
    private String userKey;
    private String nickname;
	private String description;
}
