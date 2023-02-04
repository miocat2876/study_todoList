package com.example.famback.fam.member.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberValidationRequest {
    private String email;
    private String code;
}
