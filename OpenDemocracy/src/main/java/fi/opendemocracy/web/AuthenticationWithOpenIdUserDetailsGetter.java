package fi.opendemocracy.web;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;

import fi.opendemocracy.domain.ODUser;
import fi.opendemocracy.domain.UserRole;

public class AuthenticationWithOpenIdUserDetailsGetter implements
		UserDetailsService {

	public NormalizedOpenIdAttributesBuilder normalizedOpenIdAttributesBuilder;

	public AuthenticationWithOpenIdUserDetailsGetter(
			NormalizedOpenIdAttributesBuilder normalizedOpenIdAttributesBuilder) {
		this.normalizedOpenIdAttributesBuilder = normalizedOpenIdAttributesBuilder;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {

		List<ODUser> oduserList = ODUser
				.findODUsersByOpenIdIdentifier(username).getResultList();
		ODUser user = oduserList.size() == 0 ? null : oduserList.get(0);
		if (user == null) {
			user = new ODUser();
			user.setOpenIdIdentifier(username);
			user.setUserRole(UserRole.ROLE_USER);
			OpenIDAuthenticationToken token = (OpenIDAuthenticationToken) SecurityContextHolder
					.getContext().getAuthentication();
			if (token != null) {
				NormalizedOpenIdAttributes attrs = normalizedOpenIdAttributesBuilder
						.build(token);
				user.setEmailAddress(attrs.getEmailAddress());
				user.setUsername(attrs.getFullName());
				user.setDescription(attrs.getUserLocalIdentifier());
			}
			user.persist();
		}
		throwExceptionIfNotFound(user, username);
		return user;
	}

	private void throwExceptionIfNotFound(ODUser user, String loginOpenId) {
		if (user == null) {
			throw new UsernameNotFoundException("User with open id login "
					+ loginOpenId + "  has not been found.");
		}
	}
}