package com.opendemocracy.voting.ui;

import com.opendemocracy.voting.VotingApplication;
import com.opendemocracy.voting.data.Proposition;
import com.opendemocracy.voting.data.PropositionContainer;
import com.vaadin.event.Action;
import com.vaadin.ui.Table;

@SuppressWarnings("serial")
public class PropositionList extends Table {
	private final VotingApplication vApp;
	

    //Right-click actions
    static final Action ACTION_OPEN = new Action("Open");
    static final Action ACTION_VIEW_RESULTS = new Action("Results");
    static final Action ACTION_VIEW_EXPERT_VOTES = new Action("Expert votes");
    
    static final Action[] ACTIONS_MENU = new Action[] { ACTION_OPEN, ACTION_VIEW_RESULTS,
    	ACTION_VIEW_EXPERT_VOTES };
	

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
		/*
		addListener(new ValueChangeListener(){
			public void valueChange(
					com.vaadin.data.Property.ValueChangeEvent event) {
				Proposition p = (Proposition) event.getProperty().getValue();
				vApp.getMainWindow().addWindow(new PropositionVote(vApp,p));
			}
		});
		*/
        // Menu actions
        addActionHandler(new Action.Handler() {
            public Action[] getActions(Object target, Object sender) {
                return ACTIONS_MENU;
            }

            public void handleAction(Action action, Object sender, Object target) {
                if (ACTION_OPEN == action) {
    				Proposition p = (Proposition) target;
    				vApp.getMainWindow().addWindow(new PropositionVote(vApp,p));
                } else if (ACTION_VIEW_RESULTS == action) {
                	getWindow().showNotification("TODO: Results");
                } else if (ACTION_VIEW_EXPERT_VOTES == action) {
                	getWindow().showNotification("TODO: View expert votes");
                }

            }

        });

	}

}