package fi.opendemocracy.web.ui;

import com.vaadin.event.Action;
import com.vaadin.spring.roo.addon.annotations.RooVaadinEntityView;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

import fi.opendemocracy.domain.Category;
import fi.opendemocracy.web.AbstractEntityView;
import fi.opendemocracy.web.EntityEditor;
import fi.opendemocracy.web.ThemeConstants;
import fi.opendemocracy.web.ui.dialogs.ModalCategoryForm;
import fi.opendemocracy.web.ui.dialogs.ModalClaimExpertise;

@RooVaadinEntityView(formBackingObject = fi.opendemocracy.domain.Category.class)
public class CategoryView extends
		AbstractEntityView<fi.opendemocracy.domain.Category> {

	// right-click actions
	static final Action ACTION_NEW_CATEGORY = new Action("Create new");
	static final Action ACTION_OPEN_CATEGORY = new Action("Open");
	static final Action ACTION_SUBSCRIBE = new Action("Subscribe");
	static final Action ACTION_VIEW_PROPOSITIONS = new Action(
			"View Propositions");
	static final Action ACTION_VIEW_EXPERTS = new Action("View Experts");
	static final Action ACTION_CLAIM_EXPERTISE = new Action("Claim Expertise");
	static final Action[] ACTIONS_MENU = new Action[] { ACTION_NEW_CATEGORY };

	// Claim expertise modal
	private ModalClaimExpertise expertModal;
	private ModalCategoryForm createCategoryModal;

	public CategoryView() {
		setCaption(ThemeConstants.TAB_CAPTION_CATEGORIES);
		setIcon(ThemeConstants.TAB_ICON_CATEGORIES);
		constructTable();
	}

	private void constructTable() {
		getTable().setNullSelectionAllowed(false);

		// create custom columns
		ColumnCountGenerator columnPropositionCount = new ColumnCountGenerator(
				"propositions");
		ColumnCountGenerator columnExpertCount = new ColumnCountGenerator(
				"experts");

		getTable().addGeneratedColumn("Propositions", columnPropositionCount);
		getTable().addGeneratedColumn("Experts", columnExpertCount);

		// add right-click menu
		getTable().addActionHandler(new Action.Handler() {
			@Override
			public Action[] getActions(Object target, Object sender) {
				return ACTIONS_MENU;
			}

			@Override
			public void handleAction(Action action, Object sender, Object target) {
				if (ACTION_OPEN_CATEGORY == action) {
					createView(null);
				} else if (ACTION_NEW_CATEGORY == action && log()) {
					if (createCategoryModal == null) {
						createCategoryModal = new ModalCategoryForm();
						createCategoryModal.addListener(new CloseListener() {
							@Override
							public void windowClose(CloseEvent e) {
								getTable().setEditable(false);
							}
						});
					}
					getWindow().addWindow(createCategoryModal);
				} else if (ACTION_VIEW_PROPOSITIONS == action) {
					// TODO
					getWindow().showNotification("TODO: Propositions");
				} else if (ACTION_VIEW_EXPERTS == action) {
					// TODO
					getWindow().showNotification("TODO: Experts");
				} else if (ACTION_CLAIM_EXPERTISE == action && log()) {
					Category c = getEntityForItem(getTable().getItem(
							getTable().getValue()));
					if (expertModal == null) {
						expertModal = new ModalClaimExpertise(c);
					} else {
						expertModal.setCategory(c);
					}
					getWindow().addWindow(expertModal);
				}

			}
		});
	}

	@Override
	protected EntityEditor createForm() {
		return new CategoryForm();
	}

	@Override
	protected CustomComponent createView(String uri) {
		if (uri != null) {
			// TODO fix handling of URI
			try {
				int i = uri.indexOf("/");
				String lon = uri.substring(i + 1);
				Long id = Long.decode(lon);
				return new CategoryEntityView(getEntityForItem(getTable()
						.getItem(id)), navigator);
			} catch (NumberFormatException e) {

			}
		}
		return new CategoryEntityView(getEntityForItem(getTable().getItem(
				getTable().getValue())), navigator);

	}

	@Override
	protected void configureTable(Table table) {
		table.setContainerDataSource(getTableContainer());
		setupGeneratedColumns(table);
		getTable().setVisibleColumns(
				new Object[] { "name", "Propositions", "Experts" });
	}

	// column generator for data linked to category
	private class ColumnCountGenerator implements Table.ColumnGenerator {
		private String sumType;

		public ColumnCountGenerator(String type) {
			sumType = type;
		}

		@Override
		public Component generateCell(Table source, Object itemId,
				Object columnId) {
			// TODO: Count associated items and return sum
			Category c = getEntityForItem(getTable().getItem(itemId));
			if (sumType.equals("propositions")) {
				long count = Category.getPropositionCount(c);
				return new Label(Long.toString(count));
			} else if (sumType.equals("experts")) {
				long count = Category.getExpertCount(c);
				return new Label(Long.toString(count));
			}
			return null;
		}
	}

}
