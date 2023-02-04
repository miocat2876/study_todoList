package com.example.famback.fam.member.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMemberRequest {
    private String numPk;
    private String email;
    private String password;
}
