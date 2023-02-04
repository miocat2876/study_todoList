package com.example.famback.fam.account.request;

import com.example.famback.util.paging.PagingDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AccountGroupAccountTypeRequest{
	private String memberClassKey;
	private String searchDate;
}
