package fi.opendemocracy.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RooJavaBean
@RooToString
@RooEntity
public class PropositionOption {

    @NotNull
    @Size(min = 2, max = 255)
    private String title;

    @NotNull
    @Size(min = 2, max = 4096)
    private String description;
}
