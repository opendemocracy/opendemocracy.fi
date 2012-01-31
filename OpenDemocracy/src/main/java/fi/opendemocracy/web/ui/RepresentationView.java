package fi.opendemocracy.web.ui;

import com.vaadin.spring.roo.addon.annotations.RooVaadinEntityView;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import fi.opendemocracy.web.AbstractEntityView;
import fi.opendemocracy.web.EntityEditor;

@RooVaadinEntityView(formBackingObject = fi.opendemocracy.domain.Representation.class)
public class RepresentationView extends
		AbstractEntityView<fi.opendemocracy.domain.Representation> {

	@Override
	protected EntityEditor createForm() {
		return new RepresentationForm();
	}

	@Override
	protected VerticalLayout createView() {
		return null;
	}

	@Override
	protected void configureTable(Table table) {
		table.setContainerDataSource(getTableContainer());
		table.setVisibleColumns(getTableColumns());

		setupGeneratedColumns(table);
	}

}
