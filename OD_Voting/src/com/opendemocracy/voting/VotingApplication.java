package com.opendemocracy.voting;

import com.opendemocracy.voting.data.CategoryContainer;
import com.opendemocracy.voting.data.ExpertContainer;
import com.opendemocracy.voting.data.OptionContainer;
import com.opendemocracy.voting.data.PropositionContainer;
import com.opendemocracy.voting.data.RepresentationContainer;
import com.opendemocracy.voting.data.UserContainer;
import com.opendemocracy.voting.data.VoteContainer;
import com.vaadin.Application;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

public class VotingApplication extends Application implements ClickListener,
		ValueChangeListener, ItemClickListener {
	private static final long serialVersionUID = -687661872076562571L;

	// Data sources
	private RepresentationContainer representationData = RepresentationContainer
			.createWithTestData();
	private CategoryContainer categoryData = CategoryContainer
			.createWithTestData();
	private ExpertContainer expertData = ExpertContainer.createWithTestData();
	private OptionContainer optionData = OptionContainer.createWithTestData();
	private UserContainer userData = UserContainer.createWithTestData();
	private VoteContainer voteData = VoteContainer.createWithTestData();
	private PropositionContainer propositionData = PropositionContainer
			.createWithTestData();

	private ViewManager viewManager;

	@Override
	public void init() {
		buildMainLayout();
	}

	private void buildMainLayout() {
		setMainWindow(new Window("OpenDemocracy Propositions"));
		setTheme("opendemocracy");
		viewManager = new ViewManager(this);

		// Main VerticalLayout t(toolbar | content)
		getMainWindow().setContent(getViewManager().getLayout());
	}

	public void setMainComponent(Component c) {
		getViewManager().getLayout().setSecondComponent(c);
	}

	public ViewManager getViewManager() {
		if (viewManager == null) {
			viewManager = new ViewManager(this);
		}
		return viewManager;
	}

	public void addCategory() {

	}

	public void addExpert() {

	}

	public void addOption() {

	}

	public void addProposition() {

	}

	private void addRepresentation() {

	}

	private void addUser() {

	}

	private void addVote() {

	}

	public PropositionContainer getPropositionData() {
		return propositionData;
	}

	public void itemClick(ItemClickEvent event) {

	}

	public void valueChange(ValueChangeEvent event) {
		getViewManager().valueChange(event);
	}

	public void buttonClick(ClickEvent event) {
		getViewManager().buttonClick(event);
	}

}
