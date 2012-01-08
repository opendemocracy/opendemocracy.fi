package com.opendemocracy.voting.ui;

import com.opendemocracy.voting.VotingApplication;
import com.opendemocracy.voting.data.Category;
import com.opendemocracy.voting.data.CategoryContainer;
import com.opendemocracy.voting.data.Expert;
import com.opendemocracy.voting.data.ExpertContainer;
import com.opendemocracy.voting.data.Proposition;
import com.opendemocracy.voting.data.PropositionContainer;
import com.vaadin.ui.Table;

@SuppressWarnings("serial")
public class ExpertList extends Table {
	private final VotingApplication vApp;
	public ExpertList(VotingApplication app) {
		this.vApp = app;
		setSizeFull();
		setContainerDataSource(app.getExpertData());

		setVisibleColumns(ExpertContainer.NATURAL_COL_ORDER);
		setColumnHeaders(ExpertContainer.COL_HEADERS_ENGLISH);

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
				Expert e = (Expert) event.getProperty().getValue();
				//vApp.getMainWindow().addWindow(new PropositionVote(vApp,p));
			}
		});

	}

}