package com.example.famback.fam.exemple.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountListRequest {
    private String memberClassKey;
	private String searchDate;
    private String dateListType;
}
