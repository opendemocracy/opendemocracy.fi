package fi.opendemocracy.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import fi.opendemocracy.domain.ODUser;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import java.util.Set;
import fi.opendemocracy.domain.Category;
import java.util.HashSet;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import fi.opendemocracy.domain.PropositionOption;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@RooJavaBean
@RooToString
@RooEntity
public class Proposition {

    @NotNull
    @ManyToOne
    private ODUser author;

    @NotNull
    @Size(min = 2, max = 127)
    private String name;

    @NotNull
    @Size(min = 2)
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Category> categories = new HashSet<Category>();

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<PropositionOption> propositionOptions = new HashSet<PropositionOption>();

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date ts;
}