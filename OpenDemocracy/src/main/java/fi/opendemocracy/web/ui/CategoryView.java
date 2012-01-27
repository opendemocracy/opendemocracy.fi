package fi.opendemocracy.web.ui;

import fi.opendemocracy.web.AbstractEntityView;
import fi.opendemocracy.web.EntityEditor;
import fi.opendemocracy.web.ThemeConstants;

import com.vaadin.spring.roo.addon.annotations.RooVaadinEntityView;
import com.vaadin.ui.Table;

@RooVaadinEntityView(formBackingObject = fi.opendemocracy.domain.Category.class)
public class CategoryView extends AbstractEntityView<fi.opendemocracy.domain.Category> {

	public CategoryView(){
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
