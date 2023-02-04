package com.example.famback.fam.exemple.mapper;

import com.example.famback.fam.exemple.domain.AccountDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AccountStatsMapper {
	List<AccountDomain> findGroupNicknameByMemberClassKeyToSearchDate(AccountDomain accountDomain);
	List<AccountDomain> findGroupAccountTypeByMemberClassKeyToSearchDate(AccountDomain accountDomain);
}
