package fi.opendemocracy.voting.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import fi.opendemocracy.voting.domain.Proposition;
import javax.persistence.ManyToOne;

@RooJavaBean
@RooToString
@RooEntity
public class PropositionOption {

    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @NotNull
    @Size(min = 2, max = 4096)
    private String description;

    @NotNull
    @ManyToOne
    private Proposition proposition;
}
