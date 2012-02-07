package fi.opendemocracy.web.ui;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.TypedQuery;

import com.vaadin.Application;
import com.vaadin.addon.jpacontainer.EntityContainer;
import com.vaadin.addon.jpacontainer.EntityProvider;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.spring.roo.addon.annotations.RooVaadinEntityView;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Table.FooterClickEvent;
import com.vaadin.ui.themes.Reindeer;

import fi.opendemocracy.domain.Category;
import fi.opendemocracy.domain.Expert;
import fi.opendemocracy.domain.ODUser;
import fi.opendemocracy.domain.Proposition;
import fi.opendemocracy.domain.PropositionOption;
import fi.opendemocracy.domain.Representation;
import fi.opendemocracy.domain.Vote;
import fi.opendemocracy.web.TabNavigator;
import fi.opendemocracy.web.ThemeConstants;
import fi.opendemocracy.web.ui.dialogs.ModalClaimExpertise;
import fi.opendemocracy.web.ui.dialogs.ModalTrustExpert;

public class CategoryEntityView extends CustomComponent implements
TabNavigator.View {
	private AbsoluteLayout mainLayout;
	private Panel scrollPanel;
	private VerticalLayout scrollContent;
	private final Category sourceCategory;
	private final BeanItemContainer<Expert> expertContainer;
	private final BeanItemContainer<Proposition> propositionContainer;
	private VerticalLayout trustExpertForm;
	private Window wDialog;
	private ModalClaimExpertise claimExpertiseModal;
	private ModalTrustExpert trustExpertModal;
	private TabNavigator navigator;

	
	public CategoryEntityView(Category c){
		sourceCategory = c;
		claimExpertiseModal = new ModalClaimExpertise(sourceCategory);
		final Set<Category> finderSet = new HashSet<Category>(Arrays.asList(sourceCategory));
		final List<Expert> expertList = Expert.findExpertsByCategory(sourceCategory).getResultList();
		final List<Proposition> propositionList = Proposition.findPropositionsByCategories(finderSet).getResultList();
		
		//TODO: BeanItemContainer -> JPAContainer
		expertContainer = (expertList.size() > 0) ? new BeanItemContainer<Expert>(expertList) : null;
		propositionContainer = (propositionList.size() > 0) ? new BeanItemContainer<Proposition>(propositionList) : null;
		
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
		Label description = new Label("<p>" + sourceCategory.getDescription() + "</p>");
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
		
		if(expertContainer != null){
			final Table expertTable = setupNewTable(ThemeConstants.TAB_CAPTION_EXPERTS, ThemeConstants.TAB_ICON_EXPERTS);	
			expertTable.setSizeFull();
			
			expertTable.addGeneratedColumn("Username", new ExpertColumnGenerator("username"));
			expertTable.addGeneratedColumn("Representation", new ExpertColumnGenerator("representation"));
			expertTable.addGeneratedColumn("Distrust", new ExpertColumnGenerator("distrust"));
			
			expertTable.setContainerDataSource(expertContainer);
			expertTable.setVisibleColumns(new Object[] {"Username", "Representation", "Distrust"});
			expertTable.addListener(new ItemClickListener() {
				@Override
				public void itemClick(ItemClickEvent event) {
					Object value = event.getItemId();
					if(expertTable.getValue() == value && event.getButton() == ItemClickEvent.BUTTON_LEFT){
						//TODO: Cleanup following line ;)
						Expert e = (Expert) (((BeanItem<Expert>)expertTable.getItem(value)).getBean());
						trustExpert(e);
					}
				}
			});
			expertTable.setFooterVisible(true);
			expertTable.setColumnFooter("Username", expertTable.size() + " experts");
			tables.addComponent(expertTable);
		}else{
			tables.addComponent(new Label("No experts available in this category yet."));
		}
		if(propositionContainer != null){
			final Table propositionTable = setupNewTable(ThemeConstants.TAB_CAPTION_PROPOSITION, ThemeConstants.TAB_ICON_PROPOSITION);
			propositionTable.setSizeFull();
			propositionTable.setContainerDataSource(propositionContainer);
			propositionTable.addGeneratedColumn("Author", new PropositionColumnGenerator("username"));
			propositionTable.setVisibleColumns(new Object[] {"name", "Author"});
			tables.addComponent(propositionTable);
			propositionTable.setFooterVisible(true);
			propositionTable.setColumnFooter("name", propositionTable.size() + " propositions");
			propositionTable.addListener(new ItemClickListener() {
				@Override
				public void itemClick(ItemClickEvent event) {
					Object value = event.getItemId();
					if(propositionTable.getValue() == value && event.getButton() == ItemClickEvent.BUTTON_LEFT){
						//TODO: Cleanup following line ;)
						Proposition p = (Proposition) (((BeanItem<Proposition>)propositionTable.getItem(value)).getBean());
						navigator.openChildTab(new PropositionEntityView(p), "proposition/view/" + p.getId().toString());
					}
				}
			});
		}else{
			tables.addComponent(new Label("No propositions available in this category yet."));
		}

		Button toggleExpertise = new Button("Claim expertise", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if(getApplication().getUser() != null){
					getWindow().addWindow(claimExpertiseModal);
				}else{
					getWindow().showNotification("Not logged in");
				}
			}
		});
		
		scrollContent.addComponent(toggleExpertise);
		return scrollContent;
	}

	private Table setupNewTable(String title, ThemeResource icon){
		Table table = new Table();
		table.setSelectable(true);
		table.setNullSelectionAllowed(false);
		table.setCaption(title);
		table.setIcon(icon);
        table.addStyleName(Reindeer.TABLE_STRONG);
		return table;
	}
	
	//Trust expert modal
	private void trustExpert(Expert e) {
		if(getApplication().getUser() == null){
			getWindow().showNotification("Login to see/trust experts");
			return;
		}
		if(trustExpertModal == null){
			trustExpertModal = new ModalTrustExpert(e);
		}else{
			trustExpertModal.setExpert(e);
		}
		getWindow().addWindow(trustExpertModal);
	}
	
	//Column generators for custom tables
	//TODO: Make getters in domain classes instead of generators?
    private class ExpertColumnGenerator implements Table.ColumnGenerator {
    	private String column;
    	private LayoutClickListener parentListener;
    	
    	public ExpertColumnGenerator(String column){
    		this.column = column;
    	}
    	
        public Component generateCell(final Table source, final Object itemId, Object columnId) {
        	CssLayout labelWrapper = new CssLayout();
        	Label generatedCell;
           	BeanItem<Expert> item = (BeanItem<Expert>) source.getItem(itemId);
        	final Expert expert = item.getBean();
        	if(column == "representation"){
	        	BigDecimal rep = Category.getRepresentation(expert);
	        	generatedCell = new Label((rep == null) ? "0" : rep.toPlainString());
        	}else if (column == "username"){
        		String username = expert.getOdUser().getUsername();
        		if(username == null){
        			generatedCell = new Label("N/A");
        		} else {
        			generatedCell = new Label(username);	
        		}
        	}else if (column == "distrust"){
        		BigDecimal rep = Category.getDistrust(expert);
        		generatedCell = new Label((rep == null) ? "0" : rep.toPlainString());
        	}else{
        		generatedCell = new Label("Error");
        	}
    		labelWrapper.addListener(new LayoutClickListener() {
                @Override
                public void layoutClick(LayoutClickEvent event) {
                	if(source.getValue() == itemId && event.getButton() == ItemClickEvent.BUTTON_LEFT){
						//TODO: Cleanup following line ;)
						Expert e = (Expert) (((BeanItem<Expert>)source.getItem(itemId)).getBean());
						trustExpert(e);
					}else{
						source.select(itemId);
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
    	public PropositionColumnGenerator(String column){
    		this.column = column;
    	}
        public Component generateCell(Table source, Object itemId, Object columnId) {
        	BeanItem<Proposition> item = (BeanItem<Proposition>) source.getItem(itemId);
        	final Proposition proposition = item.getBean();
        	if (column == "username"){
        		String username = proposition.getAuthor().getUsername();
        		if(username == null){
        			return new Label("N/A");
        		}
        		return new Label(username);
        	}
			return new Label("Error");
        }
    }
	
	@Override
	public void init(TabNavigator navigator, Application application) {
		this.navigator = navigator;
	}

	@Override
	public void navigateTo(String requestedDataId) {
		
		
	}

	@Override
	public String getWarningForNavigatingFrom() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
