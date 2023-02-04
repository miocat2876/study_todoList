package com.example.famback.fam.exemple.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AccountUpdateRequest {
    private String userKey;
    private String searchDate;
    private String accountCode;
    private String payCode;
    private String description;
    private int money;
    private String numPk;
    private String accountTypeCode;
    private String title;
}
