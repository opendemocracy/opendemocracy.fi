package com.opendemocracy.voting.ui;


import java.util.Collection;

import com.opendemocracy.voting.data.Option;
import com.opendemocracy.voting.data.Proposition;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/*Create a view for provided proposition*/
public class PropositionDetailsView {

	private Proposition proposition;
	private VerticalLayout layout;
	
	public PropositionDetailsView(Proposition p){
		proposition = p;
		VerticalLayout layout = new VerticalLayout();
		Collection<Option> options = proposition.getOptions();
		layout.addComponent(new Label(proposition.getDescription()));
		layout.setSizeFull();
	}
	
	public VerticalLayout getLayout(){
		return layout;
	}
}
