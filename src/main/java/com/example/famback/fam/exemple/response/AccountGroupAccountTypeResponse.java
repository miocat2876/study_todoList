package com.example.famback.fam.exemple.response;

import com.example.famback.fam.exemple.domain.AccountDomain;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class AccountGroupAccountTypeResponse {
	private int accountCount;
	private String accountName;
	private String accountTypeName;

    public static List<AccountGroupAccountTypeResponse> mapping(List<AccountDomain> accountDomains){
		List<AccountGroupAccountTypeResponse> accountListResponses = new ArrayList<>();
		accountDomains.forEach((accountDomain)-> accountListResponses.add(AccountGroupAccountTypeResponse.builder()
				.accountCount(accountDomain.getAccountCount())
				.accountName(accountDomain.getAccountName())
				.accountTypeName(accountDomain.getAccountTypeName())
				.build()));
		return accountListResponses;
	}
}
