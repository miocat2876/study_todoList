package com.example.famback.fam.board.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardListRequest{
	private String memberClassFk;
	private int currentPage;
	private String searchValue;
}
