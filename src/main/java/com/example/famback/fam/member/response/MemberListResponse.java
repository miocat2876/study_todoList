package com.example.famback.fam.member.response;

import lombok.Data;

@Data
public class MemberListResponse {

    private int member_pk;
    private String member_code;
    private String member_mail;
    private String member_nickname;
    private String member_password;
    private String req_date;
    private String update_date;
    private String member_delete_yn;
}
