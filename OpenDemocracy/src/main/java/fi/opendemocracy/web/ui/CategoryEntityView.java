package fi.opendemocracy.web.ui;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.util.filter.Compare.Equal;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.themes.Reindeer;

import fi.opendemocracy.domain.Category;
import fi.opendemocracy.domain.Expert;
import fi.opendemocracy.domain.Proposition;
import fi.opendemocracy.web.EntityProviderUtil;
import fi.opendemocracy.web.TabNavigator;
import fi.opendemocracy.web.ThemeConstants;
import fi.opendemocracy.web.ui.dialogs.ModalClaimExpertise;
import fi.opendemocracy.web.ui.dialogs.ModalTrustExpert;

public class CategoryEntityView extends CustomComponent {
	private AbsoluteLayout mainLayout;
	private Panel scrollPanel;
	private VerticalLayout scrollContent;
	private final Category sourceCategory;
	private ModalClaimExpertise claimExpertiseModal;
	private ModalTrustExpert trustExpertModal;
	private CloseListener closeTrustExpertModal;

	private TabNavigator navigator;
	private JPAContainer<Expert> expertContainer;
	private HashMap<String, ExpertColumnGenerator> expertTableGeneratedColumns = new HashMap<String, ExpertColumnGenerator>();
	private JPAContainer<Proposition> propositionContainer;
	private final Table expertTable = setupNewTable(
			ThemeConstants.TAB_CAPTION_EXPERTS, ThemeConstants.TAB_ICON_EXPERTS);

	public CategoryEntityView(Category c, TabNavigator navigator) {
		this.navigator = navigator;
		sourceCategory = c;

		expertContainer = new JPAContainer<Expert>(Expert.class);
		expertContainer.setEntityProvider(EntityProviderUtil.get()
				.getEntityProvider(Expert.class));
		expertContainer
				.addContainerFilter(new Equal("category", sourceCategory));

		propositionContainer = new JPAContainer<Proposition>(Proposition.class);
		propositionContainer.setEntityProvider(EntityProviderUtil.get()
				.getEntityProvider(Proposition.class));
		propositionContainer.addContainerFilter(new Equal("categories",
				sourceCategory));

		mainLayout = buildMainLayout();
		setCompositionRoot(mainLayout);
		setCaption(c.getName());
		setIcon(ThemeConstants.TAB_ICON_CATEGORIES);
	}

	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.addStyleName("blue-bottom");

		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");

		// scrollPanel
		scrollPanel = buildScrollPanel();
		mainLayout.addComponent(scrollPanel);

		return mainLayout;
	}

	private Panel buildScrollPanel() {
		// common part: create layout
		scrollPanel = new Panel();
		scrollPanel.setWidth("100.0%");
		scrollPanel.setHeight("100.0%");
		scrollPanel.setImmediate(false);

		// scrollContent
		scrollContent = buildScrollContent();
		scrollPanel.setContent(scrollContent);

		return scrollPanel;
	}

	private VerticalLayout buildScrollContent() {
		// common part: create layout
		scrollContent = new VerticalLayout();
		scrollContent.setWidth("100.0%");
		scrollContent.setHeight("-1px");
		scrollContent.setImmediate(false);
		scrollContent.setMargin(true);
		scrollContent.setSpacing(true);

		Label title = new Label("<h1>" + sourceCategory.getName() + "</h1>");
		title.setWidth(100, Sizeable.UNITS_PERCENTAGE);
		Label description = new Label("<p>" + sourceCategory.getDescription()
				+ "</p>");
		description.setWidth(100, Sizeable.UNITS_PERCENTAGE);

		title.setContentMode(Label.CONTENT_XHTML);
		description.setContentMode(Label.CONTENT_XHTML);

		scrollContent.addComponent(title);
		scrollContent.addComponent(description);

		HorizontalLayout tables = new HorizontalLayout();
		tables.setSizeFull();
		tables.setSpacing(true);
		scrollContent.addComponent(tables);
		scrollContent.setExpandRatio(tables, 1.0f);

		if (expertContainer != null) {
			expertTable.setSizeFull();

			expertTableGeneratedColumns.put("Username",
					new ExpertColumnGenerator("username"));
			expertTableGeneratedColumns.put("Average Trust",
					new ExpertColumnGenerator("representation"));
			expertTableGeneratedColumns.put("Supporters",
					new ExpertColumnGenerator("supporters"));
			expertTableGeneratedColumns.put("Average Distrust",
					new ExpertColumnGenerator("distrust"));
			expertTableGeneratedColumns.put("Opposers",
					new ExpertColumnGenerator("opposers"));

			Iterator<Map.Entry<String, ExpertColumnGenerator>> it = expertTableGeneratedColumns
					.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, ExpertColumnGenerator> col = it.next();
				expertTable.addGeneratedColumn(col.getKey(), col.getValue());
			}

			expertTable.setContainerDataSource(expertContainer);
			expertTable.setImmediate(true);
			expertTable.setVisibleColumns(new Object[] { "Username",
					"Average Trust", "Supporters", "Average Distrust",
					"Opposers" });
			expertTable.addListener(new ItemClickListener() {
				@Override
				public void itemClick(ItemClickEvent event) {
					Object value = event.getItemId();
					if (expertTable.getValue() == value
							&& event.getButton() == com.vaadin.event.MouseEvents.ClickEvent.BUTTON_LEFT) {
						final Expert e = expertContainer.getItem(value)
								.getEntity();
						trustExpert(e);
					}
				}
			});

			trustExpertModal = new ModalTrustExpert();
			closeTrustExpertModal = new CloseListener() {
				@Override
				public void windowClose(CloseEvent ev) {
					// Refresh table superhack
					expertTable.setEditable(false);
				}
			};
			trustExpertModal.addListener(closeTrustExpertModal);

			expertTable.setFooterVisible(true);
			expertTable.setColumnFooter("Username", expertTable.size()
					+ " experts");
			tables.addComponent(expertTable);
		} else {
			tables.addComponent(new Label(
					"No experts available in this category yet."));
		}
		if (propositionContainer != null) {
			final Table propositionTable = setupNewTable(
					ThemeConstants.TAB_CAPTION_PROPOSITION,
					ThemeConstants.TAB_ICON_PROPOSITION);
			propositionTable.setSizeFull();
			propositionTable.setContainerDataSource(propositionContainer);
			propositionTable.addGeneratedColumn("Author",
					new PropositionColumnGenerator("username"));
			propositionTable
					.setVisibleColumns(new Object[] { "name", "Author" });
			tables.addComponent(propositionTable);
			propositionTable.setFooterVisible(true);
			propositionTable.setColumnFooter("name", propositionTable.size()
					+ " propositions");
			propositionTable.addListener(new ItemClickListener() {
				@Override
				public void itemClick(ItemClickEvent event) {
					Object value = event.getItemId();
					if (propositionTable.getValue() == value
							&& event.getButton() == com.vaadin.event.MouseEvents.ClickEvent.BUTTON_LEFT) {
						navigator.navigateTo("proposition/view/" + value);
					}
				}
			});
		} else {
			tables.addComponent(new Label(
					"No propositions available in this category yet."));
		}

		Button toggleExpertise = new Button("Claim expertise",
				new Button.ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						if (getApplication().getUser() != null) {
							claimExpertise();
						} else {
							getWindow().showNotification("Not logged in");
						}
					}
				});

		scrollContent.addComponent(toggleExpertise);
		return scrollContent;
	}

	private Table setupNewTable(String title, ThemeResource icon) {
		Table table = new Table();
		table.setSelectable(true);
		table.setNullSelectionAllowed(false);
		table.setCaption(title);
		table.setIcon(icon);
		table.addStyleName(Reindeer.TABLE_STRONG);
		table.setImmediate(true);
		return table;
	}

	// Trust expert modal
	private void trustExpert(final Expert e) {
		if (getApplication().getUser() == null) {
			getWindow().showNotification("Login to see/trust experts");
			return;
		}
		trustExpertModal.setExpert(e);
		getWindow().addWindow(trustExpertModal);
	}

	// Claim expertise modal
	private void claimExpertise() {
		if (getApplication().getUser() == null) {
			getWindow().showNotification("Login to see/trust experts");
			return;
		}
		claimExpertiseModal = new ModalClaimExpertise(sourceCategory);
		claimExpertiseModal.addListener(new CloseListener() {
			@Override
			public void windowClose(CloseEvent ev) {
				// Refresh table superhack
				expertTable.setEditable(false);
			}
		});
		getWindow().addWindow(claimExpertiseModal);
	}

	// Column generators for custom tables
	// TODO: Make getters in domain classes instead of generators?
	private class ExpertColumnGenerator implements Table.ColumnGenerator {
		private String column;

		public ExpertColumnGenerator(String column) {
			this.column = column;
		}

		// TODO: Check error handling / null statements
		@Override
		public Component generateCell(final Table source, final Object itemId,
				Object columnId) {
			CssLayout labelWrapper = new CssLayout();
			Label generatedCell;
			final Expert expert = expertContainer.getItem(itemId).getEntity();
			if (column == "representation") {
				BigDecimal rep = Category.getRepresentation(expert);
				generatedCell = new Label((rep == null) ? "0"
						: Integer.toString(Math.round(rep.floatValue())));
			} else if (column == "supporters") {
				long supporterCount = Category.getSupporters(expert);
				generatedCell = new Label(Long.toString(supporterCount));
			} else if (column == "username") {
				String username = expert.getOdUser().getUsername();
				if (username == null) {
					generatedCell = new Label("N/A");
				} else {
					generatedCell = new Label(username);
				}
			} else if (column == "distrust") {
				BigDecimal rep = Category.getDistrust(expert);
				generatedCell = new Label((rep == null) ? "0"
						: Integer.toString(Math.round(rep.floatValue())));
			} else if (column == "opposers") {
				long supporterCount = Category.getOpposers(expert);
				generatedCell = new Label(Long.toString(supporterCount));
			} else {
				generatedCell = new Label("Error");
			}
			labelWrapper.addListener(new LayoutClickListener() {
				@Override
				public void layoutClick(LayoutClickEvent event) {
					if (source.getValue() == itemId
							&& event.getButton() == com.vaadin.event.MouseEvents.ClickEvent.BUTTON_LEFT) {
						Expert e = expertContainer.getItem(itemId).getEntity();
						trustExpert(e);
					} else {
						source.setValue(itemId);
					}
				}
			});
			labelWrapper.setSizeFull();
			labelWrapper.addComponent(generatedCell);
			return labelWrapper;
		}
	}

	private class PropositionColumnGenerator implements Table.ColumnGenerator {
		private String column;

		public PropositionColumnGenerator(String column) {
			this.column = column;
		}

		@Override
		public Component generateCell(Table source, Object itemId,
				Object columnId) {
			final Proposition proposition = propositionContainer
					.getItem(itemId).getEntity();
			if (column == "username") {
				String username = proposition.getAuthor().getUsername();
				if (username == null) {
					return new Label("N/A");
				}
				return new Label(username);
			}
			return new Label("Error");
		}
	}

}
