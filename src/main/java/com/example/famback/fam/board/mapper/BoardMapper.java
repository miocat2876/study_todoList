package com.example.famback.fam.board.mapper;

import com.example.famback.fam.board.domain.BoardDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
	List<BoardDomain> boardClass(BoardDomain boardDomain);
	List<BoardDomain> findBoardListByMemberClassKeyToSearchValue(BoardDomain boardDomain);
	int save(BoardDomain boardDomain);
	BoardDomain findByBoardOne(BoardDomain boardDomain);
	int updateBoard(BoardDomain boardDomain);
	int deleteBoard(BoardDomain boardDomain);
	int findBoardListCountByMemberClassKeyToSearchValue(BoardDomain boardDomain);
	int updateBoardCountByKey(BoardDomain boardDomain);
}
