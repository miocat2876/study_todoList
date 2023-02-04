package com.example.famback.fam.board.response;

import com.example.famback.util.paging.PagingDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardResponse extends PagingDto {
	private int bno;
	private String numPk;
	private String memberFk;
	private String userFK;
	private String className;
	private String title;
	private String content;
	private String nickName;
	private String createDate;
	private String updateDate;
	private String deleteDate;
	private String deleteYn;
	private String memberClassFk;
	private int count;
	private String categoryCode;
	private String categoryName;
	
}
