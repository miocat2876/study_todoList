package com.example.famback.fam.account.response;

import com.example.famback.util.paging.PagingDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountResponse extends PagingDto {
	private String accessToken;
	private String accountNumPk;
	private String userFK;
	private String accountCreateDate;
	private String accountUpdateDate;
	private String accountDeleteDate;
	private String accountDeleteYn;
	private String accountCode;
	private String accountName;
	private String payCode;
	private String payName;
	private String nickname;
	private String description;
	private int money;
	private String accountTypeCode;
	private String accountGroup;
	private String memberClassFk;
	private String searchDate;
	private String memberKey;
	private String startDate;
	private String endDate;
}
