package com.example.famback.fam.jwt.service;

import com.example.famback.error.custom.CustomException;
import com.example.famback.error.custom.defaultException.NotRequiredDataException;
import com.example.famback.fam.jwt.exception.CreateAccessTokenFail;
import com.example.famback.fam.jwt.exception.CreateTokenFail;
import com.example.famback.fam.jwt.exception.NotJwtUserInfo;
import com.example.famback.fam.member.exception.NotMemberException;
import com.example.famback.fam.jwt.mapper.JwtMapper;
import com.example.famback.fam.jwt.JwtTokenProvider;
import com.example.famback.fam.jwt.domain.TokenDomain;
import com.example.famback.fam.jwt.request.JwtRequest;
import com.example.famback.fam.jwt.response.Token;
import com.example.famback.util.NetUtil;
import com.example.famback.util.SHA256Cipher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class JwtService {
	private final JwtMapper jwtMapper;
	private final JwtTokenProvider jwtTokenProvider;

	//토큰 데이터 신규생성
	//엑세스 토큰, 리프레쉬 토큰 생성
	@Transactional
	public Token createToken(JwtRequest jwtRequest) throws CustomException {
		if(jwtRequest == null || jwtRequest.getIp() == null
				|| jwtRequest.getUserAgent() == null
				|| jwtRequest.getMemberFk() == null) throw new NotJwtUserInfo();
		Date date = new Date();
		TokenDomain domain = TokenDomain.builder()
				.memberFk(jwtRequest.getMemberFk())
				.ip(jwtRequest.getIp())
				.userAgent(jwtRequest.getUserAgent())
				.accessExpirationTime(jwtTokenProvider.getAccessExpirationTime(date))
				.refreshExpirationTime(jwtTokenProvider.getRefreshExpirationTime(date))
				.build();
		String accessToken = jwtTokenProvider.createAccessToken(domain.getMemberFk(),domain.getAccessExpirationTime(),domain.getUserAgent(),domain.getIp(),null);
		String refreshToken = jwtTokenProvider.createRefreshToken(domain.getMemberFk(),domain.getRefreshExpirationTime(),domain.getUserAgent(),domain.getIp(),null);
		String key = SHA256Cipher.generateSHA256(refreshToken);
		domain.setAccessToken(accessToken);
		domain.setRefreshToken(refreshToken);
		domain.setNumPk(key);
		if(jwtMapper.createToken(domain) != 1) throw new CreateTokenFail();
		return Token.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.refreshKey(key)
				.build();
	}

	@Transactional
	public String generateAccessToken(JwtRequest jwtRequest) throws CustomException {
		if(jwtRequest == null || jwtRequest.getNumPk() == null
				|| jwtRequest.getIp() == null
				|| jwtRequest.getUserAgent() == null) throw new NotJwtUserInfo();
		TokenDomain tokenDomain = TokenDomain.builder()
				.ip(jwtRequest.getIp())
				.userAgent(jwtRequest.getUserAgent())
				.numPk(jwtRequest.getNumPk())
				.build();
		TokenDomain domain = jwtMapper.findRefreshTokenByKey(tokenDomain.getNumPk());
		if(domain == null) throw new NotJwtUserInfo();
		//브라우저 정보로 비교 하고
		//에이전트 정보는 그냥 적재하는걸로 바꾸기.
//		if(!NetUtil.getAgentbrowser(jwtRequest.getUserAgent()).equals(NetUtil.getAgentbrowser(domain.getUserAgent()))){
//			throw new NotJwtUserInfo();
//		}
		tokenDomain.setRefreshToken(domain.getRefreshToken());
		if(jwtTokenProvider.validateRefreshToken(tokenDomain.getRefreshToken(),tokenDomain.getUserAgent(),tokenDomain.getIp())){
			return this.createAccessToken(JwtRequest.builder()
							.numPk(jwtRequest.getNumPk())
							.memberFk(jwtTokenProvider.getRefreshMemberKey(tokenDomain.getRefreshToken()))
							.userAgent(jwtRequest.getUserAgent())
							.ip(jwtRequest.getIp())
							.roles(null)
							.build());
		}else{
			throw new NotJwtUserInfo();
		}
	}

	//엑세스 토큰 생성 및 엑세스토큰,유효기간 업데이트.
	@Transactional
	public String createAccessToken(JwtRequest jwtRequest) throws CustomException {
		//엑세스 토큰 생성 및 업데이트 유효기간 토큰
		//식별자 필요 numpk 정보 필요
		if(jwtRequest == null ||jwtRequest.getNumPk() == null
				|| jwtRequest.getMemberFk() == null || jwtRequest.getIp() == null
		        || jwtRequest.getUserAgent() == null) throw new NotJwtUserInfo();
		TokenDomain tokenDomain = TokenDomain.builder()
				.numPk(jwtRequest.getNumPk())
				.userAgent(jwtRequest.getUserAgent())
				.ip(jwtRequest.getIp())
				.memberFk(jwtRequest.getMemberFk())
				.accessExpirationTime(jwtTokenProvider.getAccessExpirationTime(new Date()))
				.build();
		String accessToken = jwtTokenProvider.createAccessToken(tokenDomain.getMemberFk(),tokenDomain.getAccessExpirationTime(),tokenDomain.getUserAgent(),tokenDomain.getIp(),null);
		tokenDomain.setAccessToken(accessToken);
		if(jwtMapper.modifyAccessToken(tokenDomain) != 1) throw new CreateAccessTokenFail();
		return accessToken;
    }

	public boolean existsByKey(JwtRequest jwtRequest) throws CustomException{
		if(jwtRequest == null || jwtRequest.getMemberFk() == null ||jwtRequest.getIp() == null
				|| jwtRequest.getUserAgent() == null) throw new NotRequiredDataException();
		TokenDomain tokenDomain = TokenDomain.builder()
				.ip(jwtRequest.getIp())
				.memberFk(jwtRequest.getMemberFk())
				.userAgent(jwtRequest.getUserAgent())
				.build();
		return jwtMapper.existsByKey(tokenDomain) == 1;
	}
	public boolean deleteToken(JwtRequest jwtRequest) throws CustomException{
		if(jwtRequest == null || jwtRequest.getMemberFk() == null ||jwtRequest.getIp() == null
				|| jwtRequest.getUserAgent() == null) throw new NotRequiredDataException();
		TokenDomain tokenDomain = TokenDomain.builder()
				.memberFk(jwtRequest.getMemberFk())
				.userAgent(jwtRequest.getUserAgent())
				.ip(jwtRequest.getIp())
				.build();
		return jwtMapper.deleteToken(tokenDomain) != 0;
	}
	public boolean existsByAccessKey(JwtRequest jwtRequest) throws CustomException {
		if(jwtRequest == null || jwtRequest.getMemberFk() == null ||jwtRequest.getIp() == null
				|| jwtRequest.getUserAgent() == null) throw new NotRequiredDataException();
		TokenDomain tokenDomain = TokenDomain.builder()
				.memberFk(jwtRequest.getMemberFk())
				.ip(jwtRequest.getIp())
				.userAgent(jwtRequest.getUserAgent())
				.accessToken(jwtRequest.getAccessToken())
				.build();;
		return jwtMapper.existsByAccessKey(tokenDomain) == 1;
	}

	public int deleteMemberAllTokenByKey(JwtRequest jwtRequest)  throws CustomException{
		if(jwtRequest == null || jwtRequest.getMemberFk() == null) throw new NotRequiredDataException();
		TokenDomain tokenDomain = TokenDomain.builder()
				.memberFk(jwtRequest.getMemberFk())
				.build();
		return jwtMapper.deleteMemberAllTokenByKey(tokenDomain);
	}
}
