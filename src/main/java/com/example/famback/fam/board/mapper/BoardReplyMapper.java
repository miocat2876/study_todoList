package com.example.famback.fam.board.mapper;

import com.example.famback.fam.board.domain.BoardReplyDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardReplyMapper {
	List<BoardReplyDomain> findByBoardKey(BoardReplyDomain boardReplyDomain);
	int findCountByBoardFk(BoardReplyDomain boardReplyDomain);
	int commentSave(BoardReplyDomain boardReplyDomain);
}
