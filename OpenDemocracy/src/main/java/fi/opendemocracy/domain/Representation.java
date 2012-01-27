package fi.opendemocracy.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import fi.opendemocracy.domain.Expert;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import fi.opendemocracy.domain.ODUser;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@RooJavaBean
@RooToString
@RooEntity
public class Representation {

    @NotNull
    @ManyToOne
    private Expert expert;

    @NotNull
    @ManyToOne
    private ODUser odUser;

    @NotNull
    private BigDecimal trust;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date ts;
}
