package com.opendemocracy.voting.ui;

import com.opendemocracy.voting.VotingApplication;
import com.opendemocracy.voting.data.Proposition;
import com.opendemocracy.voting.data.PropositionContainer;
import com.vaadin.ui.Table;

@SuppressWarnings("serial")
public class PropositionList extends Table {
	private final VotingApplication vApp;
	public PropositionList(VotingApplication app) {
		this.vApp = app;
		setSizeFull();
		setContainerDataSource(app.getPropositionData());

		setVisibleColumns(PropositionContainer.NATURAL_COL_ORDER);
		setColumnHeaders(PropositionContainer.COL_HEADERS_ENGLISH);

		setColumnCollapsingAllowed(true);
		setColumnReorderingAllowed(true);

		/*
		 * Make table selectable, react immediatedly to user events, and pass
		 * events to the controller (our main application)
		 */
		setSelectable(true);
		setImmediate(true);
		//addListener((ValueChangeListener) app);
		setNullSelectionAllowed(true);
		
		//Modal propositionview (Temporary)
		addListener(new ValueChangeListener(){
			public void valueChange(
					com.vaadin.data.Property.ValueChangeEvent event) {
				Proposition p = (Proposition) event.getProperty().getValue();
				vApp.getMainWindow().addWindow(new ModalWindow(vApp,p));
			}
		});

	}

}