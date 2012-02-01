package fi.opendemocracy.web.ui;

import com.vaadin.spring.roo.addon.annotations.RooVaadinEntityView;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import fi.opendemocracy.web.AbstractEntityView;
import fi.opendemocracy.web.EntityEditor;

@RooVaadinEntityView(formBackingObject = fi.opendemocracy.domain.PropositionOption.class)
public class PropositionOptionView extends
		AbstractEntityView<fi.opendemocracy.domain.PropositionOption> {

	@Override
	protected EntityEditor createForm() {
		return new PropositionOptionForm();
	}

	@Override
	protected CustomComponent createView() {
		return null;
	}

	@Override
	protected void configureTable(Table table) {
		table.setContainerDataSource(getTableContainer());
		table.setVisibleColumns(getTableColumns());

		setupGeneratedColumns(table);
	}

}
