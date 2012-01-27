package fi.opendemocracy.web.ui;

import com.vaadin.spring.roo.addon.annotations.RooVaadinEntityView;
import com.vaadin.ui.Table;

import fi.opendemocracy.web.AbstractEntityView;
import fi.opendemocracy.web.EntityEditor;

@RooVaadinEntityView(formBackingObject = fi.opendemocracy.domain.Vote.class)
public class VoteView extends AbstractEntityView<fi.opendemocracy.domain.Vote> {

	@Override
	protected EntityEditor createForm() {
		return new VoteForm();
	}

	@Override
	protected void configureTable(Table table) {
		table.setContainerDataSource(getTableContainer());
		table.setVisibleColumns(getTableColumns());

		setupGeneratedColumns(table);
	}

}
