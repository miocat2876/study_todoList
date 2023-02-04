package com.example.famback.config;

import com.example.famback.config.securityFilter.CustomAuthenticationEntryPoint;
import com.example.famback.config.securityFilter.JwtAuthenticationFilter;
import com.example.famback.config.securityFilter.JwtExceptionFilter;
import com.example.famback.fam.jwt.JwtTokenProvider;
import com.example.famback.fam.jwt.mapper.JwtMapper;
import com.example.famback.fam.jwt.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtService jwtService;

    // authenticationManager를 Bean 등록합니다.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        //http.httpBasic().disable(); // 일반적인 루트가 아닌 다른 방식으로 요청시 거절, header에 id, pw가 아닌 token(jwt)을 달고 간다. 그래서 basic이 아닌 bearer를 사용한다.
        http.httpBasic().disable()
                .authorizeRequests()// 요청에 대한 사용권한 체크
                .antMatchers("/memberLogin").permitAll()
                .antMatchers("/member-validation").permitAll()
                .antMatchers("/member-validation-confirm").permitAll()
                .antMatchers("/common-code").permitAll()
                .antMatchers("/test").permitAll()
                .antMatchers("/refresh").permitAll()
                .antMatchers("/class/**").authenticated()
                //.antMatchers("/invite/**").authenticated()
                .antMatchers("/invite/*").access("request.method.equals('POST')  ? authenticated : permitAll")
                .antMatchers("/member").access("request.method.equals('POST')  ? permitAll : authenticated")
                .antMatchers("/**").authenticated()
                .and()
                .cors()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, jwtService),
                        UsernamePasswordAuthenticationFilter.class)
                 .addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
