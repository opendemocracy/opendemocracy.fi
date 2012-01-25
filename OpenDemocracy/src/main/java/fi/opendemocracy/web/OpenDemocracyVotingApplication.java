package fi.opendemocracy.web;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.vaadin.openid.OpenIdHandler;
import org.vaadin.openid.OpenIdHandler.UserAttribute;

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class OpenDemocracyVotingApplication extends Application {

	public WebApplicationContext appContext;
	
	@Override
	public void init() {
		Window window = createNewWindow();
		setMainWindow(window);
		setLogoutURL("/OpenDemocracy/j_spring_security_logout");
    }

	public Window createNewWindow() {
		final Window window = new OpenDemocracyVotingWindow();

		// remove window on close to avoid memory leaks
		window.addListener(new CloseListener() {
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
	
	public void setWebApplicationContext(WebApplicationContext appContext)
	{
		this.appContext = appContext;
	}

}
