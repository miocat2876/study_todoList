package com.example.famback.fam.account.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDeleteRequest {
    private String numPk;
    private String searchDate;
    private String accountTypeCode;
    private String userKey;
}
