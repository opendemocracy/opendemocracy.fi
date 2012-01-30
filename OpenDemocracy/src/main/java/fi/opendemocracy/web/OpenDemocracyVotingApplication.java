package fi.opendemocracy.web;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;

import com.vaadin.Application;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

import fi.opendemocracy.domain.ODUser;

public class OpenDemocracyVotingApplication extends Application {

	public WebApplicationContext appContext;

	@Override
	public void init() {
		Window window = createNewWindow();
		setMainWindow(window);
		setLogoutURL("OpenDemocracy/resources/j_spring_security_logout");
	}

	public Window createNewWindow() {
		final Window window = new OpenDemocracyVotingWindow();

		// remove window on close to avoid memory leaks
		window.addListener(new CloseListener() {
			@Override
			public void windowClose(CloseEvent e) {
				if (getMainWindow() != window) {
					OpenDemocracyVotingApplication.this.removeWindow(window);
				}
			}
		});

		return window;
	}

	@Override
	public Window getWindow(String name) {
		// See if the window already exists in the application
		Window window = super.getWindow(name);

		// If a dynamically created window is requested, but
		// it does not exist yet, create it.
		if (window == null) {
			// Create the window object.
			window = createNewWindow();
			window.setName(name);

			// Add it to the application as a regular
			// application-level window
			addWindow(window);
		}

		return window;
	}

	public boolean hasAnyRole(String... roles) {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		Object user = authentication.getPrincipal();
		if (user != null && user instanceof ODUser) {
			setUser(user);
		}
		Collection<GrantedAuthority> authorities = authentication
				.getAuthorities();
		for (GrantedAuthority authority : authorities) {
			for (String role : roles) {
				if (role.equals(authority.getAuthority())) {
					return true;
				}
			}
		}
		return false;
	}

	public void setWebApplicationContext(WebApplicationContext appContext) {
		this.appContext = appContext;
	}

}
