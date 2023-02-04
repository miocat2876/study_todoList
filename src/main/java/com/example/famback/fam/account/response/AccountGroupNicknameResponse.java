package com.example.famback.fam.account.response;

import com.example.famback.fam.account.domain.AccountDomain;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class AccountGroupNicknameResponse {
	private String nickname;
	private String accountTypeCode;
	private int money;
	private String userFk;
    public static List<AccountGroupNicknameResponse> mapping(List<AccountDomain> accountDomains){
		List<AccountGroupNicknameResponse> accountListResponses = new ArrayList<>();
		accountDomains.forEach((accountDomain)-> accountListResponses.add(AccountGroupNicknameResponse.builder()
				.nickname(accountDomain.getNickname())
				.userFk(accountDomain.getUserFk())
				.accountTypeCode(accountDomain.getAccountTypeCode())
				.money(accountDomain.getMoney())
				.build()));
		return accountListResponses;
	}
}
