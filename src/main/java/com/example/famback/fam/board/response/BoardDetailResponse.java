package com.example.famback.fam.board.response;

import com.example.famback.fam.board.domain.BoardDomain;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class BoardDetailResponse {
	private String numPk;
	private String title;
	private String content;
	private String nickName;
	private String createDate;
	private String userFk;
	private int count;

	public static BoardDetailResponse mapping(BoardDomain boardDomains){
		return BoardDetailResponse.builder()
				.title(boardDomains.getTitle())
				.content(boardDomains.getContent())
				.createDate(boardDomains.getCreateDate())
				.nickName(boardDomains.getNickName())
				.count(boardDomains.getCount())
				.numPk(boardDomains.getNumPk())
				.userFk(boardDomains.getUserFk())
				.build();
	}
}
