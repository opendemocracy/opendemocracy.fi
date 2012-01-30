package fi.opendemocracy.web;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
			user.persist();
		}
		return user;
	}

}
