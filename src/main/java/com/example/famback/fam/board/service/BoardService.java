package com.example.famback.fam.board.service;

import java.util.List;

import com.example.famback.error.custom.CustomException;
import com.example.famback.error.custom.defaultException.NotRequiredDataException;
import com.example.famback.fam.board.domain.BoardDomain;
import com.example.famback.fam.board.mapper.BoardMapper;
import com.example.famback.fam.board.request.*;
import com.example.famback.fam.board.response.BoardDetailResponse;
import com.example.famback.fam.board.response.BoardListResponse;
import com.example.famback.util.paging.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService{

	private final BoardMapper boardMapper;

	//회원리스트 조회
	public BoardListResponse boardList(BoardListRequest boardListRequest) throws CustomException{
		if(boardListRequest == null || boardListRequest.getMemberClassFk() == null || boardListRequest.getCurrentPage() == 0
		 || boardListRequest.getSearchValue() == null) throw new NotRequiredDataException();
		BoardDomain boardDomain = BoardDomain.builder()
				.memberClassFk(boardListRequest.getMemberClassFk())
				.build();
		boardDomain.setSearchValue(boardListRequest.getSearchValue());
		boardDomain.setCurrentPage(boardListRequest.getCurrentPage());
		List<BoardDomain> boardDomains = boardMapper.findBoardListByMemberClassKeyToSearchValue(boardDomain);
		if(boardDomains.size() == 0){
			return null;
		}
		int totalCount = boardMapper.findBoardListCountByMemberClassKeyToSearchValue(boardDomain);
		if(totalCount == 0){
			return null;
		}
		return BoardListResponse.mapping(boardDomains,totalCount,PageUtil.getPage(totalCount));
	}

	public BoardDetailResponse detail(BoardDetailRequest boardDetailRequest) throws CustomException {
		if(boardDetailRequest == null || boardDetailRequest.getNumPk() == null || boardDetailRequest.getMemberClassFk() == null) throw new NotRequiredDataException();
		BoardDomain boardDomain = BoardDomain.builder()
				.numPk(boardDetailRequest.getNumPk())
				.memberClassFk(boardDetailRequest.getMemberClassFk())
				.build();
		BoardDomain domain = boardMapper.findByBoardOne(boardDomain);
		if(domain == null){
			return null;
		}
		return BoardDetailResponse.mapping(domain);
	}

	public Boolean save(BoardSaveRequest boardSaveRequest) throws CustomException {
		if(boardSaveRequest == null || boardSaveRequest.getUserKey() == null || boardSaveRequest.getTitle() == null ||
				boardSaveRequest.getContent() == null ) throw new NotRequiredDataException();
		BoardDomain boardDomain = BoardDomain.builder()
				.userFk(boardSaveRequest.getUserKey())
				.title(boardSaveRequest.getTitle())
				.content(boardSaveRequest.getContent())
				.build();
		return boardMapper.save(boardDomain) == 1;
	}

	public Boolean update(BoardUpdateRequest boardUpdateRequest) throws CustomException {
		if(boardUpdateRequest == null || boardUpdateRequest.getUserKey() == null || boardUpdateRequest.getTitle() == null ||
				boardUpdateRequest.getContent() == null || boardUpdateRequest.getBoardKey() == null) throw new NotRequiredDataException();
		BoardDomain boardDomain = BoardDomain.builder()
				.numPk(boardUpdateRequest.getBoardKey())
				.userFk(boardUpdateRequest.getUserKey())
				.title(boardUpdateRequest.getTitle())
				.content(boardUpdateRequest.getContent())
				.build();
		BoardDomain domain = boardMapper.findByBoardOne(boardDomain);
		if(domain != null && boardDomain.getUserFk().equals(domain.getUserFk())){
			return boardMapper.updateBoard(boardDomain) == 1;
		}
		return false;
	}

	public Boolean delete(BoardDeleteRequest boardDeleteRequest) throws CustomException {
		if(boardDeleteRequest == null || boardDeleteRequest.getUserKey() == null || boardDeleteRequest.getBoardKey() == null
		|| boardDeleteRequest.getMemberClassKey() == null) throw new NotRequiredDataException();
		BoardDomain boardDomain = BoardDomain.builder()
				.numPk(boardDeleteRequest.getBoardKey())
				.memberClassFk(boardDeleteRequest.getMemberClassKey())
				.userFk(boardDeleteRequest.getUserKey())
				.build();
		BoardDomain domain = boardMapper.findByBoardOne(boardDomain);
		if(domain != null && boardDomain.getUserFk().equals(domain.getUserFk())){
			return boardMapper.deleteBoard(boardDomain) == 1;
		}
		return false;
	}
	public boolean countUpdate(BoardCountRequest boardCountRequest) throws CustomException{
		if(boardCountRequest == null || boardCountRequest.getBoardKey() == null) throw new NotRequiredDataException();
		BoardDomain boardDomain = BoardDomain.builder()
				.numPk(boardCountRequest.getBoardKey())
				.build();
		return boardMapper.updateBoardCountByKey(boardDomain) == 1;
	}
}
