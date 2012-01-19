package fi.opendemocracy.voting.web.ui;

import fi.opendemocracy.voting.web.AbstractEntityView;
import fi.opendemocracy.voting.web.EntityEditor;
import fi.opendemocracy.voting.web.ThemeConstants;

import com.vaadin.spring.roo.addon.annotations.RooVaadinEntityView;
import com.vaadin.ui.Table;

@RooVaadinEntityView(formBackingObject = fi.opendemocracy.voting.domain.Proposition.class)
public class PropositionView extends AbstractEntityView<fi.opendemocracy.voting.domain.Proposition> {
	public PropositionView(){
		super();
		setCaption(ThemeConstants.TAB_CAPTION_PROPOSITION);
		setIcon(ThemeConstants.TAB_ICON_PROPOSITION);
	}
    @Override
    protected EntityEditor createForm() {
        return new PropositionForm();
    }

    @Override
    protected void configureTable(Table table) {
        table.setContainerDataSource(getTableContainer());
        table.setVisibleColumns(getTableColumns());

        setupGeneratedColumns(table);
    }

}

