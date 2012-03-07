package fi.opendemocracy.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findPropositionsByCategories" })
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
