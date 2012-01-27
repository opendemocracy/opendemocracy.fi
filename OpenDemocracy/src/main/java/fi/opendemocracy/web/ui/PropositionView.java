package fi.opendemocracy.web.ui;

import com.vaadin.spring.roo.addon.annotations.RooVaadinEntityView;
import com.vaadin.ui.Table;

import fi.opendemocracy.web.AbstractEntityView;
import fi.opendemocracy.web.EntityEditor;
import fi.opendemocracy.web.ThemeConstants;

@RooVaadinEntityView(formBackingObject = fi.opendemocracy.domain.Proposition.class)
public class PropositionView extends
		AbstractEntityView<fi.opendemocracy.domain.Proposition> {

	public PropositionView(){
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
