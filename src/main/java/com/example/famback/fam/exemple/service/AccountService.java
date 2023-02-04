package com.example.famback.fam.exemple.service;

import com.example.famback.error.custom.CustomException;
import com.example.famback.error.custom.defaultException.NotRequiredDataException;
import com.example.famback.fam.exemple.mapper.AccountMapper;
import com.example.famback.fam.exemple.domain.AccountDomain;
import com.example.famback.fam.exemple.request.*;
import com.example.famback.fam.exemple.response.AccountListResponse;
import com.example.famback.fam.exemple.response.AccountOneResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class AccountService {
	private final AccountMapper accountMapper;

	//가계부조회 단건
	public AccountOneResponse accountOne(AccountOneRequest accountOneRequest) throws CustomException{
		if(accountOneRequest == null || accountOneRequest.getNumPk() == null
		|| accountOneRequest.getUserKey() == null) throw new NotRequiredDataException();
		AccountDomain accountDomain = AccountDomain.builder()
				.numPk(accountOneRequest.getNumPk())
				.userFk(accountOneRequest.getUserKey())
				.build();
		AccountDomain domain = accountMapper.findOneByKey(accountDomain);
		if(domain == null){
			return null;
		}
		return AccountOneResponse.mapping(domain);
	}
	public List<AccountListResponse> accountList(AccountListRequest accountListRequest) throws CustomException {
		if(accountListRequest == null || accountListRequest.getMemberClassKey() == null
		|| accountListRequest.getSearchDate() == null || accountListRequest.getDateListType() == null) throw new NotRequiredDataException();
		AccountDomain accountDomain = AccountDomain.builder()
				.memberClassFk(accountListRequest.getMemberClassKey())
				.searchDate(accountListRequest.getSearchDate().replace("-",""))
				.build();
		List<AccountDomain> accountDomains = null;
		if(Objects.equals(accountListRequest.getDateListType(), "00001")){
			if(accountDomain.getSearchDate().length() > 9) throw new NotRequiredDataException("날짜는 YYYYMMDD 형식으로 8자리가 필요합니다.");
			log.debug("day");
			accountDomains = accountMapper.findAccountListByDay(accountDomain);
		}else{
			if(accountDomain.getSearchDate().length() > 7) throw new NotRequiredDataException("날짜는 YYYYMM 형식으로 6자리가 필요합니다.");
			log.debug("month");
			accountDomains = accountMapper.findAccountListByMonth(accountDomain);
		}
		if(accountDomains.size() == 0 ){
			return null;
		}
		return AccountListResponse.mapping(accountDomains);
	}
	public boolean accountInsert(AccountInsertRequest accountInsertRequest) throws CustomException {
		if(accountInsertRequest == null || accountInsertRequest.getUserKey() == null
		|| accountInsertRequest.getMemberClassKey() == null || accountInsertRequest.getAccountCode() == null
		|| accountInsertRequest.getPayCode() == null || accountInsertRequest.getDescription() == null
		|| accountInsertRequest.getMoney() == 0 || accountInsertRequest.getAccountTypeCode() == null
		|| accountInsertRequest.getSearchDate() == null || accountInsertRequest.getTitle() == null) throw new NotRequiredDataException();
		AccountDomain accountDomain = AccountDomain.builder()
				.money(accountInsertRequest.getMoney())
				.searchDate(accountInsertRequest.getSearchDate().replace("-",""))
				.accountTypeCode(accountInsertRequest.getAccountTypeCode())
				.description(accountInsertRequest.getDescription())
				.payCode(accountInsertRequest.getPayCode())
				.accountCode(accountInsertRequest.getAccountCode())
				.memberClassFk(accountInsertRequest.getMemberClassKey())
				.userFk(accountInsertRequest.getUserKey())
				.title(accountInsertRequest.getTitle())
				.build();
		return accountMapper.insertAccount(accountDomain) == 1;
	}
	@Transactional
	public boolean accountUpdate(AccountUpdateRequest accountUpdateRequest) throws CustomException{
		if(accountUpdateRequest == null || accountUpdateRequest.getAccountCode() == null
				|| accountUpdateRequest.getNumPk() == null || accountUpdateRequest.getUserKey() == null
				|| accountUpdateRequest.getMoney() == 0 || accountUpdateRequest.getDescription() ==null
		        || accountUpdateRequest.getPayCode() == null || accountUpdateRequest.getSearchDate() == null
				|| accountUpdateRequest.getAccountTypeCode() == null || accountUpdateRequest.getTitle() == null
				) throw new NotRequiredDataException();

		AccountDomain accountDomain = AccountDomain.builder()
				.accountCode(accountUpdateRequest.getAccountCode())
				.userFk(accountUpdateRequest.getUserKey())
				.money(accountUpdateRequest.getMoney())
				.description(accountUpdateRequest.getDescription())
				.payCode(accountUpdateRequest.getPayCode())
				.searchDate(accountUpdateRequest.getSearchDate().replace("-",""))
				.numPk(accountUpdateRequest.getNumPk())
				.accountTypeCode(accountUpdateRequest.getAccountTypeCode())
				.title(accountUpdateRequest.getTitle())
				.build();

		AccountDomain domain = accountMapper.findOneByKey(accountDomain);
		if(domain == null) throw new NotRequiredDataException();// 잘못된 요청입니다.

		if(!accountUpdateRequest.getUserKey().equals(domain.getUserFk())){
			throw new NotRequiredDataException(); //삭제 할수 없는 권한입니다.
		}

		//(고정지출 이거나 고정수입) 이면서 금월이전 이력일경우 -> 존재하지 않을경우 이력관리 내역없음
		if((accountDomain.getAccountTypeCode().equals("00001") || accountDomain.getAccountTypeCode().equals("00002"))
				&& accountMapper.findAccountFixHistory(accountDomain) != 1) {
			       //0건이면 업데이트중 오류 캐치 못한 쿼리 에러 확률 있음.
			log.debug("고정이력");
			if(accountMapper.insertAccountFixHistory(accountDomain) == 0) throw new NotRequiredDataException();
			accountMapper.updateAccountFixHistory(accountDomain);
		}else{
			log.debug("변동이력");
		}
		return accountMapper.updateAccount(accountDomain) == 1;
	}
	@Transactional
	public boolean accountDelete(AccountDeleteRequest accountDeleteRequest) throws CustomException{
		if (accountDeleteRequest == null || accountDeleteRequest.getNumPk() == null
		|| accountDeleteRequest.getSearchDate() == null || accountDeleteRequest.getAccountTypeCode() == null
		|| accountDeleteRequest.getUserKey() == null) throw new NotRequiredDataException();
		AccountDomain accountDomain = AccountDomain.builder()
				.numPk(accountDeleteRequest.getNumPk())
				.searchDate(accountDeleteRequest.getSearchDate())
				.accountTypeCode(accountDeleteRequest.getAccountTypeCode())
				.build();

		AccountDomain domain = accountMapper.findOneByKey(accountDomain);
		if(domain == null) throw new NotRequiredDataException();// 잘못된 요청입니다.

		if(!accountDeleteRequest.getUserKey().equals(domain.getUserFk())){
			throw new NotRequiredDataException(); //삭제 할수 없는 권한입니다.
		}

		//(고정지출 이거나 고정수입) 이면서 금월이전 이력일경우 -> 존재하지 않을경우 이력관리 내역없음
		if((accountDomain.getAccountTypeCode().equals("00001") || accountDomain.getAccountTypeCode().equals("00002"))
				&& accountMapper.findAccountFixHistory(accountDomain) != 1) {
			//0건이면 업데이트중 오류 캐치 못한 쿼리 에러 확률 있음.
			log.debug("고정이력");
			if(accountMapper.insertAccountFixHistory(accountDomain) == 0) throw new NotRequiredDataException();
			accountMapper.updateAccountFixHistory(accountDomain);
		}else{
			log.debug("변동이력");
		}
		return accountMapper.deleteAccount(accountDomain) == 1;
	}

}
