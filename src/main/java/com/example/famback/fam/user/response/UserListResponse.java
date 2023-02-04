package com.example.famback.fam.user.response;

import com.example.famback.fam.memberClass.domain.MemberClassDomain;
import com.example.famback.fam.memberClass.response.MemberClassListResponse;
import com.example.famback.fam.user.domain.UserDomain;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserListResponse {
	private String numPk;
	private String createDate;
	private String nickname;
	private String description;
	private String authorityCode;

	public static List<UserListResponse> mapping(List<UserDomain> userDomains){
        List<UserListResponse> userListResponses = new ArrayList<>();
        userDomains.forEach((userDomain)->{
            UserListResponse userListResponse = new UserListResponse();
            userListResponse.setNumPk(userDomain.getNumPk());
			userListResponse.setCreateDate(userDomain.getCreateDate());
			userListResponse.setNickname(userDomain.getNickname());
			userListResponse.setDescription(userDomain.getDescription());
			userListResponse.setAuthorityCode(userDomain.getAuthorityCode());
            userListResponses.add(userListResponse);
        });
        return userListResponses;
    }
}