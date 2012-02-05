package fi.opendemocracy.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findVotesByOdUser", "findVotesByPropositionAndPropositionOption" })
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
