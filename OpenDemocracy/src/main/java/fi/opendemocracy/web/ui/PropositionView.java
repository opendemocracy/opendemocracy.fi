package fi.opendemocracy.web.ui;

import com.vaadin.event.Action;
import com.vaadin.spring.roo.addon.annotations.RooVaadinEntityView;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;

import fi.opendemocracy.web.AbstractEntityView;
import fi.opendemocracy.web.EntityEditor;
import fi.opendemocracy.web.ThemeConstants;

@RooVaadinEntityView(formBackingObject = fi.opendemocracy.domain.Proposition.class)
public class PropositionView extends
		AbstractEntityView<fi.opendemocracy.domain.Proposition> {
	// right-click actions
	static final Action ACTION_NEW_PROPOSITION = new Action("Create new");
	static final Action ACTION_OPEN_PROPOSITION = new Action("Open");
	static final Action[] ACTIONS_MENU = new Action[] { ACTION_NEW_PROPOSITION };

	public PropositionView() {
		setCaption(ThemeConstants.TAB_CAPTION_PROPOSITION);
		setIcon(ThemeConstants.TAB_ICON_PROPOSITION);
		// add right-click menu
		getTable().addActionHandler(new Action.Handler() {
			@Override
			public Action[] getActions(Object target, Object sender) {
				return ACTIONS_MENU;
			}

			@Override
			public void handleAction(Action action, Object sender, Object target) {
				if (ACTION_OPEN_PROPOSITION == action) {
					getWindow().showNotification("TODO: Open");
				} else if (ACTION_NEW_PROPOSITION == action && log()) {
					navigateTo("new");
				}
			}
		});
	}

	@Override
	protected EntityEditor createForm() {
		return new PropositionForm();
	}

	@Override
	protected CustomComponent createView(String uri) {
		if (uri != null) {
			// TODO fix handling of URI
			try {
				int i = uri.indexOf("/");
				String lon = uri.substring(i + 1);
				Long id = Long.decode(lon);
				return new PropositionEntityView(getEntityForItem(getTable()
						.getItem(id)));
			} catch (NumberFormatException e) {

			}
		}
		return new PropositionEntityView(getEntityForItem(getTable().getItem(
				getTable().getValue())));
	}

	@Override
	protected void configureTable(Table table) {
		table.setContainerDataSource(getTableContainer());
		table.setVisibleColumns(new Object[] { "name", "description",
				"categories", "propositionOptions", "author", "ts" });
		table.setColumnHeader("propositionOptions", "Options");
		table.setColumnHeader("ts", "Created");
		setupGeneratedColumns(table);
	}
}
