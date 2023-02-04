package com.example.famback.fam.jwt.response;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private String refreshExpirationTime;
    private String accessExpirationTime;
    private String refreshKey;
}