package com.example.famback.fam.board.response;

import com.example.famback.fam.board.domain.BoardDomain;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class BoardListResponse {
	private List<BoardListDto> list;
	private int count;
	private int page;

	public static BoardListResponse mapping(List<BoardDomain> boardDomains, int totalCount, int page){
		List<BoardListDto> boardListDtos = new ArrayList<>();
		boardDomains.forEach((boardDomain)->{
			boardListDtos.add(BoardListDto.builder()
					.numPk(boardDomain.getNumPk())
					.bno(boardDomain.getBno())
					.title(boardDomain.getTitle())
					.nickName(boardDomain.getNickName())
					.createDate(boardDomain.getCreateDate())
					.count(boardDomain.getCount())
					.build());
		});
		return BoardListResponse.builder()
					.list(boardListDtos)
					.count(totalCount)
					.page(page)
					.build();
	}

	@Getter
	@Setter
	@Builder
	private static class BoardListDto{
		private String numPk;
		private int bno;
		private String title;
		private String nickName;
		private String createDate;
		private int count;
	}
}
