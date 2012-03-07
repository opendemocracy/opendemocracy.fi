package fi.opendemocracy.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationStatus;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class OpenIDAuthenticationFailureHandler extends
		SimpleUrlAuthenticationFailureHandler {
	private String openIdRegistrationUrl = "/OpenDemocracy/register";
	private NormalizedOpenIdAttributesBuilder normalizedOpenIdAttributesBuilder;

	public OpenIDAuthenticationFailureHandler(
			NormalizedOpenIdAttributesBuilder normalizedOpenIdAttributesBuilder) {
		this.normalizedOpenIdAttributesBuilder = normalizedOpenIdAttributesBuilder;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		if (openIdAuthenticationSuccesfullButUserIsNotRegistered(exception)) {
			redirectToOpenIdRegistrationUrl(request, response, exception);
		} else {
			super.onAuthenticationFailure(request, response, exception);
		}
	}

	private void redirectToOpenIdRegistrationUrl(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
		addOpenIdAttributesToSession(request,
				getOpenIdAuthenticationToken(exception));
		redirectStrategy.sendRedirect(request, response, openIdRegistrationUrl);
	}

	private void addOpenIdAttributesToSession(HttpServletRequest request,
			OpenIDAuthenticationToken openIdAuthenticationToken)
			throws ServletException {
		HttpSession session = request.getSession(false);
		sessionShouldBePresent(session);
		NormalizedOpenIdAttributes normalizedOpenIdAttributes = normalizedOpenIdAttributesBuilder
				.build(openIdAuthenticationToken);
		session.setAttribute(SessionKeys.openIdAttributes.toString(),
				normalizedOpenIdAttributes);
	}

	private void sessionShouldBePresent(HttpSession session)
			throws ServletException {
		if (session == null) {
			throw new ServletException("No session found");
		}
	}

	private boolean openIdAuthenticationSuccesfullButUserIsNotRegistered(
			AuthenticationException exception) {
		return exception instanceof UsernameNotFoundException
				&& exception.getAuthentication() instanceof OpenIDAuthenticationToken
				&& OpenIDAuthenticationStatus.SUCCESS
						.equals((getOpenIdAuthenticationToken(exception))
								.getStatus());
	}

	private OpenIDAuthenticationToken getOpenIdAuthenticationToken(
			AuthenticationException exception) {
		return ((OpenIDAuthenticationToken) exception.getAuthentication());
	}

}