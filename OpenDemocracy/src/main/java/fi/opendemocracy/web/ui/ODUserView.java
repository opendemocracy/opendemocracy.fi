package fi.opendemocracy.web.ui;

import com.vaadin.spring.roo.addon.annotations.RooVaadinEntityView;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import fi.opendemocracy.domain.Category;
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

		setCaption(ThemeConstants.TAB_CAPTION_USER);
		setIcon(ThemeConstants.TAB_ICON_USERS);
	}

	@Override
	protected EntityEditor createForm() {
		return new ODUserForm();
	}

	@Override
	protected CustomComponent createView(String uri) {
		ODUser entity = null;
		if (uri != null) { 
			// TODO fix handling of URI
			try {
				int i = uri.indexOf("/");
				String lon = uri.substring(i+1);
				Long id = Long.decode(lon);
				entity = (ODUser) getEntityForItem(getTable().getItem(id));
			} catch (NumberFormatException e) {
				
			}
		}
		if (entity == null) {
			entity = (ODUser) getEntityForItem(getTable().getItem(getTable().getValue()));
		}
		Object user = getApplication().getUser();
		if (user != null && user instanceof ODUser && ((ODUser)user).getId().equals(entity.getId())) {
			return (CustomComponent) getForm();
		}
		return new UserProfile(entity);
	} 

	@Override
	protected void configureTable(Table table) {
		table.setContainerDataSource(getTableContainer());
		table.setVisibleColumns(new Object[]{"id", "username", "firstName", "lastName"});
		table.setColumnHeader("firstName", "first name");
		table.setColumnHeader("lastName", "last name");

		setupGeneratedColumns(table);
	}

}
