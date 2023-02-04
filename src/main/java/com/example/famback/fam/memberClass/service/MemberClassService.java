package com.example.famback.fam.memberClass.service;

import com.example.famback.error.custom.CustomException;
import com.example.famback.error.custom.defaultException.NotRequiredDataException;
import com.example.famback.fam.member.exception.NotMemberException;
import com.example.famback.fam.member.domain.MemberDomain;
import com.example.famback.fam.member.domain.MemberTempDataDomain;
import com.example.famback.fam.member.mapper.MemberMapper;
import com.example.famback.fam.memberClass.domain.MemberClassDomain;
import com.example.famback.fam.memberClass.exception.InviteDuplicationEmailException;
import com.example.famback.fam.memberClass.exception.NotInviteEmailAuthException;
import com.example.famback.fam.memberClass.exception.NotInviteMemberException;
import com.example.famback.fam.memberClass.request.*;
import com.example.famback.fam.memberClass.response.*;
import com.example.famback.fam.user.domain.UserDomain;
import com.example.famback.fam.memberClass.mapper.MemberClassMapper;
import com.example.famback.fam.user.mapper.UserMapper;
import com.example.famback.fam.user.request.UserCreateRequest;
import com.example.famback.fam.user.service.UserService;
import com.example.famback.type.EmailTemplateType;
import com.example.famback.type.TempContentType;
import com.example.famback.util.AES256Cipher;
import com.example.famback.util.DataUtil;
import com.example.famback.util.MailServerClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberClassService {

	private final MemberClassMapper memberClassMapper;
	private final UserService userService;
	private final UserMapper userMapper;
	private final MemberMapper memberMapper;

	private final static String link = "http://localhost:8080/invite/";
	//방생성
	public CreateMemberClassResponse createMemberClass(CreateMemberClassRequest createMemberClassRequest) throws CustomException{
		if(createMemberClassRequest == null || createMemberClassRequest.getClassName() == null
			|| createMemberClassRequest.getMemberKey() == null) throw new NotRequiredDataException();

		String memberClassKey = String.valueOf(memberClassMapper.memberClassLastNumber() + 1);
		MemberClassDomain memberClassDomain = MemberClassDomain.builder()
				.className(createMemberClassRequest.getClassName())
				.numPk(memberClassKey)
				.build();

		UserCreateRequest userCreateRequest = new UserCreateRequest();
		userCreateRequest.setMemberKey(createMemberClassRequest.getMemberKey());
		userCreateRequest.setMemberClassKey(memberClassKey);
		userCreateRequest.setNickname("관리자");
		userCreateRequest.setDescription("");
		userCreateRequest.setAuthorityCode("G0001");//신규생성은 관리자 권한으로 만듬.

		if(memberClassMapper.createMemberClass(memberClassDomain) == 1 && userService.createUser(userCreateRequest)){
			return CreateMemberClassResponse.builder()
					.memberClassKey(memberClassKey)
					.build();
		}
		return null;
	}
	//방초대 링크발송
	@Transactional
	public boolean inviteMemberClass(MemberClassInviteRequest memberClassInviteRequest) throws Exception {
		if(memberClassInviteRequest == null || memberClassInviteRequest.getMemberClassKey() == null
		|| memberClassInviteRequest.getUserKey() == null || memberClassInviteRequest.getMemberKey() == null
		|| memberClassInviteRequest.getEmail() == null || memberClassInviteRequest.getEmail().length == 0) throw new NotRequiredDataException();

		UserDomain userDomain = UserDomain.builder()
				.numPk(memberClassInviteRequest.getUserKey())
				.build();
		MemberDomain memberDomain = MemberDomain.builder()
				.numPk(memberClassInviteRequest.getMemberKey())
				.build();

		MemberDomain domain = memberMapper.memberOneFindByKey(memberDomain);

		boolean isEqualsCallMember = Arrays.stream(memberClassInviteRequest.getEmail()).anyMatch(email-> email.equals(domain.getEmail()));

		if(isEqualsCallMember) throw new InviteDuplicationEmailException();

		String userAuthorityCode = userMapper.findUserAuthorityCodeByUserKey(userDomain);
		if(!"G0001".equals(userAuthorityCode)) throw new NotInviteEmailAuthException();

		String inviteClassKey = AES256Cipher.AesEncode(memberClassInviteRequest.getMemberClassKey() + "/" + System.currentTimeMillis());
		while (inviteClassKey.contains("/")){
			inviteClassKey = AES256Cipher.AesEncode(memberClassInviteRequest.getMemberClassKey() + "/" + System.currentTimeMillis());
			if(!inviteClassKey.contains("/")) break;
		}

		for(String email : memberClassInviteRequest.getEmail()){
			memberMapper.insertMemberTempData(MemberTempDataDomain.builder()
								.key(email)
								.contentType(TempContentType.MEMBER_CLASS_INVITE_CODE.getContentType())
								.content(inviteClassKey)
								.expirationTime(new Date(new Date().getTime() + 60 * 60 * 1000L))
								.build());
		}
		String inviteLink = DataUtil.customReplace(EmailTemplateType.INVITE.getEmailTemplate(),link + inviteClassKey);
				new MailServerClient()
				.setMailType(MailServerClient.MailType.DEFAULT)
				.setRecipient(memberClassInviteRequest.getEmail())
				.setProjectName("fam")
				.setText(inviteLink)
				.setSubject("fam프로젝트 초대링크 발송입니다.")
				.call();
		return true;
	}

	    //해당 방 키를 복호화
		//해당 방에 가입 여부 멤버 키를 가져와서 유저정보를 가져온 후 해당 방키가 존재하는지 확인
		//가입되어 있으면 이미 가입된 방입니다. 예외 처리.
		//방 가입 완료.
		//
		//초대링크 페이지 이동
		//확인 버튼 클릭시 유효한 토큰이 존재하면 방 참가.
		//실패시 로그인 페이지로 이동
		//로그인시 템프 데이터에 유효한 키값이 존재시 -> 초대한 방에 가입하시겠습니까? 알람 띄움.
		//로그인시 초대한 방 리스트 띄우기.
		//참여한 방이동.
	//해당 방이 존재하지는지
	//해당 암호화 키가 존재하는지 템프에.
	public MemberClassInviteResponse attendMemberClass(MemberClassInviteRequest memberClassInviteRequest) throws Exception {
		if(memberClassInviteRequest == null || memberClassInviteRequest.getMemberClassKey() == null
		||  memberClassInviteRequest.getMemberKey() == null) throw new NotRequiredDataException();
		UserDomain userDomain = UserDomain.builder()
						.memberFk(memberClassInviteRequest.getMemberKey())
					    .build();
		MemberDomain memberDomain = MemberDomain.builder()
				.numPk(memberClassInviteRequest.getMemberKey())
				.build();

		//해당 암호화키 + 회원이메일 정보
		MemberDomain domain = memberMapper.memberOneFindByKey(memberDomain);
		MemberTempDataDomain memberTempDataDomain = MemberTempDataDomain.builder()
				.key(domain.getEmail())
				.content(memberClassInviteRequest.getMemberClassKey())
				.contentType(TempContentType.MEMBER_CLASS_INVITE_CODE.getContentType())
				.expirationTime(new Date(new Date().getTime()))
				.build();
		log.debug("초대하지 않은 사용자 여부 체크");
		MemberTempDataDomain memberTempData = memberMapper.findByKeyToContentToContentTypeToExpirationTime(memberTempDataDomain);
		if(memberTempData == null ) throw new NotInviteMemberException(); //초대하지 않은 사용자입니다.
		// 방번호 복호화
		String inviteClassKeys = AES256Cipher.AesDecode(memberClassInviteRequest.getMemberClassKey());
		String inviteClassKey = inviteClassKeys.split("/")[0];

		//해당 방이 존재하는지.
		MemberClassDomain memberClassDomain = MemberClassDomain.builder()
				.numPk(inviteClassKey)
				.build();

		log.debug("방 존재 여부 체크");
		int isMemberClass  = memberClassMapper.existsMemberClass(memberClassDomain);
		if(isMemberClass == 0 ) throw new NotRequiredDataException(); //방이 존재 하지 않습니다.
		//가입된 멤버 방에 가입된 상태인지.
		List<UserDomain> userDomains = userMapper.findClassByMemberKey(userDomain);

		//해당 방이 존재하는지.
		//해당 암호화키 + 회원이메일 정보가 존재하는지.
		log.debug("방 가입 여부 체크");
		boolean isJoinClass = userDomains.stream().anyMatch(domainItem-> domainItem.getMemberClassFk().equals(inviteClassKey));
		if(isJoinClass) throw new NotRequiredDataException(); //이미 가입된 방입니다.
		UserCreateRequest userCreateRequest = new UserCreateRequest();
		userCreateRequest.setMemberClassKey(inviteClassKey);
		userCreateRequest.setMemberKey(memberClassInviteRequest.getMemberKey());
		userCreateRequest.setNickname(domain.getEmail());
		userCreateRequest.setDescription("");
		userCreateRequest.setAuthorityCode("G0002");
		//방가입 프로세스
		memberTempDataDomain.setExpirationTime(new Date(new Date().getTime()));
		if(userService.createUser(userCreateRequest) && memberMapper.updateMemberTempDataExpirationTime(memberTempDataDomain) == 1){
			return MemberClassInviteResponse.mapping(memberClassDomain);
		}
		return null;
	}

	public MemberClassInfoResponse memberClassInfo(MemberClassInfoRequest memberClassInfoRequest) throws CustomException {
		if(memberClassInfoRequest == null || memberClassInfoRequest.getMemberClassKey() == null) throw new NotRequiredDataException();
		MemberClassDomain memberClassDomain = MemberClassDomain.builder()
				.numPk(memberClassInfoRequest.getMemberClassKey())
				.build();
		MemberClassDomain domain = memberClassMapper.findMemberClassOneByMemberClassKey(memberClassDomain);
		if(domain== null){
			return null;
		}
		return MemberClassInfoResponse.mapping(domain);
	}

	public MemberClassInfoResponse inViteMemberClassInfo(MemberClassInfoRequest memberClassInfoRequest) throws Exception {
		if(memberClassInfoRequest == null || memberClassInfoRequest.getMemberClassKey() == null) throw new NotRequiredDataException();
		String enCodeInviteClassKey = null;
		try {
			enCodeInviteClassKey = AES256Cipher.AesDecode(memberClassInfoRequest.getMemberClassKey());
		}catch (Exception e){
			throw new NotRequiredDataException(); //유효하지 않은 값
		}
		String inviteClassKey = enCodeInviteClassKey.split("/")[0];
		memberClassInfoRequest.setMemberClassKey(inviteClassKey);
		//유효시간 확인
		return memberClassInfo(memberClassInfoRequest);
	}

	public List<MemberClassListResponse> classList(MemberClassListRequest memberClassListRequest) throws CustomException {
		if(memberClassListRequest == null || memberClassListRequest.getMemberKey() == null) throw new NotRequiredDataException();
		log.debug("classList=>");
		MemberClassDomain memberClassDomain = MemberClassDomain.builder()
				.memberKey(memberClassListRequest.getMemberKey())
				.build();
		List<MemberClassDomain> domainList = memberClassMapper.findClassByMemberKey(memberClassDomain);
		if(domainList.size() == 0){
			return null;
		}
		return MemberClassListResponse.mapping(domainList);
	}

	//최신 한건에 대한 초대 정보 가져옴.
	public InviteMemberClassesResponse inviteAscOneMemberClass(InviteMemberClassesRequest inviteMemberClassesRequest) throws Exception {
		if(inviteMemberClassesRequest == null || inviteMemberClassesRequest.getMemberKey() == null) throw new NotRequiredDataException();
		MemberDomain memberDomain = MemberDomain.builder()
				.numPk(inviteMemberClassesRequest.getMemberKey())
				.build();
		MemberDomain domain =	memberMapper.memberOneFindByKey(memberDomain);
		List<MemberTempDataDomain> memberTempDataDomains =	memberMapper.findByKeyToContentTypeToExpirationTime(MemberTempDataDomain.builder()
																.key(domain.getEmail())
																.contentType(TempContentType.MEMBER_CLASS_INVITE_CODE.getContentType())
																.expirationTime(new Date(new Date().getTime()))
																.build());
		log.debug("초대 리스트 체크");
		if(memberTempDataDomains.size() == 0) return null; //초대할 리스트가 없음
		String encodeInviteClassKey = memberTempDataDomains.get(0).getContent();
		String inviteClassKey = AES256Cipher.AesDecode(encodeInviteClassKey);
		MemberClassDomain memberClassDomain = MemberClassDomain.builder()
				.numPk(inviteClassKey)
				.build();
		MemberClassDomain memberClassData = memberClassMapper.findMemberClassOneByMemberClassKey(memberClassDomain);
		memberClassData.setNumPk(encodeInviteClassKey);
		return InviteMemberClassesResponse.mapping(memberClassData);
	}
}
