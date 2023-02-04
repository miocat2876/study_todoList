package com.example.famback.fam.jwt.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class JwtRequest {
    private String numPk;
    private String ip;
    private String userAgent;
    private String memberFk;
    private String accessToken;
    private List<String> roles;
}
