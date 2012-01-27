package fi.opendemocracy.domain;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findODUsersByOpenIdIdentifier" })
public class ODUser {

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
}
