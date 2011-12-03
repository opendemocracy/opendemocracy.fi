package com.opendemocracy.voting;

import com.opendemocracy.voting.ui.MainView;
import com.opendemocracy.voting.ui.PropositionForm;
import com.opendemocracy.voting.ui.PropositionList;
import com.opendemocracy.voting.ui.PropositionListView;
import com.opendemocracy.voting.ui.PropositionMainView;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.SplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalSplitPanel;

public class ViewManager {
	private PropositionList propositionList;
	private PropositionForm propositionForm;
	private PropositionMainView propositionMainView;
	private PropositionListView PropositionListView;
	private VerticalSplitPanel layout;
	private VotingApplication app;
	private MainView mainView;

	public ViewManager(VotingApplication app) {
		this.app = app;
	}

	public void valueChange(ValueChangeEvent event) {
		Property property = event.getProperty();
		if (property == propositionList) {
			Item item = propositionList.getItem(propositionList.getValue());
			if (item != propositionForm.getItemDataSource()) {
				propositionForm.setItemDataSource(item);
			}
		}
	}

	public void showListView() {
		app.setMainComponent(getPropositionMainView());
	}
	public void showExperts() {
		app.setMainComponent(new Label("Experts"));
	}
	
	public void buttonClick(ClickEvent event) {
		final Button source = event.getButton();
		mainView.buttonClick(event);
	}

	public PropositionForm getPropositionForm() {
		if (propositionForm == null) {
			propositionForm = new PropositionForm(app);
		}
		return propositionForm;
	}

	// Proposition main view (tabbed)
	private PropositionMainView getPropositionMainView() {
		if (propositionMainView == null) {
			propositionMainView = new PropositionMainView(app);
		}
		return propositionMainView;
	}

	// public?!
	public PropositionList getPropositionList() {
		if (propositionList == null) {
			propositionList = new PropositionList(app);
		}
		return propositionList;
	}

	// public?!
	public MainView getMainView() {
		if (mainView == null) {
			mainView = new MainView(app);
		}
		return mainView;
	}

	// Deprecated
	public PropositionListView getPropositionListView() {
		if (PropositionListView == null) {
			PropositionListView = new PropositionListView(getPropositionList(),
					getPropositionForm());
		}
		return PropositionListView;
	}

	public VerticalSplitPanel getLayout() {
		if (layout == null) {
			layout = new VerticalSplitPanel();
			layout.setLocked(true);
			layout.setFirstComponent(getMainView().createToolbar());
			layout.setSplitPosition((int)Math.ceil(layout.getFirstComponent().getHeight()), layout.getFirstComponent().getHeightUnits());
			layout.setSecondComponent(getPropositionMainView());
			//layout.setExpandRatio(propositionMainView, 1.0f);
		}
		layout.setSplitPosition((int)Math.ceil(layout.getFirstComponent().getHeight()), layout.getFirstComponent().getHeightUnits());
		return layout;
	}

}