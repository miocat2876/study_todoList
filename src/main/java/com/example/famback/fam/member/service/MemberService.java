package com.example.famback.fam.member.service;

import com.example.famback.error.custom.CustomException;
import com.example.famback.error.custom.defaultException.AbnormalRequestException;
import com.example.famback.error.custom.defaultException.NotRequiredDataException;
import com.example.famback.fam.member.exception.CreateMemberFailException;
import com.example.famback.fam.member.exception.DeleteMemberException;
import com.example.famback.fam.member.exception.DuplicateMemberException;
import com.example.famback.fam.member.exception.NotMemberException;
import com.example.famback.fam.jwt.exception.NotJwtUserInfo;
import com.example.famback.fam.jwt.request.JwtRequest;
import com.example.famback.fam.member.domain.MemberDomain;
import com.example.famback.fam.member.domain.MemberTempDataDomain;
import com.example.famback.fam.member.mapper.MemberMapper;
import com.example.famback.fam.jwt.response.Token;
import com.example.famback.fam.jwt.service.JwtService;
import com.example.famback.fam.member.request.*;
import com.example.famback.fam.member.response.MemberOneResponse;
import com.example.famback.fam.memberClass.domain.MemberClassDomain;
import com.example.famback.fam.memberClass.request.MemberClassListRequest;
import com.example.famback.fam.memberClass.response.MemberClassListResponse;
import com.example.famback.fam.memberClass.service.MemberClassService;
import com.example.famback.fam.user.domain.UserDomain;
import com.example.famback.fam.user.request.UserDeleteAllRequest;
import com.example.famback.fam.user.request.UserDeleteRequest;
import com.example.famback.fam.user.service.UserService;
import com.example.famback.type.EmailTemplateType;
import com.example.famback.type.TempContentType;
import com.example.famback.util.MailServerClient;
import com.example.famback.util.SHA256Cipher;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberMapper memberMapper;
	private final UserService userService;
	private final JwtService jwtService;

	//회원 조회
	public MemberOneResponse memberOne(MemberOneRequest memberOneRequest) throws CustomException{
		if(memberOneRequest == null || memberOneRequest.getMemberKey() == null) throw new NotRequiredDataException();
		log.debug("memberOne => " + memberOneRequest.getMemberKey());
		MemberDomain memberDomain = MemberDomain.builder()
				.numPk(memberOneRequest.getMemberKey())
				.build();
		MemberDomain domain = memberMapper.memberOneFindByKey(memberDomain);
		if(domain == null){
			return null;
		}
		return MemberOneResponse.mapping(domain);
	}
	//멤버 탈퇴
	//가입중인 방리스트
	//유저 삭제
	//토큰 삭제
	//멤버 삭제
	@Transactional
	public boolean memberDelete(MemberDeleteRequest memberDeleteRequest) throws CustomException{
		if(memberDeleteRequest == null  || memberDeleteRequest.getMemberKey() == null) throw new NotRequiredDataException();
		log.debug("memberDelete=>" + memberDeleteRequest.getMemberKey());
		MemberClassListRequest memberClassListRequest = new MemberClassListRequest();
		memberClassListRequest.setMemberKey(memberDeleteRequest.getMemberKey());
		JwtRequest jwtRequest = JwtRequest.builder()
				.memberFk(memberDeleteRequest.getMemberKey())
				.build();
		if(jwtService.deleteMemberAllTokenByKey(jwtRequest) == 0){ //접속 할 수 있는 토큰이 없으나. 삭제 요청이 들어옴.
			throw new AbnormalRequestException();
		};
		UserDeleteAllRequest userDeleteAllRequest = new UserDeleteAllRequest();
		userDeleteAllRequest.setMemberKey(memberDeleteRequest.getMemberKey());
		userService.deleteUserByMemberKey(userDeleteAllRequest);
		MemberDomain memberDomain = MemberDomain.builder()
				.numPk(memberDeleteRequest.getMemberKey())
				.build();
		if(memberMapper.memberDelete(memberDomain) != 1){
			throw new DeleteMemberException();
		}
		return true;
	}
	//회원리스트 조회
	public Map<String,Object> memberList(MemberDomain memberDomain){
		Map<String,Object> result = new HashMap<>();
		result.put("members",memberMapper.memberList(memberDomain));
		result.put("totalCount",memberMapper.memberCount(memberDomain));
		return result;
	}
    @Transactional
	public Token memberLogin(LoginRequest loginRequest) throws CustomException {
		if(loginRequest == null || loginRequest.getEmail() == null || loginRequest.getPassword() == null
				|| loginRequest.getUserAgent() == null ) throw new NotRequiredDataException();
		String password = SHA256Cipher.generateSHA256(loginRequest.getPassword());
		MemberDomain memberDomain = MemberDomain.builder()
									   .ip(loginRequest.getIp())
									   .email(loginRequest.getEmail())
									   .password(password)
									   .userAgent(loginRequest.getUserAgent())
									   .build();
		String memberKey  = memberMapper.findByMember(memberDomain);
		if(memberKey == null) throw new NotMemberException();
		JwtRequest jwtRequest = JwtRequest.builder()
				.memberFk(memberKey)
				.userAgent(memberDomain.getUserAgent())
				.ip(memberDomain.getIp())
				.build();
		if(jwtService.existsByKey(jwtRequest)){
			jwtService.deleteToken(jwtRequest);
		}
		return jwtService.createToken(jwtRequest);
	}

	public boolean memberLogout(LogoutRequest logoutRequest) throws CustomException {
		if(logoutRequest == null || logoutRequest.getMemberKey() == null ||logoutRequest.getIp() == null
		   || logoutRequest.getUserAgent() == null) throw new NotRequiredDataException();
		log.debug("memberLogout");
		JwtRequest jwtRequest = JwtRequest.builder()
				.ip(logoutRequest.getIp())
				.userAgent(logoutRequest.getUserAgent())
				.memberFk(logoutRequest.getMemberKey())
				.build();
		return jwtService.deleteToken(jwtRequest);
	}
	//회원 활성화
	@Transactional
	public boolean MemberActive(MemberValidationRequest memberValidationRequest) throws CustomException{
		if(memberValidationRequest == null || memberValidationRequest.getEmail() == null
			|| memberValidationRequest.getCode() == null) throw new NotRequiredDataException();
		MemberTempDataDomain memberTempDataDomain = MemberTempDataDomain.builder()
				.content(memberValidationRequest.getCode())
				.key(memberValidationRequest.getEmail())
				.contentType(TempContentType.MEMBER_AUTH_CODE.getContentType())
				.expirationTime(new Date(new Date().getTime()))
				.build();
		MemberTempDataDomain domain = memberMapper.findByKeyToContentToContentTypeToExpirationTime(memberTempDataDomain);
		if(domain != null){
			return true;
		}
		return false;
	}

	//회원가입
	@Transactional
	public boolean memberCreate(CreateMemberRequest createMemberRequest) throws CustomException {
		if(createMemberRequest == null || createMemberRequest.getEmail() == null || createMemberRequest.getPassword() == null)
			throw new NotRequiredDataException();
		String password = SHA256Cipher.generateSHA256(createMemberRequest.getPassword());
		int lastMemberNum = memberMapper.memberLastNumber();
		MemberDomain memberDomain = MemberDomain.builder()
						.numPk(String.valueOf(lastMemberNum + 1))
						.email(createMemberRequest.getEmail())
						.password(password)
						.build();
		if(memberMapper.existsByEmail(memberDomain.getEmail()) != 0){
			throw new DuplicateMemberException();
		}
		if(memberMapper.memberCreate(memberDomain) == 0){
			throw new CreateMemberFailException();
		}
		return true;
	}
	public boolean memberValidationKeySender(MemberValidationRequest memberValidationRequest) throws Exception {
		if(memberValidationRequest == null || memberValidationRequest.getEmail() == null)
			throw new NotRequiredDataException();
		MemberTempDataDomain memberTempDataDomain = MemberTempDataDomain.builder()
				.key(memberValidationRequest.getEmail())
				.contentType(TempContentType.MEMBER_AUTH_CODE.getContentType())
				.expirationTime(new Date(new Date().getTime() + 60 * 60 * 1000L))
				.build();
		if(memberMapper.existsByEmail(memberValidationRequest.getEmail()) != 0){
			throw new DuplicateMemberException();
		}
		Map<String,String> responseEntityMap =  new MailServerClient()
				.setMailType(MailServerClient.MailType.CUSTOM)
				.setRecipient(memberTempDataDomain.getKey())
				.setMailTemplate(MailServerClient.MailTemplateType.DEFAULT)
				.setProjectName("fam")
				.setMailOption(MailServerClient.MailOptionType.RANDOM_KEY)
				.setText(EmailTemplateType.EMAIL_AUTH_CODE.getEmailTemplate())
				.setSubject("fam 프로젝트 인증키 발송입니다.")
				.call();
		//해당 타입과 키가 존재하면 업데이트
		memberTempDataDomain.setContent(responseEntityMap.get("data"));
		return memberMapper.insertMemberTempData(memberTempDataDomain) > 0;
	}
}
