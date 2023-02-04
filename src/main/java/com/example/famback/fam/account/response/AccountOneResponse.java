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
public class AccountOneResponse {
	private String numPk;
	private String userFk;
	private String nickname;
	private String accountCode;
	private String payCode;
	private String accountName;
	private String payName;
	private String description;
	private int money;
	private String accountTypeCode;
	private String accountGroup;
	private String title;

    public static AccountOneResponse mapping(AccountDomain accountDomains){
		return AccountOneResponse.builder()
				.money(accountDomains.getMoney())
				.accountCode(accountDomains.getAccountCode())
				.numPk(accountDomains.getNumPk())
				.accountTypeCode(accountDomains.getAccountTypeCode())
				.accountGroup(accountDomains.getAccountGroup())
				.accountName(accountDomains.getAccountName())
				.description(accountDomains.getDescription())
				.payName(accountDomains.getPayName())
				.payCode(accountDomains.getPayCode())
				.nickname(accountDomains.getNickname())
				.userFk(accountDomains.getUserFk())
				.title(accountDomains.getTitle())
				.build();
	}
}
