package com.example.famback.fam.user.mapper;

import com.example.famback.fam.user.domain.UserAuthorityDomain;
import com.example.famback.fam.user.domain.UserDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAuthorityMapper {
    int createUserAuthority(UserAuthorityDomain userAuthorityDomain);
}
