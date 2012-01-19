package fi.opendemocracy.voting.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import fi.opendemocracy.voting.domain.ODUser;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import java.util.Set;
import fi.opendemocracy.voting.domain.Category;
import java.util.HashSet;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;

@RooJavaBean
@RooToString
@RooEntity
public class Proposition {

    @NotNull
    @ManyToOne
    private ODUser author;

    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @NotNull
    @Size(min = 2, max = 4096)
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Category> categories = new HashSet<Category>();
}
