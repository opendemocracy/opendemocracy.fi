package fi.opendemocracy.voting.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import fi.opendemocracy.voting.domain.Category;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import fi.opendemocracy.voting.domain.ODUser;

@RooJavaBean
@RooToString
@RooEntity
public class Expert {

    @NotNull
    @ManyToOne
    private Category category;

    @NotNull
    @ManyToOne
    private ODUser odUser;
}
