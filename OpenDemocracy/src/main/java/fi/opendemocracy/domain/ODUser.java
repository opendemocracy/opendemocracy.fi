package fi.opendemocracy.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import fi.opendemocracy.domain.UserRole;
import javax.validation.constraints.NotNull;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

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
