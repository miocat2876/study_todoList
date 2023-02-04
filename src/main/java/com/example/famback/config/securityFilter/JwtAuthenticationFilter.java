package com.example.famback.config.securityFilter;

import com.example.famback.error.custom.defaultException.NotRequiredDataException;
import com.example.famback.fam.jwt.JwtTokenProvider;
import com.example.famback.fam.jwt.domain.TokenDomain;
import com.example.famback.fam.jwt.mapper.JwtMapper;
import com.example.famback.fam.jwt.request.JwtRequest;
import com.example.famback.fam.jwt.service.JwtService;
import com.example.famback.fam.member.exception.NotMemberException;
import com.example.famback.util.NetUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@Log4j2
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtService jwtService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        String accessToken = jwtTokenProvider.resolveAccessToken((HttpServletRequest) request);
        log.debug("요청 주소 => " + ((HttpServletRequest) request).getRequestURI());
        log.debug(accessToken + "= 회원 엑세스 키");
        if(accessToken != null && !"".equals(accessToken)){
            String memberKey = jwtTokenProvider.getMemberKey(accessToken);
            String ip = NetUtil.getIp((HttpServletRequest)request);
            String userAgent = NetUtil.getUserAgent((HttpServletRequest)request);
            JwtRequest jwtRequest = JwtRequest.builder()
                    .memberFk(memberKey)
                    .ip(ip)
                    .userAgent(userAgent)
                    .accessToken(accessToken)
                    .build();
            if (jwtService.existsByAccessKey(jwtRequest) && jwtTokenProvider.validateAccessToken(accessToken, userAgent, ip)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }
}