package com.example.famback.fam.account.request;

import com.example.famback.fam.account.domain.AccountDomain;
import com.example.famback.fam.board.domain.BoardDomain;
import com.example.famback.fam.board.response.BoardListResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AccountListRequest {
    private String memberClassKey;
	private String searchDate;
    private String dateListType;
}
