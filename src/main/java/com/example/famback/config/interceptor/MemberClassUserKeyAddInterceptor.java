package com.example.famback.config.interceptor;

import com.example.famback.fam.memberClass.exception.NotMemberClassException;
import com.example.famback.fam.jwt.JwtTokenProvider;
import com.example.famback.fam.user.domain.UserDomain;
import com.example.famback.fam.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
@RequiredArgsConstructor
public class MemberClassUserKeyAddInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //검증 로직
        log.debug("dataFilterInterceptor : 요청 Path = " + request.getServletPath());
        String accessToken = request.getHeader("Authorization");
        if(accessToken != null){
            String[] uriArray = request.getRequestURI().split("/");
            String memberKey = jwtTokenProvider.getMemberKey(accessToken);
            log.debug("memberKey => " + memberKey);
            request.setAttribute("memberKey",memberKey);
            if(uriArray.length > 2){
                UserDomain userDomain = new UserDomain();
                userDomain.setMemberFk(memberKey);
                userDomain.setMemberClassFk(uriArray[2]);
                 log.debug("방번호 => " + uriArray[2]);
                String userKey = userMapper.findByMemberClassFkToMemberFK(userDomain);
                log.debug("userKey => " + userKey);
                if(userKey == null){
                    throw new NotMemberClassException();
                }
                request.setAttribute("userKey",userKey);
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
