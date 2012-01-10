package fi.opendemocracy.voting.web.ui;

import fi.opendemocracy.voting.web.AbstractEntityView;
import fi.opendemocracy.voting.web.EntityEditor;
import com.vaadin.spring.roo.addon.annotations.RooVaadinEntityView;
import com.vaadin.ui.Table;

@RooVaadinEntityView(formBackingObject = fi.opendemocracy.voting.domain.PropositionOption.class)
public class PropositionOptionView extends AbstractEntityView<fi.opendemocracy.voting.domain.PropositionOption> {

    @Override
    protected EntityEditor createForm() {
        return new PropositionOptionForm();
    }

    @Override
    protected void configureTable(Table table) {
        table.setContainerDataSource(getTableContainer());
        table.setVisibleColumns(getTableColumns());

        setupGeneratedColumns(table);
    }

}
