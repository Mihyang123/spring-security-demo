package shop.kong.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Member implements UserDetails {
	private Integer memberSeq;
	private String id;
	private String password;
	private String name;
	private String role;
	private String profile;
	private String email;
	private String phone;
	private String addr;
	private String remark;
	private String socialYn;
	private String delYn;
	private LocalDateTime createdDt;
	private LocalDateTime updatedDt;
	private Integer updatedNo;
	
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

	@Override
	public String getUsername() {
		return this.getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
