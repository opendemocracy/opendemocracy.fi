package fi.opendemocracy.voting.web.ui;

import fi.opendemocracy.voting.web.AbstractEntityView;
import fi.opendemocracy.voting.web.EntityEditor;
import com.vaadin.spring.roo.addon.annotations.RooVaadinEntityView;
import com.vaadin.ui.Table;

@RooVaadinEntityView(formBackingObject = fi.opendemocracy.voting.domain.ODUser.class)
public class ODUserView extends AbstractEntityView<fi.opendemocracy.voting.domain.ODUser> {

    @Override
    protected EntityEditor createForm() {
        return new ODUserForm();
    }

    @Override
    protected void configureTable(Table table) {
        table.setContainerDataSource(getTableContainer());
        table.setVisibleColumns(getTableColumns());

        setupGeneratedColumns(table);
    }

}
