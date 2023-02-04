package com.example.famback.fam.board.request;

import com.example.famback.util.paging.PagingDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardReplyRequest extends PagingDto {

    private int bno;
	private int depth;
	private String numPk;
	private String parentNum;
	private String parentNickname;
	private String userFK;
	private String memberClassFk;
	private String memberFk;
    private String boardFk;
	private String content;
	private String nickname;
	private String createDate;
	private String updateDate;
	private String deleteDate;
	private String deleteYn;
}
