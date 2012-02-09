package fi.opendemocracy.web.ui;

import com.vaadin.spring.roo.addon.annotations.RooVaadinEntityView;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import fi.opendemocracy.domain.ODUser;
import fi.opendemocracy.domain.Proposition;
import fi.opendemocracy.web.AbstractEntityView;
import fi.opendemocracy.web.EntityEditor;
import fi.opendemocracy.web.ThemeConstants;

@RooVaadinEntityView(formBackingObject = fi.opendemocracy.domain.ODUser.class)
public class ODUserView extends
		AbstractEntityView<fi.opendemocracy.domain.ODUser> {

	public ODUserView() {
		super();

		setCaption(ThemeConstants.TAB_CAPTION_PROPOSITION);
		setIcon(ThemeConstants.TAB_ICON_PROPOSITION);
	}

	@Override
	protected EntityEditor createForm() {
		return new ODUserForm();
	}

	@Override
	protected CustomComponent createView() {
		ODUser entity = (ODUser) getEntityForItem(getTable().getItem(getTable().getValue()));
		Object user = getApplication().getUser();
		if (user != null && user instanceof ODUser && ((ODUser)user).getId().equals(entity.getId())) {
			return (CustomComponent) getForm();
		}
		return new UserProfile(entity);
	} 

	@Override
	protected void configureTable(Table table) {
		table.setContainerDataSource(getTableContainer());
		table.setVisibleColumns(getTableColumns());

		setupGeneratedColumns(table);
	}

}
