package com.example.famback.fam.user.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserAuthorityRequest {
    private String userKey;
}
