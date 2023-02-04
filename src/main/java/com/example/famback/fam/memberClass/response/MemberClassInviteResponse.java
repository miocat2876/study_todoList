package com.example.famback.fam.memberClass.response;

import com.example.famback.fam.memberClass.domain.MemberClassDomain;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberClassInviteResponse {
    private String memberClassKey;

    public static MemberClassInviteResponse mapping(MemberClassDomain memberClassDomain){

        return MemberClassInviteResponse.builder()
                .memberClassKey(memberClassDomain.getNumPk())
                .build();
    }
}
