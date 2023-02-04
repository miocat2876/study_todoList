package com.example.famback.fam.jwt.mapper;

import com.example.famback.fam.jwt.domain.TokenDomain;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JwtMapper {
	int existsByKey(TokenDomain tokenDomain);
	int createToken(TokenDomain tokenDomain);
	int deleteToken(TokenDomain tokenDomain);
	int modifyAccessToken(TokenDomain tokenDomain);
	TokenDomain findRefreshTokenByKey(String key);
	int existsByAccessKey(TokenDomain tokenDomain);
	int deleteMemberAllTokenByKey(TokenDomain tokenDomain);
}
