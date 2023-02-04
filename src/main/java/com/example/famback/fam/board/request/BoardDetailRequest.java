package com.example.famback.fam.board.request;

import com.example.famback.util.paging.PagingDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDetailRequest{
	private String numPk;
	private String memberClassFk;
}
