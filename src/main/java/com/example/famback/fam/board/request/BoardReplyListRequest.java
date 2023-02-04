package com.example.famback.fam.board.request;

import com.example.famback.util.paging.PagingDto;
import lombok.*;

@Getter
@Setter
public class BoardReplyListRequest {
	private int currentPage;
	private String boardKey;
}
