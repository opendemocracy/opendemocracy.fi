package fi.opendemocracy.voting.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import fi.opendemocracy.voting.domain.ODUser;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import fi.opendemocracy.voting.domain.PropositionOption;
import java.math.BigDecimal;

@RooJavaBean
@RooToString
@RooEntity
public class Vote {

    @NotNull
    @ManyToOne
    private ODUser odUser;

    @NotNull
    @ManyToOne
    private PropositionOption propositionOption;

    @NotNull
    private BigDecimal support;
}
