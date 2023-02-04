package com.example.famback.fam.memberClass.response;


import com.example.famback.fam.memberClass.domain.MemberClassDomain;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class InviteMemberClassesResponse {

    private String numPk;
	private String className;

    public static List<InviteMemberClassesResponse> mapping(List<MemberClassDomain> memberClassDomains){
        List<InviteMemberClassesResponse> inviteMemberClassesResponses = new ArrayList<>();
        memberClassDomains.forEach((memberClassDomain)->{
            InviteMemberClassesResponse inviteMemberClassesResponse = new InviteMemberClassesResponse();
            inviteMemberClassesResponse.setNumPk(memberClassDomain.getNumPk());
            inviteMemberClassesResponse.setClassName(memberClassDomain.getClassName());
            inviteMemberClassesResponses.add(inviteMemberClassesResponse);
        });
        return inviteMemberClassesResponses;
    }

    public static InviteMemberClassesResponse mapping(MemberClassDomain memberClassDomains){
        InviteMemberClassesResponse inviteMemberClassesResponses = new InviteMemberClassesResponse();
        inviteMemberClassesResponses.setClassName(memberClassDomains.getClassName());
        inviteMemberClassesResponses.setNumPk(memberClassDomains.getNumPk());
        return inviteMemberClassesResponses;
    }
}