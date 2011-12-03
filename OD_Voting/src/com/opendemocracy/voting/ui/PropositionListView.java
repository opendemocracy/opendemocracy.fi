package com.opendemocracy.voting.ui;

import com.vaadin.ui.HorizontalSplitPanel;

@SuppressWarnings("serial")
public class PropositionListView extends HorizontalSplitPanel {
	public PropositionListView(PropositionList propositionList,
			PropositionForm propositionForm) {
		// addStyleName("view");
		setFirstComponent(propositionList);
		setSecondComponent(propositionForm);
		setSplitPosition(100);
	}
}