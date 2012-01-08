package com.opendemocracy.voting.ui;

import com.opendemocracy.voting.VotingApplication;
import com.opendemocracy.voting.data.Category;
import com.opendemocracy.voting.data.Proposition;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class PropositionMainView extends VerticalLayout implements
		TabSheet.SelectedTabChangeListener {

	private TabSheet propositionMenu;
	private VotingApplication app;
	private PropositionAddForm form;
	private VerticalLayout list;
	private VerticalLayout add;
	private VerticalLayout view;
	private VerticalLayout help;
	private final ThemeResource propositionIcon = new ThemeResource("icons/16/propositions.png");
	
	public PropositionMainView(VotingApplication app) {
		this.app = app;
		this.setSizeFull();
		// List propositions
		list = new VerticalLayout();
		list.setMargin(false);
		list.addComponent(app.getViewManager().getPropositionList());
		list.setSizeFull();

		// Add proposition
		add = new VerticalLayout();
		add.setMargin(true);
		form = app.getViewManager().getPropositionForm();
		add.addComponent(form);
		add.setWidth("100%");

		// Propositions tab
		view = new VerticalLayout();
		view.setMargin(true);
		view.addComponent(new Label("View proposition"));
		view.setSizeFull();
		
		// Help
		help = new VerticalLayout();
		help.setMargin(true);
		help.addComponent(new Label("Help"));
		help.setSizeFull();

		

		// Add main tabs
		propositionMenu = new TabSheet();
		propositionMenu.setSizeFull();
		propositionMenu.addTab(list, "List Propositions", propositionIcon);
		propositionMenu.addTab(add, "Add proposition", new ThemeResource(
				"icons/16/document-add.png"));
		propositionMenu.addTab(help, "Help", new ThemeResource("icons/16/help.png"));
		
		propositionMenu.addListener(this);
		addComponent(propositionMenu);
		
	}
	
	public void selectedTabChange(SelectedTabChangeEvent event) {
		TabSheet tabsheet = event.getTabSheet();
		Tab tab = tabsheet.getTab(tabsheet.getSelectedTab());
		if (tab != null) {
			getWindow().showNotification("Selected tab: " + tab.getCaption());
		}
	}
	
	public void openPropositionTab(Proposition p){
		if(false){
			//TODO: If exists, setselected
			//categoryMenu.setSelectedTab(newTab.getComponent());
		}else{
			PropositionTab tabContents = new PropositionTab(p, app);
			Tab newTab = propositionMenu.addTab(tabContents, p.getTitle());
		    newTab.setClosable(true);
		    newTab.setIcon(propositionIcon);
		    propositionMenu.setSelectedTab(newTab.getComponent());
		}
	}
	/**
	 * @return the list
	 */
	public VerticalLayout showPropositionList() {
		propositionMenu.setSelectedTab(list);
		return list;
	}

	/**
	 * @return the add
	 */
	public VerticalLayout showAddProposition() {
		propositionMenu.setSelectedTab(add);
		return add;
	}

	/**
	 * @return the view
	 */
	public VerticalLayout showProposition() {
		propositionMenu.setSelectedTab(view);
		return view;
	}

	/**
	 * @return the help
	 */
	public VerticalLayout showHelp() {
		propositionMenu.setSelectedTab(help);
		return help;
	}
	
	private AbsoluteLayout addCategoryLayout;
	private RichTextArea richTextArea_1;
	private NativeSelect languageSelect;
	private Label categoryLabel;
	private Button button_1;
	private ComboBox categoryComboBox;
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		addCategoryLayout = new AbsoluteLayout();
		addCategoryLayout.setImmediate(false);
		addCategoryLayout.setWidth("100%");
		addCategoryLayout.setHeight("100%");
		addCategoryLayout.setMargin(false);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// categoryComboBox
		categoryComboBox = new ComboBox();
		categoryComboBox.setImmediate(false);
		categoryComboBox.setDescription("Category name");
		categoryComboBox.setWidth("-1px");
		categoryComboBox.setHeight("-1px");
		addCategoryLayout.addComponent(categoryComboBox, "top:80.0px;left:40.0px;");
		
		// button_1
		button_1 = new Button();
		button_1.setCaption("Button");
		button_1.setImmediate(true);
		button_1.setWidth("-1px");
		button_1.setHeight("-1px");
		addCategoryLayout.addComponent(button_1, "top:340.0px;left:40.0px;");
		
		// categoryLabel
		categoryLabel = new Label();
		categoryLabel.setImmediate(false);
		categoryLabel.setWidth("-1px");
		categoryLabel.setHeight("-1px");
		categoryLabel.setValue("New category");
		addCategoryLayout.addComponent(categoryLabel, "top:42.0px;left:40.0px;");
		
		// languageSelect
		languageSelect = new NativeSelect();
		languageSelect.setImmediate(false);
		languageSelect.setDescription("Language");
		languageSelect.setWidth("-1px");
		languageSelect.setHeight("-1px");
		addCategoryLayout.addComponent(languageSelect, "top:78.0px;left:236.0px;");
		
		// richTextArea_1
		richTextArea_1 = new RichTextArea();
		richTextArea_1.setImmediate(false);
		richTextArea_1.setWidth("-1px");
		richTextArea_1.setHeight("-1px");
		addCategoryLayout.addComponent(richTextArea_1, "top:120.0px;left:40.0px;");
		
		return addCategoryLayout;
	}
}