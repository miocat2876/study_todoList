package com.example.famback.fam.board.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardSaveRequest {
	private String title;
	private String userKey;
	private String content;
}
