package fi.opendemocracy.voting.web.ui;

import fi.opendemocracy.voting.web.AbstractEntityView;
import fi.opendemocracy.voting.web.EntityEditor;
import fi.opendemocracy.voting.web.ThemeConstants;

import com.vaadin.spring.roo.addon.annotations.RooVaadinEntityView;
import com.vaadin.ui.Table;

@RooVaadinEntityView(formBackingObject = fi.opendemocracy.voting.domain.Category.class)
public class CategoryView extends AbstractEntityView<fi.opendemocracy.voting.domain.Category> {

	public CategoryView(){
		super();
		setCaption(ThemeConstants.TAB_CAPTION_CATEGORIES);
		setIcon(ThemeConstants.TAB_ICON_CATEGORIES);
	}
	
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
