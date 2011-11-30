package com.opendemocracy.voting;

import com.vaadin.Application;
import com.opendemocracy.voting.data.*;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.opendemocracy.voting.ui.*;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class VotingApplication extends Application implements
ClickListener, ValueChangeListener, ItemClickListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -687661872076562571L;

	
	//Toolbar components
	private Button btnProposition = new Button("Propositions");
	private Button btnExperts = new Button("Experts");
	private Button btnCategories = new Button("Categories");
	private Button btnGroups = new Button("Groups");
	private Button btnProfile = new Button("My profile");
	
	//Data sources
	private CategoryContainer categoryData;
	private ExpertContainer expertData;
	private OptionContainer optionData;
	private PropositionContainer propositionData = PropositionContainer.createWithTestData(); 
	private RepresentationContainer representationData;
	private UserContainer userData;
	private VoteContainer voteData;
	
	//Proposition components
	private PropositionList propositionList = null;
	private PropositionForm propositionForm = null;
	private PropositionMainView propositionMainView = null;
	
	
	private PropositionListView PropositionListView = null;
	
	private VerticalLayout layout = null;
	
	@Override
	public void init() {
		buildMainLayout();
	}
	
	private void buildMainLayout() {
		setMainWindow(new Window("OpenDemocracy Propositions"));
		setTheme("opendemocracy");
		
		//Main VerticalLayout t(toolbar | content)
		layout = new VerticalLayout();
		layout.setSizeFull();
		layout.addComponent(createToolbar());
		layout.addComponent(getPropositionMainView());
		layout.setExpandRatio(propositionMainView, 1.0f);
		getMainWindow().setContent(layout);
	}
	

	//Proposition main view (tabbed)
	private PropositionMainView getPropositionMainView() {
		if (propositionMainView == null) {
			propositionMainView = new PropositionMainView(this);
		}
		return propositionMainView;
	}	

	//public?!
	public PropositionList getPropositionList() {
		if (propositionList == null) {
			propositionList = new PropositionList(this);
		}
		return propositionList;
	}	
	
	//Deprecated
	private PropositionListView getPropositionListView() {
		if (PropositionListView == null) {
			propositionList = new PropositionList(this);
			propositionForm = new PropositionForm(this);
			PropositionListView = new PropositionListView(propositionList, propositionForm);
		}
		return PropositionListView;
	}
	
	private void setMainComponent(Component c) {
		layout.addComponent(c, 1);
	}
	
	private HorizontalLayout createToolbar() {
		HorizontalLayout lo = new HorizontalLayout();
		lo.addComponent(btnProposition);
		lo.addComponent(btnExperts);
		lo.addComponent(btnCategories);
		lo.addComponent(btnGroups);
		lo.addComponent(btnProfile);
		
		btnProposition.setIcon(new ThemeResource("icons/32/propositions.png"));
		btnExperts.setIcon(new ThemeResource("icons/32/experts.png"));
		btnCategories.setIcon(new ThemeResource("icons/32/folder.png"));
		btnGroups.setIcon(new ThemeResource("icons/32/users.png"));
		btnProfile.setIcon(new ThemeResource("icons/32/user.png"));
		
		btnProposition.addListener((ClickListener) this);
		

		btnExperts.addListener(new Button.ClickListener() {
	                public void buttonClick(ClickEvent event) {
	            	    getMainWindow().addWindow(new ModalWindow("TEST"));
	                }
	    });
		
		
		
		lo.setMargin(true);
		lo.setSpacing(true);
		lo.setStyleName("toolbar");
		lo.setWidth("100%");

		Embedded em = new Embedded("", new ThemeResource("images/logo.png"));
		lo.addComponent(em);
		lo.setComponentAlignment(em, Alignment.MIDDLE_RIGHT);
		lo.setExpandRatio(em, 1);
		
		return lo;
	}
	
	private void addCategory(){
		
	}
	private void addExpert(){
		
	}
	private void addOption(){
		
	}
	private void addProposition(){
		showListView();
		propositionForm.addProposition();
	}
	private void addRepresentation(){
		
	}
	/*private void addUser(){
		
	}*/
	private void addVote(){
	
	}
	private void showListView() {
		setMainComponent(getPropositionListView());
	}
	public PropositionContainer getPropositionData() {
		return propositionData;
	}

	public void itemClick(ItemClickEvent event) {
		
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

	public void buttonClick(ClickEvent event) {
		final Button source = event.getButton();
		if (source == btnProposition) {
			addProposition();
		}
	}

}
