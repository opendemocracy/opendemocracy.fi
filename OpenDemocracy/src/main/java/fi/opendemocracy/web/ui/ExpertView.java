package fi.opendemocracy.web.ui;

import com.vaadin.spring.roo.addon.annotations.RooVaadinEntityView;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;

import fi.opendemocracy.web.AbstractEntityView;
import fi.opendemocracy.web.EntityEditor;

@RooVaadinEntityView(formBackingObject = fi.opendemocracy.domain.Expert.class)
public class ExpertView extends
		AbstractEntityView<fi.opendemocracy.domain.Expert> {

	@Override
	protected EntityEditor createForm() {
		return new ExpertForm();
	}

	@Override
	protected CustomComponent createView(String uri) {
		return null;
	}

	@Override
	protected void configureTable(Table table) {
		table.setContainerDataSource(getTableContainer());
		table.setVisibleColumns(getTableColumns());

		setupGeneratedColumns(table);
	}

}
