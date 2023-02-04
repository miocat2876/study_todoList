package com.example.famback.fam.account.request;

import com.example.famback.util.paging.PagingDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountGroupNicknameRequest {
	private String memberClassKey;
	private String searchDate;
}
