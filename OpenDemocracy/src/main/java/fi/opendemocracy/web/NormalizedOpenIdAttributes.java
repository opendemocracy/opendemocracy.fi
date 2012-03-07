package fi.opendemocracy.web;

import java.io.Serializable;

public class NormalizedOpenIdAttributes implements Serializable {
	private String userLocalIdentifier;
	private String emailAddress;
	private String fullName;
	private String loginReplacement;

	public NormalizedOpenIdAttributes(String userLocalIdentifier,
			String emailAddress, String fullName, String loginReplacement) {
		this.userLocalIdentifier = userLocalIdentifier;
		this.emailAddress = emailAddress;
		this.fullName = fullName;
		this.loginReplacement = loginReplacement;
	}

	public String getUserLocalIdentifier() {
		return userLocalIdentifier;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getFullName() {
		return fullName;
	}

	public String getLoginReplacement() {
		return loginReplacement;
	}
}