package com.opendemocracy.voting.ui;

import com.opendemocracy.voting.VotingApplication;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class PropositionMainView extends VerticalLayout implements
		TabSheet.SelectedTabChangeListener {

	private TabSheet propositionMenu;
	private TabSheet viewTabs;
	private VotingApplication app;
	private PropositionAddForm form;
	private VerticalLayout list;
	private VerticalLayout add;
	private VerticalLayout view;
	private VerticalLayout help;

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
		add.setSizeFull();

		// Propositions tab
		view = new VerticalLayout();
		view.setMargin(true);
		view.addComponent(new Label("View proposition"));
		view.setSizeFull();
		propositionMenu = new TabSheet();
		viewTabs = new TabSheet();
		viewTabs.addTab(list, "List propositions", new ThemeResource(
				"icons/16/propositions.png"));
		viewTabs.setSizeFull();
		
		// Help
		help = new VerticalLayout();
		help.setMargin(true);
		help.addComponent(new Label("Help"));
		help.setSizeFull();

		
		
		// Add main tabs
		propositionMenu.setSizeFull();
		propositionMenu.addTab(viewTabs, "Propositions", new ThemeResource(
				"icons/16/propositions.png"));
		propositionMenu.addTab(add, "Add proposition", new ThemeResource(
				"icons/16/document-add.png"));
		propositionMenu.addTab(viewTabs, "View", new ThemeResource("icons/16/help.png"));
		propositionMenu.addTab(help, "Help", new ThemeResource("icons/16/help.png"));
		
		propositionMenu.addListener(this);
		addComponent(propositionMenu);
		
		propositionMenu.addTab(viewTabs);
	}
	
	public void selectedTabChange(SelectedTabChangeEvent event) {
		TabSheet tabsheet = event.getTabSheet();
		Tab tab = tabsheet.getTab(tabsheet.getSelectedTab());
		if (tab != null) {
			getWindow().showNotification("Selected tab: " + tab.getCaption());
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
}