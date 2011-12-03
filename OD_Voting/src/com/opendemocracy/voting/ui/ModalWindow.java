/*MODAL TEST*/

package com.opendemocracy.voting.ui;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class ModalWindow extends Window {

	public ModalWindow(String title, String label) {
		super(title);
		this.setModal(true);

		VerticalLayout layout = (VerticalLayout) this.getContent();
		layout.setMargin(true);
		layout.setSpacing(true);

		Label message = new Label(label);
		this.addComponent(message);

		Button close = new Button("Close", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				close();
			}
		});

		layout.addComponent(close);
		layout.setComponentAlignment(close, Alignment.TOP_RIGHT);

	}

	public void close(ClickEvent event) {
		(this.getParent()).removeWindow(this);
	}

}