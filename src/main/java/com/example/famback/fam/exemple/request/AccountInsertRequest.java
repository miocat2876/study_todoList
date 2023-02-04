package com.example.famback.fam.exemple.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountInsertRequest {
    private String userKey;
    private String memberClassKey;
	private String accountCode;
    private String payCode;
    private String description;
    private int money;
    private String accountTypeCode;
    private String searchDate;
    private String title;
}
