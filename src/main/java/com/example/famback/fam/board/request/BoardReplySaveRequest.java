package com.example.famback.fam.board.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardReplySaveRequest {
	private String boardKey;
	private String content;
	private String parentNum;
	private String userKey;
	private String memberClassKey;
}
