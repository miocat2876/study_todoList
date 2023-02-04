package com.example.famback.fam.board.service;

import com.example.famback.error.custom.CustomException;
import com.example.famback.error.custom.defaultException.NotRequiredDataException;
import com.example.famback.fam.board.request.BoardCountRequest;
import com.example.famback.fam.member.exception.NotMemberException;
import com.example.famback.fam.board.domain.BoardDomain;
import com.example.famback.fam.board.domain.BoardReplyDomain;
import com.example.famback.fam.board.mapper.BoardMapper;
import com.example.famback.fam.board.mapper.BoardReplyMapper;
import com.example.famback.fam.board.request.BoardReplyListRequest;
import com.example.famback.fam.board.request.BoardReplySaveRequest;
import com.example.famback.fam.board.response.BoardReplyListResponse;
import com.example.famback.fam.user.mapper.UserMapper;
import com.example.famback.util.paging.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardReplyService {

	private final BoardReplyMapper boardReplyMapper;
	private final BoardMapper boardMapper;

	//회원리스트 조회
	public BoardReplyListResponse boardReplyList(BoardReplyListRequest boardReplyListRequest) throws CustomException {
		if(boardReplyListRequest == null || boardReplyListRequest.getBoardKey() == null || boardReplyListRequest.getCurrentPage() == 0) throw new NotRequiredDataException();
		BoardReplyDomain boardReplyDomain = BoardReplyDomain.builder()
				.boardFk(boardReplyListRequest.getBoardKey())
				.build();
		boardReplyDomain.setCurrentPage(boardReplyListRequest.getCurrentPage());
		List<BoardReplyDomain> boardReplyDomains = boardReplyMapper.findByBoardKey(boardReplyDomain);
		if(boardReplyDomains.size() == 0 ){
			return null;
		}
		int totalCount = boardReplyMapper.findCountByBoardFk(boardReplyDomain);
		if(totalCount == 0 ){
			return null;
		}
		return BoardReplyListResponse.mapping(boardReplyDomains,totalCount,PageUtil.getPage(totalCount));
	}

	public boolean commentSave(BoardReplySaveRequest boardReplySaveRequest) throws CustomException{
		if(boardReplySaveRequest == null || boardReplySaveRequest.getBoardKey() == null || boardReplySaveRequest.getUserKey() == null
				|| boardReplySaveRequest.getContent() == null || boardReplySaveRequest.getParentNum() == null
				|| boardReplySaveRequest.getMemberClassKey() == null) throw new NotRequiredDataException();
		BoardReplyDomain boardReplyDomain = BoardReplyDomain.builder()
				.boardFk(boardReplySaveRequest.getBoardKey())
				.userFk(boardReplySaveRequest.getUserKey())
				.content(boardReplySaveRequest.getContent())
				.parentNum(boardReplySaveRequest.getParentNum())
				.build();
		BoardDomain boardDomain = new BoardDomain();
		boardDomain.setNumPk(boardReplySaveRequest.getBoardKey());
		boardDomain.setMemberClassFk(boardReplySaveRequest.getMemberClassKey());
		BoardDomain domain = boardMapper.findByBoardOne(boardDomain);
		if(domain != null && boardReplyDomain.getUserFk().equals(domain.getUserFk())){
			return boardReplyMapper.commentSave(boardReplyDomain) == 1;
		}
		return false;
	}

}
