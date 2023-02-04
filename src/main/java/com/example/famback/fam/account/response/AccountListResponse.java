package com.example.famback.fam.account.response;

import com.example.famback.fam.account.domain.AccountDomain;
import com.example.famback.fam.account.request.AccountListRequest;
import com.example.famback.fam.board.response.BoardListResponse;
import com.example.famback.util.paging.PagingDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class AccountListResponse {
	private String accountDate;
	private int money;
	private String accountTypeCode;
	private String startDate;
	private String endDate;
	private String nickname;
	private String numPk;
	private String payCode;
	private String accountCode;
	private String email;
	private String payName;
	private String accountName;
	private String accountTypeName;
	private String description;
	private String title;
    public static List<AccountListResponse> mapping(List<AccountDomain> accountDomains){
		List<AccountListResponse> accountListResponses = new ArrayList<>();
		accountDomains.forEach((accountDomain)-> accountListResponses.add(AccountListResponse.builder()
				.money(accountDomain.getMoney())
				.accountDate(accountDomain.getAccountDate())
				.accountTypeCode(accountDomain.getAccountTypeCode())
				.startDate(accountDomain.getStartDate())
				.endDate(accountDomain.getEndDate())
				.nickname(accountDomain.getNickname())
				.numPk(accountDomain.getNumPk())
				.payCode(accountDomain.getPayCode())
				.accountCode(accountDomain.getAccountCode())
				.email(accountDomain.getEmail())
				.payName(accountDomain.getPayName())
				.accountName(accountDomain.getAccountName())
				.accountTypeName(accountDomain.getAccountTypeName())
				.description(accountDomain.getDescription())
				.title(accountDomain.getTitle())
				.build()));
		return accountListResponses;
	}
}
