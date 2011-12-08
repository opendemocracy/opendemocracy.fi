package com.opendemocracy.voting.ui;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.layout.client.Layout;
import com.opendemocracy.voting.VotingApplication;
import com.opendemocracy.voting.components.VoteOptionSlider;
import com.opendemocracy.voting.data.Option;
import com.opendemocracy.voting.data.Proposition;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
//Vote window
//TODO: Laziness, save vote
public class PropositionVote extends Window implements ValueChangeListener{
	
	public PropositionVote(final VotingApplication app, final Proposition p){
		super(p.getTitle());

		Button vote = new Button("Vote", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				app.getMainWindow().showNotification("TODO: Register vote");
				close();
			}
		});
		Button cancel = new Button("Close", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				close();
			}
		});
		
		HorizontalLayout footer = new HorizontalLayout();
		footer.addComponent(vote);
		footer.addComponent(cancel);
		
		this.setModal(true);
		
		VerticalLayout vLayout = (VerticalLayout) this.getContent();
		Label pDesc = new Label(p.getDescription());
		pDesc.setContentMode(Label.CONTENT_XHTML);
		pDesc.setWidth("100%");
		vLayout.addComponent(pDesc);
		ArrayList pOptions = new ArrayList<Option>(p.getOptions());
		int i = 1;
		for (Iterator<Option> it = (Iterator<Option>) pOptions.iterator(); it
				.hasNext();) {
			Option o = it.next();
			Label title = new Label("<b>"+o.getTitle()+"</b>");
			title.setContentMode(Label.CONTENT_XHTML);
			vLayout.addComponent(title);
			VoteOptionSlider oS = new VoteOptionSlider();
			vLayout.addComponent(oS);
			vLayout.addComponent(new Label(o.getDescription()));
			oS.addListener(this);
		}
		vLayout.setSizeFull();
		vLayout.setMargin(true);
		vLayout.setSpacing(false);
		vLayout.addComponent(footer);
		vLayout.setComponentAlignment(footer, Alignment.BOTTOM_LEFT);

		setWidth("440px");
	}

	public void close(ClickEvent event) {
		(this.getParent()).removeWindow(this);
	}

	public void valueChange(ValueChangeEvent event) {
		Property property = event.getProperty();
		VoteOptionSlider oS = (VoteOptionSlider) property;
		oS.setMaxAllowed((Double)oS.getValue());
		// TODO Auto-generated method stub
		
	}

}