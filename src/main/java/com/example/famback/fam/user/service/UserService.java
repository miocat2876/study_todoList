package com.example.famback.fam.user.service;

import com.example.famback.error.custom.CustomException;
import com.example.famback.error.custom.defaultException.NotRequiredDataException;
import com.example.famback.fam.member.exception.NotMemberException;
import com.example.famback.fam.jwt.JwtTokenProvider;
import com.example.famback.fam.member.request.UserJoinClassRequest;
import com.example.famback.fam.user.domain.UserAuthorityDomain;
import com.example.famback.fam.user.domain.UserDomain;
import com.example.famback.fam.user.mapper.UserAuthorityMapper;
import com.example.famback.fam.user.mapper.UserMapper;
import com.example.famback.fam.user.request.*;
import com.example.famback.fam.user.response.UserListResponse;
import com.example.famback.fam.user.response.UserOneResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserMapper userMapper;
	private final UserAuthorityMapper userAuthorityMapper;
	//방가입
	@Transactional
	public boolean createUser(UserCreateRequest userCreateRequest) throws CustomException{
		if(userCreateRequest == null || userCreateRequest.getMemberClassKey() == null
		|| userCreateRequest.getMemberKey() == null) throw new NotRequiredDataException();
		UserDomain userDomain = UserDomain.builder()
				.memberClassFk(userCreateRequest.getMemberClassKey())
				.memberFk(userCreateRequest.getMemberKey())
				.authorityCode(userCreateRequest.getAuthorityCode())
				.nickname(userCreateRequest.getNickname())
				.description(userCreateRequest.getDescription())
				.build();
		UserAuthorityDomain userAuthorityDomain = UserAuthorityDomain.builder()
				.authorityCode(userCreateRequest.getAuthorityCode())
				.build();
		String lastUserKey = userMapper.findLastUserKey();
		String createUserKey = Integer.parseInt(lastUserKey) + 1 + "";
		userDomain.setNumPk(createUserKey);
		userAuthorityDomain.setUserFk(createUserKey);

		if(userMapper.createUser(userDomain) != 1 || userAuthorityMapper.createUserAuthority(userAuthorityDomain) != 1){
			throw new NotRequiredDataException(); //적재실패.
		}

		return true;
	}
	@Transactional
	public boolean deleteUser(UserDeleteRequest userDeleteRequest) throws  CustomException{
		if(userDeleteRequest == null || userDeleteRequest.getUserKey() == null
		|| userDeleteRequest.getDeleteTargetUserKey() == null) throw new NotRequiredDataException();
		UserDomain userDomain = UserDomain.builder()
			.numPk(userDeleteRequest.getDeleteTargetUserKey())
			.build();
		log.debug("deleteUser => " + userDeleteRequest.getDeleteTargetUserKey());
		log.debug("deleteUser => " + userDeleteRequest.getUserKey());
		if(!Objects.equals(userDeleteRequest.getUserKey(), userDeleteRequest.getDeleteTargetUserKey())){
			UserDomain validationUserDomain = UserDomain.builder()
				.numPk(userDeleteRequest.getUserKey())
				.build();
			log.debug("deleteUser notEq => " + userDeleteRequest.getDeleteTargetUserKey());
			UserDomain deleteTargetDomain = userMapper.findOneByUserKey(userDomain);
			UserDomain domain = userMapper.findOneByUserKey(validationUserDomain);
			if(domain == null || deleteTargetDomain == null || !"G0001".equals(domain.getAuthorityCode())
			|| "G0001".equals(deleteTargetDomain.getAuthorityCode())){
				throw new NotRequiredDataException(); //잘못된 요청입니다.
			}
		}
		return userMapper.deleteUser(userDomain) == 1;
	}

	public boolean modifyMemberInfoClass(UserModifyRequest userModifyRequest) throws CustomException{
		if(userModifyRequest == null || userModifyRequest.getUserKey() == null ||
		userModifyRequest.getDescription() == null || userModifyRequest.getNickname() == null) throw new NotRequiredDataException();
		UserDomain userDomain = UserDomain.builder()
				.numPk(userModifyRequest.getUserKey())
				.description(userModifyRequest.getDescription())
				.nickname(userModifyRequest.getNickname())
				.build();
		return userMapper.updateUser(userDomain) == 1;
	}

	public UserOneResponse userOne(UserOneRequest userOneRequest) throws CustomException{
		if(userOneRequest == null || userOneRequest.getUserKey() == null) throw new NotRequiredDataException();
		UserDomain userDomain = UserDomain.builder()
				.numPk(userOneRequest.getUserKey())
				.build();
		UserDomain domain = userMapper.findOneByUserKey(userDomain);
		if(domain == null){
			return null;
		}
		return UserOneResponse.mapping(domain);
	}

	public List<UserListResponse> userList(UserListRequest userListRequest) throws CustomException{
		if(userListRequest == null || userListRequest.getMemberClassKey() == null) throw new NotRequiredDataException();
		UserDomain userDomain = UserDomain.builder()
				.memberClassFk(userListRequest.getMemberClassKey())
				.build();
		List<UserDomain> domains = userMapper.findUserListByMemberClassKey(userDomain);
		if(domains.size() == 0){
			return null;
		}
		return UserListResponse.mapping(domains);
	}

	public List<UserDomain> joinClassList(UserJoinClassRequest userJoinClassRequest) throws CustomException {
		if(userJoinClassRequest == null || userJoinClassRequest.getMemberKey() == null) throw new NotRequiredDataException();
		UserDomain userDomain = UserDomain.builder()
				.memberFk(userJoinClassRequest.getMemberKey())
				.build();
		return userMapper.findClassByMemberKey(userDomain);
	}
	public String userAuthorityCode(UserAuthorityRequest userAuthorityRequest) throws CustomException {
		if(userAuthorityRequest == null || userAuthorityRequest.getUserKey() == null) throw new NotRequiredDataException();
		UserDomain userDomain = UserDomain.builder()
				.numPk(userAuthorityRequest.getUserKey())
				.build();
		return userMapper.findUserAuthorityCodeByUserKey(userDomain);
	}

	public int deleteUserByMemberKey(UserDeleteAllRequest userDeleteAllRequest) throws CustomException {
		if(userDeleteAllRequest == null || userDeleteAllRequest.getMemberKey() == null) throw new NotRequiredDataException();
		UserDomain userDomain = UserDomain.builder()
				.memberFk(userDeleteAllRequest.getMemberKey())
				.build();
		return userMapper.deleteUserByMemberKey(userDomain);
	}
}
