package fi.opendemocracy.voting.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import fi.opendemocracy.voting.domain.Category;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import fi.opendemocracy.voting.domain.Expert;
import fi.opendemocracy.voting.domain.ODUser;
import java.math.BigDecimal;

@RooJavaBean
@RooToString
@RooEntity
public class Representation {

    @NotNull
    @ManyToOne
    private Category category;

    @NotNull
    @ManyToOne
    private Expert expert;

    @NotNull
    @ManyToOne
    private ODUser odUser;

    @NotNull
    private BigDecimal trust;
}
