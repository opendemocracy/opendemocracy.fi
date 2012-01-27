package fi.opendemocracy.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import fi.opendemocracy.domain.ODUser;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import fi.opendemocracy.domain.Proposition;
import fi.opendemocracy.domain.PropositionOption;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@RooJavaBean
@RooToString
@RooEntity
public class Vote {

    @NotNull
    @ManyToOne
    private ODUser odUser;

    @NotNull
    @ManyToOne
    private Proposition proposition;

    @NotNull
    @ManyToOne
    private PropositionOption propositionOption;

    @NotNull
    private BigDecimal support;

    private String comment;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date ts;
}