package com.example.famback.config.securityFilter;

import com.example.famback.error.custom.CustomCodeType;
import com.example.famback.error.custom.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        try{
            filterChain.doFilter(request, response);
        }catch (CustomException e){
            log.error("CustomException =>"+ e.getMessage());
            String[] aa = e.getMessage().split(":");
            if(CustomCodeType.is(e.getMessage())){
                request.setAttribute("exception", e.getMessage());
                filterChain.doFilter(request, response);
            }else{
                throw new RuntimeException(e);
            }
        }catch (RuntimeException e){
            log.error("RuntimeException =>"+ e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
