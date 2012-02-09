package fi.opendemocracy.web;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class OpenIDAuthenticationSucessHandler extends SimpleUrlAuthenticationSuccessHandler {

	public OpenIDAuthenticationSucessHandler() {
		super();
	}

	public OpenIDAuthenticationSucessHandler(String defaultTargetUrl) {
		super(defaultTargetUrl);
	}
	

}
