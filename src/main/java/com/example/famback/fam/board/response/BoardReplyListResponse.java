package com.example.famback.fam.board.response;

import com.example.famback.fam.board.domain.BoardReplyDomain;
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
public class BoardReplyListResponse {
	private List<BoardReplyListDto> list;
	private int count;
	private int page;

	public static BoardReplyListResponse mapping(List<BoardReplyDomain> boardReplyDomains, int totalCount, int page){
		List<BoardReplyListDto> boardReplyListDtos = new ArrayList<>();
		boardReplyDomains.forEach((boardReplyDomain)->{
			boardReplyListDtos.add(BoardReplyListDto.builder()
					.numPk(boardReplyDomain.getNumPk())
					.depth(boardReplyDomain.getDepth())
					.nickname(boardReplyDomain.getNickname())
					.parentNickname(boardReplyDomain.getParentNickname())
					.userFk(boardReplyDomain.getUserFk())
					.createDate(boardReplyDomain.getCreateDate())
					.content(boardReplyDomain.getContent())
					.build());
		});
		return BoardReplyListResponse.builder()
					.list(boardReplyListDtos)
					.count(totalCount)
					.page(page)
					.build();
	}

	@Getter
	@Setter
	@Builder
	private static class BoardReplyListDto{
		private int depth;
		private String numPk;
		private String nickname;
		private String parentNickname;
		private String userFk;
		private String createDate;
		private String content;
	}
}
