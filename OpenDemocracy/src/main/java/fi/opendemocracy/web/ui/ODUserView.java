package fi.opendemocracy.web.ui;

import fi.opendemocracy.web.AbstractEntityView;
import fi.opendemocracy.web.EntityEditor;
import com.vaadin.spring.roo.addon.annotations.RooVaadinEntityView;
import com.vaadin.ui.Table;

@RooVaadinEntityView(formBackingObject = fi.opendemocracy.domain.ODUser.class)
public class ODUserView extends AbstractEntityView<fi.opendemocracy.domain.ODUser> {

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
