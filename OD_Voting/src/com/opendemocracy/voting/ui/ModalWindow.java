/*MODAL TEST*/

package com.opendemocracy.voting.ui;

import java.util.ArrayList;
import java.util.Iterator;

import com.opendemocracy.voting.VotingApplication;
import com.opendemocracy.voting.data.Option;
import com.opendemocracy.voting.data.Proposition;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class ModalWindow extends Window {
	
	public ModalWindow(String title, String label) {
		super(title);
		this.setModal(true);
		
		Button close = new Button("Close", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				close();
			}
		});
		
		VerticalLayout layout = (VerticalLayout) this.getContent();
		layout.setMargin(true);
		layout.setSpacing(true);

		Label message = new Label(label);
		this.addComponent(message);



		layout.addComponent(close);
		layout.setComponentAlignment(close, Alignment.TOP_RIGHT);

	}
	
	public ModalWindow(final VotingApplication app, final Proposition p){
		super(p.getTitle());
		
		Button save = new Button("Save", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				app.addProposition(p);
				close();
			}
		});
		Button cancel = new Button("Back", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				close();
			}
		});
		HorizontalLayout footer = new HorizontalLayout();
		footer.addComponent(save);
		footer.addComponent(cancel);
		this.setModal(true);
		VerticalLayout vLayout = (VerticalLayout) this.getContent();
		Label pDesc = new Label(p.getDescription());
		pDesc.setContentMode(Label.CONTENT_XHTML);
		pDesc.setHeight("200px");
		pDesc.setWidth("100%");
		vLayout.addComponent(pDesc);
		ArrayList pOptions = new ArrayList<Option>(p.getOptions());
		vLayout.addComponent(new Label("Options:"));
		int i = 1;
		for (Iterator<Option> it = (Iterator<Option>) pOptions.iterator(); it
				.hasNext();) {
			Option o = it.next();
			Label oTitle = new Label("<b>"+ (i++) + ":" +o.getTitle()+ "</b>");
			oTitle.setContentMode(Label.CONTENT_XHTML);
			vLayout.addComponent(oTitle);
			vLayout.addComponent(new Label(o.getDescription()));
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

}