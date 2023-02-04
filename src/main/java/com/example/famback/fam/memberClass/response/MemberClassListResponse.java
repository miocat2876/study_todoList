package com.example.famback.fam.memberClass.response;

import com.example.famback.fam.memberClass.domain.MemberClassDomain;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MemberClassListResponse {
    private String numPk;
    private String className;
    private String userFk;

    public static List<MemberClassListResponse> mapping(List<MemberClassDomain> memberClassDomains){
        List<MemberClassListResponse> userJoinClassResponses = new ArrayList<>();
        memberClassDomains.forEach((memberClassDomain)->{
            MemberClassListResponse userJoinClassResponse = new MemberClassListResponse();
            userJoinClassResponse.setNumPk(memberClassDomain.getNumPk());
            userJoinClassResponse.setClassName(memberClassDomain.getClassName());
            userJoinClassResponse.setUserFk(memberClassDomain.getUserFk());
            userJoinClassResponses.add(userJoinClassResponse);
        });
        return userJoinClassResponses;
    }
}
