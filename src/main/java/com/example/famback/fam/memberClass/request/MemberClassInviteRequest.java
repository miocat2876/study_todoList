package com.example.famback.fam.memberClass.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberClassInviteRequest {
    private String userKey;
    private String memberKey;
    private String memberClassKey;
    private String[] email;
}
