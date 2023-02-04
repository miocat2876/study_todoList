package com.example.famback.fam.board.domain;

import com.example.famback.util.paging.PagingDto;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardReplyDomain extends PagingDto {

    private int bno;
	private int depth;
	private String numPk;
	private String parentNum;
	private String parentNickname;
	private String userFk;
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
