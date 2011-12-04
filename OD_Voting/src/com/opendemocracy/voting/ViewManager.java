package com.opendemocracy.voting;

import com.opendemocracy.voting.ui.MainView;
import com.opendemocracy.voting.ui.PropositionForm;
import com.opendemocracy.voting.ui.PropositionList;
import com.opendemocracy.voting.ui.PropositionMainView;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalSplitPanel;

public class ViewManager {
	private PropositionList propositionList;
	private PropositionForm propositionForm;
	private PropositionMainView propositionMainView;
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

	public void showPropositionForm() {
		getLayout().setSecondComponent(getPropositionForm());
	}

	public void showListView() {
		getLayout().setSecondComponent(getPropositionMainView());
	}

	public void showExperts() {
		getLayout().setSecondComponent(new Label("Experts"));
	}

	public void buttonClick(ClickEvent event) {
		mainView.buttonClick(event);
	}

	private PropositionForm getPropositionForm() {
		if (propositionForm == null) {
			propositionForm = new PropositionForm(app);
		}
		return propositionForm;
	}

	public PropositionList getPropositionList() {
		if (propositionList == null) {
			propositionList = new PropositionList(app);
		}
		return propositionList;
	}
	
	private PropositionMainView getPropositionMainView() {
		if (propositionMainView == null) {
			propositionMainView = new PropositionMainView(app);
		}
		return propositionMainView;
	}

	private MainView getMainView() {
		if (mainView == null) {
			mainView = new MainView(app);
		}
		return mainView;
	}

	public VerticalSplitPanel getLayout() {
		if (layout == null) {
			layout = new VerticalSplitPanel();
			layout.setLocked(true);
			layout.setFirstComponent(getMainView().createToolbar());
			layout.setSplitPosition(
					(int) Math.ceil(layout.getFirstComponent().getHeight()),
					layout.getFirstComponent().getHeightUnits());
			layout.setSecondComponent(getPropositionMainView());
			// layout.setExpandRatio(propositionMainView, 1.0f);
		}
		layout.setSplitPosition((int) Math.ceil(layout.getFirstComponent()
				.getHeight()), layout.getFirstComponent().getHeightUnits());
		return layout;
	}

}