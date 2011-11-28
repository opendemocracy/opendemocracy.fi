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
	private Button newProposition = new Button("Add proposition");
	
	//Data sources
	private CategoryContainer categoryData;
	private ExpertContainer expertData;
	private OptionContainer optionData;
	private PropositionContainer propositionData = PropositionContainer.createWithTestData(); 
	private RepresentationContainer representationData;
	private UserContainer userData;
	private VoteContainer voteData;
	

	private PropositionList propositionList = null;
	private PropositionForm propositionForm = null;
	private ListView listView = null;
	private HorizontalSplitPanel horizontalSplit = new HorizontalSplitPanel();
	@Override
	public void init() {
		buildMainLayout();
		setMainComponent(getListView());
	}
	
	private void buildMainLayout() {
		setMainWindow(new Window("OpenDemocracy Propositions"));
		
		setTheme("contacts");
		
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();

		layout.addComponent(createToolbar());
		layout.addComponent(horizontalSplit);
		layout.setExpandRatio(horizontalSplit, 1);
				
		getMainWindow().setContent(layout);
		horizontalSplit.setFirstComponent(null);
		
		/*
		 * 
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();

		layout.addComponent(createToolbar());
		layout.addComponent(horizontalSplit);
		layout.setExpandRatio(horizontalSplit, 1);

		horizontalSplit.setSplitPosition(200, SplitPanel.UNITS_PIXELS);
		horizontalSplit.setFirstComponent(tree);

		getMainWindow().setContent(layout);
		 * */
	}
	
	private ListView getListView() {
		if (listView == null) {
			propositionList = new PropositionList(this);
			propositionForm = new PropositionForm(this);
			listView = new ListView(propositionList, propositionForm);
		}
		return listView;
	}
	private void setMainComponent(Component c) {
		horizontalSplit.setFirstComponent(c);
	}
	
	private HorizontalLayout createToolbar() {
		HorizontalLayout lo = new HorizontalLayout();
		lo.addComponent(newProposition);

		newProposition.addListener((ClickListener) this);

		newProposition.setIcon(new ThemeResource("icons/32/document-add.png"));

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
		setMainComponent(getListView());
	}
	public PropositionContainer getDataSource() {
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
		if (source == newProposition) {
			addProposition();
		}
	}

}
