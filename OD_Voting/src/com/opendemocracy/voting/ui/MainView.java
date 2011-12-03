package com.opendemocracy.voting.ui;

import java.util.ArrayList;

import com.opendemocracy.voting.VotingApplication;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class MainView {
	// Toolbar components
	private Button btnProposition = new Button("Propositions");
	private Button btnExperts = new Button("Experts");
	private Button btnCategories = new Button("Categories");
	private Button btnGroups = new Button("Groups");
	private Button btnProfile = new Button("My profile");
	private ArrayList<Button> buttons = new ArrayList<Button>();
	private VotingApplication app;

	public MainView(VotingApplication app) {
		super();
		this.app = app;
	}

	public HorizontalLayout createToolbar() {
		HorizontalLayout lo = new HorizontalLayout();

		buttons.add(btnProposition);
		buttons.add(btnCategories);
		buttons.add(btnExperts);
		buttons.add(btnGroups);
		buttons.add(btnProfile);
		
		for (Button b : buttons) {
			lo.addComponent(b);
			b.addListener((ClickListener)app);
		}

		ThemeResource iconPropositions = new ThemeResource("icons/32/propositions.png");
		btnProposition.setIcon(iconPropositions);
		btnExperts.setIcon(new ThemeResource("icons/32/experts.png"));
		btnCategories.setIcon(new ThemeResource("icons/32/folder.png"));
		btnGroups.setIcon(new ThemeResource("icons/32/users.png"));
		btnProfile.setIcon(new ThemeResource("icons/32/user.png"));

		lo.setMargin(true);
		lo.setSpacing(true);
		lo.setStyleName("toolbar");
		lo.setWidth("100%");
		lo.setHeight(90, Sizeable.UNITS_PIXELS);

		Embedded em = new Embedded("", new ThemeResource("images/logo.png"));
		lo.addComponent(em);
		
		lo.setComponentAlignment(em, Alignment.MIDDLE_RIGHT);
		lo.setExpandRatio(em, 1);

		return lo;
	}

	public void buttonClick(ClickEvent event) {
		final Button source = event.getButton();
		if (source == btnProposition) {
			app.getViewManager().showListView();
		}else if (source == btnExperts){
			app.getViewManager().showExperts();
		}else{
			app.getMainWindow().addWindow(new ModalWindow("Not implemented yet",source.getCaption()));			
		}
	}

}
