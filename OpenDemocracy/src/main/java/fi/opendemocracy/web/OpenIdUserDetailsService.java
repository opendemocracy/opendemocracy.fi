package fi.opendemocracy.web;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import fi.opendemocracy.domain.ODUser;
import fi.opendemocracy.domain.UserRole;

public class OpenIdUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String id)
			throws UsernameNotFoundException, DataAccessException {
		List<ODUser> oduserList = ODUser.findODUsersByOpenIdIdentifier(id)
				.getResultList();
		ODUser user = oduserList.size() == 0 ? null : oduserList.get(0);
		if (user == null) {
			user = new ODUser();
			user.setOpenIdIdentifier(id);
			user.setUserRole(UserRole.ROLE_USER);
			user.setDescription("New user");
			OpenIDAuthenticationToken token = (OpenIDAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
			List<OpenIDAttribute> attributes = token.getAttributes();
			for (OpenIDAttribute oIDA : attributes) {
				if (oIDA.getName().equalsIgnoreCase("email")) {
					StringBuilder email = new StringBuilder();
					for (String mail : oIDA.getValues()) {
						email.append(mail);
					}
					user.setEmailAddress(email.toString());
				}
				if (oIDA.getName().equalsIgnoreCase("name")) {
					StringBuilder names = new StringBuilder();
					for (String name : oIDA.getValues()) {
						names.append(name);
					}
					user.setEmailAddress(names.toString());
				}
			}
			user.persist();
		}
		return user;
	}

}
