package com.example.famback.fam.memberClass.response;


import com.example.famback.fam.board.domain.BoardDomain;
import com.example.famback.fam.memberClass.domain.MemberClassDomain;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
public class MemberClassInfoResponse {

    private String numPk;
	private String className;

    public static MemberClassInfoResponse mapping(MemberClassDomain memberClassDomain){
        return MemberClassInfoResponse.builder()
                .numPk(memberClassDomain.getNumPk())
                .className(memberClassDomain.getClassName())
                .build();
    }
}