package com.example.famback.fam.board.controller;

import com.example.famback.error.custom.defaultException.CustomDefaultCodeType;
import com.example.famback.error.custom.CustomException;
import com.example.famback.error.custom.CustomCodeGroup;
import com.example.famback.fam.board.request.*;
import com.example.famback.fam.board.response.BoardDetailResponse;
import com.example.famback.fam.board.response.BoardListResponse;
import com.example.famback.fam.board.response.BoardReplyListResponse;
import com.example.famback.fam.board.service.BoardReplyService;
import com.example.famback.fam.board.service.BoardService;
import com.example.famback.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/class/{memberClassKey}")
public class BoardController {

	private final BoardService boardService;
	private final BoardReplyService boardReplyService;

	//전체조회
	@GetMapping("/board")
	public ResponseEntity<ResponseDto<BoardListResponse>> boardList(@PathVariable String memberClassKey, BoardListRequest boardListRequest) throws Exception {
		log.debug("[Get] boardList");
		boardListRequest.setMemberClassFk(memberClassKey);
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		BoardListResponse boardListResponse = boardService.boardList(boardListRequest);
		if(boardListResponse != null){
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<BoardListResponse>builder()
				.customCodeGroup(customCodeGroup)
				.body(boardListResponse)
				.build(),
				new HttpHeaders(),
				HttpStatus.OK.value());
	}

	//상세조회
	@GetMapping("/board/{boardKey}")
	public ResponseEntity<ResponseDto<BoardDetailResponse>> detail(@PathVariable String memberClassKey, @PathVariable String boardKey, BoardDetailRequest boardDetailRequest) throws CustomException {
		log.debug("[Get] boardDetail");
		boardDetailRequest.setNumPk(boardKey);
		boardDetailRequest.setMemberClassFk(memberClassKey);
		log.debug("boardDetail => " + boardDetailRequest.toString());
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		BoardDetailResponse boardDetailResponse = boardService.detail(boardDetailRequest);
		if(boardDetailResponse != null){
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<BoardDetailResponse>builder()
				.customCodeGroup(customCodeGroup)
				.body(boardDetailResponse)
				.build(),
				new HttpHeaders(),
				HttpStatus.OK.value());
	}

	//등록
	@PostMapping("/board")
	public ResponseEntity<ResponseDto<String>> boardSave(@RequestAttribute("userKey") String userKey, @RequestBody BoardSaveRequest boardSaveRequest) throws CustomException {
		boardSaveRequest.setUserKey(userKey);
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		if(boardService.save(boardSaveRequest)){
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<String>builder()
				.customCodeGroup(customCodeGroup)
				.body("")
				.build(),
				new HttpHeaders(),
				HttpStatus.OK.value());
	}

	//수정
	@PutMapping("/board")
	public ResponseEntity<ResponseDto<String>> update(@RequestAttribute("userKey") String userKey, @RequestBody BoardUpdateRequest boardUpdateRequest) throws CustomException {
		log.debug("[Put] board");
		boardUpdateRequest.setUserKey(userKey);
		System.out.println(boardUpdateRequest.toString());
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		if(boardService.update(boardUpdateRequest)){
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<String>builder()
				.customCodeGroup(customCodeGroup)
				.body("")
				.build(),
				new HttpHeaders(),
				HttpStatus.OK.value());
	}

	//삭제
	@DeleteMapping("/board/{boardKey}")
	public ResponseEntity<ResponseDto<String>> boardDelete(@PathVariable String memberClassKey, @RequestAttribute("userKey") String userKey, @PathVariable String boardKey) throws CustomException {
		log.debug("[delete] board");
		BoardDeleteRequest boardDeleteRequest = new BoardDeleteRequest();
		boardDeleteRequest.setUserKey(userKey);
		boardDeleteRequest.setBoardKey(boardKey);
		boardDeleteRequest.setMemberClassKey(memberClassKey);
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		if(boardService.delete(boardDeleteRequest)){
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<String>builder()
				.customCodeGroup(customCodeGroup)
				.body("")
				.build(),
				new HttpHeaders(),
				HttpStatus.OK.value());
	}

	//댓글 전체조회
	@GetMapping("/board/{boardKey}/comments")
	public ResponseEntity<ResponseDto<BoardReplyListResponse>> boardReplyList(@PathVariable String memberClassKey,@PathVariable String boardKey, BoardReplyListRequest boardReplyListRequest) throws CustomException {
		log.debug("[get] comments");
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		boardReplyListRequest.setBoardKey(boardKey);
		BoardReplyListResponse boardReplyListResponse = boardReplyService.boardReplyList(boardReplyListRequest);
		if(boardReplyListResponse != null){
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<BoardReplyListResponse>builder()
				.customCodeGroup(customCodeGroup)
				.body(boardReplyListResponse)
				.build(),
				new HttpHeaders(),
				HttpStatus.OK.value());
	}

	//댓글 등록
	@PostMapping("/board/{boardKey}/comments")
	public ResponseEntity<ResponseDto<String>> commentSave(@RequestAttribute("userKey") String userKey, @PathVariable String memberClassKey, @PathVariable String boardKey, @RequestBody BoardReplySaveRequest boardReplySaveRequest) throws CustomException {
		log.debug("[post] comments");
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		boardReplySaveRequest.setBoardKey(boardKey);
		boardReplySaveRequest.setUserKey(userKey);
		boardReplySaveRequest.setMemberClassKey(memberClassKey);
		if(boardReplyService.commentSave(boardReplySaveRequest)){
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<String>builder()
				.customCodeGroup(customCodeGroup)
				.body("")
				.build(),
				new HttpHeaders(),
				HttpStatus.OK.value());
	}
	@PutMapping("/board/{boardKey}/count")
	public ResponseEntity<ResponseDto<String>> boardCount(@PathVariable String boardKey, BoardCountRequest boardCountRequest) throws CustomException {
		log.debug("[get] count");
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		boardCountRequest.setBoardKey(boardKey);
		if(boardService.countUpdate(boardCountRequest)){
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<String>builder()
				.customCodeGroup(customCodeGroup)
				.body("")
				.build(),
				new HttpHeaders(),
				HttpStatus.OK.value());
	}
}
