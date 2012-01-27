package fi.opendemocracy.web.ui;

import com.vaadin.spring.roo.addon.annotations.RooVaadinEntityView;
import com.vaadin.ui.Table;

import fi.opendemocracy.web.AbstractEntityView;
import fi.opendemocracy.web.EntityEditor;

@RooVaadinEntityView(formBackingObject = fi.opendemocracy.domain.Category.class)
public class CategoryView extends
		AbstractEntityView<fi.opendemocracy.domain.Category> {

	@Override
	protected EntityEditor createForm() {
		return new CategoryForm();
	}

	@Override
	protected void configureTable(Table table) {
		table.setContainerDataSource(getTableContainer());
		table.setVisibleColumns(getTableColumns());

		setupGeneratedColumns(table);
	}

}
