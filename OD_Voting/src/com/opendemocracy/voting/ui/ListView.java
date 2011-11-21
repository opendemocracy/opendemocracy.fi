package com.opendemocracy.voting.ui;

import com.vaadin.ui.HorizontalSplitPanel;

@SuppressWarnings("serial")
public class ListView extends HorizontalSplitPanel {
	public ListView(PropositionList propositionList, PropositionForm propositionForm) {
		addStyleName("view");
		setFirstComponent(propositionList);
		setSecondComponent(propositionForm);
		setSplitPosition(40);
	}
}