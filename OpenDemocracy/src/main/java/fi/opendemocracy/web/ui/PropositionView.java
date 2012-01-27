package fi.opendemocracy.web.ui;

import fi.opendemocracy.web.AbstractEntityView;
import fi.opendemocracy.web.EntityEditor;
import com.vaadin.spring.roo.addon.annotations.RooVaadinEntityView;
import com.vaadin.ui.Table;

@RooVaadinEntityView(formBackingObject = fi.opendemocracy.domain.Proposition.class)
public class PropositionView extends AbstractEntityView<fi.opendemocracy.domain.Proposition> {

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
