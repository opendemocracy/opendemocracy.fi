package fi.opendemocracy.domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

@RooJavaBean
@RooEntity(finders = { "findODUsersByOpenIdIdentifier" })
public class ODUser implements UserDetails {

	@NotNull
	@Enumerated(EnumType.STRING)
	private UserRole userRole;

	@NotNull
	private String openIdIdentifier;

	private String username;

	private String emailAddress;

	private String description;

	private String firstName;

	private String lastName;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date ts;

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		grantedAuthorities.add(new GrantedAuthorityImpl(this.userRole.name()));
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return null;
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

	@Override
	public String toString() {
		return (username == null || username.isEmpty()) ? getId().toString()
				: username;
	}
}
