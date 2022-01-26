package shop.kong.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import shop.kong.dao.CustomDao;
import shop.kong.entity.Member;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final PasswordEncoder passwordEncoder;
	private final CustomDao dao;
	
	@Override
	public Authentication authenticate(Authentication authentication) {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        Map<String, Object> user = new HashMap<>();
        
        user.put("name", name);
        user.put("password", password);
        
        Member member = (Member) this.dao.dbDetail("member.getMembers", user);
        if(member == null) new Exception("member is not exist!");
        
        if (!passwordEncoder.matches(password, member.getPassword())) {
        	throw new BadCredentialsException("password is not valid");
        }
            
        return new UsernamePasswordAuthenticationToken(name, password, member.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
