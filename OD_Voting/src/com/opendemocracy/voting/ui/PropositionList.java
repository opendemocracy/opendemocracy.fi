package com.opendemocracy.voting.ui;

import com.opendemocracy.voting.VotingApplication;
import com.opendemocracy.voting.data.PropositionContainer;
import com.vaadin.ui.Table;

@SuppressWarnings("serial")
public class PropositionList extends Table {
	public PropositionList(VotingApplication app) {
		setSizeFull();
		setContainerDataSource(app.getDataSource());

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
		addListener((ValueChangeListener) app);
		setNullSelectionAllowed(true);

	}

}