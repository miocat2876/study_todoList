package com.example.famback.fam.account.service;

import com.example.famback.error.custom.CustomException;
import com.example.famback.error.custom.defaultException.NotRequiredDataException;
import com.example.famback.fam.account.domain.AccountDomain;
import com.example.famback.fam.account.mapper.AccountMapper;
import com.example.famback.fam.account.mapper.AccountStatsMapper;
import com.example.famback.fam.account.request.*;
import com.example.famback.fam.account.response.AccountGroupAccountTypeResponse;
import com.example.famback.fam.account.response.AccountGroupNicknameResponse;
import com.example.famback.fam.account.response.AccountListResponse;
import com.example.famback.fam.account.response.AccountOneResponse;
import com.example.famback.fam.member.exception.NotMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Log4j2
@Service
@RequiredArgsConstructor
public class AccountStatsService {
	private final AccountStatsMapper accountStatsMapper;

	//카테고리별 그룹
	public List<AccountGroupAccountTypeResponse> groupAccountType(AccountGroupAccountTypeRequest accountGroupAccountTypeRequest) throws CustomException{
		if(accountGroupAccountTypeRequest == null || accountGroupAccountTypeRequest.getMemberClassKey() == null
		|| accountGroupAccountTypeRequest.getSearchDate() == null) throw new NotRequiredDataException();

		AccountDomain accountDomain = AccountDomain.builder()
				.memberClassFk(accountGroupAccountTypeRequest.getMemberClassKey())
				.searchDate(accountGroupAccountTypeRequest.getSearchDate())
				.build();

		List<AccountDomain> domainList = accountStatsMapper.findGroupAccountTypeByMemberClassKeyToSearchDate(accountDomain);
		if(domainList.size() == 0 ){
			return null;
		}
		return AccountGroupAccountTypeResponse.mapping(domainList);
	}

	//닉네임별 그룹
	public List<AccountGroupNicknameResponse> groupNickName(AccountGroupNicknameRequest accountGroupNicknameRequest) throws CustomException{
		if(accountGroupNicknameRequest == null || accountGroupNicknameRequest.getMemberClassKey() == null
		|| accountGroupNicknameRequest.getSearchDate() == null) throw new NotRequiredDataException();

		AccountDomain accountDomain = AccountDomain.builder()
				.memberClassFk(accountGroupNicknameRequest.getMemberClassKey())
				.searchDate(accountGroupNicknameRequest.getSearchDate())
				.build();

		List<AccountDomain> domainList = accountStatsMapper.findGroupNicknameByMemberClassKeyToSearchDate(accountDomain);
		if(domainList.size() == 0 ){
			return null;
		}
		return AccountGroupNicknameResponse.mapping(domainList);
	}
}
