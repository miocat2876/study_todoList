package com.example.famback.fam.board.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardUpdateRequest {
	private String boardKey;
	private String title;
	private String userKey;
	private String content;
}
