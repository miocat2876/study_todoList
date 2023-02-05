package com.example.famback.fam.todo.mapper;

import com.example.famback.fam.exemple.domain.AccountDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TodoMapper {
	AccountDomain findOneByKey(AccountDomain accountDomain);
	List<AccountDomain>findAccountListByMonth(AccountDomain accountDomain);
	int findAccountCount(AccountDomain accountDomain);
	int insertAccount(AccountDomain accountDomain);
	int updateAccount(AccountDomain accountDomain);
	int findAccountFixHistory(AccountDomain accountDomain);
	int insertAccountFixHistory(AccountDomain accountDomain);
	int updateAccountFixHistory(AccountDomain accountDomain);
	List<AccountDomain> findAccountFix(AccountDomain accountDomain);
	int updateAccountFix(AccountDomain accountDomain);
	int deleteAccountFix(AccountDomain accountDomain);
	int deleteAccount(AccountDomain accountDomain);
	List<AccountDomain> findAccountListByDay(AccountDomain accountDomain);

}
