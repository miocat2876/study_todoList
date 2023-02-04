package com.example.famback.fam.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

// 토큰을 생성하고 검증하는 클래스입니다.
// 해당 컴포넌트는 필터클래스에서 사전 검증을 거칩니다.
@Log4j2
@Component
@RequiredArgsConstructor
public class JwtTokenProvider{

    private final UserDetailsService jwtUserDetails;

    // 객체 초기화, secretKey를 Base64로 인코딩한다.

    public String getRefreshSecretKey(){
        return Base64.getEncoder().encodeToString(JwtContext.REFRESH_SECRET_KEY.getBytes());
    }

    public String getAccessSecretKey(){
        return Base64.getEncoder().encodeToString(JwtContext.ACCESS_SECRET_KEY.getBytes());
    }

    public Date getRefreshExpirationTime(Date date){
        return new Date(date.getTime() + JwtContext.REFRESH_EXPIRATION_TIME);
    }

    public Date getAccessExpirationTime(Date date){
        return new Date(date.getTime() + JwtContext.ACCESS_EXPIRATION_TIME);
    }

    // JWT 엑세스 토큰 생성
    public String createAccessToken(String memberKey, Date refreshExpirationTime, String userAgent, String ip, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(memberKey); // JWT payload 에 저장되는 정보단위, 보통 여기서 user를 식별하는 값을 넣는다.
        claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .claim("ip",ip)
                .claim("userAgent",userAgent)
                .setIssuedAt(new Date()) // 토큰 발행 시간 정보
                .setExpiration(refreshExpirationTime) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, this.getAccessSecretKey())  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }

    // JWT 리프레쉬 토큰 생성
    public String createRefreshToken(String memberKey, Date refreshExpirationTime, String userAgent, String ip, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(memberKey); // JWT payload 에 저장되는 정보단위, 보통 여기서 user를 식별하는 값을 넣는다.
        claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.
        //Refresh Token
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .claim("ip",ip)
                .claim("userAgent",userAgent)
                .setIssuedAt(new Date()) // 토큰 발행 시간 정보
                .setExpiration(refreshExpirationTime) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, this.getRefreshSecretKey())  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }


    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        log.debug(this.getMemberKey(token) + "토큰에서 인증 정보조회 프로바이더 멤버키");
        UserDetails userDetails = jwtUserDetails.loadUserByUsername(this.getMemberKey(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getMemberKey(String token) {
        return Jwts.parser().setSigningKey(this.getAccessSecretKey()).parseClaimsJws(token).getBody().getSubject();
    }
    public String getRefreshMemberKey(String token) {
        return Jwts.parser().setSigningKey(this.getRefreshSecretKey()).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
    public String resolveAccessToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateAccessToken(String accessToken,String userAgent,String ip) {
        if(accessToken == null || userAgent == null || ip == null) return false;
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(this.getAccessSecretKey()).parseClaimsJws(accessToken);
            Claims body = claims.getBody();
            return (body.getExpiration().after(new Date()) && ip.equals(body.get("ip")));// && userAgent.equals(body.get("userAgent"))
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateRefreshToken(String refreshToken, String userAgent, String ip){
        if(refreshToken == null || userAgent == null || ip == null) return false;
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(this.getRefreshSecretKey()).parseClaimsJws(refreshToken);
            Claims body = claims.getBody();
            log.debug(body.getExpiration().before(new Date()));
            return (body.getExpiration().after(new Date()) && ip.equals(body.get("ip")));// && userAgent.equals(body.get("userAgent"))
        } catch (Exception e) {
            return false;
        }
    }
}