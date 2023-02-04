package com.example.famback.config.securityFilter;

import com.example.famback.error.custom.CustomException;
import com.example.famback.fam.member.domain.MemberDomain;
import com.example.famback.fam.member.exception.NotMemberException;
import com.example.famback.fam.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;


//getAuthorities()
// Collection<? extends   GrantedAuthority>
// 계정이 갖고있는 권한 목록을 리턴한다.
//
// getPassword()
// String
// 계정의 비밀번호를 리턴한다.
//
// getUsername()
// String
// 계정의 이름을 리턴한다.
//
// isAccountNonExpired()
// boolean
// 계정이 만료되지 않았는 지 리턴한다. (true: 만료안됨)
//
// isAccountNonLocked()
// boolean
// 계정이 잠겨있지 않았는 지 리턴한다. (true: 잠기지 않음)
//
// isCredentialNonExpired()
// boolean
// 비밀번호가 만료되지 않았는 지 리턴한다. (true: 만료안됨)
//
// isEnabled()
// boolean
// 계정이 활성화(사용가능)인 지 리턴한다. (true: 활성화)

@RequiredArgsConstructor
@Component
@Log4j2
public class JwtUserDetails implements UserDetailsService {
	private final MemberMapper memberMapper;
	@Override
	public UserDetails loadUserByUsername(String memberKey) throws CustomException {
		MemberDomain memberDomain = MemberDomain.builder()
				.numPk(memberKey)
				.build();
		MemberDomain domain = memberMapper.memberOneFindByKey(memberDomain);
		if (domain == null){
			throw new NotMemberException();
		}
		return new UserDetails() {
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				return null;
			}
			@Override
			public String getPassword() {
				return domain.getPassword();
			}
			@Override
			public String getUsername() {
				return domain.getEmail();
			}
			@Override
			public boolean isAccountNonExpired() {
				return false;
			}
			@Override
			public boolean isAccountNonLocked() {
				return false;
			}
			@Override
			public boolean isCredentialsNonExpired() {
				return false;
			}
			@Override
			public boolean isEnabled() {
				return "N".equals(domain.getDeleteYn()) ;
			}
		};
	}
}
