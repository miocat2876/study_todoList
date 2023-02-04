package com.example.famback.fam.board.domain;

import com.example.famback.util.paging.PagingDto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardDomain extends PagingDto {
	private String memberClassKey;
	private int bno;
	private String numPk;
	private String memberFk;
	private String userFk;
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
