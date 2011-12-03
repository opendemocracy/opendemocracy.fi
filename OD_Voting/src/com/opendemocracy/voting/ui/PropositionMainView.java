package com.opendemocracy.voting.ui;

import com.opendemocracy.voting.VotingApplication;
import com.vaadin.terminal.ThemeResource;
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

	public PropositionMainView(VotingApplication app) {
		this.app = app;
		this.setSizeFull();
		// List propositions
		VerticalLayout l1 = new VerticalLayout();
		l1.setMargin(false);
		l1.addComponent(app.getViewManager().getPropositionList());
		l1.setSizeFull();

		// Add proposition
		VerticalLayout l2 = new VerticalLayout();
		l2.setMargin(true);
		l2.addComponent(new Label("Add proposition."));
		l2.setSizeFull();

		// Help
		VerticalLayout l3 = new VerticalLayout();
		l3.setMargin(true);
		l3.addComponent(new Label("WTF?"));
		l3.setSizeFull();

		t = new TabSheet();
		t.setSizeFull();
		t.addTab(l1, "List propositions", new ThemeResource(
				"icons/16/propositions.png"));
		t.addTab(l2, "Add proposition", new ThemeResource(
				"icons/16/document-add.png"));
		t.addTab(l3, "Help", new ThemeResource("icons/16/help.png"));
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
}