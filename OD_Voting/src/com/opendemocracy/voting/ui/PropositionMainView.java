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

	private TabSheet t;
	private VotingApplication app;
	private PropositionForm form;
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

		form = new PropositionForm(app);
		add.addComponent(form);
		form.addProposition();
		add.setSizeFull();

		// View proposition
		view = new VerticalLayout();
		view.setMargin(true);
		view.addComponent(new Label("View proposition"));
		view.setSizeFull();
		
		// Help
		help = new VerticalLayout();
		help.setMargin(true);
		help.addComponent(new Label("Help"));
		help.setSizeFull();

		t = new TabSheet();
		t.setSizeFull();
		t.addTab(list, "List propositions", new ThemeResource(
				"icons/16/propositions.png"));
		t.addTab(add, "Add proposition", new ThemeResource(
				"icons/16/document-add.png"));
		t.addTab(view, "View", new ThemeResource("icons/16/help.png"));
		t.addTab(help, "Help", new ThemeResource("icons/16/help.png"));
		t.addListener(this);
		addComponent(t);
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
		t.setSelectedTab(list);
		return list;
	}

	/**
	 * @return the add
	 */
	public VerticalLayout showAddProposition() {
		t.setSelectedTab(add);
		return add;
	}

	/**
	 * @return the view
	 */
	public VerticalLayout showProposition() {
		t.setSelectedTab(view);
		return view;
	}

	/**
	 * @return the help
	 */
	public VerticalLayout showHelp() {
		t.setSelectedTab(help);
		return help;
	}
}