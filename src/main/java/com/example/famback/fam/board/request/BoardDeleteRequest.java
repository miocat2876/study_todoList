package com.example.famback.fam.board.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDeleteRequest {
	private String boardKey;
	private String userKey;
	private String memberClassKey;
}
