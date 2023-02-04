package com.example.famback.fam.account.domain;

import com.example.famback.util.paging.PagingDto;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDomain extends PagingDto {
	private String accessToken;
	private String numPk;
	private String userFk;
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
	private String rownum;
	private String accountTypeName;
	private String accountDate;
	private String email;
	private String title;
	private int accountCount;
}
